package factoryEnvironment;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import commons.BrowserList;
import commons.GlobalConstants;
import exceptions.BrowserNotSupport;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LocalFactory {
	
	private String browserName;
	private WebDriver driver;
	
	public LocalFactory(String browserName) {
		this.browserName = browserName;
	}
	
	public WebDriver createDriver() {
		BrowserList browserlst = BrowserList.valueOf(browserName.toUpperCase());
		
		if (browserlst == BrowserList.FIREFOX) {
			WebDriverManager.firefoxdriver().clearDriverCache().setup();
			
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, GlobalConstants.PROJECT_PATH + "/browserLogs/FirefoxLog.log");
			
			FirefoxProfile profile = new FirefoxProfile();
			File file = new File(GlobalConstants.PROJECT_PATH + "/browserExtensions/simple_translate_extension-2.8.0.xpi");	
			profile.addExtension(file);
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(false);
			FirefoxOptions options = new FirefoxOptions();
			options.setAcceptInsecureCerts(false);
			options.setProfile(profile);
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-geolocation");
			
			options.addPreference("browser.download.folderList", 2);
			options.addPreference("browser.download.dir", GlobalConstants.PROJECT_PATH + "/downloadFiles");
			options.addPreference("browser.download.useDownloadDir", true);
			options.addPreference("browser.helperApps.neverAsk.saveToDisk", 
					"multipart/x-zip,application/zip,application/x-zip-compressed,application/x-compressed,application/msword,"
					+ "application/csv,text/csv,image/png,image/jpeg,application/pdf,text/html,text/plain,application/excel,"
					+ "application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/octest-stream");
			options.addPreference("pdfjs.disabled", true);
			
			// incogito browser
//			options.addArguments("-private");
			
			driver = new FirefoxDriver(options);
		} else if (browserlst == BrowserList.H_FIREFOX) {
			WebDriverManager.firefoxdriver().clearDriverCache().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--headless");
			options.addArguments("window-size=1920x1080");
			driver = new FirefoxDriver(options);
		} else if (browserlst == BrowserList.CHROME) {
			WebDriverManager.chromedriver().setup();
			
			
			System.setProperty("webdriver.chrome.args", "--disable-logging");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			
//			File file = new File(GlobalConstants.PROJECT_PATH + "/browserExtensions/quickTranslateExtension.crx");	
			
			// save password popup
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			
			// save downloaded files
			prefs.put("profile.default_content_setting.popups", 0);
			prefs.put("download.default_directory", GlobalConstants.PROJECT_PATH + "/downloadFiles");
						
			ChromeOptions options = new ChromeOptions();
		    options.setAcceptInsecureCerts(true);
//		    options.addExtensions(file);
		    options.addArguments("--disable-notifications");
			options.addArguments("--disable-geolocation");
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.setExperimentalOption("prefs", prefs);
			
			// incogito browser
			options.addArguments("--incogito");
			
			driver = new ChromeDriver(options);
		} else if (browserlst == BrowserList.H_CHROME) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("window-size=1920x1080");
			driver = new ChromeDriver(options);
		} else if (browserlst == BrowserList.EDGE) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (browserlst == BrowserList.OPERA) {
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
		} else if (browserlst == BrowserList.COCCOC) {
			// Coc Coc Browser tru di 5-6 version cua ChromeDriver
			WebDriverManager.chromedriver().driverVersion("93.0.4577.63").setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("đường dẫn của file .exe");
			driver = new ChromeDriver(options);
		} else if (browserlst == BrowserList.BRAVE) {
			// Brave browser version nao thi Chromedriver version do
			WebDriverManager.chromedriver().driverVersion("95.0.4577.63").setup();
			ChromeOptions options = new ChromeOptions();
			options.setBinary("đường dẫn của file .exe");
			driver = new ChromeDriver(options);
		}else if (browserlst == BrowserList.SAFARI) {
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
		} else {
			throw new BrowserNotSupport(browserName);
		}
		return driver;
	}
}
