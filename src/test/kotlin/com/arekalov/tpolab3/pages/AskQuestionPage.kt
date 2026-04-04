package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class AskQuestionPage(private val driver: WebDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    private val titleField = By.xpath("//input[@id='title' or @name='title']")
    private val bodyEditor = By.xpath("//div[@class='wmd-input' or @id='wmd-input']")
    private val tagsField = By.xpath("//input[contains(@class,'s-input') and @name='tagnames']")
    private val reviewButton = By.xpath("//button[contains(.,'Review your question')]")
    private val submitButton = By.xpath("//button[contains(.,'Post your question')]")
    private val draftIndicator = By.xpath("//*[contains(@class,'js-draft-saved') or contains(text(),'Draft saved')]")

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
        return driver.findElements(
            By.xpath("//*[contains(@class,'js-title-error') or contains(@class,'d-error')]")
        ).any { it.isDisplayed }
    }
}
