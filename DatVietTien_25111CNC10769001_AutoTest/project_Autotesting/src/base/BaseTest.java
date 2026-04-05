package base;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;

public class BaseTest {
	protected static WebDriver driver;
  @BeforeSuite(alwaysRun = true)
  public void setUp() throws InterruptedException {
	  System.out.println("Bắt đầu test");
	  System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
	  driver = new ChromeDriver();
	  driver.manage().window().maximize();
	  driver.get("https://pst.plt.pro.vn/");
	  Thread.sleep(3000);
	  login();
  }
  protected void login() throws InterruptedException {
      // Thực hiện nhập credential và click login
      driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[2]/div/form/div[1]/input"))
              .sendKeys("tester@plt.pst.com");
      Thread.sleep(1000);
      driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div[2]/div/form/div[2]/input"))
              .sendKeys("123456");
      Thread.sleep(1000);
      driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/div/form/button")).click();
      Thread.sleep(5000);
  }
  @AfterSuite(alwaysRun = true)
  public void afterSuite() {
	// Nếu muốn giữ browser mở giữa các lần bấm nút thì để comment; nếu muốn đóng cuối cùng, bật lại driver.quit()
      if (driver != null) {
          // driver.quit();
          System.out.println("Đã hoàn thành test!");
      }
  }
}
