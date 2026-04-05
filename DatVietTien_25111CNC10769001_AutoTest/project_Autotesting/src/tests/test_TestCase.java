package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import models.TestCase;
import pages.AddTestCasePage;
import pages.DeleteTestCase;
import pages.UpdateTestCase;
import utils.QuanLyDiem;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;

import java.awt.AWTException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.*;

public class test_TestCase extends BaseTest {
	AddTestCasePage addTC;
	UpdateTestCase updateTC;
	DeleteTestCase deleteTC;
  @Test (priority = 1)
  public void addTestCase() throws InterruptedException, AWTException {
	  addTC = new AddTestCasePage(driver);
      updateTC = new UpdateTestCase(driver);
      deleteTC = new DeleteTestCase(driver);
	  //Click module Testcase
	  driver.findElement(By.xpath("/html/body/div/div/nav/div/div[1]/div[2]/a[2]")).click();
	  Thread.sleep(3000);
	  //Click Add more
	  driver.findElement(By.xpath("/html/body/div/div/main/div/div/div[2]/div[2]/button[1]")).click();
	  addTC.addTestCase_FromJSON("E:\\TestCase.json");
	  Thread.sleep(3000);
	  addTC.soSanhTestCase("E:\\TestCase.json");
	  Thread.sleep(3000);
//	  String oldTCID = "TC_111";
//	  String newTitle = "Title_After_Update_1";
	  JSONParser parser = new JSONParser();
	  try {
          // 1. Load file JSON
          FileReader reader = new FileReader("E:\\TestCase_Update.json");
          JSONArray jsonArray = (JSONArray) parser.parse(reader);

          // 2. Duyệt từng SRS và gọi updateTestcase
          for (Object obj : jsonArray) {
              JSONObject srsObj = (JSONObject) obj;
              updateTC.updateTestCase(srsObj);
          }
      } catch (Exception e) {
          System.out.println("⚠️ Lỗi khi đọc JSON hoặc update SRS: " + e.getMessage());
      }
	  deleteTC.deleteTestCase("TC_111");
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
