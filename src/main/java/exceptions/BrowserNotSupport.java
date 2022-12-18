package exceptions;

public class BrowserNotSupport extends IllegalArgumentException{

	private static final long serialVersionUID = 1L;
	
	
	public BrowserNotSupport(String browserName) {
		super(String.format("Browser with name = %s is invalid", browserName));
	}
}
