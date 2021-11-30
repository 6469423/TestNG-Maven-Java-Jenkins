package swaglabs_example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import swaglabs_example.framework.BasePage;
import swaglabs_example.framework.TestConfig;

/**
 * Common header page
 */
public class HeaderPage extends BasePage {

	@FindBy(css = "svg[data-icon='shopping-cart']")
	private WebElement cartIcon;

	@FindBy(css = ".shopping_cart_badge")
	private WebElement cartBadge;

	public HeaderPage(WebDriver driver, TestConfig testConfig) {
		super(driver, testConfig);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Go to the shopping cart
	 *
	 * @return Instance of shopping cart page
	 */
	public CartPage goToCart() {
		CartPage cartPage = new CartPage(getDriver(), getTestConfig());

		cartIcon.click();

		if (!cartPage.cartHeaderExists()) {
			throw new RuntimeException("Cart page has not loaded");
		}

		return cartPage;
	}

	/**
	 * Get total shopping cart items based on badge
	 *
	 * @return Total shopping cart items
	 */
	public int getCartBadgeTotal() {
		int badgeCount = 0;

		if (exists(cartBadge)) {
			String badgeCountValue = cartBadge.getText();
			if (badgeCountValue == null || badgeCountValue.equals("")) {
				return badgeCount;
			} else {
				return Integer.parseInt(badgeCountValue);
			}
		}

		return badgeCount;
	}
}
