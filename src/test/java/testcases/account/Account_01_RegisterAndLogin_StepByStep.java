package testcases.account;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Account_01_RegisterAndLogin_StepByStep {
    WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver.get("http://demo.guru99.com/v4/");
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void TC_01_RegisterToS() {
        System.out.println("Step 01: Click on  link ");
        driver.findElement(By.xpath("//a[text()='here']")).click();
    }

    @AfterClass
    public void afterClass() {

    }
}
