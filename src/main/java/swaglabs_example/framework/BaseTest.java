package swaglabs_example.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import swaglabs_example.data.TestScenario;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Common test functionality
 */
public class BaseTest {
	private final String config = "config.properties";
	private WebDriver driver;
	private TestConfig testConfig;

	/**
	 * Get test config
	 */
	@BeforeTest
	protected void baseTestConfig() {
		testConfig = TestConfig.getInstance(config);
	}

	/**
	 * Setup Chrome driver and open URL
	 */
	@BeforeMethod
	protected void baseTestSetup() {
		System.setProperty(
			"webdriver.chrome.driver",
			System.getProperty("user.dir") + testConfig.getChromedriverPath());
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(testConfig.getImplicitTimeout(), TimeUnit.SECONDS);
		driver.get(testConfig.getUrl());
	}

	/**
	 * Quit Chrome driver
	 */
	@AfterMethod
	protected void baseTestTeardown() {
		if (Objects.nonNull(driver)) {
			driver.quit();
		}
	}

	/**
	 * Get test configuration
	 *
	 * @return Test configuration
	 */
	protected TestConfig getTestConfig() {
		return testConfig;
	}

	/**
	 * Get web driver
	 *
	 * @return Web driver
	 */
	protected WebDriver getDriver() {
		return driver;
	}

	/**
	 * Get test scenario data from JSON files
	 *
	 * @return Test scenarios
	 */
	protected List<TestScenario> getTestScenarios() {
		List<TestScenario> scenarios = new ArrayList<>();

		try {
			Path scenarioFilePath = FileSystems.getDefault()
				.getPath(System.getProperty("user.dir") + testConfig.getScenarioPath());

			List<Path> fileWithName = Files.walk(scenarioFilePath)
				.filter(s -> s.toString().endsWith(".json"))
				.map(Path::getFileName).sorted().collect(Collectors.toList());

			for (Path name : fileWithName) {
				scenarios.add(getFileAsObject(TestScenario.class, "scenarios/" + name.toString()));
			}
		} catch (Exception e) {
			throw new RuntimeException("Error reading scenario data", e);
		}
		return scenarios;
	}

	/**
	 * Bind a file to a class
	 *
	 * @param aClass Class to bind to
	 * @param filePath File path
	 * @return Instance of the class
	 */
	protected  <T> T getFileAsObject(Class<T> aClass, String filePath) {
		ClassLoader classLoader = getClass().getClassLoader();
		ObjectMapper mapper = new ObjectMapper();

		try {
			InputStream input = Objects.requireNonNull(classLoader.getResourceAsStream(filePath));
			return mapper.readValue(IOUtils.toString(input, Charset.defaultCharset()), aClass);
		} catch (Exception e) {
			throw new RuntimeException("Error binding file to object " + filePath, e);
		}
	}

}
