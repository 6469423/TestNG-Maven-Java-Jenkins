package swaglabs_example.framework;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manage test configuration
 */
public class TestConfig {
	private String chromedriverPath;
	private String scenarioPath;
	private int implicitTimeout;
	private String url;

	public static TestConfig getInstance(String config) {
		Properties properties = new Properties();
		TestConfig testConfig = new TestConfig();

		try (InputStream input = new FileInputStream("src/test/resources/" + config)) {
			properties.load(input);
			testConfig.setChromedriverPath(properties.getProperty("chromedriver_path"));
			testConfig.setScenarioPath(properties.getProperty("scenario_path"));
			testConfig.setImplicitTimeout(Integer.parseInt(properties.getProperty("implicit_timeout")));
			testConfig.setUrl(properties.getProperty("url"));
			return testConfig;
		} catch (Exception e) {
			throw new RuntimeException("Unable to load test properties", e);
		}
	}

	public String getChromedriverPath() {
		return chromedriverPath;
	}

	public void setChromedriverPath(String chromedriverPath) {
		this.chromedriverPath = chromedriverPath;
	}

	public String getScenarioPath() {
		return scenarioPath;
	}

	public void setScenarioPath(String scenarioPath) {
		this.scenarioPath = scenarioPath;
	}

	public int getImplicitTimeout() {
		return implicitTimeout;
	}

	public void setImplicitTimeout(int implicitTimeout) {
		this.implicitTimeout = implicitTimeout;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
