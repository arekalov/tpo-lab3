package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class LoginPage(private val driver: WebDriver) {

    companion object {
        private const val URL_LOGIN = "https://stackoverflow.com/users/login"
        private const val XPATH_EMAIL = "//input[@id='email']"
        private const val XPATH_PASSWORD =
            "//input[@type='password' and @name='password' and @autocomplete='current-password']"
        private const val XPATH_SUBMIT = "//button[@id='submit-button']"
        private const val XPATH_ERROR = "//*[@id=\"login-form\"]/div[1]/p"
    }

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    private val emailField = By.xpath(XPATH_EMAIL)
    private val passwordField = By.xpath(XPATH_PASSWORD)
    private val submitButton = By.xpath(XPATH_SUBMIT)
    private val errorMessage = By.xpath(XPATH_ERROR)

    fun open(): LoginPage {
        driver.get(URL_LOGIN)
        return this
    }

    fun enterEmail(email: String): LoginPage {
        val field = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField))
        field.clear()
        field.sendKeys(email)
        return this
    }

    fun enterPassword(password: String): LoginPage {
        val field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField))
        field.clear()
        field.sendKeys(password)
        return this
    }

    fun submit(): MainPage {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click()
        return MainPage(driver)
    }

    fun submitExpectingError(): LoginPage {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click()
        return this
    }

    fun hasError(): Boolean {
        return driver.findElements(errorMessage).isNotEmpty()
    }

    fun loginAs(email: String, password: String): MainPage {
        return enterEmail(email).enterPassword(password).submit()
    }
}
