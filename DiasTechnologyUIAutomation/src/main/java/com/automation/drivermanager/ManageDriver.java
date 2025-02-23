package com.automation.drivermanager;

import com.automation.propertyreader.PropertyReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;


public class ManageDriver {
    private static final Logger log = LogManager.getLogger(ManageDriver.class.getName());
    public static WebDriver driver;

    public String baseUrl = PropertyReader.getInstance().getProperty("baseUrl");

    public ManageDriver() {
        PageFactory.initElements(driver, this);
        PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/test/java/resources/propertiesfile/log4j2.properties");
    }

    public void selectBrowser(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            log.info("Launching Chrome Browser");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-cookies");
            options.addArguments("--disable-cache");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-geolocation");
            // Chrome WebDriver'ı başlat
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("ie")) {
            driver = new InternetExplorerDriver();
        } else {
            System.out.println("Wrong browser name");
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get(baseUrl);
    }
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}