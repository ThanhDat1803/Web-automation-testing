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

public class UpdateSRS {
	private WebDriver driver;
    private WebDriverWait wait;

    public UpdateSRS(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
	public void updateSRS(JSONObject obj) {
	    try {
	        String srsId = (String) obj.get("idSRS");
	        String newTitle = (String) obj.get("description");

	        // --- 1. Tìm kiếm TC để update ---
	        WebElement searchBox = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[3]/div/input"));
	        searchBox.clear();
	        searchBox.sendKeys(srsId);
	        Thread.sleep(2000);

	        // Click Update
	        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[6]/table/tbody/tr/td[8]/div/button[1]")).click();
	        Thread.sleep(2000);

	        // --- 2. Update Title ---
	        WebElement titleField = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[3]/textarea"));
	        titleField.clear();
	        titleField.sendKeys(newTitle);
	        Thread.sleep(2000);

	        // Click nút "Update" trong form
	        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[5]/button[1]")).click();
	        Thread.sleep(3000);

	        // --- 3. So sánh dữ liệu trong bảng ---
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	        	    By.xpath("//*[@id=\"root\"]/div/main/div/div/div[5]/table")
	        	));
	        List<WebElement> rows = driver.findElements(
	            By.xpath("//*[@id=\"root\"]/div/main/div/div/div[5]/table/tbody/tr")
	        );

	        boolean updated = false;

	        for (WebElement row : rows) {
	            String tableTcId = row.findElement(By.xpath("./td[1]")).getText().trim();
	            String tableTitle = row.findElement(By.xpath("./td[4]")).getText().trim();

	            if (tableTcId.equalsIgnoreCase(srsId)) {

	                // In ra kết quả mong đợi và thực tế
	                System.out.println("🔹 SRS_ID: " + srsId);
	                System.out.println("   Expected Title → " + newTitle);
	                System.out.println("   Actual Title   → " + tableTitle);

	                if (tableTitle.equalsIgnoreCase(newTitle)) {
	                    //System.out.println("✅ Update thành công!");
	                    updated = true;
	                } else {
	                    System.out.println("❌ Update thất bại, Title chưa đổi.");
	                }
	                break;
	            }
	        }

	        if (updated) {
                System.out.println("✅ Update PASS: " + newTitle);
                QuanLyDiem.congDiem("Update_SRS", true, 0.5);
                System.out.println("✅ Đã Update SRS thành công: " + srsId);
            } else {
                System.out.println("❌ Update FAIL: " + newTitle);
                QuanLyDiem.congDiem("Update_SRS", false, 2);
            }

	    } catch (Exception e) {
	        System.out.println("⚠️ Lỗi khi update srs: " + e.getMessage());
	    }
	}
}
