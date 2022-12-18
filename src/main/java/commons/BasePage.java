package commons;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import pageUIs.jQuery.uploadFiles.BasePageJQueryUI;

public class BasePage {
	private long longTimeout = GlobalConstants.LONG_TIMEOUT;
	private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;
	
	// constructor method 
	public static BasePage getBasePageObject() {
		return new BasePage();
	}
	
	@Step("Open Page URL '{0}'")
	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}
	
	
	public String getTitle(WebDriver driver) {
		return driver.getTitle();
	}
	
	public String getPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}
	
	public String getPageSourceCode(WebDriver driver) {
		return driver.getPageSource();
	}
	
	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}
	
	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}
	
	public void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	public Alert waitForAlertPresence(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, this.longTimeout);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void acceptAlert(WebDriver driver) {
		waitForAlertPresence(driver).accept();
	}
	
	public void cancelAlert(WebDriver driver) {
		waitForAlertPresence(driver).dismiss();
	}
	
	public String getAlertText(WebDriver driver) {
		return waitForAlertPresence(driver).getText();
	}
	
	public void sendkeyToAlert(WebDriver driver, String textValue) {
		waitForAlertPresence(driver).sendKeys(textValue);
	}
	
	
	public void switchToWindowByID (WebDriver driver, String currentWindowID) {
		Set<String> allWindowsIDs = driver.getWindowHandles();
		for (String id : allWindowsIDs) {
			if (!id.equals(currentWindowID)) {
				driver.switchTo().window(id);
				}	
			}
	}
	
	public void switchToWindowByTitlePage(WebDriver driver, String expectedTitle) {
		Set<String> allWindowsIDs = driver.getWindowHandles();
		for (String id : allWindowsIDs) {
			driver.switchTo().window(id);
			String actualTitleString = driver.getTitle();
				if (actualTitleString.equals(expectedTitle)) {
					break;
				}
		}
	}
	
	public void switchToWindowByLink(WebDriver driver, String expectedRelativeLink) {
		Set<String> allWindowsIDs = driver.getWindowHandles();
			for (String id : allWindowsIDs) {
				driver.switchTo().window(id);
					String actualLink = driver.getCurrentUrl();
						if (actualLink.contains(expectedRelativeLink)) {
							break;
						}
				}
		}
	
	public void closeAllTabWindowParent(WebDriver driver, String parentID) {
		Set<String> allWindowIDs = driver.getWindowHandles();
		for (String id : allWindowIDs) {
			if (!id.equals(parentID)) {
				driver.switchTo().window(id);
				driver.close();
			}
			driver.switchTo().window(parentID);
		}
	}
	
	// locator type: css= / id= / xpath= / name= / class=
	private By getByLocator(String locatorType) {
		
		By by = null;
		
		if (locatorType.startsWith("id=") || locatorType.startsWith("ID=") || locatorType.startsWith("Id=")) {
			by = By.id(locatorType.substring(3));
		} else if (locatorType.startsWith("css=") || locatorType.startsWith("CSS=") || locatorType.startsWith("Css=")) {
			by = By.cssSelector(locatorType.substring(4));
		} else if (locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=") || locatorType.startsWith("Xpath=")) {
			by = By.xpath(locatorType.substring(6));
		} else if (locatorType.startsWith("class=") || locatorType.startsWith("CLASS=") || locatorType.startsWith("Class=")) {
			by = By.className(locatorType.substring(6));
		} else if (locatorType.startsWith("name=") || locatorType.startsWith("NAME=") || locatorType.startsWith("Name=")) {
			by = By.name(locatorType.substring(5));
		} else {
			throw new RuntimeException("Locator Type is not supported");
		}
		
		return by;
	}
	
	
	private String getDynamicXpath(String locatorType, String... dynamicValues) {
		if (locatorType.startsWith("xpath=") || locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=") ) {
			locatorType = String.format(locatorType, (Object[]) dynamicValues);
		} 
		return locatorType;
	}
	
	private WebElement getWebElement(WebDriver driver, String locatorType) {
		return driver.findElement(getByLocator(locatorType));
	}
	
	public List<WebElement> getListWebElements(WebDriver driver, String locatorType){
		return driver.findElements(getByLocator(locatorType));
	}
	
	public List<WebElement> getListWebElements(WebDriver driver, String locatorType, String... dynamicValues){
		return driver.findElements(getByLocator(getDynamicXpath(locatorType, dynamicValues)));
	}
	
	public void clickToElement(WebDriver driver, String locatorType ) {
		getWebElement(driver, locatorType).click();
	}
	
	public void clickToElement(WebDriver driver, String locatorType , String... dynamicValues) {
		getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).click();
	}
	
	public void sendkeyToElement(WebDriver driver, String locatorType, String textValue) {
		WebElement element = getWebElement(driver, locatorType);
		element.clear();
		element.sendKeys(textValue);
	}
	
	public void sendkeyToElement(WebDriver driver, String locatorType, String textValue, String... dynamicValues) {
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		element.clear();
		element.sendKeys(textValue);
	}
	
	public void clearValueInElementByPressKey(WebDriver driver, String locatorType) {
		String osName = System.getProperty("os.name").toLowerCase();
		
		WebElement element = getWebElement(driver, locatorType);
		if (osName.equals("windows")) {
			element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
		} else {
			element.sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.DELETE));
		}
	}
	
	public void clearValueInElementByPressKey(WebDriver driver, String locatorType, String... dynamicValues) {
		String osName = System.getProperty("os.name").toLowerCase();
		
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		if (osName.equals("windows")) {
			element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
		} else {
			element.sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.DELETE));
		}
	}
	
	public void selectItemInDefaultDropdown(WebDriver driver, String locatorType, String textItem) {
		Select select = new Select(getWebElement(driver, locatorType));
		select.selectByVisibleText(textItem);
	}
	
	public void selectItemInDefaultDropdown(WebDriver driver, String locatorType, String textItem, String... dynamicValues) {
		Select select = new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		select.selectByVisibleText(textItem);
	}
	
	protected String getFirstSelectedItemDefaultDropdown(WebDriver driver, String locatorType) {
		Select select = new Select(getWebElement(driver, locatorType));
		 return select.getFirstSelectedOption().getText();
	}
	
	protected String getFirstSelectedItemDefaultDropdown(WebDriver driver, String locatorType, String... dynamicValues) {
		Select select = new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		 return select.getFirstSelectedOption().getText();
	}
	
	protected Boolean isDropdownMultiple(WebDriver driver, String locatorType) {
		Select select = new Select(getWebElement(driver, locatorType));
		return select.isMultiple();
	}
	
	protected Boolean isDropdownMultiple(WebDriver driver, String locatorType, String... dynamicValues) {
		Select select = new Select(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		return select.isMultiple();
	}
	
	protected void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childLocator, String textItem) {
		clickToElement(driver, parentLocator);
		sleepInSecond(1);
		
		WebDriverWait explicitWait = new WebDriverWait(driver, this.longTimeout);
		List<WebElement> lstItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(childLocator)));
		for (WebElement item : lstItems) {
			 if (item.getText().trim().equalsIgnoreCase(textItem)) {
				 JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				 jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				 sleepInSecond(1);
				 item.click();
				 break;
			}
		}
	}
	
	
	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getElementAttribute(WebDriver driver, String locatorType, String attributeName) {
	  return getWebElement(driver, locatorType).getAttribute(attributeName);
	}
	
	public String getElementAttribute(WebDriver driver, String locatorType, String attributeName, String... dynamicValues) {
		  return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getAttribute(attributeName);
	}
	
	public String getElementText(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).getText();
	}
	
	public String getElementText(WebDriver driver, String locatorType, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getText();
	}
	
	public String getElementCssValue(WebDriver driver, String locatorType, String propertyName) {
		return getWebElement(driver, locatorType).getCssValue(propertyName);
	}
	
	public String getElementCssValue(WebDriver driver, String locatorType, String propertyName, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getCssValue(propertyName);
	}

	public String getHexaColorFromRgba(String rgbaValue) {
		return Color.fromString(rgbaValue).asHex();
	}
	
	public Integer getElementSize(WebDriver driver, String locatorType) {
		return getListWebElements(driver, locatorType).size();
	}
	
	public Integer getElementSize(WebDriver driver, String locatorType, String... dynamicValues) {
		return getListWebElements(driver, getDynamicXpath(locatorType, dynamicValues)).size();
	}
	
	public void checkToDefaultCheckboxRadio(WebDriver driver, String locatorType) {
		WebElement element = getWebElement(driver, locatorType);
		if (!element.isSelected()) {
			element.click();
		}
	}
	
	public void checkToDefaultCheckboxOrRadio(WebDriver driver, String locatorType, String... dynamicValues) {
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		if (!element.isSelected()) {
			element.click();
		}
	}
	
	public void UncheckToDefaultCheckboxRadio(WebDriver driver, String locatorType) {
		WebElement element = getWebElement(driver, locatorType);
		if (element.isSelected()) {
			element.click();
		}
	}
	
	public void UncheckToDefaultCheckboxRadio(WebDriver driver, String locatorType, String... dynamicValues) {
		WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
		if (element.isSelected()) {
			element.click();
		}
	}
	
	public Boolean isElementDisplayed(WebDriver driver, String locatorType) {
		try {
			return getWebElement(driver, locatorType).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public Boolean isElementDisplayed(WebDriver driver, String locatorType, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isDisplayed();
	}
	
	public void overrideImplicitTimeout(WebDriver driver, Long timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}
	
	public boolean isElementUndisplayed(WebDriver driver, String locatorType) {
		System.out.println("Start time = " + new Date().toString());
		overrideImplicitTimeout(driver, shortTimeout);
		List<WebElement> Elements = getListWebElements(driver, locatorType);
		overrideImplicitTimeout(driver, longTimeout);
		
		if (Elements.size() == 0) {
			System.out.println("The element not in DOM");
			System.out.println("End time = " + new Date().toString());
			return true;
		} else if (Elements.size() > 0 && !Elements.get(0).isDisplayed()) {
			System.out.println("The element in DOM but not visible/displayed");
			System.out.println("End time = " + new Date().toString());
			return true;
		}else {
			System.out.println("The element in DOM and visible");
			return false;
		}
	}
	
	public boolean isElementUndisplayed(WebDriver driver, String locatorType, String... dynamicValues) {
		System.out.println("Start time = " + new Date().toString());
		overrideImplicitTimeout(driver, shortTimeout);
		List<WebElement> Elements = getListWebElements(driver, getDynamicXpath(locatorType, dynamicValues));
		overrideImplicitTimeout(driver, longTimeout);
		
		if (Elements.size() == 0) {
			System.out.println("The element not in DOM");
			System.out.println("End time = " + new Date().toString());
			return true;
		} else if (Elements.size() > 0 && !Elements.get(0).isDisplayed()) {
			System.out.println("The element in DOM but not visible/displayed");
			System.out.println("End time = " + new Date().toString());
			return true;
		}else {
			System.out.println("The element in DOM and visible");
			return false;
		}
	}
	
	public Boolean isElementEnabled(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).isEnabled();
	}
	
	public Boolean isElementEnabled(WebDriver driver, String locatorType, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isEnabled();
	}
	
	public Boolean isElementSelected(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).isSelected();
	}
	
	public Boolean isElementSelected(WebDriver driver, String locatorType, String... dynamicValues) {
		return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isSelected();
	}
	
	public void switchToFrameIframe(WebDriver driver, String locatorType) {
		driver.switchTo().frame(getWebElement(driver, locatorType));
	}
	
	public void switchDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	// <Javascript Executor>
	public void hoverMouseToElement(WebDriver driver, String locatorType) {
		Actions actions = new Actions(driver);
		actions.moveToElement(getWebElement(driver, locatorType)).perform();
	}
	
	public void pressKeyToElement(WebDriver driver, String locatorType, Keys key) {
		Actions actions = new Actions(driver);
		actions.sendKeys(getWebElement(driver, locatorType), key).perform();
	}
	
	public void pressKeyToElement(WebDriver driver, String locatorType, Keys key, String... dynamicValues) {
		Actions actions = new Actions(driver);
		actions.sendKeys(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)), key).perform();
	}
	
	public Object executeForBrowser(WebDriver driver, String javaScript) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public String getElementValueByJsXpath(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		
		if (xpathLocator.startsWith("xpath=")) {
			xpathLocator = xpathLocator.replace("xpath=", "");
		}else if (xpathLocator.startsWith("XPATH=")) {
			xpathLocator = xpathLocator.replace("XPATH=", "");
		}else if (xpathLocator.startsWith("Xpath=")) {
			xpathLocator = xpathLocator.replace("Xpath=", "");
		}else if (xpathLocator.startsWith("XPath=")) {
			xpathLocator = xpathLocator.replace("XPath=", "");
		}
		
		return (String) jsExecutor.executeScript("return $(document.evaluate(\""+ xpathLocator+"\", documentNode, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue).val()");
	}
	
	public void navigateToUrlByJS(WebDriver driver, String url) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	protected void highlightElement(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getWebElement(driver, locatorType);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	protected void clickToElementByJS(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, locatorType));
	}

	protected void scrollToElement(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locatorType));
	}

	protected void sendkeyToElementByJS(WebDriver driver, String locatorType, String value) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", getWebElement(driver, locatorType));
	}

	protected void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, locatorType));
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

	protected String getElementValidationMessage(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", getWebElement(driver, locatorType));
	}

	public boolean isImageLoaded(WebDriver driver, String locatorType) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getWebElement(driver, locatorType));
		if (status) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isImageLoaded(WebDriver driver, String locatorType, String...dynamicValues) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
		if (status) {
			return true;
		} else {
			return false;
		}
	}
	
	
	// </Javascript Executor>
	
	// <Wait>
	protected void waitForElementUndisplayed(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
		overrideImplicitTimeout(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
	}
	
	protected void waitForElementUndisplayed(WebDriver driver, String locatorType, String...dynamicValues ) {
		WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
		overrideImplicitTimeout(driver, shortTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}
	
	protected void waitForElementVisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locatorType)));
	}
	
	protected void waitForElementVisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}
	
	protected void waitForAllElementsVisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorType)));
	}
	
	protected void waitForAllElementsVisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}
	
	protected void waitForElementInvisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
	}
	
	protected void waitForElementInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}
	
	protected void waitForAllElementsInvisible(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElements(driver, locatorType)));
	}
	
	protected void waitForAllElementsInvisible(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElements(driver, getDynamicXpath(locatorType, dynamicValues))));
	}
	
	protected void waitForElementClickable(WebDriver driver, String locatorType) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locatorType)));
	}
	
	protected void waitForElementClickable(WebDriver driver, String locatorType, String... dynamicValues) {
		WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
	}
	// </Wait>
	
	
	
	// <Upload File>
	
	public void uploadMultipleFiles(WebDriver driver, String ... fileNames) {
		
		String filePath = GlobalConstants.UPLOAD_FILE_FOLDER;
		
		String fullFileName = "";
		for (String file : fileNames) {
			fullFileName = fullFileName + filePath + file + "\n";
		}
		
		fullFileName = fullFileName.trim();
		getWebElement(driver, BasePageJQueryUI.UPLOAD_FILE).sendKeys(fullFileName);
	}
	
	// </Upload File>
	
	// <Cookie>
	public Set<Cookie> getAllCookies(WebDriver driver){
		return driver.manage().getCookies();
	}
	
	public void setCookies( WebDriver driver, Set<Cookie> cookies) {
		for (Cookie cookie : cookies) {
			driver.manage().addCookie(cookie);
		}
		
		sleepInSecond(3);
	}
	
	// </Cookie>

	// <Pattern Object>
