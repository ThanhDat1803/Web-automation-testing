package pages;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.File;

import javax.imageio.ImageIO;

import org.testng.Assert;

import utils.QuanLyDiem;

public class CompareImg {
	public static void compareImg(File imgFile1, File imgFile2) {
		try {
		    Image image1 = ImageIO.read(imgFile1);
		    Image image2 = ImageIO.read(imgFile2);

		    PixelGrabber grab1 = new PixelGrabber(image1, 0, 0, -1, -1, false);
		    PixelGrabber grab2 = new PixelGrabber(image2, 0, 0, -1, -1, false);

		    grab1.grabPixels();
		    grab2.grabPixels();

		    int width1 = grab1.getWidth();
		    int height1 = grab1.getHeight();
		    int width2 = grab2.getWidth();
		    int height2 = grab2.getHeight();

		    // --- Kiểm tra kích thước ---
		    if (width1 != width2 || height1 != height2) {
		        System.out.println("❌ Kích thước hình ảnh không giống nhau → FAIL");
		        QuanLyDiem.congDiem("Image_Compare", false, 1);
		        return;
		    }

		    int[] data1 = (int[]) grab1.getPixels();
		    int[] data2 = (int[]) grab2.getPixels();

		    boolean match = java.util.Arrays.equals(data1, data2);

		    System.out.println("=====================================");
		    System.out.println("So sánh hình ảnh:");

		    if (match) {
		        System.out.println("✅ Hai hình ảnh giống nhau → PASS");
		        QuanLyDiem.congDiem("Image_Compare", true, 0.1);
		    } else {
		        System.out.println("❌ Hai hình ảnh KHÔNG giống nhau → FAIL");
		        QuanLyDiem.congDiem("Image_Compare", false, 1);
		    }

		    // Assert để TestNG đánh dấu kết quả
		    Assert.assertTrue(match, "❌ Hai hình ảnh khác nhau!");

		} catch (Exception e) {
		    System.out.println("⚠️ Lỗi xử lý hình ảnh: " + e.getMessage());
		    QuanLyDiem.congDiem("Image_Compare", false, 1);
		}
	}
}
