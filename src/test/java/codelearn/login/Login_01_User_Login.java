package codelearn.login;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import codelearn.data.UserLocatorMapper;
import commons.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import pageObjects.PageGenerateManager;
import pageObjects.UserHomePO;

public class Login_01_User_Login extends BaseTest{
	
	private String language;

	@Parameters({"envName", "serverName", "browserName", "ipAddress", "portNumber", "osName", "osVersion", "lang"})
	@BeforeClass
	public void beforeClass(
			@Optional("local") String envName, 
			@Optional("user") String serverName, 
			@Optional("chrome") String browserName, 
			@Optional("localhost") String ipAddress, 
			@Optional("4444") String portNumber, 
			@Optional("OS X")String osName, 
			@Optional("Big Sur")String osVersion,
			@Optional("english") String lang) {
		
		log.info("Pre-condition - Step01: Open browser and admin url");
		driver = getBrowserDriver(envName, serverName, browserName, ipAddress, portNumber, osName, osVersion);
		this.language = lang;
		
		userHomePO = PageGenerateManager.getUserHomePage(driver);
		
		userLocatorMapper = userLocatorMapper.getUserLocatorMapper(this.language);
		
	
	}
	
	@Description("Guest visit the home page")
	@Story("Guest visit the home page")
	@Test
	public void TC_01_Visit_Homepage() {
		log.info("Visit_HomePage - Step 01: User clicks on the Language icon");
		userHomePO.clickToLanguageIcon();
		
		log.info("Visit_HomePage - Step 02: Users selects language is "+ this.language);
		userHomePO.selectLanguageByValue(driver, this.language);
		
		log.info("Visit_HomePage - Step 03: Verify the main menu");
		Assert.assertTrue(userHomePO.isLearningMenuDisplayed(driver, userLocatorMapper.getLearningMenuAtHomePage()));
		
	}

	
	@AfterTest(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}
	
    private WebDriver driver;
	private UserLocatorMapper userLocatorMapper;
	private UserHomePO userHomePO;

}
