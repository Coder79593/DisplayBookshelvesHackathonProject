package drivers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.FileInputStream;
import java.io.IOException;
import org.openqa.selenium.Platform;

public class BaseClass {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static Properties properties = new Properties();
    private static final Logger logger = LogManager.getLogger(BaseClass.class);

    static {
        try {
            FileInputStream input = new FileInputStream("src/test/resources/config.properties");
            properties.load(input);
        } catch (IOException e) {
            logger.error("Error loading config file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static WebDriver initializeBrowser() {
        if (driver.get() == null) {
            String executionEnv = getProperty("execution_env").toLowerCase();
            String browser = getProperty("browser").toLowerCase();
            String os = getProperty("os").toLowerCase();
            String hubURL = getProperty("hubURL");

            if (executionEnv.equals("remote")) {
                DesiredCapabilities capabilities = new DesiredCapabilities();

                switch (os) {
                    case "windows":
                        capabilities.setPlatform(Platform.WINDOWS);
                        break;
                    case "mac":
                        capabilities.setPlatform(Platform.MAC);
                        break;
                    case "linux":
                        capabilities.setPlatform(Platform.LINUX);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported OS: " + os);
                }


                switch (browser) {
                    case "chrome":
                        capabilities.setBrowserName("chrome");
                        break;
                    case "edge":
                        capabilities.setBrowserName("MicrosoftEdge");
                        break;
                    case "firefox":
                        capabilities.setBrowserName("firefox");
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported browser: " + browser);
                }

                try {
                    driver.set(new RemoteWebDriver(new URL(hubURL), capabilities));
                } catch (MalformedURLException e) {
                    throw new RuntimeException("Invalid hub URL: " + hubURL, e);
                }

            } else {
                switch (browser) {
                    case "chrome":
                        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
                        driver.set(new ChromeDriver());
                        break;
                    case "edge":
                        System.setProperty("webdriver.edge.driver", "Driver/msedgedriver.exe");
                        driver.set(new EdgeDriver());
                        break;
                    case "firefox":
                        System.setProperty("webdriver.gecko.driver", "Driver/geckodriver.exe");
                        driver.set(new FirefoxDriver());
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported browser: " + browser);
                }
            }

            driver.get().manage().window().maximize();
            logger.info("Browser initialized: " + browser);

            String url = getProperty("url");
            if (url != null) {
                driver.get().navigate().to(url);
                logger.info("Navigated to URL: " + url);
            } else {
                logger.error("URL not specified in config.properties");
                throw new RuntimeException("URL not specified in config.properties");
            }
        }
        return driver.get();
    }

    public static void quitBrowser() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            logger.info("Browser closed successfully");
        }
    }
}

