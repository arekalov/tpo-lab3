package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.pages.LoginPage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

class CommentTest : BaseTest() {

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
    fun `add comment link is visible on question page`() {
        driver.get(TEST_QUESTION_URL)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//a[contains(@class,'js-add-link') and contains(.,'comment')])[1]")
            )
        )
        val links = driver.findElements(
            By.xpath("//a[contains(@class,'js-add-link') and contains(.,'comment')]")
        )
        assertTrue(links.isNotEmpty(), "Add comment link should be visible on question page")
    }

    @Test
    fun `clicking add comment reveals textarea`() {
        driver.get(TEST_QUESTION_URL)
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[contains(@class,'js-add-link') and contains(.,'comment')])[1]")
            )
        ).click()

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//textarea[contains(@class,'js-comment-text-input')]")
            )
        )
        val textarea = driver.findElements(
            By.xpath("//textarea[contains(@class,'js-comment-text-input')]")
        )
        assertTrue(textarea.isNotEmpty(), "Comment textarea should be visible after clicking add comment")
    }

    @Test
    fun `comment textarea accepts text input`() {
        driver.get(TEST_QUESTION_URL)
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[contains(@class,'js-add-link') and contains(.,'comment')])[1]")
            )
        ).click()

        val textarea = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//textarea[contains(@class,'js-comment-text-input')]")
            )
        )
        val commentText = "This is a selenium test comment"
        textarea.sendKeys(commentText)

        assertEquals(commentText, textarea.getAttribute("value"), "Textarea should contain the typed text")
    }
}
