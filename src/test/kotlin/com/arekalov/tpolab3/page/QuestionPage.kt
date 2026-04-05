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

        private const val XPATH_UPVOTE_BUTTON = "//button[contains(@class, 'js-vote-up-btn')][1]"
        private const val XPATH_DOWNVOTE_BUTTON = "//button[contains(@class,'js-vote-down-btn')]"
        private const val XPATH_VOTE_SIGNUP_TOOLTIP = "//button[@id='signup-modal-submit-button']"
        private const val XPATH_VOTE_ERROR_TOOLTIP = "//p[@id='notice-toast-message']"
        private const val XPATH_VOTE_COUNT = "//div[contains(@class,'js-vote-count')]"
        private const val XPATH_FIRST_TAG = "(//div[@class='d-flex ps-relative fw-wrap']//li//a)[1]"

        private const val XPATH_SAVE_BUTTON = "//button[contains(@class,'js-saves-btn')]"
        private const val XPATH_SAVE_BUTTON_ACTIVE = "//button[contains(@class,'js-saves-btn') and @aria-pressed='true']"
        private const val XPATH_SAVE_TOOLTIP = "//div[contains(@class,'s-popover') and contains(.,'Log in')]"
        private const val XPATH_DELETE_LINK = "//a[normalize-space()='Delete']"
        private const val XPATH_DELETE_CONFIRM_DIALOG = "//div[contains(@class,'popup') or contains(@class,'modal')]"

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

    fun isUpvoteButtonVisible(): Boolean = isDisplayed(By.xpath(XPATH_UPVOTE_BUTTON))

    fun getVoteCount(): String = waitVisible(By.xpath(XPATH_VOTE_COUNT)).text.trim()

    fun clickUpvote(): QuestionPage {
        jsClick(waitClickable(By.xpath(XPATH_UPVOTE_BUTTON)))
        return this
    }

    fun clickDownvote(): QuestionPage {
        waitClickable(By.xpath(XPATH_DOWNVOTE_BUTTON)).click()
        return this
    }

    fun isVoteTooltipVisible(): Boolean = isDisplayed(By.xpath(XPATH_VOTE_ERROR_TOOLTIP))

    fun clickFirstTag(): TagPage {
        val tag = waitClickable(By.xpath(XPATH_FIRST_TAG))
        scrollTo(tag)
        tag.click()
        return TagPage(driver)
    }

    fun isSaveButtonVisible(): Boolean = isDisplayed(By.xpath(XPATH_SAVE_BUTTON))

    fun isSaved(): Boolean = driver.findElements(By.xpath(XPATH_SAVE_BUTTON_ACTIVE)).isNotEmpty()

    fun clickSave(): QuestionPage {
        val button = waitClickable(By.xpath(XPATH_SAVE_BUTTON))
        val isPressed = button.getAttribute("aria-label")
        if (isPressed == "Save") {
            button.click()
        }
        return this
    }

    fun clickUnsave(): QuestionPage {
        val button = waitClickable(By.xpath(XPATH_SAVE_BUTTON))
        val isPressed = button.getAttribute("aria-label")
        if (isPressed != "Save") {
            button.click()
        }
        return this
    }

    fun isSaveLoginTooltipVisible(): Boolean = isDisplayed(By.xpath(XPATH_VOTE_SIGNUP_TOOLTIP))

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
