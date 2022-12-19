Framework này sử dụng Data Driven để quản lý locators của những website có nhiều language. Với cách này bạn sẽ cần: 
   
   1. file JSON - là nơi chứa text của locator bao gồm "key": "value"
    
    {
	    "homePage": {
		    "languages": {
			    "englishLang": "English",
			    "vietLang": "Tiếng Việt"
		  },
		  "loginButton": "Đăng nhập",
		  "registerButton": "Đăng ký",
		  "learningMenu": "Học tập",
		  "trainingMenu": "Luyện tập",
		  "fightsMenu": "Thi đấu",
		  "challengeMenu": "Thử thách",
		  "evaluatingMenu": "Đánh giá",
		  "discussionMenu": "Thảo luận",
		  "gameMenu": "Trò chơi",
		  "sharingMenu": "Chia sẻ"
	  },
	
	  "loginPage": {
		  "loginButton": "Đăng nhập"
	  }
  }
    
  2. class để đọc file JSON - trong đó chứa các hàm get() để mapping với data từ file JSON.
  
  public class UserLocatorMapper {
	
		public static UserLocatorMapper getUserLocatorMapper(String language) {
			File file = null;
			try {
				ObjectMapper mapper = new ObjectMapper(); 
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				
				if (language.equals("english")) {
					file = new File(GlobalConstants.PROJECT_PATH+ "/src/test/resources/UserLocatorEnglish.json");
					
				}else if (language.equals("vietnamese")) {
					file = new File(GlobalConstants.PROJECT_PATH+ "/src/test/resources/UserLocatorVietnamese.json");
				}
				
				return mapper.readValue(file, UserLocatorMapper.class);
				
			}
			catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
			
			
		}
		
		
		// Home Page
		@JsonProperty("homePage")
		private HomePage homePage;
		
		public static class HomePage {
			
			@JsonProperty("loginButton")
			private String loginButton;
			
			@JsonProperty("registerButton")
			private String registerButton;
			
			@JsonProperty("learningMenu")
			private String learningMenu;
			
			@JsonProperty("trainingMenu")
			private String trainingMenu;
			
			@JsonProperty("fightsMenu")
			private String fightsMenu;
			
			@JsonProperty("challengeMenu")
			private String challengeMenu;
			
			@JsonProperty("evaluatingMenu")
			private String evaluatingMenu;
			
			@JsonProperty("discussionMenu")
			private String discussionMenu;
			
			@JsonProperty("gameMenu")
			private String gameMenu;
			
			@JsonProperty("sharingMenu")
			private String sharingMenu;
			
		}
		
		public final String getLoginButtonAtHomePage() {
			return homePage.loginButton;
		}
		
		public final String getRegisterButtonAtHomePage() {
			return homePage.registerButton;
		}
		
		public final String getLearningMenuAtHomePage() {
			return homePage.learningMenu;
		}
		
		public final String getTrainingMenuAtHomePage() {
			return homePage.trainingMenu;
		}
		
		public final String getFightsMenuAtHomePage() {
			return homePage.fightsMenu;
		}
		
		public final String getChallengeMenuAtHomePage() {
			return homePage.challengeMenu;
		}
		
		public final String getEvaluatingMenuAtHomePage() {
			return homePage.evaluatingMenu;
		}
		
		public final String getDiscussionMenuAtHomePage() {
			return homePage.discussionMenu;
		}
		
		public final String getGameMenuAtHomePage() {
			return homePage.gameMenu;
		}
		
		public final String getSharingMenuAtHomePage() {
			return homePage.sharingMenu;
		}
		
		// Login Page
		@JsonProperty("loginPage")
		private LoginPage loginPage;
		
		public static class LoginPage {
			@JsonProperty("loginButton")
			private String loginButton;
		}
		
		public final String getSharingMenuAtLoginPage() {
			return loginPage.loginButton;
		}
}
  
   3. file runTest.xml chứa tham số lang để có thể thay đổi ngôn ngữ cần test.
   
   <?xml version="1.0" encoding="UTF-8"?>
    <suite parallel="false" name="Codelearn">

    <listeners>
        <listener class-name="commons.MethodListener"></listener>
        <listener class-name="reportConfig.ReportNGListener" />
        <listener class-name="org.uncommons.reportng.HTMLReporter"></listener>
        <listener class-name="org.uncommons.reportng.JUnitXMLReporter"></listener>
    </listeners>

	<parameter name="envName" value="local"/>
	<parameter name="serverName" value="user"/>
	<parameter name="lang" value="english"/>
  
      <test name="Run on Chrome">
            <parameter name="browser" value="chrome" />
            <parameter name="osName" value="OS X" />
            <parameter name="osVersion" value="Big Sur" />
            <parameter name="ipAddress" value="" />
            <parameter name="portNumber" value="" />
                <classes>
                    <class name="codelearn.login.Login_01_User_Login"/>
                </classes> 
      </test> 
    </suite> 
    
   4. Test case gọi đến file Mapper 
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

    
