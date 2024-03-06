package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Duration;
import java.util.List;

public class Main {
    ChromeDriver registrationDriver = new ChromeDriver();
    ChromeDriver data1Driver = new ChromeDriver();
    ChromeDriver data2Driver = new ChromeDriver();
    String name = "Rokas";
    String lastname = "Rokas";
    String email = "Rokas@Rokas.com";
    String pasw = "Rokas1234";

    @Before
    public void lab4RegisterMethod() {
        ChromeDriver driver = this.registrationDriver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        driver.manage().window().maximize();
        driver.get("https://demowebshop.tricentis.com/");
        driver.findElement(By.className("ico-login")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("register-button"))).click();

        driver.findElement(By.id("FirstName")).sendKeys(this.name);
        driver.findElement(By.id("LastName")).sendKeys(this.lastname);
        driver.findElement(By.id("Email")).sendKeys(this.email);
        driver.findElement(By.id("Password")).sendKeys(this.pasw);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(this.pasw);
        driver.findElement(By.id("gender-male")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("register-button"))).click();
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("validation-summary-errors")));
            System.out.println("Email already exist!");
        } catch (Exception e) {
            // Handle exception if the error div is found or if there is any other issue
            wait.until(ExpectedConditions.elementToBeClickable(By.className("register-continue-button"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.className("ico-logout"))).click();

        }
    }
    @Test
    public void testData1(){
        lab4test(this.data1Driver, "data1.txt");
        lab4test(this.data2Driver, "data2.txt");
    }

    @After
    public void quitDrivers(){
        if (this.registrationDriver != null) {
            this.registrationDriver.quit();
        }
        if (this.data1Driver != null) {
            this.data1Driver.quit();
        }
        if (this.data2Driver != null) {
            this.data2Driver.quit();
        }
    }
    public void lab4test(ChromeDriver driver, String dataFile) {
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        JavascriptExecutor js = driver;
        driver.get("https://demowebshop.tricentis.com/");
        driver.findElement(By.className("ico-login")).click();

        // 3. Užpildyti 'Email:', 'Password:' ir spausti 'Log in'
        WebElement emailField = driver.findElement(By.id("Email"));
        emailField.sendKeys(email);

        WebElement passwordField = driver.findElement(By.id("Password"));
        passwordField.sendKeys(pasw);
        driver.findElement(By.cssSelector("input.button-1.login-button")).click();
        driver.findElement(By.linkText("Digital downloads")).click();

        //    skaitymas iš failo
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/" + dataFile));
            String productName;
            while ((productName = reader.readLine()) != null) {
                driver.findElement(By.xpath("//a[contains(text(),'" + productName + "')]/parent::h2/following::div[@class='add-info']/descendant::input")).click();
                System.out.println(productName);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Did not find the file");
            System.out.print("EXCEPTION: " + e);
//      throw e;
        }
        driver.findElement(By.linkText("Shopping cart")).click();
        driver.findElement(By.id("termsofservice")).click();
        driver.findElement(By.id("checkout")).click();
        boolean foundOtherOption = false;

        try {
            WebElement addressDropdown = driver.findElement(By.className("address-select"));
            Select select = new Select(addressDropdown);
            List<WebElement> options = select.getOptions();
            for (WebElement option : options) {
                if (!option.getText().equals("New Address")) {
                    foundOtherOption = true;
                    option.click();
                    break;
                }
            }
        } catch (Exception ex) {
        }

// Check the flag value to determine if any option other than "New Address" was found
        if (!foundOtherOption) {
            Select countryDropdown = new Select(driver.findElement(By.id("BillingNewAddress_CountryId")));
            countryDropdown.selectByVisibleText("Lithuania");
            driver.findElement(By.id("BillingNewAddress_City")).sendKeys("Vilnius");
            driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("Str 1");
            driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("06277");
            driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("37061234567");
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.button-1.new-address-next-step-button"))).click();
        js.executeScript("window.scrollBy(0,250)", "");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.button-1.payment-method-next-step-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.button-1.payment-info-next-step-button"))).click();
        js.executeScript("window.scrollBy(0,250)", "");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.button-1.confirm-order-next-step-button"))).click();

        String successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='title']/strong[text()]"))).getText();
        Assert.assertEquals(true, successMessage.contains("successfully"));
    }
}
