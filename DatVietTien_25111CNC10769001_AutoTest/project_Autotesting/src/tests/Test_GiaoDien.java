package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.DSachDefect;
import pages.DSachTestCase;
import pages.DropDown;
import pages.ExportExcel;
import pages.MenuTest;
import utils.QuanLyDiem;

import org.testng.annotations.BeforeClass;

import java.io.IOException;

import org.testng.annotations.AfterClass;

public class Test_GiaoDien extends BaseTest {
	MenuTest menuTest;
    DropDown dropDown;
    DSachDefect dSachDefect;
    DSachTestCase dSachTestCase;
    ExportExcel exportExcel;
  @Test
  public void f() throws InterruptedException, IOException {
	  menuTest = new MenuTest();
      dropDown = new DropDown();
      dSachDefect = new DSachDefect();
      dSachTestCase = new DSachTestCase();
      exportExcel = new ExportExcel();
      MenuTest.kiemTraMenu(); // gọi class MenuTest
	  DropDown.kiemtraDropDown(); // gọi class DropDown
	  DSachDefect.testDSachDefect(); // gọi class DSachDefect
	  DSachTestCase.testDSachTestCase();// gọi class DSachTestCase
	  ExportExcel.testExportExcel();// gọi class ExportExcel
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
