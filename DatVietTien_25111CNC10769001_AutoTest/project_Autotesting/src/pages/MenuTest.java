package pages;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import base.BaseTest;
import utils.QuanLyDiem;
public class MenuTest extends BaseTest {
	public static void kiemTraMenu() throws InterruptedException {
        Thread.sleep(2000);

        try {
            String[] expectedMenu = {"Home", "Testcase", "Defect List", "SRS", "Test Plan"};
            List<String> expectedList = Arrays.asList(expectedMenu);
            List<String> actualList = new ArrayList<>();

            // Lấy danh sách menu
            List<WebElement> menuItems = driver.findElements(
                By.xpath("//nav//div[contains(@class,'flex')]/div/a[not(contains(text(),'Welcome')) and not(contains(text(),'Test User'))]")
            );

            for (WebElement item : menuItems) {
                actualList.add(item.getText().trim());
            }

            System.out.println("Expected: " + expectedList);
            System.out.println("Actual  : " + actualList);

            boolean sameSize = (menuItems.size() == expectedMenu.length);
            boolean sameContent = expectedList.equals(actualList);
            boolean allCorrect = sameSize && sameContent;

            System.out.println("Cùng số lượng: " + sameSize);
            System.out.println("Cùng nội dung: " + sameContent);
            System.out.println("Kết quả tổng : " + (allCorrect ? "✅ Khớp" : "❌ Không khớp"));

            // ✅ Gộp điểm
            if (allCorrect) {
                QuanLyDiem.congDiem("Check_Menu_Items", true, 0.6); // cộng điểm nếu đúng hết
            } else {
                QuanLyDiem.congDiem("Check_Menu_Items", false, 1.0); // fail nếu có sai
            }

            // 🔥 Assert TestNG
            Assert.assertTrue(allCorrect, "❌ Menu chính không khớp số lượng hoặc nội dung!");

        	} catch (NoSuchElementException e) {
            System.out.println("❌ Không tìm thấy menu: " + e.getMessage());
            QuanLyDiem.congDiem("Check_Menu_Items", false, 1.0);
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi kiểm tra menu: " + e.getMessage());
            QuanLyDiem.congDiem("Check_Menu_Items", false, 1.0);
        }
    }
}
