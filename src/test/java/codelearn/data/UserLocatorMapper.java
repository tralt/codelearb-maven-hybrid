package codelearn.data;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import commons.GlobalConstants;

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
