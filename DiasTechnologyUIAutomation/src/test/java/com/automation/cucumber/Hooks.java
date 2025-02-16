package com.automation.cucumber;

import com.automation.propertyreader.PropertyReader;
import com.automation.utility.Utility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Hooks extends Utility implements TestWatcher {

    @Before
    public void setUp() {
        selectBrowser(PropertyReader.getInstance().getProperty("browser"));
    }

    public static void takeScreenshot(WebDriver driver, String scenarioName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File src = screenshot.getScreenshotAs(OutputType.FILE);
            String projectPath = System.getProperty("user.dir");
            String screenshotPath = projectPath + "/screenshots/" + scenarioName + ".png";
            FileUtils.copyFile(src, new File(screenshotPath));
            System.out.println("Screenshot saved: " + screenshotPath);
        } catch (IOException e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        takeScreenshot(driver, context.getDisplayName().replace(" ", "_"));
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.println("Test Disabled: " + context.getDisplayName());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            takeScreenshot(driver, scenario.getName().replace(" ", "_"));
        }
        if (driver != null) {
            driver.quit();
        }
    }
}
