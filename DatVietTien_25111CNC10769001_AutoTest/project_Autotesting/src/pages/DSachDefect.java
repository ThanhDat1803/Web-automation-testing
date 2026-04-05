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

public class DSachDefect extends BaseTest{
	
	public static void testDSachDefect() throws InterruptedException {
		// TODO Auto-generated method stub
		 // Chờ trang load
        Thread.sleep(5000);
        // Click vào menu "Defect List"
        driver.findElement(By.xpath("/html/body/div/div/nav/div/div[1]/div[2]/a[3]")).click();
        Thread.sleep(3000);
        // Kết quả mong đợi
        try {
            // 🔹 Danh sách header mong đợi
            List<String> expectedHeaders = Arrays.asList(
                "Defect ID", "Title", "Screen", "Steps", "Expected", "Actual",
                "Priority", "Severity", "Date", "Owner", "TC ID", "Build",
                "System", "Assign To", "Status", "Evidence"
            );

            // 🔹 Lấy danh sách các <th> trong bảng
            List<WebElement> headerElements = driver.findElements(By.xpath("//table/thead/tr/th"));

            // 🔹 Thu thập text từng header
            List<String> actualHeaders = new ArrayList<>();
            for (WebElement th : headerElements) {
                String text = th.getText().trim();
                if (!text.isEmpty()) {
                    actualHeaders.add(text);
                }
            }

            // 🔹 So sánh
            boolean sameSize = (expectedHeaders.size() == actualHeaders.size());
            boolean sameContent = expectedHeaders.equals(actualHeaders);
            boolean finalResult = sameSize && sameContent;

            // 🔹 In kết quả chi tiết
            System.out.println("==========================================");
            System.out.println("🔎 Kiểm tra danh sách cột Defect:");
            System.out.println("Expected headers (" + expectedHeaders.size() + "): " + expectedHeaders);
            System.out.println("Actual headers   (" + actualHeaders.size() + "): " + actualHeaders);
            System.out.println("------------------------------------------");

            // Chi tiết từng cột
            int minSize = Math.min(expectedHeaders.size(), actualHeaders.size());
            for (int i = 0; i < minSize; i++) {
                String expected = expectedHeaders.get(i);
                String actual = actualHeaders.get(i);
                boolean match = expected.equals(actual);
                System.out.println("Cột " + (i + 1) + ": Expected = '" + expected + "', Actual = '" + actual + "' → " + (match ? "✅ PASS" : "❌ FAIL"));
            }

            System.out.println("------------------------------------------");
            System.out.println("Cùng số lượng: " + sameSize);
            System.out.println("Cùng nội dung: " + sameContent);
            System.out.println("➡️ Kết quả tổng: " + (finalResult ? "✅ ĐÚNG HOÀN TOÀN" : "❌ KHÔNG KHỚP"));
            System.out.println("==========================================");

            // 🔹 Gộp điểm
            if (finalResult) {
                QuanLyDiem.congDiem("Check_Defect_Headers", true, 0.4);
            } else {
                QuanLyDiem.congDiem("Check_Defect_Headers", false, 1.0);
            }

            // 🔹 Assert TestNG
            Assert.assertTrue(finalResult, "❌ Header bảng Defect KHÔNG khớp danh sách mong đợi!");

        } catch (NoSuchElementException e) {
            System.out.println("❌ Không tìm thấy header bảng Defect: " + e.getMessage());
            QuanLyDiem.congDiem("Check_Defect_Headers", false, 1.0);
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi kiểm tra header bảng Defect: " + e.getMessage());
            QuanLyDiem.congDiem("Check_Defect_Headers", false, 1.0);
        }
    }
}
