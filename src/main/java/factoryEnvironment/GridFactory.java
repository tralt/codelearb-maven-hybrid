package factoryEnvironment;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import commons.BrowserList;
import exceptions.BrowserNotSupport;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GridFactory {

	private WebDriver driver;
	private String browserName;
	private String ipAddress;
	private String portNumber;
	
	public GridFactory(String browserName, String ipAddress, String portNumber) {
		this.browserName = browserName;
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
	}
	
	public WebDriver createDriver() {
		BrowserList browserlst = BrowserList.valueOf(browserName.toUpperCase());
		DesiredCapabilities capability = null;
		
		if (browserlst == BrowserList.FIREFOX) {
			WebDriverManager.firefoxdriver().clearDriverCache().setup();
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName("firefox");
			capability.setPlatform(Platform.WINDOWS);
			
			FirefoxOptions options = new FirefoxOptions();
			options.merge(capability);
			
		}else if (browserlst == BrowserList.H_FIREFOX) {
			WebDriverManager.firefoxdriver().clearDriverCache().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.setHeadless(true);
			options.addArguments("window-size=19020x1080");
			driver = new FirefoxDriver(options);
			
		}else if (browserlst == BrowserList.CHROME) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			capability.setPlatform(Platform.WINDOWS);
			options.merge(capability);
			
		} else if (browserlst == BrowserList.H_CHROME) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.setHeadless(true);
			options.addArguments("window-size=19020x1080");
			driver = new ChromeDriver(options);
			
		}else if (browserlst == BrowserList.EDGE) {
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
		
		try {
			driver = new RemoteWebDriver(new URL(String.format("https://%s:%s/wd/hub", ipAddress, portNumber)), capability);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		
		return driver;
	}
}
