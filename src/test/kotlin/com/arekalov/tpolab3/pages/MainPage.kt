package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class MainPage(private val driver: WebDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    companion object {
        const val URL = "https://stackoverflow.com"
    }

    fun open(): MainPage {
        driver.get(URL)
        return this
    }

    fun clickLogin(): LoginPage {
        val loginLink = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, '/users/login') and not(contains(@class,'s-btn--primary'))]")
            )
        )
        loginLink.click()
        return LoginPage(driver)
    }

    fun searchFor(query: String): SearchPage {
        val searchBox = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@name='q' and @type='text']")
            )
        )
        searchBox.clear()
        searchBox.sendKeys(query)
        searchBox.submit()
        return SearchPage(driver)
    }

    fun navigateToAskQuestion(): AskQuestionPage {
        val askBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'/questions/ask')]")
            )
        )
        askBtn.click()
        return AskQuestionPage(driver)
    }

    fun isLoggedIn(): Boolean {
        return driver.findElements(
            By.xpath("//a[contains(@href,'/users/logout')]")
        ).isNotEmpty()
    }
}
