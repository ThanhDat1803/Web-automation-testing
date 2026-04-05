package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import base.BaseTest;
import utils.QuanLyDiem;

public class KieuChu extends BaseTest {

	public static void testKieuChu() throws InterruptedException {
		 // Mở trang Testcase
        driver.findElement(By.xpath("//a[contains(text(),'Testcase')]")).click();
        Thread.sleep(3000);

        try {
            // 🔹 Kiểm tra font chữ của toàn bộ body 
            WebElement body = driver.findElement(By.xpath("/html/body"));
            String actualFontBody = body.getCssValue("font-family");

            // 👈 Font mong đợi (hệ thống Tailwind)
            String expectedFontBody = "ui-sans-serif, system-ui, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", \"Segoe UI Symbol\", \"Noto Color Emoji\"";

            boolean fontOK = actualFontBody.toLowerCase().contains(expectedFontBody.toLowerCase());

            System.out.println("Font body - Expected: " + expectedFontBody);
            System.out.println("Font body - Actual  : " + actualFontBody);
            System.out.println("Font body - Kết quả : " + fontOK);

            if (fontOK) {
                System.out.println("✅ Font body hiển thị đúng");
                QuanLyDiem.congDiem("Check_Font_Body", true, 0.4);
            } else {
                System.out.println("❌ Font body KHÔNG đúng");
                QuanLyDiem.congDiem("Check_Font_Body", false, 1.0);
            }

        } catch (NoSuchElementException e) {
            System.out.println("❌ Không tìm thấy phần tử body: " + e.getMessage());
            QuanLyDiem.congDiem("Check_Font_Body", false, 1.5); // lỗi nặng hơn → trừ mạnh hơn
        }
    }
}
