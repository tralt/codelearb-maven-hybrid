package commons;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageFactory {
	private long longTimeout = GlobalConstants.LONG_TIMEOUT;
	
	// constructor method 
	public static BasePageFactory getBasePageObject() {
		return new BasePageFactory();
	}
	
	/**
	 * Common function
	 * Open an URL
	 * */
	protected void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}
	
	/**
	 * Common function
	 * Get Title of the current page
	 * */
	protected String getTitle(WebDriver driver) {
		return driver.getTitle();
	}
	
	/**
	 * Common function
	 * Get URL of the current page
	 * */
	protected String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}
	
	/**
	 * Common function
	 * Get Source Code of the current page
	 * */
	protected String getPageSourceCode(WebDriver driver) {
		return driver.getPageSource();
	}
	
	/**
	 * Common function
	 * Back to the previous page
	 * */
	protected void backToPage(WebDriver driver) {
		driver.navigate().back();
	}
	
	/**
	 * Common function
	 * Forward to the page
	 * */
	protected void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}
	
	/**
	 * Common function
	 * Refresh the current page
	 * */
	protected void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	/**
	 * Common function
	 * Wait for the alert presence
	 * */
	protected Alert waitForAlertPresence(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, this.longTimeout);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}
	
	protected void acceptAlert(WebDriver driver) {
		waitForAlertPresence(driver).accept();
	}
	
	protected void cancelAlert(WebDriver driver) {
		waitForAlertPresence(driver).dismiss();
	}
	
	protected String getAlertText(WebDriver driver) {
		return waitForAlertPresence(driver).getText();
	}
	
	protected void sendkeyToAlert(WebDriver driver, String textValue) {
		waitForAlertPresence(driver).sendKeys(textValue);
	}
	
	/**
	 * Common function
	 * Switch to thw window by ID
	 * Có thể viết thành 1 method trong trường hợp chỉ có 2 tab/window 
	 * */
	protected void switchToWindowByID (WebDriver driver, String currentWindowID) {
		Set<String> allWindowsIDs = driver.getWindowHandles();
		for (String id : allWindowsIDs) {
			if (!id.equals(currentWindowID)) {
				driver.switchTo().window(id);
				}	
			}
	}
		
	/**
	 * Common function
	 * Switch to the window by the title of page
	 * Dùng được cho trường hợp có nhiều tab
	 * */
	protected void switchToWindowByTitlePage(WebDriver driver, String expectedTitle) {
		Set<String> allWindowsIDs = driver.getWindowHandles();
		for (String id : allWindowsIDs) {
			driver.switchTo().window(id);
			String actualTitleString = driver.getTitle();
				if (actualTitleString.equals(expectedTitle)) {
					break;
				}
		}
	}
		
	/**
	 * Common function
	 * Switch to the window by the link
	 * */
	protected void switchToWindowByLink(WebDriver driver, String expectedRelativeLink) {
		Set<String> allWindowsIDs = driver.getWindowHandles();
			for (String id : allWindowsIDs) {
				driver.switchTo().window(id);
					String actualLink = driver.getCurrentUrl();
						if (actualLink.contains(expectedRelativeLink)) {
							break;
						}
				}
		}
	
	protected void closeAllTabWindowParent(WebDriver driver, String parentID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			if (!id.equals(parentID)) {
				driver.switchTo().window(id);
				driver.close();
			}
			driver.switchTo().window(parentID);
		}
	}
	
	private By getByXpath(String xpathLocator) {
		 return By.xpath(xpathLocator);
	}
	
	private WebElement getWebElement(WebDriver driver, String xpathLocator) {
		return driver.findElement(By.xpath(xpathLocator));
	}
	
	protected List<WebElement> getListWebElements(WebDriver driver, String xpathLocator){
		return driver.findElements(getByXpath(xpathLocator));
	}
	
	protected void clickToElement(WebDriver driver, WebElement element) {
		element.click();
	}
	
	protected void sendkeyToElement(WebDriver driver, WebElement element, String textValue) {
		element.clear();
		element.sendKeys(textValue);
	}
	
	protected void selectItemInDefaultDropdown(WebDriver driver, WebElement element, String textItem) {
		Select select = new Select(element);
		select.selectByValue(textItem);
	}
	
	protected String getFirstSelectedItemDefaultDropdown(WebDriver driver, WebElement element) {
		Select select = new Select(element);
		 return select.getFirstSelectedOption().getText();
	}
	
	protected Boolean isDropdownMultiple(WebDriver driver, WebElement element) {
		Select select = new Select(element);
		return select.isMultiple();
	}
	
	
	private void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected String getElementAttribute(WebDriver driver, WebElement element, String attributeName) {
	  return element.getAttribute(attributeName);
	}
	
	protected String getElementText(WebDriver driver, WebElement element) {
		return element.getText();
	}
	
	protected String getElementCssValue(WebDriver driver, WebElement element, String propertyName) {
		return element.getCssValue(propertyName);
	}

	protected String getHexaColorFromRgba(String rgbaValue) {
		return Color.fromString(rgbaValue).asHex();
	}
	
	protected Integer getElementSize(WebDriver driver, String xpathLocator) {
		return getListWebElements(driver, xpathLocator).size();
	}
	
	protected void checkToDefaultCheckboxRadio(WebDriver driver, WebElement element) {
		if (!element.isSelected()) {
			element.click();
		}
	}
	
	protected void UncheckToDefaultCheckboxRadio(WebDriver driver, WebElement element) {
		if (element.isSelected()) {
			element.click();
		}
	}
	
	protected Boolean isElementDisplayed(WebDriver driver, WebElement element) {
		return element.isDisplayed();
	}
	
	protected Boolean isElementEnabled(WebDriver driver, WebElement element) {
		return element.isEnabled();
	}
	
	protected Boolean isElementSelected(WebDriver driver, WebElement element) {
		return element.isSelected();
	}
	
	protected void switchToFrameIframe(WebDriver driver, WebElement element) {
		driver.switchTo().frame(element);
	}
	
	protected void switchDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	// <Javascript Executor>
	protected void hoverMouseToElement(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}
	
	protected Object executeForBrowser(WebDriver driver, String javaScript) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javaScript);
	}

	protected String getInnerText(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	protected boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	protected void scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	protected void navigateToUrlByJS(WebDriver driver, String url) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	protected void highlightElement(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(driver, xpathLocator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	protected void clickToElementByJS(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, xpathLocator));
	}

	protected void scrollToElement(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, xpathLocator));
	}

	protected void sendkeyToElementByJS(WebDriver driver, String xpathLocator, String value) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getWebElement(driver, xpathLocator));
	}

	protected void removeAttributeInDOM(WebDriver driver, String xpathLocator, String attributeRemove) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, xpathLocator));
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, this.longTimeout);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	protected String getElementValidationMessage(WebDriver driver, WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", element);
	}

	protected boolean isImageLoaded(WebDriver driver, WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", element);
		if (status) {
			return true;
		} else {
			return false;
		}
	}
	// </Javascript Executor>
	
	// <Wait>
	protected void waitForElementVisible(WebDriver driver, WebElement element) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOf(element));
	}
	
	protected void waitForAllElementsVisible(WebDriver driver, List<WebElement> elements) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}
	
	protected void waitForElementInvisible(WebDriver driver, WebElement element) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOf(element));
	}
	
	protected void waitForAllElementsInvisible(WebDriver driver, List<WebElement> elements) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(elements));
	}
	
	protected void waitForElementClickable(WebDriver driver, WebElement element) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(element));
	}
	// </Wait>
	
	
}
