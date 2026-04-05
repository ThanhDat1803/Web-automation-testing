package pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import models.SRS;
import models.TestPlan;
import utils.QuanLyDiem;

public class AddTestPlan {
	private WebDriver driver;
	private WebDriverWait wait;
	public AddTestPlan(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
	// ===== ĐỌC JSON & TRẢ VỀ DANH SÁCH ===== //
    public List<TestPlan> docJsonTestPlan(String jsonPath) {
        List<TestPlan> tcList = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONArray arr = (JSONArray) parser.parse(new FileReader(jsonPath));

            for (Object obj : arr) {
                JSONObject json = (JSONObject) obj;

                TestPlan testPlan = new TestPlan(
                        (String) json.get("Title"),
                        (String) json.get("SRS"),
                        (String) json.get("Version"),
                        (String) json.get("Description"),
                        (String) json.get("Start Date"),
                        (String) json.get("End Date"),
                        (String) json.get("UpLoadFile")
                );

                tcList.add(testPlan);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Lỗi đọc JSON: " + e.getMessage());
        }

        return tcList;
    }

    // ===== HÀM THÊM TỪ JSON ===== //
    public void addTestPlan_FromJSON(String jsonPath) throws InterruptedException, AWTException {
        List<TestPlan> list = docJsonTestPlan(jsonPath);

        for (TestPlan tc : list) {
        	addTestPlan(tc);
        }
    }
	public void addTestPlan(TestPlan tp) throws InterruptedException {

        System.out.println("🟦 Đang thêm Test Plan: " + tp.getTitle());
        
        try {
        	// --- Click nút Add ---
//    	    driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[2]/button")).click();
//    	    Thread.sleep(1000);
    	    
    	    WebElement btnADD = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/main/div/div/div[2]/button")));
    	    btnADD.click();
    	    Thread.sleep(1000);
    	    
        	// Nhập Title
    	    driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[1]/div/div[1]/input")).sendKeys(tp.getTitle());
    	    
    	    // --- Chọn SRS ---
    	    WebElement srsDropdown = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[1]/div/div[2]/div[1]/div"));
    	    srsDropdown.click();
    	    Thread.sleep(700);
    	    // chọn theo text (đúng tên trong JSON)
    	    WebElement srsOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'" + tp.getSRS() + "')]")));
    	    srsOption.click();
    	    Thread.sleep(800);
    	    
    	    // --- Chọn Version ---
    	    WebElement versionDropdown = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[1]/div/div[2]/div[2]/div"));
    	    versionDropdown.click();
    	    Thread.sleep(700);
    	    WebElement versionOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'" + tp.getVersion() + "')]")));
    	    versionOption.click();
    	    Thread.sleep(800);

            // Description
            driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[1]/div/div[3]/textarea")).sendKeys(tp.getDescription());

            // Start Date
            driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[2]/div/div[1]/input")).sendKeys(tp.getStartDate());

            // End Date
            driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[2]/div/div[2]/input")).sendKeys(tp.getEndDate());

            // --- Upload file ---
    	    WebElement uploadBox = driver.findElement(
    	            By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[2]/div/div[4]"));
    	    uploadBox.click();
    	    Thread.sleep(1000);

    	    // Dán file bằng Robot
    	    StringSelection ss = new StringSelection(tp.getFilePath());
    	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

    	    Robot robot = new Robot();
    	    Thread.sleep(800);
    	    robot.keyPress(KeyEvent.VK_CONTROL);
    	    robot.keyPress(KeyEvent.VK_V);
    	    robot.keyRelease(KeyEvent.VK_V);
    	    robot.keyRelease(KeyEvent.VK_CONTROL);
    	    Thread.sleep(500);
    	    robot.keyPress(KeyEvent.VK_ENTER);
    	    robot.keyRelease(KeyEvent.VK_ENTER);
    	    Thread.sleep(2000);

            // Save
            driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[3]/button[1]")).click();
            Thread.sleep(3000);
			
		} catch (Exception e) {
			System.out.println("⚠️ Lỗi khi thêm Test Plan: " + e.getMessage());
            QuanLyDiem.congDiem("Them_TestPlan", false, 0);
		}
    }
	public void soSanh(String jsonPath) throws InterruptedException {
        List<TestPlan> expectedList = docJsonTestPlan(jsonPath);

        Thread.sleep(1500);

        try {
            WebElement lastPage = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[5]/button[2]"));
            lastPage.click();
            Thread.sleep(1000);
        } catch (Exception ignored) {}
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(
        	    By.xpath("//*[@id='root']/div/main/div/div/div[3]/table/tbody")
        	));
        List<WebElement> rows = driver.findElements(By.xpath("//*[@id='root']/div/main/div/div/div[3]/table/tbody/tr"));

        System.out.println("\n================ SO SÁNH TOÀN BỘ DỮ LIỆU TestPlan ================\n");

        boolean allMatch = true; // ✅ Giả định ban đầu là tất cả đúng

        for (TestPlan expected : expectedList) {
            boolean match = false;

            for (WebElement row : rows) {
                String actualTitle = row.findElement(By.xpath("./td[3]")).getText().trim();

                if (actualTitle.equalsIgnoreCase(expected.getTitle())) {

                    System.out.println("✅ *Khớp*: " + expected.getTitle());
                    System.out.println("   Expected → " + expected.getTitle());
                    System.out.println("   Actual   → " + actualTitle);
                    System.out.println("--------------------------------------------------");
                    
                    match = true;
                    break;
                }
            }

            if (!match) {
                System.out.println("❌ *Không khớp hoặc không tìm thấy*: " + expected.getTitle());
                System.out.println("   Expected → " + expected.getTitle());
                System.out.println("   Actual   → Không tồn tại dòng phù hợp trong bảng");
                System.out.println("--------------------------------------------------");

                allMatch = false; // ❌ Nếu có 1 cái sai → FAIL
            }
        }

        // ✅ Cộng điểm chỉ 1 lần
        if (allMatch) {
            System.out.println("🏆 KẾT QUẢ CUỐI: PASS — Tất cả dữ liệu JSON trùng khớp bảng.");
            QuanLyDiem.congDiem("Compare_TestPlan", true, 1); // +5 điểm
        } else {
            System.out.println("❌ KẾT QUẢ CUỐI: FAIL — Có dữ liệu không đúng hoặc thiếu.");
            QuanLyDiem.congDiem("Compare_TestPlan", false, 1); // 0 điểm
        }
    }
}
