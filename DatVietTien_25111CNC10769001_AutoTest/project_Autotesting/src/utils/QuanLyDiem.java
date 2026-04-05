package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class QuanLyDiem {
	private static Map<String, Double> diemTheoChucNang = new HashMap<>();
    private static double tongDiem = 0;
    
    public static void congDiem(String chucNang, boolean ketQua, double diemCong) {
        if (ketQua) {
            tongDiem += diemCong;
            diemTheoChucNang.put(chucNang, diemTheoChucNang.getOrDefault(chucNang, 0.0) + diemCong);
            System.out.println("🏆 " + chucNang + " PASS → +" + diemCong + " điểm");
        } else {
            System.out.println("❌ " + chucNang + " FAIL → +0 điểm");
        }
    }

    public static void inTongKet() {
        System.out.println("──────────────");
        System.out.println("🎯 Tổng điểm: " + tongDiem);
        for (Entry<String, Double> e : diemTheoChucNang.entrySet()) {
            System.out.println("  ➜ " + e.getKey() + ": " + e.getValue() + " điểm");
        }
        // Đọc điểm cũ từ file
        double diemCu = LuuTruDiem.docDiem();
        double diemMoi = diemCu + tongDiem;

        // Lưu lại điểm mới
        LuuTruDiem.luuDiem(diemMoi);

        System.out.println("💾 Điểm cộng dồn tổng: " + diemMoi);
        System.out.println("──────────────");
    }
    public static void reset() {
        tongDiem = 0;
        diemTheoChucNang.clear();
    }
}
