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

public class UpdateTestCase {
	private WebDriver driver;
	private WebDriverWait wait;
    public UpdateTestCase(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void updateTestCase(JSONObject obj) {
	    try {
	        String tcId = (String) obj.get("TC_ID");
	        String newTitle = (String) obj.get("Title");

	        // --- 1. Tìm kiếm TC để update ---
	        WebElement searchBox = driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[2]/div[1]/input"));
	        searchBox.clear();
	        searchBox.sendKeys(tcId);
	        Thread.sleep(2000);
	        
	        // 2. Chọn checkbox
	        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[4]/table/tbody/tr/td[1]/input")).click();
	        Thread.sleep(1000);
	        
	        // Click Update
	        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[2]/div[2]/button[2]")).click();
	        Thread.sleep(2000);

	        // 4. Update Title
	        WebElement titleField = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/table/tbody/tr/td[3]/input"));
	        titleField.clear();
	        titleField.sendKeys(newTitle);
	        Thread.sleep(1000);

	        // Click nút "Update" trong form
	        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[3]/button")).click();
	        Thread.sleep(3000);
	        
	        // 6. Trở lại module Testcase
	        driver.findElement(By.xpath("/html/body/div[1]/div/nav/div/div[1]/div[2]/a[2]")).click();
	        Thread.sleep(2500);
	        
	        // 7. So sánh
	        WebElement searchAgain = driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[2]/div[1]/input"));
	        searchAgain.clear();
	        searchAgain.sendKeys(tcId);
	        Thread.sleep(1500);
	        // --- So sánh dữ liệu trong bảng ---
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	        	    By.xpath("//*[@id='root']/div/main/div/div/div[4]/table/tbody/tr")
	        	));
	        List<WebElement> rows = driver.findElements(By.xpath("//*[@id='root']/div/main/div/div/div[4]/table/tbody/tr"));
	        boolean updated = false;

	        for (WebElement row : rows) {
	            String actualNewTitle = row.findElement(By.xpath("./td[4]")).getText().trim();
	            
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
	            QuanLyDiem.congDiem("Update_TestCase", true, 0.5);
	            System.out.println("✅ Đã Update Test case thành công: " + tcId);
	        } else {
	            System.out.println("❌ Update FAIL: " + newTitle);
	            QuanLyDiem.congDiem("Update_TestCase", false, 0.5);
	        }

	        driver.navigate().refresh();
	        Thread.sleep(1200);
	        
	    } catch (Exception e) {
	        System.out.println("⚠️ Lỗi khi update srs: " + e.getMessage());
	    }
	}
    
    public void updateTestCase1(String tcId, String newTitle) throws InterruptedException {
    System.out.println("🟦 Đang update Test Case: " + tcId + " → title: " + newTitle);

    try {
        // 1. Tìm kiếm TC
        WebElement searchBox = driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[2]/div[1]/input"));
        searchBox.clear();
        searchBox.sendKeys(tcId);
        Thread.sleep(1500);

        // 2. Chọn checkbox
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[4]/table/tbody/tr/td[1]/input")).click();
        Thread.sleep(1000);

        // 3. Bấm nút Update
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[2]/div[2]/button[2]")).click();
        Thread.sleep(2000);

        // 4. Update Title
        WebElement titleField = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/table/tbody/tr/td[3]/input"));
        titleField.clear();
        titleField.sendKeys(newTitle);
        Thread.sleep(1000);

        // 5. Bấm nút Save Update
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[3]/button/span")).click();
        Thread.sleep(2000);

        // 6. Trở lại module Testcase
        driver.findElement(By.xpath("/html/body/div[1]/div/nav/div/div[1]/div[2]/a[2]")).click();
        Thread.sleep(2500);

        // 7. So sánh
        WebElement searchAgain = driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[2]/div[1]/input"));
        searchAgain.clear();
        searchAgain.sendKeys(tcId);
        Thread.sleep(1500);

        List<WebElement> rows = driver.findElements(By.xpath("//*[@id='root']/div/main/div/div/div[4]/table/tbody/tr"));
        boolean updated = false;

        for (WebElement row : rows) {
            String actualNewTitle = row.findElement(By.xpath("./td[4]")).getText().trim();
            
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
            QuanLyDiem.congDiem("Update_TestPCase", true, 2);
            System.out.println("✅ Đã Update Test Plan thành công: " + tcId);
        } else {
            System.out.println("❌ Update FAIL: " + newTitle);
            QuanLyDiem.congDiem("Update_TestPCase", false, 2);
        }

        driver.navigate().refresh();
        Thread.sleep(1200);

    } catch (Exception e) {
        System.out.println("⚠ Lỗi update test case: " + e.getMessage());
        QuanLyDiem.congDiem("Update_TestCase", false, 2);
    	}
    }
}
