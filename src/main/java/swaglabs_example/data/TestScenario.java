package swaglabs_example.data;

import java.util.List;

/**
 * Test Scenario Data
 */
public class TestScenario {
	private String username;
	private String password;
	private List<String> itemsToAdd;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getItemsToAdd() {
		return itemsToAdd;
	}

	public void setItemsToAdd(List<String> itemsToAdd) {
		this.itemsToAdd = itemsToAdd;
	}

	/**
	 * Override for TestNG displaying
	 *
	 * @return Test scenario information
	 */
	@Override
	public String toString() {
		return username + ":" + itemsToAdd;
	}
}
