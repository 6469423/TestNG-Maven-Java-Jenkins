package swaglabs_example.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

/**
 * Base page with common functionality
 */
public class BasePage {
	private WebDriver driver;
	private TestConfig testConfig;

	public BasePage(WebDriver driver, TestConfig testConfig) {
		this.driver = driver;
		this.testConfig = testConfig;
	}

	protected WebDriver getDriver() {
		return driver;
	}

	protected TestConfig getTestConfig() {
		return testConfig;
	}

	/**
	 * Check visibility of element
	 *
	 * @param element Web element
	 * @return True if it exists
	 */
	protected boolean exists(WebElement element) {
		try {
			getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		} finally {
			getDriver().manage().timeouts().implicitlyWait(testConfig.getImplicitTimeout(), TimeUnit.SECONDS);
		}
	}
}
