package com.arekalov.tpolab3.page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

internal class LoginPage(driver: WebDriver) : BasePage(driver) {

    companion object {
        const val URL_FRAGMENT = "/users/login"

        private const val XPATH_EMAIL_INPUT = "//input[@id='email']"
        private const val XPATH_PASSWORD_INPUT = "//input[@id='password']"
        private const val XPATH_SUBMIT_BUTTON = "//button[@id='submit-button']"
        private const val XPATH_ERROR_MESSAGE = "//div[@id='js-error-message'] | //p[contains(@class,'error')]"
        private const val XPATH_USER_TOPBAR = "//a[contains(@class,'js-site-switcher-button') and @aria-label='Site switcher']"
        private const val XPATH_LOGOUT_BUTTON = "//a[@class='js-gps-track'][normalize-space()='log out']"
    }

    fun isEmailInputVisible(): Boolean = isDisplayed(By.xpath(XPATH_EMAIL_INPUT))

    fun isPasswordInputVisible(): Boolean = isDisplayed(By.xpath(XPATH_PASSWORD_INPUT))

    fun fillEmail(email: String): LoginPage {
        val input = waitClickable(By.xpath(XPATH_EMAIL_INPUT))
        input.clear()
        input.sendKeys(email)
        return this
    }

    fun fillPassword(password: String): LoginPage {
        val input = waitClickable(By.xpath(XPATH_PASSWORD_INPUT))
        input.clear()
        input.sendKeys(password)
        return this
    }

    fun submitLogin(): MainPage {
        waitClickable(By.xpath(XPATH_SUBMIT_BUTTON)).click()
        return MainPage(driver)
    }

    fun hasErrorMessage(): Boolean = isDisplayed(By.xpath(XPATH_ERROR_MESSAGE))

    fun isLoggedIn(): Boolean {
        waitClickable(By.xpath(XPATH_USER_TOPBAR)).click()
        return waitVisibleElements(By.xpath(XPATH_LOGOUT_BUTTON)).isNotEmpty()
    }
}
