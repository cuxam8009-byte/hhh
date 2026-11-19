# Dymith — ghi chú giai đoạn nền tảng

Tài liệu kiểm thử Phase 0 ban đầu đã được thay thế bởi **Home UI / Performance Pass 2**. Giao diện trang chủ hiện tại không còn các mục cũ về RSS, kính blur hoặc widget.

## Kiểm thử hiện tại

1. Mở app nguội và mở lại từ nền.
2. Ở trang chủ, xác nhận đồng hồ/ngày khớp giờ thiết bị, 8 shortcut hiển thị và dock tìm kiếm nằm dưới cùng.
3. Thử nhập `example.com`, `localhost:8080`, truy vấn tiếng Việt và chọn từng search engine.
4. Thử recent chip, xóa lịch sử, đổi Sáng/Tối/Tự động.
5. Mở một shortcut; xác nhận dock HTML biến mất và toolbar native xuất hiện.
6. Quay về trang chủ; xác nhận toolbar native ẩn, dock trang chủ trở lại.
7. Duyệt trang dài, cuộn liên tục, mở/đóng bàn phím; kiểm tra toolbar và IME inset không chồng lấp.
8. Vào Menu > Chẩn đoán và báo cáo, sao chép báo cáo để so sánh p50/p90/p99 và dropped frames.

## Các tính năng trình duyệt nền vẫn được giữ

- Rust URL resolver và blocklist/JNI mẫu.
- WebView Android, download manager, bookmark/history trong phiên và chẩn đoán.
- Native address toolbar khi đang ở website.

Xem `docs/PHASE1_PERFORMANCE.md` để biết thay đổi Pass 2 và quy trình benchmark.
