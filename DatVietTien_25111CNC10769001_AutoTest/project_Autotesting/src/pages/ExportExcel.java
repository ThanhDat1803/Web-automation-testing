package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.BaseTest;
import utils.QuanLyDiem;

public class ExportExcel extends BaseTest{
	public static void testExportExcel() throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		  Thread.sleep(5000);
	        // Điều hướng đến trang Defects
	        driver.findElement(By.xpath("/html/body/div/div/nav/div/div[1]/div[2]/a[3]")).click();
	        Thread.sleep(2000);

	        // Chọn tất cả checkbox trong bảng
//	        driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[3]/table/thead/tr/th[1]/input")).click();
//	        Thread.sleep(2000);
	        //Chọn 5 cái trong Defect
	        driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[3]/table/tbody/tr[1]/td[1]/input")).click();
	        Thread.sleep(3000);
	        
	        driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[3]/table/tbody/tr[2]/td[1]/input")).click();
	        Thread.sleep(3000);
	        
	        driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[3]/table/tbody/tr[3]/td[1]/input")).click();
	        Thread.sleep(3000);
	        
	        driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[3]/table/tbody/tr[4]/td[1]/input")).click();
	        Thread.sleep(3000);
	        
	        driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[3]/table/tbody/tr[5]/td[1]/input")).click();
	        Thread.sleep(3000);
	        // Nhấn Export Excel
	        driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[2]/div[2]/button[4]")).click();
	        Thread.sleep(3000);

	        // Lấy số dòng hiển thị trên giao diện (trừ header)
	        List<WebElement> rows = driver.findElements(By.xpath("/html/body/div/div/main/div/div/div[3]/table/tbody/tr"));
	        int expectedRowCount = rows.size();

	        // File Excel xuất ra
	        String excelPath = "C:\\Users\\pc\\Downloads\\Defects_Test User.xlsx";

	        FileInputStream fis = new FileInputStream(excelPath);
	        int actualRowCount = 0;

	        try (XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
	            XSSFSheet sheet = workbook.getSheetAt(0);
	            actualRowCount = sheet.getLastRowNum(); // đếm từ 0 → bỏ header = dữ liệu thực tế
	            System.out.println("==========================================");
	            System.out.println("📘 Đang đọc file: " + excelPath);
	            System.out.println("Số dòng trong Excel (trừ header): " + actualRowCount);
	            System.out.println("------------------------------------------");

	            // In nội dung Excel (chỉ 5 dòng đầu tiên cho gọn)
	            int maxRowToShow = Math.min(actualRowCount, 5);
	            for (int a = 0; a <= maxRowToShow; a++) {
	                XSSFRow row = sheet.getRow(a);
	                if (row == null) continue;
	                for (int i = 0; i < row.getLastCellNum(); i++) {
	                    XSSFCell cell = row.getCell(i);
	                    if (cell == null) {
	                        System.out.print("NULL || ");
	                    } else {
	                        System.out.print(cell.toString() + " || ");
	                    }
	                }
	                System.out.println();
	            }
	            System.out.println("==========================================");
	        }

	        // So sánh kết quả bằng boolean
	        boolean isMatch = (expectedRowCount == actualRowCount);

	        System.out.println("Expected số dòng (UI): " + expectedRowCount);
	        System.out.println("Actual số dòng (Excel): " + actualRowCount);
	        System.out.println("Kết quả so sánh: " + (isMatch ? "✅ PASS" : "❌ FAIL"));
	     // 🔹 Thêm điểm
	        if (isMatch) {
	            QuanLyDiem.congDiem("Export_Defect_Excel", true, 1.0); // PASS: cộng 1 điểm
	        } else {
	            QuanLyDiem.congDiem("Export_Defect_Excel", false, 1.0); // FAIL: vẫn trừ hoặc 0 điểm
	        }
	        System.out.println("==========================================");

	 
	    }
}
