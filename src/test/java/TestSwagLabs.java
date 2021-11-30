import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import swaglabs_example.data.TestScenario;
import swaglabs_example.framework.BaseTest;
import swaglabs_example.pages.CartPage;
import swaglabs_example.pages.HeaderPage;
import swaglabs_example.pages.LoginPage;
import swaglabs_example.pages.ProductsPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for Swag Labs
 */
public class TestSwagLabs extends BaseTest {

	/**
	 * Test login of locked out user and error message
	 */
	@Test
	public void testLockedOutLogin() {
		LoginPage loginPage;
		TestScenario testScenario;

		testScenario = getFileAsObject(TestScenario.class, "tests/locked_user.json");

		loginPage = new LoginPage(getDriver(), getTestConfig());

		assertThatThrownBy(() ->
			loginPage.login(testScenario.getUsername(), testScenario.getPassword()))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining(
				"Products page has not loaded");

		assertThat(loginPage.isUserLockedOut()).as("User should have locked out error message").isTrue();
	}

	/**
	 * Test login of unknown user and error message
	 */
	@Test
	public void testUnknownUserLogin() {
		LoginPage loginPage;
		TestScenario testScenario;

		testScenario = getFileAsObject(TestScenario.class, "tests/unknown_user.json");

		loginPage = new LoginPage(getDriver(), getTestConfig());

		assertThatThrownBy(() ->
			loginPage.login(testScenario.getUsername(), testScenario.getPassword()))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining(
				"Products page has not loaded");

		assertThat(loginPage.isUserUnknown()).as("User should have unknown error message").isTrue();
	}

	/**
	 * Test with an unknown item
	 * - Login
	 * - Check to see if unknown item on page
	 */
	@Test
	public void testUnknownItem() {
		LoginPage loginPage;
		ProductsPage productsPage;
		TestScenario testScenario;

		testScenario = getFileAsObject(TestScenario.class, "tests/standard_add_non_existing.json");

		loginPage = new LoginPage(getDriver(), getTestConfig());

		// Login and validate we are on the correct page
		productsPage = loginPage.login(testScenario.getUsername(), testScenario.getPassword());

		// Validate the products we want to use are not on the page
		String product = testScenario.getItemsToAdd().get(0);

		assertThat(productsPage.productExists(product))
			.as(String.format("'%s' product exists", product))
			.isFalse();
	}

	/**
	 * Test workflow scenarios
	 * - Login
	 * - Verify products page
	 * - Add products to cart
	 * - Verify products in cart
	 *
	 * @param testScenario Test scenario data
	 */
	@Test(dataProvider = "scenarios")
	public void testLoginAddProductScenario(TestScenario testScenario) {
		LoginPage loginPage;
		ProductsPage productsPage;
		HeaderPage headerPage;
		CartPage cartPage;

		loginPage = new LoginPage(getDriver(), getTestConfig());
		headerPage = new HeaderPage(getDriver(), getTestConfig());

		// Login and validate we are on the correct page
		productsPage = loginPage.login(testScenario.getUsername(), testScenario.getPassword());

		// Validate the products we want to use are on the page
		for (String product : testScenario.getItemsToAdd()) {
			assertThat(productsPage.productExists(product))
				.as(String.format("'%s' product exists", product))
				.isTrue();
		}

		// Add products to cart
		for (String product : testScenario.getItemsToAdd()) {
			productsPage.addProductToCart(product);
		}

		// Go to the cart and verify they are in the cart
		cartPage = headerPage.goToCart();

		assertThat(headerPage.getCartBadgeTotal())
			.as(String.format("Cart total should be: %s", testScenario.getItemsToAdd().size()))
			.isEqualTo(testScenario.getItemsToAdd().size());

		for (String item : testScenario.getItemsToAdd()) {
			assertThat(cartPage.itemExistsInCart(item))
				.as(String.format("'%s' item exists in cart", item))
				.isTrue();
		}
	}

	/**
	 * Provide test data for workflow scenarios
	 *
	 * @return List of scenarios
	 */
	@DataProvider(name = "scenarios")
	public Object[][] scenarios() {
		List<TestScenario> scenarios = getTestScenarios();
		Object[][] testData = new Object[scenarios.size()][1];

		for (int i = 0; i < scenarios.size(); i++) {
			testData[i][0] = scenarios.get(i);
		}

		return testData;
	}

}
