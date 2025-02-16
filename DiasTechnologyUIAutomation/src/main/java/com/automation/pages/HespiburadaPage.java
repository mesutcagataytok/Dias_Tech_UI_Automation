package com.automation.pages;

import com.automation.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.*;

public class HespiburadaPage extends Utility {
    @FindBy(xpath = "//span[text()[contains(.,'Elektronik')]]")
    WebElement electronicsLink;
    @FindBy(xpath = "//a[text()[contains(.,'Bilgisayar/Tablet')]]")
    WebElement computerTabletLink;
    @FindBy(xpath = "(//a[text()[contains(.,'Tablet')]])[2]")
    WebElement tabletLink;
    @FindBy(css = "button#onetrust-accept-btn-handler")
    WebElement acceptCookiesButton;
    @FindBy(xpath = "//a[@title='Apple Tablet']")
    WebElement appleTabletLink;
    @FindBy(xpath = "//span[text()[contains(.,'13,2')]]")
    WebElement inchButton;
    @FindBy(xpath = "//button[@data-test-id='addToCart']")
    WebElement addToBasketButton;
    @FindBy(xpath = "(//button[@type='button'])[1]")
    WebElement goToBasketButton;
    @FindBy(id = "txtUserName")
    WebElement userNameInput;
    @FindBy(css = "input#txtPassword")
    WebElement passwordInput;
    @FindBy(id = "btnLogin")
    WebElement loginButton;

    public void selectTablet() {
        driver.navigate().refresh();
        // Elektronik gözükene kadar beklet
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()[contains(.,'Elektronik')]]")));
        // Elektronik alanına hover olur
        mouseHoverByElement(electronicsLink);
        // Bilgisay/Tablet gözükene kadar beklet
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()[contains(.,'Bilgisayar/Tablet')]]")));
        //  Bilgisay/Tablet hover yap
        mouseHoverByElement(computerTabletLink);
        // Tablet gözükene kadar beklet
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//a[text()[contains(.,'Tablet')]])[2]")));
        clickOnElement(tabletLink);
    }

    public void acceptCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button#onetrust-accept-btn-handler")));
            clickOnElement(acceptCookiesButton);
        }
       catch (Exception e){
           System.out.println("There is no cookies");
       }
    }

    public void selectFilter() {
        // Apple tablet gelene kadar beklet
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title='Apple Tablet']")));
        // Apple tablet seçilir
        //clickOnElement(appleTabletLink);
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("document.querySelector('a[title=\"Apple Tablet\"]').click();");
        driver.navigate().refresh();
        // 13,2 inç seçeneği gelene kadar beklet
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()[contains(.,'Apple Tablet Fiyatları ve Modelleri')]]")));
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//span[text()[contains(.,'13,2')]]")));
        //clickOnElement(inchButton);
        js1.executeScript("document.querySelector('a[title=\"Apple 13,2 inç Tablet\"]').click();");
        // Apple 13,2 inç Tablet Fiyatları ve Modelleri text i gelene kadar bekler
        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()[contains(.,'Apple 13,2 inç Tablet Fiyatları ve Modelleri')]]")));
    }

    public void theHighestPricedProductIsSelected() throws InterruptedException {
        // Tüm fiyatları içeren WebElement'leri bul
        List<WebElement> pricesElement = driver.findElements(By.xpath("//div[@data-test-id='price-current-price']"));

        List<Double> prices = new ArrayList<>();
        Map<Double, WebElement> priceElementMap = new HashMap<>(); // Fiyat ile WebElement eşleştirme

        // Fiyatları temizleyip listeye ekleyelim
        for (WebElement priceElement : pricesElement) {
            String priceText = priceElement.getText();
            System.out.println("Gelen fiyat: " + priceText);

            // Fiyatı temizleyerek sayıya çevirme
            String cleanPrice = priceText.replace(".", "").replace(",", ".").replaceAll("[^0-9.]", "");
            double numericPrice = Double.parseDouble(cleanPrice);

            prices.add(numericPrice);
            priceElementMap.put(numericPrice, priceElement); // Fiyat ile WebElement'i eşleştir
        }

        // En yüksek fiyatı bul
        double maxPrice = findMaxPrice(prices);
        System.out.println("En yüksek fiyat: " + maxPrice);

        // En yüksek fiyatın WebElement'ini al ve tıkla
        WebElement highestPriceElement = priceElementMap.get(maxPrice);
        if (highestPriceElement != null) {
            highestPriceElement.click();
            System.out.println("En yüksek fiyatlı ürüne tıklandı!");
        } else {
            System.out.println("En yüksek fiyatlı ürün bulunamadı!");
        }
    }

    // Listenin en yüksek değerini bulan metot
    public static double findMaxPrice(List<Double> prices) {
        return prices.stream().max(Double::compare).orElse(0.0);
    }
    public void addToBasketAndVerify() {
        Set<String> windowHandles = driver.getWindowHandles();
        List<String> windowList = new ArrayList<>(windowHandles);

        driver.switchTo().window(windowList.get(1));
        System.out.println("Switched to the second tab.");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-test-id='default-price']/div/span")));

        // Detay sayfasında gelen fiyat alınır
        String defaultPriceElementText = driver.findElement(By.xpath("//div[@data-test-id='default-price']/div/span")).getText();
        String cleanPrice = defaultPriceElementText.replace(".", "").replace(",", ".").replaceAll("[^0-9.]", "");
        double defaultPrice = Double.parseDouble(cleanPrice);
        System.out.println("Default price: " + defaultPrice);

        // Sepete ekle butonu gelene kadar bekler

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@data-test-id='addToCart']")));

        // Sepete ekle butonuna tıklar
        clickOnElement(addToBasketButton);

        // Sepete git butonun gelmesi beklenir

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@type='button'])[1]")));

        // Sepete git butonuna tıklanır
        clickOnElement(goToBasketButton);

        // Login sayfasına yönlerdirdiği için login olunur...
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("txtUserName")));
        clickOnElement(userNameInput);
        sendTextToElement(userNameInput,"cagataytok@windowslive.com");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#txtPassword")));
        clickOnElement(passwordInput);
        sendTextToElement(passwordInput,"Test1234.");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("btnLogin")));
        clickOnElement(loginButton);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("(//div[contains(@class, 'product_price')]/div)[4]")));
        String basketPriceElementText = driver.findElement(By.xpath("(//div[contains(@class, 'product_price')]/div)[4]")).getText();
        String basketCleanPrice = basketPriceElementText.replace(".", "").replace(",", ".").replaceAll("[^0-9.]", "");
        double basketPrice = Double.parseDouble(basketCleanPrice);
        System.out.println("Basket price: " + basketPrice);

        if (defaultPrice == basketPrice){
            System.out.println("Test is succesfull");
        }
        else {
            throw new RuntimeException("Test Failed!");
        }
    }
}

