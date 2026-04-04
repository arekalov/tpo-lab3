package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class SearchPage(private val driver: WebDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    private val resultItems = By.xpath("//div[contains(@class,'question-summary') or contains(@class,'s-post-summary')]")
    private val noResultsMessage = By.xpath("//*[contains(text(),'No results found') or contains(text(),'no results')]")
    private val resultCount = By.xpath("//*[contains(@class,'results-header') or contains(@class,'fs-body3')]")
    private val searchInput = By.xpath("//input[@name='q' and @type='text']")

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
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]//h3)[1]")
            )
        )
        return first.text
    }

    fun clickFirstResult(): QuestionPage {
        val link = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "(//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]//h3/a)[1]"
                )
            )
        )
        link.click()
        return QuestionPage(driver)
    }

    fun getCurrentQuery(): String {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).getAttribute("value") ?: ""
    }

    fun filterByTag(tag: String): SearchPage {
        driver.get("https://stackoverflow.com/questions/tagged/$tag")
        return this
    }
}
