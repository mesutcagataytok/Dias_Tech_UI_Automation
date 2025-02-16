package com.automation.cucumber.runner;

import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.jupiter.api.AfterAll;

@Cucumber
public class TestRunner {
    @AfterAll
    public static void tearDown() {
        // Test sonrası işlemler buraya eklenebilir
    }
}
