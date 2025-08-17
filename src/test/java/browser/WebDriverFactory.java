package browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static WebDriver createDriver(Browser browser) {
        String driverPath;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--start-maximized");

        if (browser == Browser.CHROME) {
            driverPath = System.getProperty("user.dir") + "/driver/chrome/chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", driverPath);
            return new ChromeDriver(options);
        }
        else if (browser == Browser.YANDEX) {
            driverPath = System.getProperty("user.dir") + "/driver/yandex/yandexdriver.exe";
            System.setProperty("webdriver.chrome.driver", driverPath);
            options.setBinary("C:/Users/Kauf/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
            return new ChromeDriver(options);
        }
        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }
}