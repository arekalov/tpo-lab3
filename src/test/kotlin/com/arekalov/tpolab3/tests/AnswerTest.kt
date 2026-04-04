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

class AnswerTest : BaseTest() {

    companion object {
        const val TEST_QUESTION_URL =
            "https://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-processing-an-unsorted-array"
        private const val XPATH_LOGOUT = "//a[contains(@href,'/users/logout')]"
        private const val XPATH_ANSWER_EDITOR =
            "//div[@id='wmd-input' or contains(@class,'wmd-input')]"
        private const val XPATH_POST_ANSWER = "//button[contains(.,'Post Your Answer')]"
    }

    @BeforeEach
    fun login() {
        LoginPage(driver).open().loginAs(Config.email, Config.password)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_LOGOUT))
        )
    }

    @Test
    fun `answer editor is visible on question page`() {
        driver.get(TEST_QUESTION_URL)

        wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_ANSWER_EDITOR))
        )
        val editors = driver.findElements(By.xpath(XPATH_ANSWER_EDITOR))
        assertTrue(editors.isNotEmpty(), "Answer editor should be visible on the question page")
    }

    @Test
    fun `question title is displayed on question page`() {
        driver.get(TEST_QUESTION_URL)
        val questionPage = QuestionPage(driver)
        val title = questionPage.getTitle()
        assertTrue(title.isNotBlank(), "Question title should be displayed")
    }

    @Test
    fun `post answer button is present on question page`() {
        driver.get(TEST_QUESTION_URL)

        wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_POST_ANSWER))
        )
        val buttons = driver.findElements(By.xpath(XPATH_POST_ANSWER))
        assertTrue(buttons.isNotEmpty(), "Post Your Answer button should be present")
    }
}
