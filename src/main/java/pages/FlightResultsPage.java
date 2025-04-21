package pages;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightResultsPage {
    WebDriver driver;

    public FlightResultsPage(WebDriver driver) {
        this.driver = driver;
    }
    
  //Locators for "1 stop" Checkbox selection from Filter Options 
    By cCheckbox = By.cssSelector("input[type='checkbox'][value='1']");

    //Locators for selecting Departure Filter sorting
    By earliestRadioButton = By.cssSelector("input[type='radio'][value='earliest'][name='oneWayType']");
    
    //Locator for selecting the card
    By akasaAirCard = By.xpath("//p[text()='Akasa Air']/ancestor::div[contains(@class,'cursor-pointer')]");

    //Locator for clicking Book button at the bottom
    By bookButton = By.xpath("//button[contains(text(), 'Book') and contains(@class, 'bg-brand-solid')]");


    public void switchToNewTab() {
        try {
            String currentTab = driver.getWindowHandle();
            Set<String> allTabs = driver.getWindowHandles();
            for (String tab : allTabs) {
                if (!tab.equals(currentTab)) {
                    driver.switchTo().window(tab);
                    System.out.println("✅ Switched to new tab.");
                    
                    // Wait for the page title to load or a specific condition
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
                    wait.until(ExpectedConditions.titleIs(driver.getTitle()));  // or any other condition you expect

                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to switch to new tab.");
            e.printStackTrace();
        }
    }

    public void clickHiddenCheckbox() {
 	    try {
 	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

 	        // Wait until checkbox is present (even if hidden)
 	        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(cCheckbox));

 	        // Optional: Scroll to the checkbox in case it’s just offscreen
 	        JavascriptExecutor js = (JavascriptExecutor) driver;
 	        js.executeScript("arguments[0].scrollIntoView(true);", checkbox);
 	        Thread.sleep(2000); // brief wait after scroll

 	        // Force-click using JavaScript
 	        js.executeScript("arguments[0].click();", checkbox);

 	        System.out.println("✅ Hidden checkbox clicked via JS.");
 	    } catch (Exception e) {
 	        System.out.println("❌ Failed to click hidden checkbox.");
 	        e.printStackTrace();
 	    }
 	}

    public boolean isCheckboxSelected() {
        return driver.findElement(cCheckbox).isSelected();
    }

    
    

    

    public void clickEarliestRadioButton() {
 	    try {
 	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
 	        JavascriptExecutor js = (JavascriptExecutor) driver;

 	        // Wait for the radio button to be present in DOM
 	        WebElement radioBtn = wait.until(ExpectedConditions.presenceOfElementLocated(earliestRadioButton));

 	        // Scroll into view and use JS to click
 	        js.executeScript("arguments[0].scrollIntoView(true);", radioBtn);
 	        Thread.sleep(300);

 	        if (!radioBtn.isSelected()) {
 	            js.executeScript("arguments[0].click();", radioBtn);
 	            System.out.println("✅ Radio button clicked via JS.");
 	        } else {
 	            System.out.println("ℹ️ Radio button already selected.");
 	        }

 	        // Wait for Akasa Air card to be present and clickable
 	        WebElement akasaAirCardElement = wait.until(ExpectedConditions.presenceOfElementLocated(akasaAirCard));
 	        js.executeScript("arguments[0].scrollIntoView(true);", akasaAirCardElement);
 	        Thread.sleep(300);
 	        js.executeScript("arguments[0].click();", akasaAirCardElement);
 	        System.out.println("✅ Akasa Air card clicked via JS.");
 	        Thread.sleep(6000);


 	    } catch (Exception e) {
 	        System.out.println("❌ Failed to click radio button or Akasa Air card.");
 	        e.printStackTrace();
 	        
 	    }
 	}


    public void clickBookButton() {
 	   // Define WebDriverWait locally
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        
        // Wait until the button is clickable
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(bookButton));
        button.click();
    }

    }

