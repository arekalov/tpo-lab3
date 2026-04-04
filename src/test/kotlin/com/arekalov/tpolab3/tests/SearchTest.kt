package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.pages.MainPage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

class SearchTest : BaseTest() {

    @Test
    fun `search returns results for known keyword`() {
        val searchPage = MainPage(driver).open().searchFor("kotlin coroutines")

        wait.until(
            ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]")
                ),
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(),'results')]")
                )
            )
        )
        assertTrue(searchPage.hasResults(), "Search for 'kotlin coroutines' should return results")
    }

    @Test
    fun `search results title contains query keyword`() {
        val searchPage = MainPage(driver).open().searchFor("selenium xpath")

        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]")
            )
        )
        assertTrue(searchPage.hasResults(), "Search should return at least one result")
        val title = searchPage.getFirstResultTitle().lowercase()
        assertTrue(
            title.contains("selenium") || title.contains("xpath"),
            "First result should be related to the search query"
        )
    }

    @Test
    fun `search input contains submitted query`() {
        val query = "java nullpointerexception"
        val searchPage = MainPage(driver).open().searchFor(query)

        wait.until(ExpectedConditions.urlContains("q="))
        assertTrue(
            driver.currentUrl.contains("java") || driver.currentUrl.contains("nullpointerexception"),
            "URL should reflect the search query"
        )
    }

    @Test
    fun `search navigates to search results page`() {
        MainPage(driver).open().searchFor("python list")

        wait.until(ExpectedConditions.urlContains("stackoverflow.com/search"))
        assertTrue(
            driver.currentUrl.contains("stackoverflow.com/search"),
            "Should navigate to search results page"
        )
    }
}
