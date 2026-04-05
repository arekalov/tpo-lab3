package com.arekalov.tpolab3.page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

internal class SearchPage(driver: WebDriver) : BasePage(driver) {

    companion object {
        const val URL_FRAGMENT = "/search"

        private const val XPATH_RESULTS = "//div[@class=' js-post-summaries']/*"
        private const val XPATH_FIRST_RESULT_LINK = "(//div[contains(@class,'s-post-summary')]//h3/a)[1]"
        private const val XPATH_NO_RESULTS = "//div[text()='Try different or less specific keywords.']"
        private const val XPATH_SEARCH_INPUT = "//input[@name='q' and @type='text']"
    }

    fun hasResults(): Boolean =
        waitVisibleElements(By.xpath(XPATH_RESULTS)).isNotEmpty()

    fun hssNoResults(): Boolean =
        !driver.findElements(By.xpath(XPATH_RESULTS)).isEmpty()

    fun hasNoResultsMessage(): Boolean =
        waitVisible(By.xpath(XPATH_NO_RESULTS)).isDisplayed


    fun getFirstResultTitle(): String =
        waitVisible(By.xpath(XPATH_FIRST_RESULT_LINK)).text.trim()

    fun clickFirstResult(): SearchPage {
        waitClickable(By.xpath(XPATH_FIRST_RESULT_LINK)).click()
        return this
    }

    fun searchFor(query: String): SearchPage {
        val input = waitClickable(By.xpath(XPATH_SEARCH_INPUT))
        input.clear()
        input.sendKeys(query)
        input.submit()
        return SearchPage(driver)
    }
}
