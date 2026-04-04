package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class AskQuestionPage(private val driver: WebDriver) {

    companion object {
        private const val XPATH_TITLE = "//input[@id='title' or @name='title']"
        private const val XPATH_BODY = "//div[@class='wmd-input' or @id='wmd-input']"
        private const val XPATH_TAGS =
            "//input[contains(@class,'s-input') and @name='tagnames']"
        private const val XPATH_REVIEW = "//button[contains(.,'Review your question')]"
        private const val XPATH_SUBMIT = "//button[contains(.,'Post your question')]"
        private const val XPATH_TITLE_ERROR =
            "//*[contains(@class,'js-title-error') or contains(@class,'d-error')]"
    }

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    private val titleField = By.xpath(XPATH_TITLE)
    private val bodyEditor = By.xpath(XPATH_BODY)
    private val tagsField = By.xpath(XPATH_TAGS)
    private val reviewButton = By.xpath(XPATH_REVIEW)
    private val submitButton = By.xpath(XPATH_SUBMIT)

    fun enterTitle(title: String): AskQuestionPage {
        val field = wait.until(ExpectedConditions.elementToBeClickable(titleField))
        field.clear()
        field.sendKeys(title)
        return this
    }

    fun enterBody(body: String): AskQuestionPage {
        val editor = wait.until(ExpectedConditions.elementToBeClickable(bodyEditor))
        editor.click()
        editor.sendKeys(body)
        return this
    }

    fun enterTag(tag: String): AskQuestionPage {
        val field = wait.until(ExpectedConditions.elementToBeClickable(tagsField))
        field.sendKeys(tag)
        field.sendKeys(Keys.ENTER)
        return this
    }

    fun clickReview(): AskQuestionPage {
        wait.until(ExpectedConditions.elementToBeClickable(reviewButton)).click()
        return this
    }

    fun submitQuestion(): QuestionPage {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click()
        return QuestionPage(driver)
    }

    fun isTitleErrorVisible(): Boolean {
        return driver.findElements(By.xpath(XPATH_TITLE_ERROR)).any { it.isDisplayed }
    }
}
