package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Xpath_PartII {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String firstname,lastname, emailAddress, password, fullname ;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "/Users/tungluu/Downloads/software/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		firstname = "luu";
		lastname = "Tung";
		fullname = firstname + " " + lastname;
		emailAddress = "abcd"+ generateRandomNumber() +"@gmail.com";
		password = "123456";
	}

	@Test
	public void TC_01_Login_with_empty() {
		driver.get("http://live.techpanda.org/index.php");
		driver.findElement(By.xpath("//li[@class='first']/following::a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.name("login[password]")).sendKeys("");
        driver.findElement(By.xpath("//div[@class='buttons-set']/button[@title='Login']")).click();       
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
        Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");
	}

	@Test
	public void TC_02_Login_with_invalid_email() {
		
		driver.get("http://live.techpanda.org/index.php");
		driver.findElement(By.xpath("//li[@class='first']/following::a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("123434234@12323.123");
		driver.findElement(By.name("login[password]")).sendKeys("123456");
        driver.findElement(By.xpath("//div[@class='buttons-set']/button[@title='Login']")).click();       
        Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");

	}


	@Test
	public void TC_03_Login_with_password_less_6_characters() {
		driver.get("http://live.techpanda.org/index.php");
		driver.findElement(By.xpath("//li[@class='first']/following::a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("luutung@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("123");
        driver.findElement(By.xpath("//div[@class='buttons-set']/button[@title='Login']")).click();       
        Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
		
	}
	@Test
	public void TC_04_Login_with_incorrect_email_and_password() {
		driver.get("http://live.techpanda.org/index.php");
		driver.findElement(By.xpath("//li[@class='first']/following::a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("luutung@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("123456");
        driver.findElement(By.xpath("//div[@class='buttons-set']/button[@title='Login']")).click();       
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg'] //span")).getText(), "Invalid login or password.");
		
	}
	@Test
	public void TC_05_Create_a_new_account() {
		driver.get("http://live.techpanda.org/index.php");
		driver.findElement(By.xpath("//li[@class='first']/following::a[@title='My Account']")).click();
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		driver.findElement(By.id("firstname")).sendKeys(firstname);
		driver.findElement(By.id("lastname")).sendKeys(lastname);
		driver.findElement(By.id("email_address")).sendKeys(emailAddress);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirmation")).sendKeys(password);
		driver.findElement(By.xpath("//button[@title='Register']")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg'] //span")).getText(), "Thank you for registering with Main Website Store.");
        String contacinforText = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contacinforText.contains(fullname));
        Assert.assertTrue(contacinforText.contains(emailAddress));
        driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//span[text()='Account']")).click();
        driver.findElement(By.xpath("//a[text()='Log Out']")).click();

	}
	@Test
	public void TC_06_Login_with_valid_email_and_password() {
		
		driver.get("http://live.techpanda.org/index.php");
		driver.findElement(By.xpath("//li[@class='first']/following::a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("abc33@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys(password);
        driver.findElement(By.xpath("//div[@class='buttons-set']/button[@title='Login']")).click();       
        String contacinforText = driver.findElement(By.xpath("//h3[text()='Contact Information']/parent::div/following-sibling::div/p")).getText();
        Assert.assertTrue(contacinforText.contains("Luu Tung"));
        Assert.assertTrue(contacinforText.contains("abc33@gmail.com"));
	}
	

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
	public int generateRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(99999);
	}
}