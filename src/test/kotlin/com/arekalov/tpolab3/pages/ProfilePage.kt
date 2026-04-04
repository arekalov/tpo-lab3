package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class ProfilePage(private val driver: WebDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    private val displayName = By.xpath("//div[contains(@class,'grid--cell fl1')]//div[@class='fs-headline2 fw-bold'] | //h1[contains(@class,'fs-headline')]")
    private val reputationBadge = By.xpath("//div[contains(@class,'js-user-rep') or @itemprop='description']")
    private val questionsTab = By.xpath("//a[contains(@href,'?tab=questions')]")
    private val answersTab = By.xpath("//a[contains(@href,'?tab=answers')]")
    private val profileNavLink = By.xpath("//a[contains(@href,'/users/') and contains(@class,'avatar')]")

    fun openCurrentUser(): ProfilePage {
        val link = wait.until(ExpectedConditions.elementToBeClickable(profileNavLink))
        link.click()
        return this
    }

    fun open(userId: String): ProfilePage {
        driver.get("https://stackoverflow.com/users/$userId")
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
