package pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;

import dataprovider.Data;

public class MailPage {
	private static String URL_MATCH = "compose";
	private static WebDriver driver;
	private static final String COMPOSE_URL = "https://e.mail.ru/compose/";
	private static final String ADDRESS_LOCATOR = "//textarea[@data-original-name='To']";
	private static final String SUBJECT_LOCATOR = "Subject";
	private static final String SEND_BUTTON_LOCATOR = "//*[@data-name='send']";
	private static final String IS_SAVED_MESSAGE = "//*[@class='b-toolbar__message']";
	private static final String SAVE_DRAFT_BUTTON_LOCATOR = "//*[@data-name='saveDraft']";
	private static final String TEXT_BOX_LOCATOR = "//*[@id='tinymce']";
	private static final String IFRAME_TEXT_FRAME_LOCATOR = "//iframe[contains(@id, 'composeEditor_ifr')]";
	protected static final String LOGOUT_LINK = "PH_logoutLink";

	
	@FindBy(xpath = ADDRESS_LOCATOR)
	private WebElement address;

	@FindBy(name= SUBJECT_LOCATOR)
	private WebElement subject;

	@FindBy(xpath = TEXT_BOX_LOCATOR)
	private WebElement textBoxLoc;

	@FindBy(xpath = IFRAME_TEXT_FRAME_LOCATOR)
	private WebElement iframeTextBox;

	@FindBy(xpath = IS_SAVED_MESSAGE)
	private WebElement savedLetterNotification;

	@FindBy(xpath = SAVE_DRAFT_BUTTON_LOCATOR)
	private WebElement saveDraftButton;

	@FindBy(xpath = SEND_BUTTON_LOCATOR)
	private WebElement sendButton;

	@FindBy(id = LOGOUT_LINK)
	private WebElement logout;

	public MailPage(WebDriver driver) {

		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void writeLetter() {
		driver.get(COMPOSE_URL);
		//explicitWait.until(ExpectedConditions.visibilityOfElementLocated((By) address));
	}

	public void writeAddress(Data data) {
//		WebDriverWait explicitWait = (new WebDriverWait(driver, 10));
//		explicitWait.until(ExpectedConditions.visibilityOfElementLocated((By) address));
		
		address.click();
		address.sendKeys(data.getAddress());
//		explicitWait.until(ExpectedConditions.visibilityOfElementLocated((By) subject));
	}

	public void writeSubject(Data data) {

		subject.click();
		subject.sendKeys(data.getSubject());
		
	}

	public void writeBody(Data data) {
		Actions actions = new Actions(driver);
		WebElement textFrame = iframeTextBox;
		driver.switchTo().frame(textFrame);
		WebElement textBox = textBoxLoc;
		actions.click(textBox).sendKeys(data.getBody()).build().perform();
		driver.switchTo().defaultContent();
		//explicitWait.withTimeout(5, TimeUnit.SECONDS);
	}

	public void saveLetter() {
		saveDraftButton.click();
	    try {
	    	Alert alert = driver.switchTo().alert();
	    	if (alert != null && alert.getText().length() > 1) {
	    	alert.accept();
	    	return;
	    	}

	    	} catch (Exception e) {
	    	e.getSuppressed();
	    	}
//		WebDriverWait explicitWait = (new WebDriverWait(driver, 10));
//		explicitWait.until(ExpectedConditions.visibilityOfElementLocated((By) savedLetterNotification));
	}

	public void sendLetter() {
		driver.getCurrentUrl();
		sendButton.click();

		// driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}

    public boolean isAlertPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
    

    	}

