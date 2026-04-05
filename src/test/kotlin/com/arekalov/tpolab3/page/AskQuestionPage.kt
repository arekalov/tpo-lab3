package com.arekalov.tpolab3.page

import org.openqa.selenium.By
import org.openqa.selenium.Keys.BACK_SPACE
import org.openqa.selenium.WebDriver

internal class AskQuestionPage(driver: WebDriver) : BasePage(driver) {

    companion object {
        const val URL_FRAGMENT = "/questions/ask"

        private const val XPATH_TITLE_INPUT = "//input[@id='post-title-input']"
        private const val XPATH_BODY_EDITOR = "//div[@role='textbox']"
        private const val XPATH_TAGS_INPUT = "//input[@id='filterInput']"
        private const val XPATH_NEXT_BUTTON = "//button[normalize-space()='Submit to Staging Ground']"
        private const val XPATH_TITLE_ERROR = "//*[contains(@class,'d-error') and ancestor::*[@id='question-form']]"
        private const val XPATH_TAGS_SPAN = "//*[@id='text-input-container']/div[1]/div/span/span/*"
    }

    fun isTitleInputVisible(): Boolean = isDisplayed(By.xpath(XPATH_TITLE_INPUT))

    fun isBodyEditorVisible(): Boolean = isDisplayed(By.xpath(XPATH_BODY_EDITOR))

    fun isTagsInputVisible(): Boolean = isDisplayed(By.xpath(XPATH_TAGS_INPUT))

    fun isNextButtonClickable(): Boolean = isEnabled(By.xpath(XPATH_NEXT_BUTTON))

    fun fillTitle(title: String): AskQuestionPage {
        val input = waitClickable(By.xpath(XPATH_TITLE_INPUT))
        input.clear()
        input.sendKeys(title)
        return this
    }

    fun fillBody(text: String): AskQuestionPage {
        val editor = waitClickable(By.xpath(XPATH_BODY_EDITOR))
        editor.click()
        editor.sendKeys(text)
        return this
    }

    fun fillTag(tag: String): AskQuestionPage {
        val input = waitClickable(By.xpath(XPATH_TAGS_INPUT))
        input.click()
        while (input.findElements(By.xpath(XPATH_TAGS_SPAN)).isNotEmpty()) {
            input.sendKeys(BACK_SPACE)
        }
        input.sendKeys(tag)
        clickInBody()
        return this
    }

    fun clickNext(): AskQuestionPage {
        waitClickable(By.xpath(XPATH_NEXT_BUTTON)).click()
        return this
    }

    fun hasTitleError(): Boolean = isDisplayed(By.xpath(XPATH_TITLE_ERROR))

    private fun clickInBody(): AskQuestionPage {
        driver.findElement(By.tagName("body")).click()
        return this
    }
}
