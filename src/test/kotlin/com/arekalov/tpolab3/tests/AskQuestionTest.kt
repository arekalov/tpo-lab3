package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.pages.LoginPage
import com.arekalov.tpolab3.pages.MainPage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

class AskQuestionTest : BaseTest() {

    companion object {
        private const val URL_FRAGMENT_ASK = "/questions/ask"
        private const val XPATH_LOGOUT = "//a[contains(@href,'/users/logout')]"
        private const val XPATH_TITLE_INPUT = "//input[@id='title' or @name='title']"
    }

    @BeforeEach
    fun login() {
        LoginPage(driver).open().loginAs(Config.email, Config.password)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_LOGOUT))
        )
    }

    @Test
    fun `ask question page opens after clicking ask button`() {
        MainPage(driver).open().navigateToAskQuestion()
        wait.until(ExpectedConditions.urlContains(URL_FRAGMENT_ASK))
        assertTrue(driver.currentUrl.contains("/questions/ask"), "Should navigate to ask question page")
    }

    @Test
    fun `title field shows error when empty on review`() {
        val askPage = MainPage(driver).open().navigateToAskQuestion()
        wait.until(ExpectedConditions.urlContains(URL_FRAGMENT_ASK))

        askPage.clickReview()

        assertTrue(askPage.isTitleErrorVisible(), "Title error should appear when title is empty")
    }

    @Test
    fun `question form accepts title input`() {
        val askPage = MainPage(driver).open().navigateToAskQuestion()
        wait.until(ExpectedConditions.urlContains(URL_FRAGMENT_ASK))

        askPage.enterTitle("How to test Selenium with Kotlin?")

        val titleValue = driver.findElement(By.xpath(XPATH_TITLE_INPUT)).getAttribute("value")
        assertTrue(
            titleValue?.contains("Selenium") == true,
            "Title field should contain the entered text"
        )
    }
}
