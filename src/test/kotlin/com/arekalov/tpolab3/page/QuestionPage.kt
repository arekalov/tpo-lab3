package com.arekalov.tpolab3.page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

internal class QuestionPage(driver: WebDriver) : BasePage(driver) {

    companion object {
        const val URL_FRAGMENT = "/questions/"

        private const val XPATH_TITLE = "//h1[@itemprop='name']//a | //h1[contains(@class,'fs-headline')]"
        private const val XPATH_QUESTION_BODY = "//div[@itemprop='text'] | //div[contains(@class,'s-prose js-post-body')]"
        private const val XPATH_TAGS = "//div[@class='d-flex ps-relative fw-wrap']//li"
        private const val XPATH_ANSWERS = "//div[@id='answers']//div[contains(@class,'answer')]"
        private const val XPATH_ACCEPTED_ANSWER = "//div[contains(@class,'answer') and contains(@class,'accepted-answer')]"
        private const val XPATH_SGNUP_ERROR_TITLE = "//div[@class='message-text']"

        private const val XPATH_ANSWER_EDITOR = "//div[@id='js-stacks-editor-container']"
        private const val XPATH_ANSWER_MULTILINE = "//div[@role='textbox']"
        private const val XPATH_POST_ANSWER_BUTTON = "//button[@id='submit-button']"
    }

    fun getTitle(): String =
        waitVisible(By.xpath(XPATH_TITLE)).text.trim()

    fun isQuestionBodyVisible(): Boolean =
        isDisplayed(By.xpath(XPATH_QUESTION_BODY))

    fun getTags(): List<String> =
        waitVisibleElements(By.xpath(XPATH_TAGS)).map { it.text.trim() }

    fun hasAnswers(): Boolean =
        waitVisibleElements(By.xpath(XPATH_ANSWERS)).isNotEmpty()

    fun hasAcceptedAnswer(): Boolean =
        isDisplayed(By.xpath(XPATH_ACCEPTED_ANSWER))

    fun isAnswerEditorVisible(): Boolean =
        isDisplayed(By.xpath(XPATH_ANSWER_EDITOR))

    fun isErrorVisible(): Boolean =
        isDisplayed(By.xpath(XPATH_SGNUP_ERROR_TITLE))

    fun fillAnswer(text: String): QuestionPage {
        val editor = waitClickable(By.xpath(XPATH_ANSWER_EDITOR))
        scrollTo(editor)
        editor.click()
        val box = waitVisible(By.xpath(XPATH_ANSWER_MULTILINE))
        box.click()
        box.sendKeys(text)
        return this
    }

    fun clickPostAnswer(): QuestionPage {
        val button = waitClickable(By.xpath(XPATH_POST_ANSWER_BUTTON))
        scrollTo(button)
        button.click()
        return this
    }
}
