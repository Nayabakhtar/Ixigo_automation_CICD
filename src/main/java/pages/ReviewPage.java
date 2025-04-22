// This is an auto-inserted comment
package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReviewPage {
    WebDriver driver;

    public ReviewPage(WebDriver driver) {
        this.driver = driver;
    }

 // Locators for total amount
    By amountSummary = By.xpath("//div[contains(@class, 'justify-between')]//p[2]");
    By amountDetails = By.xpath("//div[contains(@class,'gap-5')]/h5");

    public String getAmountFromSummary() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));

            // More robust XPath (customize based on actual UI)
            WebElement amountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(),'Total')]/following-sibling::p")));

            // Scroll and wait briefly
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", amountElement);
            Thread.sleep(2000);

            String rawAmount = amountElement.getText();
            System.out.println("💰 Captured Amount (raw): [" + rawAmount + "]");
            Thread.sleep(1000);

            if (rawAmount == null || rawAmount.trim().isEmpty()) {
                System.out.println("⚠️ Warning: Captured amount from Summary is blank or null.");
            }

            String normalizedAmount = normalizeAmount(rawAmount);
            System.out.println("✅ Normalized Amount: [" + normalizedAmount + "]");

            return normalizedAmount;

        } catch (Exception e) {
            System.out.println("❌ Error while fetching amount from summary:");
            e.printStackTrace();
            return ""; 
        }
    }



    	public String getAmountFromDetails() {
    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));
    	    String amount = wait.until(ExpectedConditions.visibilityOfElementLocated(amountDetails)).getText();
    	    String normalized = normalizeAmount(amount);
    	    System.out.println("🧾 Amount from Details: " + normalized);
    	    return normalized;
    	}

       // Helper method to remove currency symbols, commas, etc.
    	private String normalizeAmount(String amount) {
    	    if (amount == null) return "";
    	    return amount.replaceAll("[^\d]", ""); // Keep only digits
    	}
       
}
