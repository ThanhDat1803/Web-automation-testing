package pages;

import java.awt.AWTException;
import java.io.FileReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import models.SRS;
import models.TestCase;
import utils.QuanLyDiem;

public class AddTestCasePage {
	private WebDriver driver;
	private WebDriverWait wait;
    public AddTestCasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    // ===== ĐỌC JSON & TRẢ VỀ DANH SÁCH ===== //
    public List<TestCase> docJsonTestCase(String jsonPath) {
        List<TestCase> tcList = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONArray arr = (JSONArray) parser.parse(new FileReader(jsonPath));

            for (Object obj : arr) {
                JSONObject json = (JSONObject) obj;

                TestCase testCase = new TestCase(
                        String.valueOf(json.get("Test Plan")),
                        String.valueOf(json.get("TC_ID")),
                        String.valueOf(json.get("Title")),
                        String.valueOf(json.get("Estimation")),    // ✅ xử lý kiểu Long thành String
                        String.valueOf(json.get("Test Type")),
                        String.valueOf(json.get("Area")),
                        String.valueOf(json.get("Pre-condition")),
                        String.valueOf(json.get("Step")),
                        String.valueOf(json.get("Test Data")),
                        String.valueOf(json.get("Expected Results")),
                        String.valueOf(json.get("Priority")),
                        String.valueOf(json.get("Result")),
                        String.valueOf(json.get("Author")),
                        String.valueOf(json.get("Note")),
                        String.valueOf(json.get("Date"))
                );

                tcList.add(testCase);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Lỗi đọc JSON: " + e.getMessage());
        }

        return tcList;
    }
    // ===== HÀM THÊM TỪ JSON ===== //
    public void addTestCase_FromJSON(String jsonPath) throws InterruptedException, AWTException {
    	List<TestCase> list = docJsonTestCase(jsonPath);

        for (int i = 0; i < list.size(); i++) {
            TestCase tc = list.get(i);

            // Dòng trên giao diện bắt đầu từ 1
            int rowIndex = i + 1;

            // Gọi hàm nhập dữ liệu theo dòng
            addTestCase(tc, rowIndex);

            // Nếu chưa phải testcase cuối → click Add Test Case để tạo dòng mới
            if (i < list.size() - 1) {
                WebElement addBtn = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[3]/button[1]/span"));
                addBtn.click();

                // Chờ dòng mới xuất hiện
                int nextRow = rowIndex + 1;
                String nextRowXpath = String.format("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr[%d]", nextRow);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nextRowXpath)));

