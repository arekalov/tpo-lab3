package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

internal class TagPage(private val driver: WebDriver) : BasePage(driver) {

    companion object {
        private const val URL_TAGGED_TEMPLATE = "https://stackoverflow.com/questions/tagged/"
        private const val XPATH_TAG_TITLE = "//h1[contains(@class,'fs-headline')]"
        private const val XPATH_QUESTION_ITEMS =
            "//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]"
        private const val XPATH_PAGINATION_NEXT = "//a[@rel='next']"
        private const val XPATH_FIRST_QUESTION_TITLE =
            "(//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]//h3)[1]"
    }

    private val tagTitle = By.xpath(XPATH_TAG_TITLE)
    private val questionItems = By.xpath(XPATH_QUESTION_ITEMS)
    private val paginationNext = By.xpath(XPATH_PAGINATION_NEXT)

    fun open(tag: String): TagPage {
        driver.get("$URL_TAGGED_TEMPLATE$tag")
        return this
    }

    fun getTagName(): String {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tagTitle)).text.trim()
    }

    fun getQuestionCount(): Int {
        return driver.findElements(questionItems).size
    }

    fun hasQuestions(): Boolean {
        return driver.findElements(questionItems).isNotEmpty()
    }

    fun getFirstQuestionTitle(): String {
        val el = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_FIRST_QUESTION_TITLE))
        )
        return el.text.trim()
    }

    fun clickNextPage(): TagPage {
        wait.until(ExpectedConditions.elementToBeClickable(paginationNext)).click()
        return this
    }

    fun hasNextPage(): Boolean {
        return driver.findElements(paginationNext).isNotEmpty()
    }
}
