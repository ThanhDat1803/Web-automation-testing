package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import models.TestPlan;
import pages.DeleteTestPlan;
import pages.UpdateSRS;
import pages.UpdateTestPlan;
import utils.QuanLyDiem;

import org.testng.annotations.BeforeClass;

import java.awt.AWTException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

public class test_TestPlan extends BaseTest{
	pages.AddTestPlan addtestplan;
	pages.UpdateTestPlan updatetestplan;
	pages.DeleteTestPlan deleteTP;
  @Test (priority = 1)
  public void AddTestPlan() throws InterruptedException, AWTException {
	  addtestplan = new pages.AddTestPlan(driver);
      updatetestplan = new UpdateTestPlan(driver);
      deleteTP = new DeleteTestPlan(driver);
	  driver.findElement(By.xpath("/html/body/div/div/nav/div/div[1]/div[2]/a[5]")).click();
	  Thread.sleep(3000);
	  addtestplan.addTestPlan_FromJSON("E:\\TestPlan.json");
	  Thread.sleep(3000);
	  addtestplan.soSanh("E:\\TestPlan.json");
	  Thread.sleep(3000);
	  JSONParser parser = new JSONParser();
	  try {
          // 1. Load file JSON
          FileReader reader = new FileReader("E:\\TestPlan_up.json");
          JSONArray jsonArray = (JSONArray) parser.parse(reader);

          // 2. Duyệt từng SRS và gọi updateTestcase
          for (Object obj : jsonArray) {
              JSONObject srsObj = (JSONObject) obj;
              updatetestplan.updateTestCase(srsObj);
          }
      } catch (Exception e) {
          System.out.println("⚠️ Lỗi khi đọc JSON hoặc update SRS: " + e.getMessage());
      }
	  Thread.sleep(3000);
	  deleteTP.deleteTestPlan("Update Login and Authentication Test Plan");
	  // ✅ In tổng kết điểm SAU KHI HOÀN THÀNH 3 BƯỚC  Update Login and Authentication Test Plan
	  QuanLyDiem.inTongKet();
  }
  @BeforeClass
  public void beforeClass() throws InterruptedException {
	  // ✅ Tắt warning Selenium DevTools
	  java.util.logging.Logger.getLogger("org.openqa.selenium.devtools").setLevel(java.util.logging.Level.OFF);
	  java.util.logging.Logger.getLogger("org.openqa.selenium.chromium").setLevel(java.util.logging.Level.OFF);
	  java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);

	    // ✅ Tắt log TestNG
	  java.util.logging.Logger.getLogger("org.testng").setLevel(java.util.logging.Level.OFF);
	  java.util.logging.Logger.getLogger("org.testng.internal").setLevel(java.util.logging.Level.OFF);
  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}