//	
//	/** Click to dynamic button by Text
//	 * @author trale
//	 * @param driver
//	 * @param buttonText
//	 */
//	@Step("Click to the '{1} button'")
//	public void clickToButtonByText(WebDriver driver, String buttonText) {
//		waitForElementClickable(driver, BasePageNopComUI.DYNAMIC_BUTTON_BY_TEXT, buttonText);
//		clickToElement(driver, BasePageNopComUI.DYNAMIC_BUTTON_BY_TEXT, buttonText);
//	}
//	
//	/** Enter to dynamic textbox by ID
//	 * @author trale
//	 * @param driver
//	 * @param textboxID
//	 * @param value
//	 */
//	@Step("Enter to the '{1}' textbox with value is {2}")
//	public void inputToTextboxByID(WebDriver driver, String textboxID, String value) {
//		waitForElementVisible(driver, BasePageNopComUI.DYNAMIC_TEXTBOX_BY_ID, textboxID);
//		sendkeyToElement(driver, BasePageNopComUI.DYNAMIC_TEXTBOX_BY_ID, value, textboxID);
//	}
//	
//	/** Select to dynamic dropdown by Name Attribute
//	 * @author trale
//	 * @param driver
//	 * @param dropdownAttributeName
//	 * @param itemValue
//	 */
//	@Step("Select value is '{2}' on the '{1}' dropdown")
//	public void selectToDropdownByName(WebDriver driver, String dropdownAttributeName, String itemValue) {
//		waitForElementClickable(driver, BasePageNopComUI.DYNAMIC_DROPDOWN_BY_NAME, dropdownAttributeName);
//		selectItemInDefaultDropdown(driver, BasePageNopComUI.DYNAMIC_DROPDOWN_BY_NAME, itemValue, dropdownAttributeName);
//	}
//	
//	/** Click to dynamic radio button by Label Name
//	 * @author trale
//	 * @param driver
//	 * @param radioButtonLabelName
//	 */
//	@Step("Check to the '{0}' radio button")
//	public void clickToRadioButtonByLabel(WebDriver driver, String radioButtonLabelName) {
//		waitForElementClickable(driver, BasePageNopComUI.DYNAMIC_RADION_BUTTON_BY_LABEL, radioButtonLabelName);
//		checkToDefaultCheckboxOrRadio(driver, BasePageNopComUI.DYNAMIC_RADION_BUTTON_BY_LABEL, radioButtonLabelName);
//	}
//	
//	/** Click to dynamic checkbox by Label Name
//	 * @author trale
//	 * @param driver
//	 * @param checkboxLabelName
//	 */
//	@Step("Check to the '{0}' radio button")
//	public void clickToCheckboxByLabel(WebDriver driver, String checkboxLabelName) {
//		waitForElementClickable(driver, BasePageNopComUI.DYNAMIC_CHECKBOX_BY_LABEL, checkboxLabelName);
//		checkToDefaultCheckboxOrRadio(driver, BasePageNopComUI.DYNAMIC_CHECKBOX_BY_LABEL, checkboxLabelName);
//	}
//	
//	/** Get value in textbox by textboxID
//	 * @author trale
//	 * @param driver
//	 * @param textboxID
//	 * @return value as a string
//	 */
//	public String getTextboxValueByID(WebDriver driver, String textboxID) {
//		waitForElementVisible(driver, BasePageNopComUI.DYNAMIC_TEXTBOX_BY_ID, textboxID);
//		return getElementAttribute(driver, BasePageNopComUI.DYNAMIC_TEXTBOX_BY_ID, "value" ,textboxID);
//	}
	
	// </Pattern Object>
	
	
	public static long getRandomNumberByDateTime() {
		return Calendar.getInstance().getTimeInMillis() % 100000;
	}
	
}
