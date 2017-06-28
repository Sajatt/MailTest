package test.java;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;



import dataprovider.Data;
import pages.Draft;
import pages.MailPage;
import pages.MainPage;
import pages.Sent;

public class LogInTest {
	WebDriverWait wait;
	static WebDriver driver;
	String address;
	String subject;
	String body;
	String[] line;
	private static final String FILE_URL = "C:\\Users\\Anna_Ivanova\\workspace\\mail\\src\\resourse\\mail_template.txt";
	private static final String MAIL_RU_URL = "https://mail.ru";
	public static final By LOGOUT_LINK_LOCATOR = By.id("PH_logoutLink");
	
	
	
	@Parameters("browser")
	@BeforeClass
	public void setUp(String browser) {
		 
		  if(browser.equalsIgnoreCase("firefox")) {
		 
			  driver = new FirefoxDriver();
		 
		 
		  }else if (browser.equalsIgnoreCase("chrome")) { 
		 
			  System.setProperty("webdriver.chrome.driver", "D:\\seleniumgrid\\chromedriver.exe");
		 
			  driver = new ChromeDriver();
		 
		  } 
				
//		
//		DesiredCapabilities capabilities = new DesiredCapabilities();
//		capabilities.setBrowserName("firefox");
//		capabilities.setPlatform(Platform.WINDOWS);
//		try {
//			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
		driver.get(MAIL_RU_URL);
		 wait = (new WebDriverWait(driver, 10));
wait.withTimeout(10, TimeUnit.SECONDS);
// driver = new FirefoxDriver();
		//driver.get(MAIL_RU_URL);
		//explicitWait.withTimeout(5, TimeUnit.SECONDS);
	}
	
	 @AfterMethod
	 public void driverWait(){
		 wait.withTimeout(10, TimeUnit.SECONDS);
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	 }
	 
	@SuppressWarnings("static-access")
	@Test
	public void loginTest() {

		MainPage page = PageFactory.initElements(driver, MainPage.class);
		page.logIn();
		Assert.assertTrue(page.isElementPresent(LOGOUT_LINK_LOCATOR));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Sucsessfully loged in");
		
	}

	@Test(dataProvider = "mailData", dependsOnMethods = { "loginTest" })
	public void writeLetterTest(Data data) throws InterruptedException {
				
		MailPage mail = PageFactory.initElements(driver, MailPage.class);
		mail.writeLetter();
		wait.withTimeout(10, TimeUnit.SECONDS);

		mail.writeAddress(data);
		wait.withTimeout(5, TimeUnit.SECONDS);
		mail.writeSubject(data);
		wait.withTimeout(5, TimeUnit.SECONDS);
		mail.writeBody(data);
		mail.saveLetter();
		
		System.out.println("The letter was written &saved sucsessfully");

		Draft draft = PageFactory.initElements(driver, Draft.class);
		driver.get(draft.DRAFTS_URL);
		wait.withTimeout(10, TimeUnit.SECONDS);
		String subj = data.getSubject();
		String address = data.getAddress();
		String body = data.getBody();

		Assert.assertTrue(!(driver.findElements(By.xpath("//div[contains(text(),'" + subj + "')]")).isEmpty()),
				"Can`t find subject");
		Assert.assertTrue(!(driver.findElements(By.xpath("//div[contains(text(),'" + address + "')]")).isEmpty()),
				"Can`t find address");
		Assert.assertTrue(
				!(driver.findElements(By.xpath("//span[contains(text(),'" + body.substring(0, 20) + "')]")).isEmpty()),
				"Can`t find inner text");
		System.out.println("The letter is in the draft box");

	}

	@Test(dataProvider = "mailData", dependsOnMethods = { "writeLetterTest" })
	public void sendLetterTest(Data data) {
		String subj = data.getSubject();
		String address = data.getAddress();
		String body = data.getBody();
		Draft draft = PageFactory.initElements(driver, Draft.class);
		draft.clickDraft(data);
		MailPage mail = PageFactory.initElements(driver, MailPage.class);
		mail.sendLetter();
		Sent sent = PageFactory.initElements(driver, Sent.class);
		driver.get(sent.URL_SENT);
		wait.withTimeout(10, TimeUnit.SECONDS);

		Assert.assertTrue(!(driver.findElements(By.xpath("//div[contains(text(),'" + subj + "')]")).isEmpty()),
				"Can`t find subject");
		wait.withTimeout(5, TimeUnit.SECONDS);
		Assert.assertTrue(!(driver.findElements(By.xpath("//div[contains(text(),'" + address + "')]")).isEmpty()),
				"Can`t find address");
		wait.withTimeout(5, TimeUnit.SECONDS);
		Assert.assertTrue(!(driver.findElements(By.xpath("//span[contains(text(),'" + body.substring(0, 20) + "')]")).isEmpty()),
				"Can`t find inner text");
		wait.withTimeout(5, TimeUnit.SECONDS);
		System.out.println("The letter was sucsessfuly sent");

	}

 
	@AfterClass
	public void cleanUpAndLogOut() throws InterruptedException {
		System.out.println("Cleaning sent box...");
		Sent sent = PageFactory.initElements(driver, Sent.class);
		sent.cleanUpSent();
		System.out.println("Sent box is empty");
		System.out.println("Cleaning draft box...");
		Draft draft = PageFactory.initElements(driver, Draft.class);
		draft.cleanUpDraft();
		System.out.println("Draft box is empty");
		draft.logOut();
		 wait.withTimeout(10, TimeUnit.SECONDS);
		Assert.assertTrue(Draft.isElementPresent((By) Draft.logout), "Wrong page");
		System.out.println("Sucsessfully loged out");
		driver.quit();

	}

	

		

	@SuppressWarnings("finally")
	@DataProvider(name = "mailData")
	public Object[][] readFromFile() {

		List<Data> dataList = new ArrayList<Data>();
		BufferedReader readFromFile = null;
		try {
			readFromFile = new BufferedReader(new FileReader(FILE_URL));
			while ((line = readFromFile.readLine().split(";")) != null) {

				Data newData = new Data(line[0], line[1], line[2]);
				dataList.add(newData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (readFromFile != null)
				try {
					readFromFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			Object[][] dataArray = new Object[dataList.size()][1];

			for (int i = 0; i < dataList.size(); i++) {
				dataArray[i][0] = dataList.get(i);

				dataArray.toString();

			}
			return dataArray;
		}
	}
}
