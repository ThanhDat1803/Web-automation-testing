package pages;

import java.util.List;
import utils.QuanLyDiem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DeleteTestCase {
	private WebDriver driver;

    public DeleteTestCase(WebDriver driver) {
        this.driver = driver;
    }
    
    public void deleteTestCase(String tcId) throws InterruptedException {
        System.out.println("🟥 Đang xóa Test Case: " + tcId);

        try {
            // 1. Tìm TC cần xóa
            WebElement searchBox = driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[2]/div[1]/input"));
            searchBox.clear();
            searchBox.sendKeys(tcId);
            Thread.sleep(1500);

            // 2. Click Checkbox dòng đầu
            driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[4]/table/tbody/tr/td[1]/input")).click();
            Thread.sleep(800);

            // 3. Click nút Delete
            driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[2]/div[2]/button[3]")).click();
            Thread.sleep(2000);

            // 4. Xác nhận Delete  /html/body/div[1]/div/main/div/div[2]/div/div/button[1]
            driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div[2]/div/div/button[2]")).click();
            Thread.sleep(2000);

            // ---------------- SO SÁNH NGAY SAU KHI XÓA ---------------- //

            List<WebElement> rows = driver.findElements(By.xpath("//*[@id='root']/div/main/div/div/div[4]/table/tbody/tr"));

            boolean deleted = false;

            if (rows.isEmpty()) {
                System.out.println("✅ Xóa thành công, bảng trống.");
                deleted = true;

            } else if (rows.size() == 1) {
                // Có khả năng bảng chỉ còn thông báo "No search result…"
                List<WebElement> cols = rows.get(0).findElements(By.xpath("./td"));
                if (cols.size() == 1) {
                    String message = cols.get(0).getText().trim().toLowerCase();
                    System.out.println("\n🔎 So sánh kết quả xóa:");
    	            System.out.println("   ➤ Expected (Mong đợi):   Không còn xuất hiện \"" + tcId + "\" trong bảng");
    	            System.out.println("   ➤ Actual   (Thực tế):   " + message + " (vẫn còn tồn tại)");
                    if (message.contains("No search result for") && message.contains(tcId) || message.contains("no results found") || message.contains("không tìm thấy")) {
                        System.out.println("✅ Đã xóa thành công Test Case: " + tcId);
                        deleted = true;
                    }
                }
            } else {
                // Bảng vẫn còn dữ liệu → kiểm tra xem TC có còn không
                for (WebElement row : rows) {
                    String tableId = row.findElement(By.xpath("./td[2]")).getText().trim();
                    if (tableId.equalsIgnoreCase(tcId)) {
                        deleted = false;
                        break;
                    } else {
                        deleted = true;
                    }
                }
            }

            // Ghi điểm
            if (deleted) {
                QuanLyDiem.congDiem("Xoa_TestCase", true, 0.5);
            } else {
            	QuanLyDiem.congDiem("Xoa_TestCase", false, 2);
            }

            // 5. Refresh sau khi so sánh xong
            driver.navigate().refresh();
            Thread.sleep(1500);

        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi xóa Test Case: " + e.getMessage());
            QuanLyDiem.congDiem("Xoa_TestCase", false, 2);
        }
    }
}
