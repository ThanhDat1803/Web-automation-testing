package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class LuuTruDiem {
	private static final String FILE = "E:\\diem.txt";

    public static double docDiem() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            return Double.parseDouble(br.readLine());
        } catch (Exception e) {
            return 0;
        }
    }

    public static void luuDiem(double tongDiemMoi) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            bw.write(String.valueOf(tongDiemMoi));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
