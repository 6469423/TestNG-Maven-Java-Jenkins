package swaglabs_example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import swaglabs_example.framework.BasePage;
import swaglabs_example.framework.TestConfig;

/**
 * Login page
 */
public class LoginPage extends BasePage {

	@FindBy(css = "input[data-test='username']")
	private WebElement usernameInput;

	@FindBy(css = "input[data-test='password']")
	private WebElement passwordInput;

	@FindBy(css = ".btn_action")
	private WebElement loginButton;

	@FindBy(css = "h3[data-test='error']")
	private WebElement errorMessage;

	private final String ERROR_LOCKED_OUT = "Sorry, this user has been locked out.";
	private final String ERROR_UNKNOWN_USER = "Username and password do not match any user in this service";

	public LoginPage(WebDriver driver, TestConfig testConfig) {
		super(driver, testConfig);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Get error message from login
	 *
	 * @return Error message
	 */
	private String getErrorMessage() {
		return errorMessage.getText().trim();
	}

	/**
	 * Is the error a lockout message
	 *
	 * @return True if user is locked out
	 */
	public boolean isUserLockedOut() {
		if (exists(errorMessage)) {
			return getErrorMessage().contains(ERROR_LOCKED_OUT);
		}
		return false;
	}

	/**
	 * Is the error an unknown uer
	 *
	 * @return True if user is unknown
	 */
	public boolean isUserUnknown() {
		if (exists(errorMessage)) {
			return getErrorMessage().contains(ERROR_UNKNOWN_USER);
		}
		return false;
	}

	/**
	 * Set username
	 *
	 * @param username Username
	 */
	private void setUsername(String username) {
		usernameInput.sendKeys(username);
	}

	/**
	 * Set password
	 *
	 * @param password Password
	 */
	private void setPassword(String password) {
		passwordInput.sendKeys(password);
	}

	/**
	 * Click Login button
	 */
	private void clickLogin() {
		loginButton.click();
	}

	/**
	 * Login to Swag Labs
	 *
	 * @param username Username
	 * @param password Password
	 * @return Products page
	 */
	public ProductsPage login(String username, String password) {
		ProductsPage productsPage = new ProductsPage(getDriver(), getTestConfig());

		setUsername(username);
		setPassword(password);
		clickLogin();

		if (!productsPage.productHeaderExists()) {
			throw new RuntimeException("Products page has not loaded");
		}

		return productsPage;
	}
}
