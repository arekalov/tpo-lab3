package com.arekalov.tpolab3

import io.github.bonigarcia.wdm.WebDriverManager
import me.bramar.undetectedselenium.SeleniumStealthOptions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

abstract class BaseTest {

    protected lateinit var driver: WebDriver
    protected lateinit var wait: WebDriverWait

    @BeforeEach
    fun setUp() {
        val browser = System.getProperty("browser", "chrome").lowercase()
        driver = when (browser) {
            "firefox" -> {
                WebDriverManager.firefoxdriver().setup()
                FirefoxDriver(FirefoxOptions())
            }
            else -> {
                WebDriverManager.chromedriver().setup()
                val options = ChromeOptions().apply {
                    addArguments("--disable-blink-features=AutomationControlled")
                    addArguments("--no-sandbox")
                    addArguments("--disable-dev-shm-usage")
                    addArguments("--disable-infobars")
                    addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/146.0.0.0 Safari/537.36")
                    setExperimentalOption("excludeSwitches", listOf("enable-automation", "enable-logging"))
                    setExperimentalOption("useAutomationExtension", false)
                }
                ChromeDriver(options).also { SeleniumStealthOptions.getDefault().apply(it) }
            }
        }
        wait = WebDriverWait(driver, Duration.ofSeconds(15))
        driver.manage().window().maximize()
    }

    @AfterEach
    fun tearDown() {
        if (::driver.isInitialized) {
            driver.quit()
        }
    }
}
