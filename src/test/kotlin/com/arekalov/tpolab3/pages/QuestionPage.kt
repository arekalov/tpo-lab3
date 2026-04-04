package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class QuestionPage(private val driver: WebDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    private val questionTitle = By.xpath("//h1[@itemprop='name']/a | //h1[contains(@class,'fs-headline1')]")
    private val answerEditor = By.xpath("//div[@id='wmd-input' or contains(@class,'wmd-input')]")
    private val postAnswerButton = By.xpath("//button[contains(.,'Post Your Answer')]")
    private val upvoteButton = By.xpath("(//button[contains(@class,'js-vote-up-btn')])[1]")
    private val downvoteButton = By.xpath("(//button[contains(@class,'js-vote-down-btn')])[1]")
    private val voteCount = By.xpath("(//div[contains(@class,'js-vote-count')])[1]")
    private val addCommentLink = By.xpath("(//a[contains(@class,'js-add-link') and contains(.,'comment')])[1]")
    private val commentTextarea = By.xpath("//textarea[contains(@class,'js-comment-text-input')]")
    private val submitCommentButton = By.xpath("//button[contains(@class,'js-comment-submit-button') or contains(.,'Add comment')]")
    private val acceptAnswerButton = By.xpath("(//a[contains(@class,'js-mark-as-accepted-answer')])[1]")

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
        return driver.findElements(
            By.xpath("//*[contains(@class,'comment-body') and contains(.,'$partialText')]")
        ).isNotEmpty()
    }

    fun acceptFirstAnswer(): QuestionPage {
        wait.until(ExpectedConditions.elementToBeClickable(acceptAnswerButton)).click()
        return this
    }

    fun isAnswerPosted(partialText: String): Boolean {
        return driver.findElements(
            By.xpath("//div[contains(@class,'answercell')]//div[contains(.,'$partialText')]")
        ).isNotEmpty()
    }
}
