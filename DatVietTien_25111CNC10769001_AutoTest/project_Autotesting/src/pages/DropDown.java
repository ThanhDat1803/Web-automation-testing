package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import base.BaseTest;
import utils.QuanLyDiem;

public class DropDown extends BaseTest{
	
	public static void kiemtraDropDown() throws InterruptedException {
		// TODO Auto-generated method stub
		 // Chuyển đến trang TestCases
        driver.findElement(By.xpath("/html/body/div/div/nav/div/div[1]/div[2]/a[2]")).click();
        Thread.sleep(3000);
        
        try {
            // 🔹 Click vào nút Add more
            driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[2]/div[2]/button[1]")).click();
            Thread.sleep(2000);

            // 🔹 Lấy dropdown Priority
            WebElement dropdownElement = driver.findElement(
                By.xpath("/html/body/div/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[11]/select")
            );

            // 🔹 Khởi tạo Select
            Select select = new Select(dropdownElement);
            List<WebElement> options = select.getOptions();

            // 🔹 Lấy danh sách text thực tế
            List<String> actualList = new ArrayList<>();
            for (WebElement option : options) {
                actualList.add(option.getText().trim());
            }

            // 🔹 Danh sách giá trị mong đợi
            List<String> expectedList = Arrays.asList("Select Priority", "Low", "Medium", "High");

            // 🔹 So sánh
            boolean sameSize = (expectedList.size() == actualList.size());
            boolean sameContent = expectedList.equals(actualList);
            boolean finalResult = sameSize && sameContent;

            // 🔹 In kết quả chi tiết
            System.out.println("==========================================");
            System.out.println("🔎 Kiểm tra dropdown Priority:");
            System.out.println("Expected list (" + expectedList.size() + "): " + expectedList);
            System.out.println("Actual list   (" + actualList.size() + "): " + actualList);
            System.out.println("------------------------------------------");

            int minSize = Math.min(expectedList.size(), actualList.size());
            for (int i = 0; i < minSize; i++) {
                String expected = expectedList.get(i);
                String actual = actualList.get(i);
                boolean match = expected.equals(actual);
                System.out.println("Vị trí " + (i + 1) + ": "
                        + "Expected = '" + expected + "', Actual = '" + actual + "' → "
                        + (match ? "✅ PASS" : "❌ FAIL"));
            }

            System.out.println("------------------------------------------");
            System.out.println("Cùng số lượng: " + sameSize);
            System.out.println("Cùng nội dung: " + sameContent);
            System.out.println("➡️ Kết quả tổng: " + (finalResult ? "✅ ĐÚNG HOÀN TOÀN" : "❌ KHÔNG KHỚP"));
            System.out.println("==========================================");

            // 🔹 Gộp điểm
            if (finalResult) {
                QuanLyDiem.congDiem("Check_Dropdown_Priority", true, 0.4);
            } else {
                QuanLyDiem.congDiem("Check_Dropdown_Priority", false, 1);
            }

            // 🔹 Assert TestNG
            Assert.assertTrue(finalResult, "❌ Dropdown Priority KHÔNG khớp với danh sách mong đợi!");

        } catch (NoSuchElementException e) {
            System.out.println("❌ Không tìm thấy dropdown Priority: " + e.getMessage());
            QuanLyDiem.congDiem("Check_Dropdown_Priority", false, 1);
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi kiểm tra dropdown Priority: " + e.getMessage());
            QuanLyDiem.congDiem("Check_Dropdown_Priority", false, 1);
        }
    }
}
