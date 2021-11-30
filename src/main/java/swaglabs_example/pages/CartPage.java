package swaglabs_example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import swaglabs_example.framework.BasePage;
import swaglabs_example.framework.TestConfig;

import java.util.List;

/**
 * Shopping cart page
 */
public class CartPage extends BasePage {

	@FindBy(css = ".subheader")
	private WebElement header;

	private By item = By.cssSelector(".cart_item");
	private By itemName = By.cssSelector(".cart_item_label > a > .inventory_item_name");

	private final String LABEL_CART = "Your Cart";

	public CartPage(WebDriver driver, TestConfig testConfig) {
		super(driver, testConfig);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Check to see if on cart page
	 *
	 * @return True if on cart page
	 */
	public boolean cartHeaderExists() {
		try {
			return getHeaderLabel().equals(LABEL_CART);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Get page header label
	 *
	 * @return Label text
	 */
	private String getHeaderLabel() {
		return header.getText().trim();
	}

	/**
	 * Check to see if item is in cart
	 *
	 * @param productName Product name
	 * @return True if product is in the cart
	 */
	public boolean itemExistsInCart(String productName) {
		for (WebElement item : getCartItems()) {
			if (item.findElement(itemName).getText().trim()
				.equals(productName)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Get list of all cart items
	 *
	 * @return List of item elements
	 */
	private List<WebElement> getCartItems() {
		return getDriver().findElements(item);
	}
}
