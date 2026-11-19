# Dymith — Minimal Home UI / Performance Pass 2

## Thiết bị tham chiếu
HONOR WOD-LX2, MT6765, Android 14, WebView 157, 90 Hz, khoảng 3.7 GiB RAM.

## Mục tiêu
Thay hoàn toàn dashboard trước bằng giao diện tham chiếu: đồng hồ lớn, lời chào, ngày, lịch sử tìm kiếm gần đây, lưới shortcut dạng vòm và dock tìm kiếm cố định ở đáy. Không thêm widget nặng hoặc nội dung tin tức vào trang chủ.

## Thay đổi
- Loại bỏ topbar/brand cũ, welcome card, quick tools, RSS feed, news thumbnails, footer và các thành phần trang chủ Pass 1.
- Tạo lại StartPage theo bảng màu trắng/đen; tự chuyển theo hệ thống hoặc chọn sáng/tối thủ công.
- Icon thao tác trên dock và suggestion dùng Canvas 2D, vẽ một lần; không có animation vô hạn hoặc `requestAnimationFrame`.
- Grid shortcuts chỉ render một lần; favicons tải lazy/asynchronously và có chữ viết tắt fallback.
- Recent searches lưu trong localStorage; chỉ cập nhật khi submit/clear.
- Suggestions dựng bằng DOM APIs + `DocumentFragment`, không dùng `innerHTML` chứa dữ liệu người dùng.
- Clock cập nhật theo ranh giới phút và dừng khi trang không hiển thị.
- Thay bridge trang chủ bằng ba message: `navigate`, `open`, `theme`. Cầu nối chỉ có ở origin local và main frame. URL được phân giải bằng Rust hiện tại; Kotlin fallback được làm tương thích hơn và native kiểm tra scheme/host trước khi mở.
- Native toolbar được ẩn khi trang chủ hoạt động và hiện lại khi duyệt website; tránh hai dock cùng chiếm đáy màn hình.
- Truyền system bottom inset sang CSS để dock không đè lên vùng điều hướng Android; không cộng IME inset lần hai khi bàn phím mở.
- Xóa `NewsFeed.kt` vì trang chủ mới không còn feed RSS.
- Tắt WebView scrollbars/overscroll glow; không bật software layer hoặc offscreen pre-raster.

## Điều chưa được tuyên bố
Chưa có APK build được trong môi trường này và chưa có benchmark chạy trên điện thoại. Vì vậy không thể khẳng định số FPS hoặc p90/p99 đã cải thiện. Những thay đổi trên làm giảm DOM, CSS effects, canvas redraw và timer work; cần đo thực tế mới xác định mức cải thiện.

## Benchmark bắt buộc sau build
1. Cold start và warm start.
2. Vuốt trang chủ 10 lượt, cả khi favicon chưa tải xong và khi cache đã ấm.
3. Focus ô tìm kiếm, gõ 20–30 ký tự tiếng Việt, mở/đóng bảng suggestion.
4. Đổi engine, theme, xóa lịch sử, chọn recent query.
5. Mở shortcut và search query; xác nhận native toolbar ẩn/hiện đúng.
6. Duyệt trang web nặng, cuộn 30 giây, quay lại trang chủ.
7. Ghi Perfetto FrameTimeline, FrameMetrics, CPU, PSS và dropped frames; so sánh p50/p90/p99.
