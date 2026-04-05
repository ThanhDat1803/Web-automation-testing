package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import models.SRS;
import utils.QuanLyDiem;

public class AddSRS {
	private WebDriver driver;
    private WebDriverWait wait;

    public AddSRS(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== ĐỌC JSON & TRẢ VỀ DANH SÁCH ===== //
    public List<SRS> docJsonSRS(String jsonPath) {
        List<SRS> srsList = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONArray arr = (JSONArray) parser.parse(new FileReader(jsonPath));

            for (Object obj : arr) {
                JSONObject json = (JSONObject) obj;

                SRS srs = new SRS(
                        (String) json.get("idSRS"),
                        (String) json.get("title"),
                        (String) json.get("version"),
                        (String) json.get("pageNumber"),
                        (String) json.get("description"),
                        (String) json.get("publicYear"),
                        (String) json.get("fileSrsDocument")
                );

                srsList.add(srs);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Lỗi đọc JSON: " + e.getMessage());
        }

        return srsList;
    }

    // ===== HÀM THÊM TỪ JSON ===== //
    public void addSRS_FromJSON(String jsonPath) throws InterruptedException, AWTException {
        List<SRS> list = docJsonSRS(jsonPath);

        for (SRS srs : list) {
            addSRS(srs);
        }
    }

    // ===== HÀM ADD SRS ===== //
    public void addSRS(SRS srs) throws InterruptedException, AWTException {

        System.out.println("🟦 Đang thêm SRS: " + srs.getIdSRS());
        
        //btn Add
        WebElement btnADD = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/main/div/div/div[3]/button")));
        btnADD.click();
	    Thread.sleep(1000);
        // ID SRS
        WebElement id_srs = driver.findElement(By.xpath("/html/body/div/div/main/div/div/div/form/div[1]/div[1]/div/div/div[1]/div[2]/input"));
        id_srs.click();
        id_srs.sendKeys(srs.getIdSRS());
        id_srs.sendKeys(Keys.ENTER);
        
        // Title
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[1]/div[2]/input")).sendKeys(srs.getTitle());

        // Version
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[1]/div[3]/input")).sendKeys(srs.getVersion());

        // Page Number
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[2]/div[2]/input")).sendKeys(srs.getPageNumber());

        // Description
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[3]/textarea")).sendKeys(srs.getDescription());

        // Public Year
        // Click mở DatePicker
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[2]/div[3]/div/div/div[1]/div")).click();
        Thread.sleep(500);

        WebElement selectDay = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[2]/div[3]/div/div/div[2]/div[2]/div/div/div/div/div[2]/div[2]/div[7]"));
        selectDay.click();
        Thread.sleep(500);

        // Upload File
        WebElement uploadBox = driver.findElement(
	            By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[4]/div"));
	    uploadBox.click();
	    Thread.sleep(1000);

	    // Dán file bằng Robot
	    StringSelection ss = new StringSelection(srs.getFileSrsDocument());
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
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/form/div[5]/button[1]")).click();
        Thread.sleep(2000);

        System.out.println("✅ Thêm SRS thành công: " + srs.getTitle());
    }
    public void soSanh(String jsonPath) throws InterruptedException {
        List<SRS> expectedList = docJsonSRS(jsonPath);

        Thread.sleep(1500);

        try {
            WebElement lastPage = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div[6]/div/button[2]"));
            lastPage.click();
            Thread.sleep(1000);
        } catch (Exception ignored) {}
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(
        	    By.xpath("//*[@id=\"root\"]/div/main/div/div/div[5]/table")
        	));
        List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[5]/table/tbody/tr"));

        System.out.println("\n================ SO SÁNH TOÀN BỘ DỮ LIỆU SRS ================\n");

        boolean allMatch = true; // ✅ Giả định ban đầu là tất cả đúng

        for (SRS expected : expectedList) {
            boolean match = false;

            for (WebElement row : rows) {
                String actualId = row.findElement(By.xpath("./td[1]")).getText().trim();
                String actualTitle = row.findElement(By.xpath("./td[2]")).getText().trim();
                String actualVersion = row.findElement(By.xpath("./td[3]")).getText().trim();

                if (actualId.equalsIgnoreCase(expected.getIdSRS())
                        && actualTitle.equalsIgnoreCase(expected.getTitle())
                        && actualVersion.equalsIgnoreCase(expected.getVersion())) {

                    System.out.println("✅ *Khớp*: " + expected.getIdSRS());
                    System.out.println("   Expected → " + expected.getIdSRS() + " | " + expected.getTitle() + " | " + expected.getVersion());
                    System.out.println("   Actual   → " + actualId + " | " + actualTitle + " | " + actualVersion);
                    System.out.println("--------------------------------------------------");
                    
                    match = true;
                    break;
                }
            }

            if (!match) {
                System.out.println("❌ *Không khớp hoặc không tìm thấy*: " + expected.getIdSRS());
                System.out.println("   Expected → " + expected.getIdSRS() + " | " + expected.getTitle() + " | " + expected.getVersion());
                System.out.println("   Actual   → Không tồn tại dòng phù hợp trong bảng");
                System.out.println("--------------------------------------------------");

                allMatch = false; // ❌ Nếu có 1 cái sai → FAIL
            }
        }

        // ✅ Cộng điểm chỉ 1 lần
        if (allMatch) {
            System.out.println("🏆 KẾT QUẢ CUỐI: PASS — Tất cả dữ liệu JSON trùng khớp bảng.");
            QuanLyDiem.congDiem("Compare_SRS", true, 1); // +5 điểm
        } else {
            System.out.println("❌ KẾT QUẢ CUỐI: FAIL — Có dữ liệu không đúng hoặc thiếu.");
            QuanLyDiem.congDiem("Compare_SRS", false, 1); // 0 điểm
        }
    }
}