                Thread.sleep(800); // nghỉ nhẹ để UI ổn định
            }
        }
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[3]/button[3]/span")).click();
    }
 // ===== HÀM Add ===== //
    public void addTestCase(TestCase tc, int index) throws InterruptedException {
        System.out.println("🟦 Đang thêm Test Case: " + tc.getTcId() + " - " + tc.getTitle());
        
        String trIndex = String.format("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr[%d]", index);
        try {
        // Test Plan
        WebElement TestplanDropdown = driver.findElement(By.xpath(trIndex + "/td[1]/select"));
        new Select(TestplanDropdown).selectByVisibleText(tc.getTestPlan());
        Thread.sleep(1000);

        // TC ID
        driver.findElement(By.xpath(trIndex + "/td[2]/input")).sendKeys(tc.getTcId());

        // Title
        driver.findElement(By.xpath(trIndex + "/td[3]/input")).sendKeys(tc.getTitle());

        // estimation
        driver.findElement(By.xpath(trIndex + "/td[4]/input")).sendKeys(tc.getEstimation());
        
        // Test Type
        WebElement TestTypeDropdown = driver.findElement(By.xpath(trIndex + "/td[5]/select"));
        new Select(TestTypeDropdown).selectByVisibleText(tc.getTestType());
        
        // area
        driver.findElement(By.xpath(trIndex + "/td[6]/input")).sendKeys(tc.getArea());
        
        //Pre-condition
        driver.findElement(By.xpath(trIndex + "/td[7]/textarea")).sendKeys(tc.getPrecondition());
        
        //Steps 
        driver.findElement(By.xpath(trIndex + "/td[8]/textarea")).sendKeys(tc.getStep());
        
        //Test Data 
        driver.findElement(By.xpath(trIndex + "/td[9]/textarea")).sendKeys(tc.getTestData());
        
        //Expected Results
        driver.findElement(By.xpath(trIndex + "/td[10]/textarea")).sendKeys(tc.getExpectedResult());
        
        //Priority  
        WebElement PriorityDropdown = driver.findElement(By.xpath(trIndex + "/td[11]/select"));
        new Select(PriorityDropdown).selectByVisibleText(tc.getPriority());
        
        //Result   
        WebElement ResultDropdown = driver.findElement(By.xpath(trIndex + "/td[12]/select"));
        new Select(ResultDropdown).selectByVisibleText(tc.getResult());
        
        //Author   
        WebElement AuthorDropdown = driver.findElement(By.xpath(trIndex + "/td[13]/select"));
        new Select(AuthorDropdown).selectByVisibleText(tc.getAuthor());
        
        //Note
        driver.findElement(By.xpath(trIndex + "/td[14]/textarea")).sendKeys(tc.getNote());
        
        //Date
        driver.findElement(By.xpath(trIndex + "/td[15]/input")).sendKeys(tc.getDate());
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi nhập dòng " + index + ": " + e.getMessage());
        }
    }
    public void soSanhTestCase(String jsonPath) throws InterruptedException {
        List<TestCase> expectedList = docJsonTestCase(jsonPath);

        Thread.sleep(1500);

        try {
            WebElement lastPage = driver.findElement(By.xpath("//*[@id='root']/div/main/div/div/div[5]/button[2]"));
            lastPage.click();
            Thread.sleep(1000);
        } catch (Exception ignored) {}
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(
        	    By.xpath("//*[@id='root']/div/main/div/div/div[3]/table/tbody")
        	));
        List<WebElement> rows = driver.findElements(By.xpath("//*[@id='root']/div/main/div/div/div[3]/table/tbody/tr"));

        System.out.println("\n================ SO SÁNH TOÀN BỘ DỮ LIỆU SRS ================\n");

        boolean allMatch = true; // ✅ Giả định ban đầu là tất cả đúng

        for (TestCase expected : expectedList) {
            boolean match = false;

            for (WebElement row : rows) {
                String actualId = row.findElement(By.xpath("./td[2]")).getText().trim();
                String actualTitle = row.findElement(By.xpath("./td[4]")).getText().trim();

                if (actualId.equalsIgnoreCase(expected.getTcId())
                        && actualTitle.equalsIgnoreCase(expected.getTitle())) {

                    System.out.println("✅ *Khớp*: " + expected.getTcId());
                    System.out.println("   Expected → " + expected.getTcId() + " | " + expected.getTitle());
                    System.out.println("   Actual   → " + actualId + " | " + actualTitle);
                    System.out.println("--------------------------------------------------");
                    
                    match = true;
                    break;
                }
            }

            if (!match) {
                System.out.println("❌ *Không khớp hoặc không tìm thấy*: " + expected.getTcId());
                System.out.println("   Expected → " + expected.getTcId() + " | " + expected.getTitle());
                System.out.println("   Actual   → Không tồn tại dòng phù hợp trong bảng");
                System.out.println("--------------------------------------------------");

                allMatch = false; // ❌ Nếu có 1 cái sai → FAIL
            }
        }

        // ✅ Cộng điểm chỉ 1 lần
        if (allMatch) {
            System.out.println("🏆 KẾT QUẢ CUỐI: PASS — Tất cả dữ liệu JSON trùng khớp bảng.");
            QuanLyDiem.congDiem("Compare_TestCase", true, 1); // + điểm
        } else {
            System.out.println("❌ KẾT QUẢ CUỐI: FAIL — Có dữ liệu không đúng hoặc thiếu.");
            QuanLyDiem.congDiem("Compare_TestCase", false, 2); // 0 điểm
        }
    }
    public void addTestCase1(TestCase tc) throws InterruptedException {
        System.out.println("🟦 Đang thêm Test Case: " + tc.getTcId() + " - " + tc.getTitle());
        try {
        // Test Plan
        WebElement TestplanDropdown = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[1]/select"));
        new Select(TestplanDropdown).selectByVisibleText(tc.getTestPlan());
        Thread.sleep(1000);

        // TC ID
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[2]/input")).sendKeys(tc.getTcId());

        // Title
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[3]/input")).sendKeys(tc.getTitle());

        // estimation
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[4]/input")).sendKeys(tc.getEstimation());
        
        // Test Type
        WebElement TestTypeDropdown = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[5]/select"));
        new Select(TestTypeDropdown).selectByVisibleText(tc.getTestType());
        
        // Title
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[6]/input")).sendKeys(tc.getArea());
        
        //Pre-condition
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[7]/textarea")).sendKeys(tc.getPrecondition());
        
        //Steps 
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[8]/textarea")).sendKeys(tc.getStep());
        
        //Test Data 
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[9]/textarea")).sendKeys(tc.getTestData());
        
        //Expected Results
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[10]/textarea")).sendKeys(tc.getExpectedResult());
        
        //Priority  
        WebElement PriorityDropdown = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[11]/select"));
        new Select(PriorityDropdown).selectByVisibleText(tc.getPriority());
        
        //Result   
        WebElement ResultDropdown = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[12]/select"));
        new Select(ResultDropdown).selectByVisibleText(tc.getResult());
        
        //Author   
        WebElement AuthorDropdown = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[13]/select"));
        new Select(AuthorDropdown).selectByVisibleText(tc.getAuthor());
        
        //Note
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[14]/textarea")).sendKeys(tc.getNote());
        
        //Date
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[2]/div/table/tbody/tr/td[15]/input")).sendKeys(tc.getDate());
        
        // Save /html/body/div[1]/div/main/div/div/div/div/div[3]/button[3]/span
        WebElement saveBtn = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/div/div/div[3]/button[3]"));
        saveBtn.click();
        Thread.sleep(2000);
        
        // --- So sánh sau khi thêm ---

        // Tìm kiếm theo TC_ID
        WebElement timIDTestCase = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div/div/main/div/div/div[2]/div[1]/input")
        ));
        timIDTestCase.sendKeys(tc.getTcId());
        Thread.sleep(2000);
        // Lấy danh sách dòng trong bảng
        List<WebElement> rows = driver.findElements(By.xpath("//*[@id='root']/div/main/div/div/div[4]/table/tbody/tr"));
        
        boolean found = false;
        for (WebElement row : rows) {
            String actualId = row.findElement(By.xpath("./td[2]")).getText().trim();
            String actualTitle = row.findElement(By.xpath("./td[4]")).getText().trim();

            // In ra Expected vs Actual để debug rõ ràng
            System.out.println("🔎 So sánh dữ liệu:");
            System.out.println("   Expected → ID: " + tc.getTcId() + " | Title: " + tc.getTitle());
            System.out.println("   Actual   → ID: " + actualId + " | Title: " + actualTitle);

            if (actualId.equalsIgnoreCase(tc.getTcId()) && actualTitle.equalsIgnoreCase(tc.getTitle())) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("✅ KẾT QUẢ: PASS — Test Case thêm đúng dữ liệu.");
            QuanLyDiem.congDiem("Them_TestCase", true, 2);  // +2 điểm
        } else {
            System.out.println("❌ KẾT QUẢ: FAIL — Dữ liệu trong bảng không khớp.");
            QuanLyDiem.congDiem("Them_TestCase", false, 2); // +0 điểm
        }

        // Refresh lại để tránh dữ liệu cache
        driver.navigate().refresh();
        
        Thread.sleep(2000);
        
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi thêm Test Case: " + e.getMessage());
            QuanLyDiem.congDiem("Them_TestCase", false, 0);
        }
    }
}
