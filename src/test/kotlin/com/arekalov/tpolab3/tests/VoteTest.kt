package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.pages.LoginPage
import com.arekalov.tpolab3.pages.QuestionPage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

class VoteTest : BaseTest() {

    companion object {
        const val TEST_QUESTION_URL = "https://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-processing-an-unsorted-array"
    }

    @BeforeEach
    fun login() {
        LoginPage(driver).open().loginAs(Config.email, Config.password)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(@href,'/users/logout')]")
            )
        )
    }

    @Test
    fun `vote buttons are present on question page`() {
        driver.get(TEST_QUESTION_URL)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//button[contains(@class,'js-vote-up-btn')])[1]")
            )
        )
        val upvoteButtons = driver.findElements(
            By.xpath("//button[contains(@class,'js-vote-up-btn')]")
        )
        assertTrue(upvoteButtons.isNotEmpty(), "Upvote button should be present on question page")
    }

    @Test
    fun `vote count is displayed on question page`() {
        driver.get(TEST_QUESTION_URL)
        val questionPage = QuestionPage(driver)
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//div[contains(@class,'js-vote-count')])[1]")
            )
        )
        val voteCountText = driver.findElement(
            By.xpath("(//div[contains(@class,'js-vote-count')])[1]")
        ).text.trim()
        assertNotNull(voteCountText, "Vote count should be displayed")
        assertTrue(voteCountText.isNotBlank(), "Vote count should not be blank")
    }

    @Test
    fun `downvote button is present on question page`() {
        driver.get(TEST_QUESTION_URL)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//button[contains(@class,'js-vote-down-btn')])[1]")
            )
        )
        val downvoteButtons = driver.findElements(
            By.xpath("//button[contains(@class,'js-vote-down-btn')]")
        )
        assertTrue(downvoteButtons.isNotEmpty(), "Downvote button should be present on question page")
    }
}
