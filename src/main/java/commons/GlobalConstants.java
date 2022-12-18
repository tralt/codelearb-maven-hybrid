package commons;

import java.io.File;

public class GlobalConstants {
	
	public static final String PORTAL_PAGE_URL = "https://demo.nopcommerce.com";
	public static final String ADMIN_PAGE_URL = "https://admin-demo.nopcommerce.com";
	public static final String PROJECT_PATH = System.getProperty("user.dir");
	public static final String OS_NAME = System.getProperty("os.name");	
	public static final String JAVA_VERSION = System.getProperty("java.version");
	public static final String UPLOAD_FILE_FOLDER = PROJECT_PATH + File.separator + "uploadFiles" + File.separator; // uploadFiles = tên folder trong máy 
	public static final String DOWNLOAD_FILE_FOLDER = PROJECT_PATH + File.separator + "downloadFiles"; // downloadFiles = tên folder trong máy 
	public static final String BROWSER_LOG = PROJECT_PATH + File.separator + "browserLogs";
	public static final String DRAG_DROP_HTML5 = PROJECT_PATH + File.separator + "dragDropHTML5";
	public static final String AUTO_IT_SCRIPT = PROJECT_PATH + File.separator + "autoIT";
	public static final String REPORTNG_SCREENSHOT = PROJECT_PATH + File.separator + "ReportNGImages" + File.separator;
	public static final long SHORT_TIMEOUT = 5;
	public static final long LONG_TIMEOUT = 30;
	public static final long RETRY_TEST_FAILD = 3;
	public static final String DB_DEV_URL = "32.18.252.185:9860";
	public static final String DB_DEV_USER = "automationfc";
	public static final String DB_DEV_PASS = "P@ssw0rld1!";
	
	
	// techPanda
	public static final String PORTAL_TECHPANDA_PAGE_URL = "http://live.techpanda.org";
	public static final String ADMIN_TECHPANDA_PAGE_URL = "http://live.techpanda.org/index.php/backendlogin/customer/";
	public static final String USER_NAME_ADMIN_TECHPANDA_STRING = "user01";
	public static final String PASSWORD_ADMIN_TECHPANDA_STRING = "guru99com";
	
		
	// Browserstack
	public static final String BROWSER_USERNAME = "miriamthanhtra_p3jAKS";
	public static final String BROWSER_AUTOMAED_KEY = "hU9ChmiqcgcxmdvB1bDJ";
	public static final String BROWSER_STACK_URL = "https://" + BROWSER_USERNAME + ":" + BROWSER_AUTOMAED_KEY + "@hub-cloud.browserstack.com/wd/hub";
		
	// SauceLab
	public static final String SAUCE_USERNAME = "oauth-tra-6f669";
	public static final String SAUCE_AUTOMAED_KEY = "b3d489c2-11cc-4947-8541-2adb9a84f55e";
	public static final String SAUCE_LAB_URL = "https://" + SAUCE_USERNAME + ":" + SAUCE_AUTOMAED_KEY + "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

	// Lambdatest
	public static final String LAMBDATEST_USERNAME = "tra";
	public static final String LAMBDATEST_AUTOMAED_KEY = "FB3rWArbLqXxwv6CL0RDKv6Pq9rkAa2RQNkdZX5q45OUvUJyi5";
	public static final String LAMBDATEST_URL = "https://" + LAMBDATEST_USERNAME + ":" + LAMBDATEST_AUTOMAED_KEY + "@hub.lambdatest.com/wd/hub";
	

}
