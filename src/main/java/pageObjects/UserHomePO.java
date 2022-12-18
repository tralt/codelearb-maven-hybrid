package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import io.qameta.allure.Step;
import pageUI.UserHomePUI;

public class UserHomePO extends BasePage{
	
	WebDriver driver;
	
	public UserHomePO(WebDriver driver) {
		this.driver = driver;
	}

	@Step("Click to the {1} ")
	public void clickToLanguageIcon() {
		waitForElementClickable(driver, UserHomePUI.LANGUAGE_ICON);
		clickToElement(driver, UserHomePUI.LANGUAGE_ICON);
	}

	@Step("Select to the {1} language")
	public void selectLanguageByValue(WebDriver driver, String language) {
		switch (language) {
		case "english":
			waitForElementClickable(driver, UserHomePUI.LANGUAGE_BUTTON_BY_TEXT, "English");
			clickToElement(driver, UserHomePUI.LANGUAGE_BUTTON_BY_TEXT, "English");
			break;
			
		case "vietnamese": 
			waitForElementClickable(driver, UserHomePUI.LANGUAGE_BUTTON_BY_TEXT, "Tiếng Việt");
			clickToElement(driver, UserHomePUI.LANGUAGE_BUTTON_BY_TEXT, "Tiếng Việt");
			break;

		default:
			waitForElementClickable(driver, UserHomePUI.LANGUAGE_BUTTON_BY_TEXT, "English");
			clickToElement(driver, UserHomePUI.LANGUAGE_BUTTON_BY_TEXT, "English");
			break;
		}
	}

	@Step("Verify the 'Learning' main menu is displayed")
	public boolean isLearningMenuDisplayed(WebDriver driver, String textLocator) {
		waitForElementVisible(driver, UserHomePUI.MAIN_MENU_BY_TEXT, textLocator);
		return isElementDisplayed(driver, UserHomePUI.MAIN_MENU_BY_TEXT, textLocator);
	}

}
