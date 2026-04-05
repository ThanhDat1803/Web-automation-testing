package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import base.BaseTest;
import utils.QuanLyDiem;

public class Layout extends BaseTest{
	public static void testLayout() throws InterruptedException {
		// TODO Auto-generated method stub
		Thread.sleep(3000);
		driver.manage().window().setSize(new Dimension(400, 800)); // Giả lập màn hình nhỏ
		Thread.sleep(3000);

		try {
		    // 🔹 Kiểm tra biểu tượng menu 3 gạch khi thu nhỏ trình duyệt
		    WebElement menuIcon = driver.findElement(By.xpath("/html/body/div/div/nav/div/div[1]/div[1]/button"));
		    boolean iconVisible = menuIcon.isDisplayed();

		    System.out.println("==========================================");
		    System.out.println("Hiển thị icon Menu (3 gạch) khi thu nhỏ:");
		    System.out.println("Expected: true");
		    System.out.println("Actual:   " + iconVisible);

		    if (iconVisible) {
		        System.out.println("✅ Icon menu hiển thị đúng!");
		        QuanLyDiem.congDiem("Check_Menu_Responsive", true, 0.8);
		    } else {
		        System.out.println("❌ Icon menu KHÔNG hiển thị!");
		        QuanLyDiem.congDiem("Check_Menu_Responsive", false, 1);
		    }

		    // 🔥 Assert để TestNG đánh dấu Pass/Fail
		    Assert.assertTrue(iconVisible, "❌ Icon menu (3 gạch) không hiển thị khi thu nhỏ!");

		} catch (NoSuchElementException e) {
		    System.out.println("❌ Không tìm thấy icon menu: " + e.getMessage());
		    QuanLyDiem.congDiem("Check_Menu_Responsive", false, 1);
		}
	}
}
