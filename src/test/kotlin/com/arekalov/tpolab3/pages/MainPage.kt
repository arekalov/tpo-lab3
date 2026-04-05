package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions

internal class MainPage(private val driver: WebDriver) : BasePage(driver) {

    companion object {
        const val URL = "https://stackoverflow.com"
        private const val XPATH_LOGIN_LINK =
            "//a[@class='s-topbar--item s-topbar--item__unset s-btn s-btn__outlined ws-nowrap js-gps-track']"
        private const val XPATH_SEARCH_INPUT = "//*[@id=\"search\"]/div/input"
        private const val XPATH_ASK_QUESTION = "//a[@id='ask-question-button']"
        private const val XPATH_LOGOUT = "//a[contains(@href,'/users/logout')]"
    }

    fun open(): MainPage {
        driver.get(URL)
        return this
    }

    fun clickLogin(): LoginPage {
        val loginLink = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath(XPATH_LOGIN_LINK))
        )
        loginLink.click()
        return LoginPage(driver)
    }

    fun searchFor(query: String): SearchPage {
        val searchBox = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath(XPATH_SEARCH_INPUT))
        )
        searchBox.clear()
        searchBox.sendKeys(query)
        searchBox.submit()
        return SearchPage(driver)
    }

    fun navigateToAskQuestion(): AskQuestionPage {
        val askBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath(XPATH_ASK_QUESTION))
        )
        askBtn.click()
        return AskQuestionPage(driver)
    }

    fun isLoggedIn(): Boolean {
        val contextActionButton = driver.findElement(By.xpath("//a[contains(@class,'js-site-switcher-button') and @aria-label='Site switcher']"))
        contextActionButton.click()
        return driver.findElements(By.xpath("//a[@class='js-gps-track'][normalize-space()='log out']")).isNotEmpty()
    }
}
