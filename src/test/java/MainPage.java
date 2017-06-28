package test.java;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MainPage {

	private static String URL_MATCH = "mail";
	private static WebDriver driver;
	private static final String MAIL_RU_URL = "https://mail.ru";
	public static final String MAIL_LOCATOR = "//input[@id='mailbox__login']";
	private static final String PASSWORD_LOCATOR = "//input[@id='mailbox__password']";
	private static final String LOGIN_SUBMIT_BUTTON = "//input[@id='mailbox__auth__button']";
	public static final By LOGOUT_LINK_LOCATOR = By.id("PH_logoutLink");
	private static final String PASSWORD = "Qwerty+123";
	private static final String MAIL = "ya.Testingtest";

	@FindBy(xpath = MAIL_LOCATOR)
	public static WebElement login;

	@FindBy(xpath = PASSWORD_LOCATOR)
	private static WebElement password;

	@FindBy(xpath = LOGIN_SUBMIT_BUTTON)
	private static WebElement submitButton;

	public MainPage(WebDriver driver) {

		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void logIn() {

		login.sendKeys(MAIL);
		password.sendKeys(PASSWORD);
		submitButton.click();
		driver.getCurrentUrl();

	}

	public static boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}
