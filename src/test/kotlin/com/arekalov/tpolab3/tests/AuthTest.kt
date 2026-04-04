package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.pages.LoginPage
import com.arekalov.tpolab3.pages.MainPage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

class AuthTest : BaseTest() {

    @Test
    fun `successful login with valid credentials`() {
        val mainPage = LoginPage(driver)
            .open()
            .loginAs(Config.email, Config.password)

        wait.until(
            ExpectedConditions.or(
                ExpectedConditions.urlContains("stackoverflow.com"),
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//a[contains(@href,'/users/logout')]")
                )
            )
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
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(@class,'js-error-message') or contains(@class,'message-error')]")
                ),
                ExpectedConditions.urlContains("login")
            )
        )
        assertTrue(loginPage.hasError(), "Error message should appear on invalid credentials")
    }

    @Test
    fun `login page is accessible from main page`() {
        val loginPage = MainPage(driver).open().clickLogin()
        wait.until(ExpectedConditions.urlContains("login"))
        assertTrue(driver.currentUrl.contains("login"), "Should navigate to login page")
    }
}
