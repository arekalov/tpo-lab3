package com.arekalov.tpolab3.page

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

internal open class BasePage(protected val driver: WebDriver) {

    companion object {
        private const val XPATH_COOKIES_BUTTON = "//*[@id='onetrust-accept-btn-handler']"
        private const val WAIT_SEC = 30L
        private const val COOKIES_WAIT_SEC = 5L
    }

    protected val wait = WebDriverWait(driver, Duration.ofSeconds(WAIT_SEC))

    protected fun waitVisible(by: By): WebElement =
        wait.until(ExpectedConditions.visibilityOfElementLocated(by))

    protected fun waitVisibleElements(by: By): List<WebElement> =
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by))

    protected fun waitClickable(by: By): WebElement =
        wait.until(ExpectedConditions.elementToBeClickable(by))

    protected fun isDisplayed(by: By): Boolean =
        waitVisible(by).isDisplayed

    protected fun isEnabled(by: By): Boolean =
        waitClickable(by).isEnabled


    protected fun waitForUrl(fragment: String) {
        wait.until(ExpectedConditions.urlContains(fragment))
    }

    protected fun scrollTo(element: WebElement) {
        (driver as JavascriptExecutor).executeScript("arguments[0].scrollIntoView(true);", element)
    }

    fun acceptCookies() {
        try {
            val cookiesWait = WebDriverWait(driver, Duration.ofSeconds(COOKIES_WAIT_SEC))
            cookiesWait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(XPATH_COOKIES_BUTTON))
            ).click()
        } catch (_: Exception) {
        }
    }

    fun closeGoogle() {
        try {
            (driver as JavascriptExecutor).executeScript(
                """
            document.getElementById('credential_picker_container')?.remove();
        """.trimIndent()
            )
        } catch (_: Exception) {
        }
    }
}
