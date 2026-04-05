package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.QuanLyDiem;

public class DeleteTestPlan {
	private WebDriver driver;
	private WebDriverWait wait;
    public DeleteTestPlan(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void deleteTestPlan(String title) throws InterruptedException {
    	System.out.println("🟦 Đang delete Test Plan: " + title);
    	try {
    		// 1️ Tìm kiếm test case cần xóa
	        WebElement searchBox = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[2]/div/input"));
	        searchBox.clear();
	        searchBox.sendKeys(title);
	        Thread.sleep(2000);

	        // 2️ Click icon xoá 
	        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[4]/table/tbody/tr/td[11]/div/button[2]")).click();
	        Thread.sleep(1000);

	        // 3️ Click nút Delete xpath xoá: /html/body/div[1]/div/main/div/div[2]/div/div/button[1]
	        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div[2]/div/div/button[2]")).click();
	        Thread.sleep(3000);
	        
	        //Lấy dữ liệu trong bảng sau khi xoá để so sánh
	        List<WebElement> rows = driver.findElements(By.xpath("//*[@id='root']/div/main/div/div/div[4]/table/tbody/tr"));
	        boolean exists  = false;

//            for (WebElement row : rows) {
//                String actualTitle = row.findElement(By.xpath("./td[3]")).getText().trim();
//                
//                /// In mong đợi & thực tế
//                System.out.println("\n🔎 So sánh kết quả xóa:");
//                System.out.println("   ➤ Expected (Mong đợi):   Không còn tồn tại \"" + title + "\" trong danh sách");
//                System.out.println("   ➤ Actual   (Thực tế):   Dòng hiển thị: " + actualTitle);
//                
//                if (actualTitle.equalsIgnoreCase(title)) {
//                	exists = true;
//                    break;
//                }
//            }
	        
	        if (rows.size() == 1) {
	            // Lấy nội dung ô hiển thị message
	            String message = rows.get(0).findElement(By.xpath("./td")).getText().trim().toLowerCase();

	            System.out.println("\n🔎 So sánh kết quả xóa:");
	            System.out.println("   ➤ Expected (Mong đợi):   Không còn xuất hiện \"" + title + "\" trong bảng");
	            System.out.println("   ➤ Actual   (Thực tế):   " + message);

	            if (message.contains("no search result") || message.contains("no results found") || message.contains("không tìm thấy")) {
	            	exists = true;
	            }
	        }
            
            if (exists) {
                System.out.println("✅ Delete PASS: " + title);
                QuanLyDiem.congDiem("Delete_TestPlan", true, 0.5);
                System.out.println("✅ Đã Delete Test Plan thành công: " + title);
            } else {
                System.out.println("❌ Delete FAIL: " + title);
                QuanLyDiem.congDiem("Delete_TestPlan", false, 2);
            }

            driver.navigate().refresh();
            Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("⚠️ Lỗi khi Delete Test Plan: " + e.getMessage());
            QuanLyDiem.congDiem("Delete_TestPlan", false, 0);
		}
    }
}
