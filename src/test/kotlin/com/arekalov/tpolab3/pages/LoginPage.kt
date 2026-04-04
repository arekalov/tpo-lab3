package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class LoginPage(private val driver: WebDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    private val emailField = By.xpath("//input[@id='email']")
    private val passwordField = By.xpath("//input[@type='password' and @name='password' and @autocomplete='current-password']")
    private val submitButton = By.xpath("//button[@id='submit-button']")
    private val errorMessage = By.xpath("//*[@id=\"login-form\"]/div[1]/p")

    fun open(): LoginPage {
        driver.get("https://stackoverflow.com/users/login")
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
