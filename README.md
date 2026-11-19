# Dymith Browser — Home UI Pass 2

Bản thay thế hoàn toàn trang chủ cũ bằng giao diện tối giản đen/trắng theo mẫu người dùng cung cấp. Ưu tiên hiển thị nhẹ, tương tác thật và không phát sinh công việc nền liên tục.

## Trang chủ mới

- Đồng hồ lớn, lời chào theo giờ và ngày tháng tiếng Việt.
- Lịch sử tìm kiếm gần đây lưu cục bộ bằng `localStorage`.
- Lưới website 4 cột: YouTube, Facebook, Zalo, Shopee, VnExpress, Gmail, GitHub và Wikipedia.
- Favicon tải bất đồng bộ/lazy từ Google Favicon endpoint; có chữ viết tắt làm fallback.
- Thanh tìm kiếm/địa chỉ cố định ở đáy, bảng gợi ý, bốn công cụ tìm kiếm (Google, DuckDuckGo, Bing, Cốc Cốc), chế độ sáng/tối/tự động và xóa lịch sử.
- Không còn header thương hiệu cũ, thẻ tiện ích, news feed, hero, gradient, blur hay bottom navigation trên trang chủ.
- Biểu tượng thao tác trong dock và gợi ý được vẽ bằng Canvas 2D tĩnh; không có `requestAnimationFrame`, polling liên tục hay animation theo từng frame.

## Tích hợp Android

| Thành phần | Vai trò |
|---|---|
| `StartPage.kt` | Toàn bộ HTML/CSS/JS của trang chủ mới |
| `MainActivity.kt` | Chuyển giữa dock HTML trên trang chủ và toolbar native khi duyệt website |
| `Core.kt` / `core/` | Phân giải URL và truy vấn bằng Rust, có fallback Kotlin tương ứng |
| `CanvasIconDrawable.kt` | Icon Canvas của toolbar native khi ở trang web |
| `Diagnostics.kt` | Báo cáo hiệu năng |

Cầu nối `WebMessageListener` chỉ cho phép origin `https://start.dymith.invalid`, chỉ nhận message từ main frame. Trang chủ gửi `navigate`, `open`, `theme`; Android kiểm tra scheme/host trước khi điều hướng. Không dùng `addJavascriptInterface`.

## Tối ưu đã thực hiện

1. Thay toàn bộ DOM/CSS trang chủ Pass 1 bằng cấu trúc đồng hồ + recent chips + lưới shortcut + dock.
2. Không tạo lại grid website mỗi khi người dùng gõ. Shortcuts và chip công cụ chỉ dựng một lần; danh sách gợi ý mới cập nhật theo nội dung input.
3. Dùng `DocumentFragment`/`replaceChildren`, `textContent` và delegated click listeners để giảm HTML string parsing và tránh chèn dữ liệu người dùng vào `innerHTML`.
4. Canvas icon chỉ vẽ khi được tạo hoặc khi theme đổi; không vẽ theo frame.
5. Đồng hồ chỉ cập nhật đúng ranh giới phút; timer dừng khi trang bị ẩn.
6. Favicon dùng `loading=lazy`, `decoding=async`, fallback chữ viết tắt.
7. Trang chủ dùng dock HTML riêng; toolbar native ẩn trên trang chủ và xuất hiện khi mở trang web, tránh hai thanh tìm kiếm chồng lấp.
8. Nền hệ thống status/navigation bar đồng bộ với theme sáng/tối.
9. Ẩn scrollbars và overscroll glow của WebView.
10. Xóa RSS loader không còn được dùng ở trang chủ mới.

## Build và kiểm thử

- Chạy GitHub Actions workflow hiện tại hoặc `ci/build.sh` trên runner có JDK, Android SDK/NDK, Rust và Cargo.
- Repo không kèm Gradle Wrapper; CI tải Gradle theo script.
- Bản này chưa được build APK hoặc benchmark trên HONOR MT6765 trong môi trường làm việc hiện tại.
- Sau khi cài, đo cùng kịch bản trên thiết bị thật: mở app nguội/nóng, cuộn trang chủ, bật bàn phím, đổi theme, mở shortcut, tìm kiếm theo domain/query, mở website nặng, quay về trang chủ. So sánh p50/p90/p99 và dropped frames bằng Perfetto FrameTimeline.

## Preview

- `preview/startpage.html`: mở bằng trình duyệt desktop/mobile để xem trang chủ ở chế độ độc lập. Cầu nối native sẽ không có trong preview; các nút mở website dùng điều hướng trình duyệt thông thường.
- `preview/home-ui-preview.png`: ảnh chụp viewport 412 × 915 CSS px (render ở DPR 2). Nếu môi trường không truy cập được Google Favicon endpoint, lối tắt hiển thị chữ viết tắt dự phòng.
