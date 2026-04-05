package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.support.Color;

import base.BaseTest;
import utils.QuanLyDiem;

public class MauSac extends BaseTest {

	public static void testMauSac() throws InterruptedException {
		// TODO Auto-generated method stub
		 // Điều hướng đến trang Testcase
        driver.findElement(By.xpath("//a[contains(text(),'Testcase')]")).click();
        Thread.sleep(3000);

        System.out.println("================================");
        // 🔹 Lấy các nút cần kiểm tra
        WebElement btnAdd = driver.findElement(By.xpath("//button[contains(text(), 'Add')]"));
        WebElement btnUpdate = driver.findElement(By.xpath("//button[contains(text(), 'Update')]"));
        WebElement btnDelete = driver.findElement(By.xpath("//button[contains(text(), 'Delete')]"));

        // 🔹 Lấy màu thực tế (CSS background-color)
        String actualAddColor = btnAdd.getCssValue("background-color");
        String actualUpdateColor = btnUpdate.getCssValue("background-color");
        String actualDeleteColor = btnDelete.getCssValue("background-color");

        // 🔹 Màu mong đợi
        String expectedAddColor = "oklch(0.546 0.245 262.881)"; //xanh
        String expectedUpdateColor = "oklch(0.681 0.162 75.834)";//vành
        String expectedDeleteColor = "oklch(0.577 0.245 27.325)";//đỏ

        // 🔹 So sánh bằng boolean
        boolean resultAdd = actualAddColor.toLowerCase().contains(expectedAddColor);
        boolean resultUpdate = actualUpdateColor.toLowerCase().contains(expectedUpdateColor);
        boolean resultDelete = actualDeleteColor.toLowerCase().contains(expectedDeleteColor);

        // 🔹 In kết quả ra console
        System.out.println("Button Add    - Expected: " + expectedAddColor + " | Actual: " + actualAddColor + " | Result: " + resultAdd);
        System.out.println("Button Update - Expected: " + expectedUpdateColor + " | Actual: " + actualUpdateColor + " | Result: " + resultUpdate);
        System.out.println("Button Delete - Expected: " + expectedDeleteColor + " | Actual: " + actualDeleteColor + " | Result: " + resultDelete);
        // ✅ Chỉ cộng điểm nếu cả 3 đều đúng
        boolean fullPass = resultAdd && resultUpdate && resultDelete;

        // Ví dụ chỉ thưởng 1.5 điểm nếu PASS trọn bộ:
        QuanLyDiem.congDiem("UI Color - Bộ 3 nút (Add/Update/Delete)", fullPass, 0.5);

        System.out.println("================================");
    }
}
