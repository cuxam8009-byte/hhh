package dev.dymith.browser

import android.net.Uri
import android.util.Log
import java.util.concurrent.atomic.AtomicLong

/**
 * Cầu nối sang lõi Rust (libdymith_core.so).
 * Nếu thư viện không nạp được, ứng dụng vẫn chạy với bản dự phòng bằng Kotlin
 * và màn hình chẩn đoán sẽ hiển thị lý do.
 */
object Core {
    private const val TAG = "DymithCore"
    private const val DEFAULT_SEARCH = "https://www.google.com/search?q=%s"

    @Volatile
    var loaded: Boolean = false
        private set

    @Volatile
    var loadError: String? = null
        private set

    @Volatile
    var loadNanos: Long = 0L
        private set

    // Số liệu đo đường nóng (mỗi request con đi qua JNI một lần).
    val requestsSeen = AtomicLong()
    val requestsBlocked = AtomicLong()
    val matchNanos = AtomicLong()

    fun load() {
        if (loaded) return
        val t0 = System.nanoTime()
        try {
            System.loadLibrary("dymith_core")
            loaded = true
        } catch (t: Throwable) {
            loadError = t.javaClass.simpleName + ": " + (t.message ?: "")
            Log.e(TAG, "Không nạp được lõi Rust", t)
        } finally {
            loadNanos = System.nanoTime() - t0
        }
    }

    fun version(): String {
        if (!loaded) return "CHƯA NẠP (" + (loadError ?: "?") + ")"
        return try {
            nativeVersion()
        } catch (t: Throwable) {
            "lỗi: " + (t.message ?: t.javaClass.simpleName)
        }
    }

    fun resolve(input: String, template: String = DEFAULT_SEARCH): String {
        if (loaded) {
            try {
                return nativeResolve(input, template)
            } catch (t: Throwable) {
                Log.e(TAG, "resolve lỗi", t)
            }
        }
        return fallbackResolve(input, template)
    }

    fun shouldBlock(url: String): Boolean {
        if (!loaded) return false
        val t0 = System.nanoTime()
        val blocked = try {
            nativeShouldBlock(url)
        } catch (t: Throwable) {
            false
        }
        matchNanos.addAndGet(System.nanoTime() - t0)
        requestsSeen.incrementAndGet()
        if (blocked) requestsBlocked.incrementAndGet()
        return blocked
    }

    private fun fallbackResolve(input: String, template: String): String {
        val s = input.trim()
        if (s.isEmpty()) return "about:blank"
        if (s.equals("about:blank", ignoreCase = true)) return "about:blank"
        if (s.startsWith("http://", true) || s.startsWith("https://", true)) return s

        // Giữ hành vi dự phòng tương đương Rust: tên miền trần cũng được mở như URL.
        if (!s.any { it.isWhitespace() || it.code < 0x20 || it.code == 0x7f }) {
            val end = s.indexOfAny(charArrayOf('/', '?', '#')).let { if (it < 0) s.length else it }
            val authority = s.substring(0, end)
            val rest = s.substring(end)
            if (authority.isNotEmpty() && !authority.contains('@')) {
                val host = when {
                    authority.startsWith('[') && authority.contains(']') -> authority.substringBefore(']') + ']'
                    authority.count { it == ':' } == 1 -> authority.substringBeforeLast(':')
                    else -> authority
                }
                val local = host.equals("localhost", true) ||
                    host.matches(Regex("(?:\\d{1,3}\\.){3}\\d{1,3}")) ||
                    host.startsWith('[') || listOf(".local", ".lan", ".internal", ".test", ".localhost").any { host.endsWith(it, true) }
                val labels = host.trimEnd('.').split('.')
                val validDomain = labels.size >= 2 && labels.all { label ->
                    label.isNotEmpty() && label.length <= 63 &&
                        label.first() != '-' && label.last() != '-' &&
                        label.all { it.isLetterOrDigit() || it == '-' || it == '_' }
                } && labels.last().length >= 2 &&
                    labels.last().lowercase() !in setOf("js", "ts", "jsx", "tsx", "json", "txt", "yml", "yaml", "toml", "html", "htm", "css", "png", "jpg", "jpeg", "gif", "svg", "pdf", "zip", "exe", "apk", "java", "kt", "php", "rb", "cpp", "hpp", "log", "lock")
                if (local || validDomain) return (if (local) "http://" else "https://") + authority + rest
            }
        }
        val safeTemplate = if (template.contains("%s") && template.startsWith("https://")) template
            else DEFAULT_SEARCH
        return safeTemplate.replace("%s", Uri.encode(s))
    }

    @JvmStatic
    external fun nativeVersion(): String

    @JvmStatic
    external fun nativeResolve(input: String, template: String): String

    @JvmStatic
    external fun nativeShouldBlock(url: String): Boolean
}
