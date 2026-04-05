package pages;

import java.time.Duration;
import java.util.List;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.QuanLyDiem;

public class UpdateTestPlan {
	private WebDriver driver;
	private WebDriverWait wait;
    public UpdateTestPlan(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void updateTestCase(JSONObject obj) {
	    try {
	        String title = (String) obj.get("Title");
	        String newTitle = (String) obj.get("NewTitle");

	        // --- 1. Tìm kiếm TC để update ---
	        WebElement searchBox = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[2]/div/input"));
	        searchBox.clear();
	        searchBox.sendKeys(title);
	        Thread.sleep(2000);

	        // Click Update
	        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[4]/table/tbody/tr/td[11]/div/button[1]")).click();
	        Thread.sleep(2000);

	        // --- 2. Update Title ---
	        WebElement titleField = driver.findElement(By.xpath("/html/body/div/div/main/div/div/div/form/div[1]/div/div[1]/input"));
            titleField.clear();
            titleField.sendKeys(newTitle);
            Thread.sleep(1000);

	        // Click nút "Update" trong form
            driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[7]/button[1]")).click();
            Thread.sleep(2000);

            // So sánh
            // Tìm kiếm lại title vừa update
            WebElement searchAgain = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("/html/body/div[1]/div/main/div/div/div[2]/div/input")));
            searchAgain.clear();
            searchAgain.sendKeys(newTitle);
            Thread.sleep(1500);

            List<WebElement> rows = driver.findElements(By.xpath("//*[@id='root']/div/main/div/div/div[4]/table/tbody/tr"));
            boolean updated = false;

            for (WebElement row : rows) {
                String actualNewTitle = row.findElement(By.xpath("./td[3]")).getText().trim();
                
                // In kết quả mong đợi vs thực tế
                System.out.println("🔎 So sánh kết quả:");
                System.out.println("   ➤ Expected (Mong đợi): " + newTitle);
                System.out.println("   ➤ Actual   (Thực tế): " + actualNewTitle);
                
                if (actualNewTitle.equalsIgnoreCase(newTitle)) {
                    updated = true;
                    break;
                }
            }
            
            if (updated) {
                System.out.println("✅ Update PASS: " + newTitle);
                QuanLyDiem.congDiem("Update_TestPlan", true, 0.5);
                System.out.println("✅ Đã Update Test Plan thành công: " + title);
            } else {
                System.out.println("❌ Update FAIL: " + newTitle);
                QuanLyDiem.congDiem("Update_TestPlan", false, 2);
            }

            driver.navigate().refresh();
            Thread.sleep(1200);

	    } catch (Exception e) {
	        System.out.println("⚠️ Lỗi khi update srs: " + e.getMessage());
	    }
	}
    public void updateTestCase1(String title, String newTitle) throws InterruptedException {
    	System.out.println("🟦 Đang update Test Plan: " + title + " → " + newTitle);
    	try {
    		// 1. Tìm kiếm TC
            WebElement searchBox = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[2]/div/input"));
            searchBox.clear();
            searchBox.sendKeys(title);
            Thread.sleep(1500);

            // 2. Click button cây bút
            driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[4]/table/tbody/tr/td[11]/div/button[1]")).click();
            Thread.sleep(1000);

            // 3. Update Title
            WebElement titleField = driver.findElement(By.xpath("/html/body/div/div/main/div/div/div/form/div[1]/div/div[1]/input"));
            titleField.clear();
            titleField.sendKeys(newTitle);
            Thread.sleep(1000);

            // 4. Bấm nút Save change
            driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[7]/button[1]")).click();
            Thread.sleep(2000);

            // 5. So sánh
            // Tìm kiếm lại title vừa update
            WebElement searchAgain = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("/html/body/div[1]/div/main/div/div/div[2]/div/input")));
            searchAgain.clear();
            searchAgain.sendKeys(newTitle);
            Thread.sleep(1500);

            List<WebElement> rows = driver.findElements(By.xpath("//*[@id='root']/div/main/div/div/div[4]/table/tbody/tr"));
            boolean updated = false;

            for (WebElement row : rows) {
                String actualNewTitle = row.findElement(By.xpath("./td[3]")).getText().trim();
                
                // In kết quả mong đợi vs thực tế
                System.out.println("🔎 So sánh kết quả:");
                System.out.println("   ➤ Expected (Mong đợi): " + newTitle);
                System.out.println("   ➤ Actual   (Thực tế): " + actualNewTitle);
                
                if (actualNewTitle.equalsIgnoreCase(newTitle)) {
                    updated = true;
                    break;
                }
            }
            
            if (updated) {
                System.out.println("✅ Update PASS: " + newTitle);
                QuanLyDiem.congDiem("Update_TestPlan", true, 2);
                System.out.println("✅ Đã Update Test Plan thành công: " + title);
            } else {
                System.out.println("❌ Update FAIL: " + newTitle);
                QuanLyDiem.congDiem("Update_TestPlan", false, 2);
            }

            driver.navigate().refresh();
            Thread.sleep(1200);
		} catch (Exception e) {
			System.out.println("⚠️ Lỗi khi Update Test Plan: " + e.getMessage());
            QuanLyDiem.congDiem("Update_TestPlan", false, 0);
		}
    }
}
