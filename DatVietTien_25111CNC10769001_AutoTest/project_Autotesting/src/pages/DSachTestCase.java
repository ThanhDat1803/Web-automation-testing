package pages;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import base.BaseTest;
import utils.QuanLyDiem;
public class DSachTestCase extends BaseTest{
	
	public static void testDSachTestCase()throws InterruptedException  {
		// TODO Auto-generated method stub
		 // Click vào menu Testcase
	      Thread.sleep(5000);
	      driver.findElement(By.xpath("/html/body/div/div/nav/div/div[1]/div[2]/a[2]")).click();
	      Thread.sleep(3000);
	    
	      // Danh sách cột mong đợi (chia rõ từng tên cột)
	      try {
	    	    // 🔹 Danh sách header mong đợi
	    	    List<String> expectedHeaders = Arrays.asList(
	    	        "Testcase ID", "SRS", "Title", "Estimation", "Test Type", "Area", 
	    	        "Pre-condition", "Steps", "Test Data", "Expected Results", 
	    	        "Priority", "Test Result", "Author", "Note", "Created Date"
	    	    );

	    	    // 🔹 Lấy tất cả <th> trong thead
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
	    	    System.out.println("🔎 Kiểm tra danh sách cột Testcase:");
	    	    System.out.println("Expected headers (" + expectedHeaders.size() + "): " + expectedHeaders);
	    	    System.out.println("Actual headers   (" + actualHeaders.size() + "): " + actualHeaders);
	    	    System.out.println("------------------------------------------");

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
	    	        QuanLyDiem.congDiem("Check_Testcase_Headers", true, 0.4);
	    	    } else {
	    	        QuanLyDiem.congDiem("Check_Testcase_Headers", false, 1.0);
	    	    }

	    	    // 🔹 Assert TestNG
	    	    Assert.assertTrue(finalResult, "❌ Header bảng Testcase KHÔNG khớp danh sách mong đợi!");

	    	} catch (NoSuchElementException e) {
	    	    System.out.println("❌ Không tìm thấy header bảng Testcase: " + e.getMessage());
	    	    QuanLyDiem.congDiem("Check_Testcase_Headers", false, 1.0);
	    	} catch (Exception e) {
	    	    System.out.println("⚠️ Lỗi kiểm tra header bảng Testcase: " + e.getMessage());
	    	    QuanLyDiem.congDiem("Check_Testcase_Headers", false, 1.0);
	    	}
	      System.out.println("==========================================");  
	  }
}
