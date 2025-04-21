package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
    
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final boolean HEADLESS = false; // Set to true if you want headless mode

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            if (HEADLESS) {
                options.addArguments("--headless=new"); // Use headless=new for Chrome v112+
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
            }

            driver.set(new ChromeDriver(options));
            System.out.println("[INFO] New WebDriver instance created");
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            //driver.get().quit();
            driver.remove();
            System.out.println("[INFO] WebDriver instance quit and cleaned up");
        }
    }
}
