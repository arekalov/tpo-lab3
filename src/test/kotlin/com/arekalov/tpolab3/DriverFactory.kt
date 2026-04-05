package com.arekalov.tpolab3

import driver.ChromeDriverBuilder
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver

object DriverFactory {
    fun create(browser: String = System.getProperty("browser", "chrome").lowercase()): WebDriver =
        when (browser) {
            "firefox" -> FirefoxDriver()
            else -> {
                val chromeOptions = ChromeOptions().apply {
                    addArguments("--user-data-dir=/tmp/my-chrome-profile")
                }
                ChromeDriverBuilder().build(chromeOptions, "/usr/local/bin/chromedriver")
            }
        }
}
