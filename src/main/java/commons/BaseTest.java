package commons;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import exceptions.BrowserNotSupport;
import factoryEnvironment.BrowserstackFactory;
import factoryEnvironment.GridFactory;
import factoryEnvironment.LambdaFactory;
import factoryEnvironment.LocalFactory;
import factoryEnvironment.SaucelabFactory;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	private WebDriver driver;
	protected final Log log;
	//private String projectPath = System.getProperty("user.dir");
	
	@BeforeSuite
	public void initBeforeSuit() {
		deleteAllureReport();
	}
	
 	protected BaseTest() {
		log = LogFactory.getLog(getClass());
	}
	
 	protected void closeBrowserAndDriver() {
		String cmd = "";
		try {
			String osName = System.getProperty("os.name").toLowerCase();
			log.info("OS name = " + osName);

			String driverInstanceName = driver.toString().toLowerCase();
			log.info("Driver instance name = " + driverInstanceName);

			if (driverInstanceName.contains("chrome")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
				} else {
					cmd = "pkill chromedriver";
				}
			} else if (driverInstanceName.contains("internetexplorer")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
				}
			} else if (driverInstanceName.contains("firefox")) {
				if (osName.contains("windows")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq geckodriver*\"";
				} else {
					cmd = "pkill geckodriver";
				}
			} else if (driverInstanceName.contains("edge")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq msedgedriver*\"";
				} else {
					cmd = "pkill msedgedriver";
				}
			} else if (driverInstanceName.contains("opera")) {
				if (osName.contains("window")) {
					cmd = "taskkill /F /FI \"IMAGENAME eq operadriver*\"";
				} else {
					cmd = "pkill operadriver";
				}
			} else if (driverInstanceName.contains("safari")) {
				if (osName.contains("mac")) {
					cmd = "pkill safaridriver";
				}
			}

			if (driver != null) {
				driver.manage().deleteAllCookies();
				driver.quit();
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			try {
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
 	
	protected WebDriver getBrowser(String browserName) {
		
		BrowserList browserlst = BrowserList.valueOf(browserName.toUpperCase());
		
		if (browserlst == BrowserList.FIREFOX) {
			//System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			WebDriverManager.firefoxdriver().clearDriverCache().setup();
			driver = new FirefoxDriver();
		} else if (browserlst == BrowserList.H_FIREFOX) {
			//System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
			WebDriverManager.firefoxdriver().clearDriverCache().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--headless");
			options.addArguments("window-size=1920x1080");
			driver = new FirefoxDriver(options);
		} else if (browserlst == BrowserList.CHROME) {
			//System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserlst == BrowserList.H_CHROME) {
			//System.setProperty("webdriver.chrome.driver", projectPath + "/browserDrivers/chromedriver");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("window-size=1920x1080");
			driver = new ChromeDriver(options);
		} else if (browserlst == BrowserList.EDGE) {
			//System.setProperty("webdriver.edge.driver", projectPath + "/browserDrivers/msedgedriver");
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
		
		driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIMEOUT, TimeUnit.SECONDS);
		return driver;
	}
	
	protected WebDriver getBrowserDriver(
			String envName, 
			String serverName, 
			String browserName, 
			String ipAddress, 
			String portNumber,
			String osName,
			String osVersion
			) {
		switch (envName) {
		case "local":
			driver = new LocalFactory(browserName).createDriver();
			break;
			
		case "grid":
			driver = new GridFactory(browserName, ipAddress, portNumber).createDriver();
			break;
			
		case "browserstack":
			driver = new BrowserstackFactory(browserName, osName, osVersion).createDriver();
			break;	
			
		case "saucelab":
			driver = new SaucelabFactory(browserName, osName).createDriver();
			break;	
			
		case "lambda":
			driver = new LambdaFactory(browserName, osName).createDriver();
			break;

		default:
			driver = new LocalFactory(browserName).createDriver();
			break;
		}
		
		driver.manage().timeouts().implicitlyWait(GlobalConstants.LONG_TIMEOUT, TimeUnit.SECONDS);
		driver.get(getEnvironmentValues(serverName));
		return driver;
	}
	
	protected String getEnvironmentValues(String serverName) {
		String envUrl = null;
		
		EnvironmentList environment = EnvironmentList.valueOf(serverName.toUpperCase());
		if (environment == EnvironmentList.USER) {
			envUrl = "https://codelearn.io/";
		} else if (environment == EnvironmentList.ADMIN) {
			envUrl = "https://codelearn.io/";
		} else if (environment == EnvironmentList.LP) {
			envUrl = "https://codelearn.io/";
		}
		
		return envUrl;
	}
	
	protected void openThePage(String url) {
		driver.get(url);
	}
	
	protected  int generateFakeNumber() {
		  Random random = new Random();
		  return random.nextInt(9999);
	  }
	
	// <Assert>
	protected boolean verifyTrue(boolean condition) {
		boolean pass = true;
		try {
			Assert.assertTrue(condition);
			log.info(" -------------------------- PASSED -------------------------- ");
		} catch (Throwable e) {
			pass = false;
			log.info(" -------------------------- FAILED -------------------------- ");
			// Add lỗi vào ReportNG
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
		}

	protected boolean verifyFalse(boolean condition) {
		boolean pass = true;
		try {
			Assert.assertFalse(condition);
			log.info(" -------------------------- PASSED -------------------------- ");
		} catch (Throwable e) {
			pass = false;
			log.info(" -------------------------- FAILED -------------------------- ");
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
		}

	protected boolean verifyEquals(Object actual, Object expected) {
		boolean pass = true;
		try {
			Assert.assertEquals(actual, expected);
			log.info(" -------------------------- PASSED -------------------------- ");
		} catch (Throwable e) {
			pass = false;
			log.info(" -------------------------- FAILED -------------------------- ");
			VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
			Reporter.getCurrentTestResult().setThrowable(e);
		}
		return pass;
	}
	// </Assert>

	public WebDriver getDriverInstance() {
		return this.driver;
	}
	
	public void deleteAllureReport() {
		try {
			String pathFolderDownload = GlobalConstants.PROJECT_PATH + "/allure-json";
			File file = new File(pathFolderDownload);
			File[] listOfFiles = file.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					System.out.println(listOfFiles[i].getName());
					new File(listOfFiles[i].toString()).delete();
				}
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	// <Date Time>
		/*
		protected String getCurrentDay() {
			DateTime nowUTC = new DateTime();
			int day = nowUTC.getDayOfMonth();
			if (day < 10) {
				String dayValue = "0" + day;
				return dayValue;
			}
			return day + "";
		}

		protected String getCurrentMonth() {
			DateTime now = new DateTime();
			int month = now.getMonthOfYear();
			if (month < 10) {
				String monthValue = "0" + month;
				return monthValue;
			}
			return month + "";
		}

		protected String getCurrentYear() {
			DateTime now = new DateTime();
			return now.getYear() + "";
		}

		protected String getToday() {
			return getCurrentMonth() + "/" + getCurrentDay() + "/" + getCurrentYear();
		}
		*/
		
		public String getCurrentDateTimeByFormat(String formatter) {
			LocalDateTime nowDate = LocalDateTime.now();
			DateTimeFormatter format1 = DateTimeFormatter.ofPattern(formatter); 
			return nowDate.format(format1);
		}
	// </Date Time>
		
	// <Console Logging>
		protected void showBrowserConsoleLogs(WebDriver driver) {
			if (driver.toString().contains("chrome")) {
				LogEntries logs = driver.manage().logs().get("browser");
				List<LogEntry> logList = logs.getAll();
				for (LogEntry logging : logList) {
					log.info("--------------------" +logging.getLevel().toString() + "-------------------- \n" + logging.getMessage());
				}
			}
		}
	// </Console Logging>
}
