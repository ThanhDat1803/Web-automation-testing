package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import base.BaseTest;
import utils.QuanLyDiem;

public class Icon extends BaseTest{
	public static void testIcon() throws InterruptedException {
		// TODO Auto-generated method stub
		 driver.findElement(By.xpath("/html/body/div/div/nav/div/div[1]/div[2]/a[3]")).click();
	        Thread.sleep(3000);

	        System.out.println("================================");

	        try {
	            // --- Check Icon Add ---
	            boolean addOK = driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[2]/div[2]/button[1]")).isDisplayed();

	            // --- Check Icon Update ---
	            boolean updateOK = driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[2]/div[2]/button[2]")).isDisplayed();

	            // --- Check Icon Delete ---
	            boolean deleteOK = driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[2]/div[2]/button[3]")).isDisplayed();

	            System.out.println("Kết quả kiểm tra:");
	            System.out.println("Add: " + addOK + " | Update: " + updateOK + " | Delete: " + deleteOK);

	            // ✅ Chỉ cộng điểm khi cả 3 cùng đúng
	            if (addOK && updateOK && deleteOK) {
	                System.out.println("✅ Tất cả 3 icon hiển thị đúng");
	                QuanLyDiem.congDiem("Check_Icon_Set", true, 0.2); // cộng tổng 1.5 điểm
	            } else {
	                System.out.println("❌ Có icon bị thiếu hoặc hiển thị sai");
	                QuanLyDiem.congDiem("Check_Icon_Set", false, 0); // không cộng hoặc trừ tùy config
	            }

	        } catch (NoSuchElementException e) {
	            System.out.println("❌ Không tìm thấy một hoặc nhiều icon: " + e.getMessage());
	            // Lỗi nặng → không thể kiểm tra → fail
	            QuanLyDiem.congDiem("Check_Icon_Set", false, 2.0);
	        }
	}
}
