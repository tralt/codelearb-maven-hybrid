package pageObjects;

import org.openqa.selenium.WebDriver;


public class PageGenerateManager {

	public static UserHomePO getUserHomePage(WebDriver driver) {
		return new UserHomePO(driver);
	}
}
