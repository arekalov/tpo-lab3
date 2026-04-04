package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class SearchPage(private val driver: WebDriver) {

    companion object {
        private const val URL_TAGGED_TEMPLATE = "https://stackoverflow.com/questions/tagged/"
        private const val XPATH_RESULT_ITEMS =
            "//div[contains(@class,'question-summary') or contains(@class,'s-post-summary')]"
        private const val XPATH_NO_RESULTS =
            "//*[contains(text(),'No results found') or contains(text(),'no results')]"
        private const val XPATH_SEARCH_INPUT = "//input[@name='q' and @type='text']"
        private const val XPATH_FIRST_RESULT_TITLE =
            "(//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]//h3)[1]"
        private const val XPATH_FIRST_RESULT_LINK =
            "(//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]//h3/a)[1]"
    }

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    private val resultItems = By.xpath(XPATH_RESULT_ITEMS)
    private val noResultsMessage = By.xpath(XPATH_NO_RESULTS)
    private val searchInput = By.xpath(XPATH_SEARCH_INPUT)

    fun getResultCount(): Int {
        return driver.findElements(resultItems).size
    }

    fun hasResults(): Boolean {
        return driver.findElements(resultItems).isNotEmpty()
    }

    fun hasNoResultsMessage(): Boolean {
        return driver.findElements(noResultsMessage).isNotEmpty()
    }

    fun getFirstResultTitle(): String {
        val first = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_FIRST_RESULT_TITLE))
        )
        return first.text
    }

    fun clickFirstResult(): QuestionPage {
        val link = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath(XPATH_FIRST_RESULT_LINK))
        )
        link.click()
        return QuestionPage(driver)
    }

    fun getCurrentQuery(): String {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).getAttribute("value") ?: ""
    }

    fun filterByTag(tag: String): SearchPage {
        driver.get("$URL_TAGGED_TEMPLATE$tag")
        return this
    }
}
