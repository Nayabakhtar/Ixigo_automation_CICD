package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.FlightResultsPage;
import pages.HomePage;
import pages.ReviewPage;

import utils.DriverFactory;


import org.openqa.selenium.TimeoutException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;




public class StepDefinitions {

    WebDriver driver;
    HomePage homePage;
    FlightResultsPage flightResultsPage;
    ReviewPage reviewPage;
    String amountFromSummary;
    String amountFromDetails;
    WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));


    @Before
    public void setUp() {
        driver = DriverFactory.getDriver();
    }

    @Given("User on the Ixigo homepage")
    public void i_am_on_the_ixigo_homepage() throws InterruptedException {
        driver.get("https://www.ixigo.com/");
        Thread.sleep(4000);

        
        homePage = new HomePage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Wait for body to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            // Use Actions to click at an offset (say 100, 100) to close popup
            Actions actions = new Actions(driver);
            actions.moveByOffset(120, 120).click().perform();

            System.out.println("✅ Pop-up got closed");
        } catch (Exception e) {
            System.out.println("❌ Pop-up not closed (exception occurred): " + e.getMessage());
        }
    }

    
    @And("User select the {string} tab")
    public void i_select_the_tab(String tabName) {
        try {
            homePage.selectRoundTrip(tabName);
        } catch (Exception e) {
            System.out.println("Skipping round trip selection: " + e.getMessage());
        }
    }


   


   //From and To Selection
    @When("User enter From as {string} and To as {string}")
    public void i_enter_from_as_and_to_as(String fromCity, String toCity) throws Exception {
        
        try {
            // Only enter To city, assuming From is pre-filled as "Kolkata"
            System.out.println("Entering destination city: " + toCity);
            homePage.enterToCity(toCity);
        } catch (Exception e) {
            System.out.println("Failed to enter destination city: " + e.getMessage());
            throw e;
        }
    }



// DEparture Date and Return Selection
    @When("User select departure date {string} June, 2025")
    public void iSelectDepartureDate(String date) {
        homePage.selectDepartureDate(date);
    }

    @And("User select return date {string} {string}")
    public void iSelectReturnDate(String date, String monthYear) {
        homePage.selectReturnDate(date, monthYear);
    }
    
    
    @And("User select 3 Adults, 1 Children, and 1 Infants")
    public void selectTravellersAndClass() {
        homePage.selectTravellers(3, 1, 1);
    }
   
    @And("User click search")
    public void iClickSearch() {
        homePage.clickSearchButton();
    }
    
    @When("User switch to the new browser tab")
    public void iSwitchToNewTab() {

        flightResultsPage = new FlightResultsPage(driver); // ✅ Initialize first!
        flightResultsPage.switchToNewTab();                

    }

    
    
    @When("User clicks and selects the 1 stop checkbox in Filter")
    public void user_clicks_and_selects_the_hidden_checkbox() {
    	flightResultsPage.clickHiddenCheckbox();
        Assert.assertTrue(flightResultsPage.isCheckboxSelected());
    }
    
    
   
    @And("User click on the Earliest Departure radio button")
    public void i_click_on_the_earliest_radio_button() {
    	flightResultsPage.clickEarliestRadioButton();
        System.out.println("✅ Clicked on the Earliest radio button");
    }
    
    
    @When("User clicks on the Book button")
    public void the_user_clicks_on_the_book_button() {
    	flightResultsPage.clickBookButton();
    }

    @Then("the booking action should be triggered")
    public void the_booking_action_should_be_triggered() {
        // Add assertion or confirmation step
        System.out.println("Book button clicked successfully.");
    }
    
    
    @When("User captures both total amounts")
    public void the_user_captures_both_total_amounts() throws InterruptedException {
    	reviewPage = new ReviewPage(driver); 
        amountFromSummary = reviewPage.getAmountFromSummary();
        Thread.sleep(5000);
        amountFromDetails = reviewPage.getAmountFromDetails();
    }

    @Then("both total amounts should match")
    public void both_total_amounts_should_match() {
        Assert.assertEquals("Total amounts do not match!", amountFromSummary, amountFromDetails);
        System.out.println("✅ Total amounts match: " + amountFromSummary);
    }
    
 
    
    
    
    
    

        @And("User take a screenshot of the Review Flight Details page")
        public void i_take_screenshot_of_review_page() {
            takeScreenshot("reviewPage");
        }

        public void takeScreenshot(String fileName) {
            try {
                // Scroll to top
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
                Thread.sleep(500);

                // Scroll to bottom
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(500);

                // Take screenshot
                TakesScreenshot ts = (TakesScreenshot) driver;
                File srcFile = ts.getScreenshotAs(OutputType.FILE);

                // File path with timestamp
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File destFile = new File("target/screenshots/" + fileName + "_" + timestamp + ".png");
                destFile.getParentFile().mkdirs();

                Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Screenshot saved: " + destFile.getAbsolutePath());

            } catch (Exception e) {
                System.err.println("Error while taking screenshot: " + e.getMessage());
                e.printStackTrace();
            }
        }
    


    
    
    
    
    
   

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
