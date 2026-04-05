package com.arekalov.tpolab3

import driver.ChromeDriverBuilder
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
    companion object {
        const val WAIT_DURATION_SEC = 15L
    }

    protected lateinit var driver: WebDriver
    protected lateinit var wait: WebDriverWait
    protected lateinit var captchaWait: WebDriverWait

    @BeforeEach
    fun setUp() {
        val browser = System.getProperty("browser", "chrome").lowercase()
        driver = when (browser) {
            "firefox" -> {
                FirefoxDriver()
            }
            else -> {
                val driverHome = "/usr/local/bin/chromedriver"
                val chromeOptions = ChromeOptions().apply {
                    addArguments("--user-data-dir=/Users/arekalov/Library/Application Support/Google/Chrome")
                    addArguments("--user-data-dir=/tmp/my-chrome-profile")
                }
                ChromeDriverBuilder().build(chromeOptions, driverHome)
            }
        }
        wait = WebDriverWait(driver, Duration.ofSeconds(WAIT_DURATION_SEC))
        driver.manage().window().maximize()
    }

    @AfterEach
    fun tearDown() {
        if (::driver.isInitialized) {
            driver.quit()
        }
    }
}
