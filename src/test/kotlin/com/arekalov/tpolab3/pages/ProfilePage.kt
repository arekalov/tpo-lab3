package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class ProfilePage(private val driver: WebDriver) {

    companion object {
        private const val URL_USERS_TEMPLATE = "https://stackoverflow.com/users/"
        private const val XPATH_DISPLAY_NAME =
            "//div[contains(@class,'grid--cell fl1')]//div[@class='fs-headline2 fw-bold'] | //h1[contains(@class,'fs-headline')]"
        private const val XPATH_REPUTATION =
            "//div[contains(@class,'js-user-rep') or @itemprop='description']"
        private const val XPATH_QUESTIONS_TAB = "//a[contains(@href,'?tab=questions')]"
        private const val XPATH_ANSWERS_TAB = "//a[contains(@href,'?tab=answers')]"
        private const val XPATH_PROFILE_NAV =
            "//a[contains(@href,'/users/') and contains(@class,'avatar')]"
    }

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    private val displayName = By.xpath(XPATH_DISPLAY_NAME)
    private val reputationBadge = By.xpath(XPATH_REPUTATION)
    private val questionsTab = By.xpath(XPATH_QUESTIONS_TAB)
    private val answersTab = By.xpath(XPATH_ANSWERS_TAB)
    private val profileNavLink = By.xpath(XPATH_PROFILE_NAV)

    fun openCurrentUser(): ProfilePage {
        val link = wait.until(ExpectedConditions.elementToBeClickable(profileNavLink))
        link.click()
        return this
    }

    fun open(userId: String): ProfilePage {
        driver.get("$URL_USERS_TEMPLATE$userId")
        return this
    }

    fun getDisplayName(): String {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(displayName)).text.trim()
    }

    fun getReputation(): String {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(reputationBadge)).text.trim()
    }

    fun clickQuestionsTab(): ProfilePage {
        wait.until(ExpectedConditions.elementToBeClickable(questionsTab)).click()
        return this
    }

    fun clickAnswersTab(): ProfilePage {
        wait.until(ExpectedConditions.elementToBeClickable(answersTab)).click()
        return this
    }

    fun isProfileDisplayed(): Boolean {
        return driver.findElements(displayName).isNotEmpty()
    }
}
