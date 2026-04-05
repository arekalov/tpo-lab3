package com.arekalov.tpolab3.test

import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.page.LoginPage
import com.arekalov.tpolab3.page.MainPage
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * UC-04: Регистрация/вход в аккаунт.
 * Актор: Гость.
 */
class LoginPageTest : BaseTest() {

    private lateinit var loginPage: LoginPage

    @BeforeEach
    fun openLoginPage() {
        loginPage = MainPage(driver).open()
            .also {
                it.acceptCookies()
                it.closeGoogle()
            }
            .clickLogin()
    }

    @Test
    fun `login page has email and password fields`() {
        waitForUrl(LoginPage.URL_FRAGMENT)
        assertTrue(loginPage.isEmailInputVisible(), "Поле email должно быть видимо")
        assertTrue(loginPage.isPasswordInputVisible(), "Поле password должно быть видимо")
    }

    @Test
    fun `valid credentials log user in and redirect to main page`() {
        loginPage
            .fillEmail(Config.email)
            .fillPassword(Config.password)
            .submitLogin()
        waitForUrl(MainPage.URL)
        assertTrue(loginPage.isLoggedIn(), "После входа должен отображаться аватар пользователя")
    }

    @Test
    fun `invalid credentials show error message`() {
        loginPage
            .fillEmail(Config.email)
            .fillPassword("wrongpassword")
            .submitLogin()
        assertTrue(loginPage.hasErrorMessage(), "При неверных данных должно появиться сообщение об ошибке")
    }
}
