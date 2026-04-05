package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.pages.LoginPage
import com.arekalov.tpolab3.pages.MainPage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import net.datafaker.Faker
import org.openqa.selenium.support.ui.ExpectedConditions

class AskQuestionTest : BaseTest() {

    companion object {
        private const val URL_FRAGMENT_ASK = "/questions/ask"
        private const val XPATH_LOGOUT = "//a[contains(@href,'/users/logout')]"
        private const val XPATH_TITLE_INPUT = "//input[@id='title' or @name='title']"
        private val faker = Faker()
    }

    @BeforeEach
    fun login() {
        val mainMage = LoginPage(driver).open().loginAs(Config.email, Config.password)
        assertTrue(mainMage.isLoggedIn())
    }

    @Test
    fun `ask question page opens after clicking ask button`() {
        MainPage(driver).open().navigateToAskQuestion()
        wait.until(ExpectedConditions.urlContains(URL_FRAGMENT_ASK))
        val currentUrl = driver.currentUrl ?: "null"
        assertTrue(currentUrl.contains(URL_FRAGMENT_ASK), "Should navigate to ask question page")
    }

    @Test
    fun `title field shows error when empty on review`() {
        val askPage = MainPage(driver).open().navigateToAskQuestion()
        wait.until(ExpectedConditions.urlContains(URL_FRAGMENT_ASK))

        askPage.submitQuestion()
        askPage.isTitleErrorVisible()
    }

    @Test
    fun `question form accepts title input`() {
        val askPage = MainPage(driver).open().navigateToAskQuestion()
        wait.until(ExpectedConditions.urlContains(URL_FRAGMENT_ASK))

        val titleText = faker.lorem().sentence(4)
        askPage.enterTitle(titleText)

        val bodyText = faker.lorem().sentence(40)
        askPage.enterBody(bodyText)

        askPage.enterTag("kotlin")

        askPage.submitQuestion()
    }
}
