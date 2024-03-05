package org.example;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Driver;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static java.lang.Thread.sleep;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.setProperty("webdriver.chrome.driver", "/Users/rokascebatorius/Downloads/chromedriver-mac-arm642/chromedriver");

        // Create ChromeOptions object
        ChromeOptions options = new ChromeOptions();

        // Set window size
        options.addArguments("--window-size=2320,1080");

        // Initialize ChromeDriver with ChromeOptions
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://demowebshop.tricentis.com/");

//        // Find the "Log in" link by XPath
//        WebElement loginLink = driver.findElement(By.xpath("//a[@class='ico-login']"));
//
//        // Click on the "Log in" link
//        loginLink.click();
//
//        // Find the "Register" button by XPath
//        WebElement registerButton = driver.findElement(By.xpath("//input[@class='button-1 register-button']"));
//
//        // Click on the "Register" button
//        registerButton.click();
//
//        // Fill in the registration form
//        WebElement firstNameInput = driver.findElement(By.id("FirstName"));
//        firstNameInput.sendKeys("John");
//
//        WebElement lastNameInput = driver.findElement(By.id("LastName"));
//        lastNameInput.sendKeys("Doe");
//
//        WebElement emailInput = driver.findElement(By.id("Email"));
//        emailInput.sendKeys("johndoe3333@example.com");
//
//        WebElement passwordInput = driver.findElement(By.id("Password"));
//        passwordInput.sendKeys("password");
//
//        WebElement confirmPasswordInput = driver.findElement(By.id("ConfirmPassword"));
//        confirmPasswordInput.sendKeys("password");
//
//        // Select gender
//        WebElement maleRadio = driver.findElement(By.id("gender-male"));
//        maleRadio.click(); // Assuming you want to select Male
//
//        // Click on the register button
//        WebElement registerButton2 = driver.findElement(By.id("register-button"));
//        registerButton2.click();
//
//        // Find the "Continue" button and click it
//        WebElement continueButton = driver.findElement(By.cssSelector("input.button-1.register-continue-button"));
//        continueButton.click();

        driver.quit();

        test();

    }

    public static void test() throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/rokascebatorius/Downloads/chromedriver-mac-arm642/chromedriver");

        // Create ChromeOptions object
        ChromeOptions options = new ChromeOptions();

        // Set window size
        options.addArguments("--window-size=2320,1080");

        // Initialize ChromeDriver with ChromeOptions
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://demowebshop.tricentis.com/");

        // Find the "Log in" link by XPath
        WebElement loginLink = driver.findElement(By.xpath("//a[@class='ico-login']"));

        // Click on the "Log in" link
        loginLink.click();

        // Find the email and password input fields and input values
        WebElement emailField = driver.findElement(By.id("Email"));
        emailField.sendKeys("johndoe3333@example.com");

        WebElement passwordField = driver.findElement(By.id("Password"));
        passwordField.sendKeys("password");

        // Find the "Remember me" checkbox and click it if needed
        WebElement rememberMeCheckbox = driver.findElement(By.id("RememberMe"));
        if (!rememberMeCheckbox.isSelected()) {
            rememberMeCheckbox.click();
        }

        // Submit the form by clicking the "Log in" button
        WebElement loginButton = driver.findElement(By.cssSelector("input.button-1.login-button"));
        loginButton.click();

        // Find the link by its text and click it
        WebElement digitalDownloadsLink = driver.findElement(By.linkText("Digital downloads"));
        digitalDownloadsLink.click();

        // Nurodome failo, iš kurio skaitysime prekių pavadinimus, kelias
        String filePath = "src/data1.txt";

        // Nuskaitome failą
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        // Skaitykite failą eilutė po eilutės
        while ((line = reader.readLine()) != null) {
            // Ieškome prekės pagal jos pavadinimą
                WebElement product = driver.findElement(By.xpath("//h2[@class='product-title']/a[contains(text(), '" + line + "')]"));

            // Jei prekė rasta, paspaudžiame mygtuką "Add to cart"
            if (product != null) {
                WebElement addToCartButton = product.findElement(By.xpath("../../div[@class='add-info']/div[@class='buttons']/input[@value='Add to cart']"));
                addToCartButton.click();
                System.out.println("Added " + line + " to cart.");
            } else {
                System.out.println("Product " + line + " not found.");
            }
        }

        // Paspauskite ant "Shopping cart" žymės
        WebElement shoppingCartLabel = driver.findElement(By.xpath("//span[@class='cart-label']"));
        shoppingCartLabel.click();

        // Paspauskite mygtuką, kad sutiktumėte su sąlygomis
        WebElement agreeCheckbox = driver.findElement(By.id("termsofservice"));
        agreeCheckbox.click();

        // Palaukite truputį, kad naršyklė galėtų atnaujinti būseną
        Thread.sleep(1000);

        // Paspauskite mygtuką "Checkout"
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        checkoutButton.click();


    }
}

//docker pull jenkins/jenkins
//docker run -itd -p 8080:8080 -p 50000:50000 --name jenkins -v /Users/rokascebatorius:/var/jenkins_home jenkins/jenkins