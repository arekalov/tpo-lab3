package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.pages.ProfilePage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

class ProfileTest : BaseTest() {

    companion object {
        // Jon Skeet's public profile — stable, always available
        const val PUBLIC_PROFILE_URL = "https://stackoverflow.com/users/22656/jon-skeet"
        const val PUBLIC_USER_ID = "22656/jon-skeet"
    }

    @Test
    fun `public profile page is accessible without login`() {
        driver.get(PUBLIC_PROFILE_URL)
        wait.until(ExpectedConditions.urlContains("/users/"))
        assertTrue(driver.currentUrl.contains("/users/"), "Should navigate to user profile page")
    }

    @Test
    fun `profile displays user display name`() {
        val profilePage = ProfilePage(driver).open(PUBLIC_USER_ID)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h1[contains(@class,'fs-headline') or contains(@class,'grid--cell')]")
            )
        )
        assertTrue(profilePage.isProfileDisplayed(), "Profile page should display the user's name")
    }

    @Test
    fun `profile page shows questions tab`() {
        driver.get(PUBLIC_PROFILE_URL)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(@href,'?tab=questions')]")
            )
        )
        val tabs = driver.findElements(By.xpath("//a[contains(@href,'?tab=questions')]"))
        assertTrue(tabs.isNotEmpty(), "Questions tab should be visible on profile page")
    }

    @Test
    fun `profile page shows answers tab`() {
        driver.get(PUBLIC_PROFILE_URL)
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(@href,'?tab=answers')]")
            )
        )
        val tabs = driver.findElements(By.xpath("//a[contains(@href,'?tab=answers')]"))
        assertTrue(tabs.isNotEmpty(), "Answers tab should be visible on profile page")
    }
}
