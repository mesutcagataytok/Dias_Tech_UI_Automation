package com.automation.cucumber.steps;

import com.automation.pages.HespiburadaPage;
import com.automation.utility.Utility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class HepsiburadaSteps extends Utility {
    HespiburadaPage hespiburadaPage = new HespiburadaPage();

    @Given("^I am on homepage$")
    public void iAmOnHomepage()
    {
        String actualSolution = driver.getTitle();
        String expectedSolution = "Türkiye'nin En Çok Tavsiye Edilen E-ticaret Markası Hepsiburada";
        Assertions.assertEquals(expectedSolution, actualSolution);
    }

    @Then("I select tablet")
    public void iSelectTablet() {
        hespiburadaPage.selectTablet();
    }

    @Then("I accept cookies")
    public void iAcceptCookies() {
        hespiburadaPage.acceptCookies();
    }

    @Then("I select filter")
    public void iSelectFilter() {
        hespiburadaPage.selectFilter();
    }

    @Then("The highest priced product is selected")
    public void theHighestPricedProductIsSelected() throws InterruptedException {
        hespiburadaPage.theHighestPricedProductIsSelected();
    }


    @Then("I add to basket and verify")
    public void iAddToBasketAndVerify() {
        hespiburadaPage.addToBasketAndVerify();
    }
}
