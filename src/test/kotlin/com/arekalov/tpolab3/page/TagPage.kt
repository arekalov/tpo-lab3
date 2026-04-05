package com.arekalov.tpolab3.page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

internal class TagPage(driver: WebDriver) : BasePage(driver) {

    companion object {
        const val URL_FRAGMENT = "/questions/tagged/"

        private const val XPATH_RESULTS = "//div[contains(@class,'s-post-summary')]"
        private const val XPATH_FIRST_RESULT_LINK = "(//div[contains(@class,'s-post-summary')]//h3/a)[1]"
        private const val XPATH_TAG_HEADER = "//h1[contains(@class,'fs-headline')]"
    }

    fun hasResults(): Boolean =
        waitVisibleElements(By.xpath(XPATH_RESULTS)).isNotEmpty()

    fun isTagHeaderVisible(): Boolean = isDisplayed(By.xpath(XPATH_TAG_HEADER))

    fun getFirstResultTitle(): String =
        waitVisible(By.xpath(XPATH_FIRST_RESULT_LINK)).text.trim()

    fun clickFirstResult(): QuestionPage {
        waitClickable(By.xpath(XPATH_FIRST_RESULT_LINK)).click()
        return QuestionPage(driver)
    }
}
