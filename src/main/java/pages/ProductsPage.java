package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import main.BasePage;
import main.TestConfig;

import java.util.List;

/**
 * Products page
 */
public class ProductsPage extends BasePage {

	@FindBy(css = ".title")
	private WebElement header;

	private By product = By.cssSelector(".inventory_item");
	private By productTitle = By.cssSelector(".inventory_item_label > a > div");
	private By productAddRemoveButton = By.cssSelector(".btn_inventory");

	private final String ADD_TO_CART = "ADD TO CART";
	private final String Product_label = "PRODUCTS";

	public ProductsPage(WebDriver driver, TestConfig testConfig) {
		super(driver, testConfig);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Check to see if on products page
	 *
	 * @return True if on products page
	 */
	public boolean productHeaderExists() {
		try {
			return getHeaderLabel().equals(Product_label);
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
	 * Check to see if product exits in the product list
	 *
	 * @param productName Name of product
	 * @return True if it exists
	 */
	public boolean productExists(String productName) {
		for (WebElement product : getProducts()) {
			if (product.findElement(productTitle).getText().trim()
				.equals(productName)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Them san pham vao gio hang
	 *
	 * @param productName Ten san pham
	 * @return 
	 */
	public boolean addProductToCart(String productName) {
		for (WebElement product : getProducts()) {
			if (product.findElement(productTitle).getText().trim()
				.equals(productName)) {
				WebElement productAddRemove = product.findElement(productAddRemoveButton);
				if (productAddRemove.getText().trim().equals(ADD_TO_CART)) {
					productAddRemove.click();
					return true;
				}
				break;
			}
		}

		return false;
	}

	/**
	 * Danh sach san pham
	 *
	 * @return Danh sach elements cua cac san pham
	 */
	private List<WebElement> getProducts() {
		return getDriver().findElements(product);
	}
}
