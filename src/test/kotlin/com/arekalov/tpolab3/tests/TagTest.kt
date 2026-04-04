package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.pages.TagPage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

class TagTest : BaseTest() {

    @Test
    fun `tag page for kotlin opens successfully`() {
        val tagPage = TagPage(driver).open("kotlin")
        wait.until(ExpectedConditions.urlContains("/tagged/kotlin"))
        assertTrue(driver.currentUrl.contains("/tagged/kotlin"), "Should navigate to kotlin tag page")
    }

    @Test
    fun `tag page displays questions`() {
        val tagPage = TagPage(driver).open("java")
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]")
            )
        )
        assertTrue(tagPage.hasQuestions(), "Tag page should display questions")
    }

    @Test
    fun `tag page title contains tag name`() {
        val tag = "python"
        driver.get("https://stackoverflow.com/questions/tagged/$tag")
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h1")
            )
        )
        val pageTitle = driver.title.lowercase()
        assertTrue(
            pageTitle.contains(tag),
            "Page title should contain the tag name"
        )
    }

    @Test
    fun `first question title is not blank on tag page`() {
        val tagPage = TagPage(driver).open("selenium")
        wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]")
            )
        )
        val firstTitle = tagPage.getFirstQuestionTitle()
        assertTrue(firstTitle.isNotBlank(), "First question title should not be blank")
    }

    @Test
    fun `filtering by different tags opens correct tag pages`() {
        val tags = listOf("kotlin", "java", "selenium")
        for (tag in tags) {
            TagPage(driver).open(tag)
            wait.until(ExpectedConditions.urlContains("/tagged/$tag"))
            assertTrue(
                driver.currentUrl.contains("/tagged/$tag"),
                "Should be on the '$tag' tag page"
            )
        }
    }
}
