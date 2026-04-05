package tests;
import base.BaseTest;
import pages.AddSRS;
import pages.DeleteSRS;
import pages.UpdateSRS;
import utils.QuanLyDiem;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.awt.AWTException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class test_SRS extends BaseTest {
	AddSRS addSRS;
	UpdateSRS updateSRS;
	DeleteSRS deleteSRS;
    WebDriverWait wait;
  @Test
  public void ChucNang_SRS() throws InterruptedException, AWTException {
	  driver.findElement(By.xpath("/html/body/div/div/nav/div/div[1]/div[2]/a[4]")).click();
	  Thread.sleep(3000);
	  addSRS = new AddSRS(driver);
	  addSRS.addSRS_FromJSON("E:\\srs.json");
	  addSRS.soSanh("E:\\srs.json");
	  Thread.sleep(3000);
	  JSONParser parser = new JSONParser();
	  updateSRS = new UpdateSRS(driver);
	  try {
          // 1. Load file JSON
          FileReader reader = new FileReader("E:\\srs_update.json");
          JSONArray jsonArray = (JSONArray) parser.parse(reader);

          // 2. Duyệt từng SRS và gọi updateTestcase
          for (Object obj : jsonArray) {
              JSONObject srsObj = (JSONObject) obj;
              updateSRS.updateSRS(srsObj);
          }
      } catch (Exception e) {
          System.out.println("⚠️ Lỗi khi đọc JSON hoặc update SRS: " + e.getMessage());
      }
	  Thread.sleep(3000);
	  deleteSRS = new DeleteSRS(driver);
	  deleteSRS.deleteSRS("SRS_112");
	  QuanLyDiem.inTongKet();
  }
  @BeforeClass
  public void beforeClass() throws InterruptedException {
  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}
