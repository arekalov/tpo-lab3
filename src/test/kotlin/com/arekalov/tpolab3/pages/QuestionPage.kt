package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

internal class QuestionPage(private val driver: WebDriver) : BasePage(driver) {

    companion object {
        private const val XPATH_QUESTION_TITLE =
            "//h1[@itemprop='name']/a | //h1[contains(@class,'fs-headline1')]"
        private const val XPATH_ANSWER_EDITOR =
            "//div[@id='wmd-input' or contains(@class,'wmd-input')]"
        private const val XPATH_POST_ANSWER = "//button[contains(.,'Post Your Answer')]"
        private const val XPATH_UPVOTE_FIRST = "(//button[contains(@class,'js-vote-up-btn')])[1]"
        private const val XPATH_DOWNVOTE_FIRST = "(//button[contains(@class,'js-vote-down-btn')])[1]"
        private const val XPATH_VOTE_COUNT_FIRST = "(//div[contains(@class,'js-vote-count')])[1]"
        private const val XPATH_ADD_COMMENT_FIRST =
            "(//a[contains(@class,'js-add-link') and contains(.,'comment')])[1]"
        private const val XPATH_COMMENT_TEXTAREA =
            "//textarea[contains(@class,'js-comment-text-input')]"
        private const val XPATH_SUBMIT_COMMENT =
            "//button[contains(@class,'js-comment-submit-button') or contains(.,'Add comment')]"
        private const val XPATH_ACCEPT_ANSWER_FIRST =
            "(//a[contains(@class,'js-mark-as-accepted-answer')])[1]"

        private fun xpathCommentBodyContaining(partialText: String) =
            "//*[contains(@class,'comment-body') and contains(.,'$partialText')]"

        private fun xpathAnswerCellContaining(partialText: String) =
            "//div[contains(@class,'answercell')]//div[contains(.,'$partialText')]"
    }

    private val questionTitle = By.xpath(XPATH_QUESTION_TITLE)
    private val answerEditor = By.xpath(XPATH_ANSWER_EDITOR)
    private val postAnswerButton = By.xpath(XPATH_POST_ANSWER)
    private val upvoteButton = By.xpath(XPATH_UPVOTE_FIRST)
    private val downvoteButton = By.xpath(XPATH_DOWNVOTE_FIRST)
    private val voteCount = By.xpath(XPATH_VOTE_COUNT_FIRST)
    private val addCommentLink = By.xpath(XPATH_ADD_COMMENT_FIRST)
    private val commentTextarea = By.xpath(XPATH_COMMENT_TEXTAREA)
    private val submitCommentButton = By.xpath(XPATH_SUBMIT_COMMENT)
    private val acceptAnswerButton = By.xpath(XPATH_ACCEPT_ANSWER_FIRST)

    fun getTitle(): String {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(questionTitle)).text
    }

    fun enterAnswer(text: String): QuestionPage {
        val editor = wait.until(ExpectedConditions.elementToBeClickable(answerEditor))
        editor.click()
        editor.sendKeys(text)
        return this
    }

    fun postAnswer(): QuestionPage {
        wait.until(ExpectedConditions.elementToBeClickable(postAnswerButton)).click()
        return this
    }

    fun upvote(): QuestionPage {
        wait.until(ExpectedConditions.elementToBeClickable(upvoteButton)).click()
        return this
    }

    fun downvote(): QuestionPage {
        wait.until(ExpectedConditions.elementToBeClickable(downvoteButton)).click()
        return this
    }

    fun getVoteCount(): Int {
        val text = wait.until(ExpectedConditions.visibilityOfElementLocated(voteCount)).text.trim()
        return text.toIntOrNull() ?: 0
    }

    fun clickAddComment(): QuestionPage {
        wait.until(ExpectedConditions.elementToBeClickable(addCommentLink)).click()
        return this
    }

    fun enterComment(text: String): QuestionPage {
        val area = wait.until(ExpectedConditions.visibilityOfElementLocated(commentTextarea))
        area.clear()
        area.sendKeys(text)
        return this
    }

    fun submitComment(): QuestionPage {
        wait.until(ExpectedConditions.elementToBeClickable(submitCommentButton)).click()
        return this
    }

    fun isCommentVisible(partialText: String): Boolean {
        return driver.findElements(By.xpath(xpathCommentBodyContaining(partialText))).isNotEmpty()
    }

    fun acceptFirstAnswer(): QuestionPage {
        wait.until(ExpectedConditions.elementToBeClickable(acceptAnswerButton)).click()
        return this
    }

    fun isAnswerPosted(partialText: String): Boolean {
        return driver.findElements(By.xpath(xpathAnswerCellContaining(partialText))).isNotEmpty()
    }
}
