package utilities;

import java.util.Locale;

import com.github.javafaker.Faker;

public class DataHelper {

	private Locale locale = new Locale("en");
	private Faker faker = new Faker();
	
	public static DataHelper getDataHelper() {
		return new DataHelper();
	}
	
	public String getFirstName() {
		return faker.name().firstName();
	}
	
	public String getLastName() {
		return faker.name().lastName();
	}
	
	public String getEmailAddress() {
		return faker.internet().emailAddress();
	}
	
	public String getCityName() {
		return faker.address().cityName();
	}
	
	public String getPhoneNumber() {
		return faker.phoneNumber().phoneNumber();
	}
	
	public String getStreetName() {
		return faker.address().streetAddress();
	}
	
	public String getStateName() {
		return faker.address().state();
	}
	
	public String getZipcode() {
		return faker.address().zipCode();
	}
	
	public String getPassword() {
		return faker.internet().password(8, 12, true, true, true);
	}
	
}
