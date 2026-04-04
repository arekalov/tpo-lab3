package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.pages.AskQuestionPage
import com.arekalov.tpolab3.pages.LoginPage
import com.arekalov.tpolab3.pages.MainPage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

class AskQuestionTest : BaseTest() {

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
    fun `ask question page opens after clicking ask button`() {
        MainPage(driver).open().navigateToAskQuestion()
        wait.until(ExpectedConditions.urlContains("/questions/ask"))
        assertTrue(driver.currentUrl.contains("/questions/ask"), "Should navigate to ask question page")
    }

    @Test
    fun `title field shows error when empty on review`() {
        val askPage = MainPage(driver).open().navigateToAskQuestion()
        wait.until(ExpectedConditions.urlContains("/questions/ask"))

        askPage.clickReview()

        assertTrue(askPage.isTitleErrorVisible(), "Title error should appear when title is empty")
    }

    @Test
    fun `question form accepts title input`() {
        val askPage = MainPage(driver).open().navigateToAskQuestion()
        wait.until(ExpectedConditions.urlContains("/questions/ask"))

        askPage.enterTitle("How to test Selenium with Kotlin?")

        val titleValue = driver.findElement(
            By.xpath("//input[@id='title' or @name='title']")
        ).getAttribute("value")
        assertTrue(
            titleValue?.contains("Selenium") == true,
            "Title field should contain the entered text"
        )
    }
}
