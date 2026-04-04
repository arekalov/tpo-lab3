package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.pages.LoginPage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

class VoteTest : BaseTest() {

    companion object {
        const val TEST_QUESTION_URL =
            "https://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-processing-an-unsorted-array"
        private const val XPATH_LOGOUT = "//a[contains(@href,'/users/logout')]"
        private const val XPATH_UPVOTE_FIRST = "(//button[contains(@class,'js-vote-up-btn')])[1]"
        private const val XPATH_UPVOTE_ANY = "//button[contains(@class,'js-vote-up-btn')]"
        private const val XPATH_VOTE_COUNT_FIRST = "(//div[contains(@class,'js-vote-count')])[1]"
        private const val XPATH_DOWNVOTE_FIRST = "(//button[contains(@class,'js-vote-down-btn')])[1]"
        private const val XPATH_DOWNVOTE_ANY = "//button[contains(@class,'js-vote-down-btn')]"
    }

    @BeforeEach
    fun login() {
        LoginPage(driver).open().loginAs(Config.email, Config.password)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_LOGOUT))
        )
    }

    @Test
    fun `vote buttons are present on question page`() {
        driver.get(TEST_QUESTION_URL)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_UPVOTE_FIRST))
        )
        val upvoteButtons = driver.findElements(By.xpath(XPATH_UPVOTE_ANY))
        assertTrue(upvoteButtons.isNotEmpty(), "Upvote button should be present on question page")
    }

    @Test
    fun `vote count is displayed on question page`() {
        driver.get(TEST_QUESTION_URL)
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_VOTE_COUNT_FIRST))
        )
        val voteCountText = driver.findElement(By.xpath(XPATH_VOTE_COUNT_FIRST)).text.trim()
        assertNotNull(voteCountText, "Vote count should be displayed")
        assertTrue(voteCountText.isNotBlank(), "Vote count should not be blank")
    }

    @Test
    fun `downvote button is present on question page`() {
        driver.get(TEST_QUESTION_URL)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_DOWNVOTE_FIRST))
        )
        val downvoteButtons = driver.findElements(By.xpath(XPATH_DOWNVOTE_ANY))
        assertTrue(downvoteButtons.isNotEmpty(), "Downvote button should be present on question page")
    }
}
