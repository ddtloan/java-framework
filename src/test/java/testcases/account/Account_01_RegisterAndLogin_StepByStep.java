package testcases.account;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Account_01_RegisterAndLogin_StepByStep {
    WebDriver driver;
    String email, username, password, loginPageUrl;

    @BeforeClass
    public void beforeClass() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/java/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("http://demo.guru99.com/v4/");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        loginPageUrl = driver.getCurrentUrl();
        email = "trucloan" + randomDataTest() + "@yopmail.com";
    }

    @Test
    public void TC_01_RegisterToSystem() {
        System.out.println("Register Test 1 - Step 01: Click to 'here' link ");
        driver.findElement(By.xpath("//a[text()='here']")).click();
        System.out.println("Step 02: Input Email ID ");
        driver.findElement(By.name("emailid")).sendKeys(email);
        System.out.println("Step 03: Click to Submit ");
        driver.findElement(By.name("btnLogin")).click();
        System.out.println("Step 04: Get username / password info ");
        username = driver.findElement(By.xpath("//td[contains(text(),'User ID :')]/following-sibling::td")).getText();
        password = driver.findElement(By.xpath("//td[contains(text(),'Password :')]/following-sibling::td")).getText();
    }

    @Test
    public void TC_02_LoginToSystem() {
        System.out.println("Login to System Tet 2 - Step 01: Open Login Page ");
        driver.get(loginPageUrl);
        System.out.println("Step 02: Input username and password");
        driver.findElement(By.name("uid")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        System.out.println("Step 03: Click login button ");
        driver.findElement(By.name("btnLogin")).click();
        System.out.println("Step 04: Verify welcom message ");
        String welcomeMessage = driver.findElement(By.cssSelector("marquee")).getText();
        Assert.assertEquals(welcomeMessage, "Welcome To Manager's Page of Guru99 Bank");
        System.out.println("Step 05: Verify userid display ");
        Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'Manger Id : " + username + "')]")).isDisplayed());
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    public int randomDataTest() {
        Random random = new Random();
        return random.nextInt(999);
    }
}
