package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.CompareImg;
import pages.Icon;
import pages.KieuChu;
import pages.Layout;
import pages.MauSac;
import utils.QuanLyDiem;

import org.testng.annotations.BeforeClass;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class UITest_TestCasePage extends BaseTest {
	Icon iconTest;
	KieuChu kieuChuTest;
	MauSac mausacTest;
	Layout layoutTest;
	CompareImg ssIMG;
  @Test(priority = 1) //html/body/div[1]/div/nav/div/div[1]/div[1]/div/img
  public void verify() throws InterruptedException {
	iconTest = new Icon();
	kieuChuTest = new KieuChu();
	mausacTest = new MauSac();
	layoutTest = new Layout();
	ssIMG = new CompareImg();
	//Chay test Icon
	Icon.testIcon();
	//Chay test Kieu Chu
	KieuChu.testKieuChu();
	//Chay test MauSac
	MauSac.testMauSac();
	//Chay test layout
	Layout.testLayout();
	//so sáng img
	WebElement imgWeb = driver.findElement(By.xpath("/html/body/div/div/nav/div/div[1]/div[1]/div/img"));
    File expect = new File("E:\\logo.png");
    File actual = imgWeb.getScreenshotAs(OutputType.FILE);
    ssIMG.compareImg(expect, actual);
    QuanLyDiem.inTongKet();
  }
  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
	  driver.quit();
  }

}
