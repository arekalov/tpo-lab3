package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.pages.LoginPage
import com.arekalov.tpolab3.pages.MainPage
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions

class AuthTest : BaseTest() {

    companion object {
        private const val URL_FRAGMENT_STACKOVERFLOW = "stackoverflow.com"
        private const val URL_FRAGMENT_LOGIN = "login"
        private const val XPATH_LOGIN_ERROR =
            "//*[contains(@class,'js-error-message') or contains(@class,'message-error')]"
    }

    @Test
    fun `successful login with valid credentials`() {
        val mainPage = LoginPage(driver)
            .open()
            .loginAs(Config.email, Config.password)

        wait.until(
            ExpectedConditions.urlContains(URL_FRAGMENT_STACKOVERFLOW)
        )
        assertTrue(mainPage.isLoggedIn(), "User should be logged in after successful authentication")
    }

    @Test
    fun `login fails with wrong password`() {
        val loginPage = LoginPage(driver)
            .open()
            .enterEmail(Config.email)
            .enterPassword("wrong_password_123!")
            .submitExpectingError()
        wait.until(
            ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(XPATH_LOGIN_ERROR)),
                ExpectedConditions.urlContains(URL_FRAGMENT_LOGIN)
            )
        )
        assertTrue(loginPage.hasError(), "Error message should appear on invalid credentials")
    }

    @Test
    fun `login page is accessible from main page`() {
        MainPage(driver).open().clickLogin()
        wait.until(ExpectedConditions.urlContains(URL_FRAGMENT_LOGIN))
        val currentUrl = driver.currentUrl ?: "null"
        assertTrue(currentUrl.contains(URL_FRAGMENT_LOGIN), "Should navigate to login page")
    }
}
