package com.arekalov.tpolab3.test

import com.arekalov.tpolab3.DriverFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

abstract class BaseTest {
    companion object {
        const val WAIT_DURATION_SEC = 30L
    }

    protected lateinit var driver: WebDriver
    protected lateinit var wait: WebDriverWait

    @BeforeEach
    fun setUp() {
        driver = DriverFactory.create()
        wait = WebDriverWait(driver, Duration.ofSeconds(WAIT_DURATION_SEC))
        driver.manage().window().maximize()
    }

    @AfterEach
    fun tearDown() {
        if (::driver.isInitialized) {
            driver.quit()
        }
    }

    protected fun waitForUrl(fragment: String) {
        wait.until(ExpectedConditions.urlContains(fragment))
    }
}