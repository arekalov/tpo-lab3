package com.arekalov.tpolab3.tests

import com.arekalov.tpolab3.BaseTest
import com.arekalov.tpolab3.pages.TagPage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.By

class TagTest : BaseTest() {

    companion object {
        private const val URL_TAGGED_TEMPLATE = "https://stackoverflow.com/questions/tagged/"
        private const val URL_FRAGMENT_TAGGED = "/tagged/"
        private const val XPATH_QUESTION_SUMMARY =
            "//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]"
        private const val XPATH_H1 = "//h1"
    }

    @Test
    fun `tag page for kotlin opens successfully`() {
        TagPage(driver).open("kotlin")
        wait.until(ExpectedConditions.urlContains("/tagged/kotlin"))
        assertTrue(driver.currentUrl.contains("/tagged/kotlin"), "Should navigate to kotlin tag page")
    }

    @Test
    fun `tag page displays questions`() {
        val tagPage = TagPage(driver).open("java")
        wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_QUESTION_SUMMARY))
        )
        assertTrue(tagPage.hasQuestions(), "Tag page should display questions")
    }

    @Test
    fun `tag page title contains tag name`() {
        val tag = "python"
        driver.get("$URL_TAGGED_TEMPLATE$tag")
        wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_H1))
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
            ExpectedConditions.presenceOfElementLocated(By.xpath(XPATH_QUESTION_SUMMARY))
        )
        val firstTitle = tagPage.getFirstQuestionTitle()
        assertTrue(firstTitle.isNotBlank(), "First question title should not be blank")
    }

    @Test
    fun `filtering by different tags opens correct tag pages`() {
        val tags = listOf("kotlin", "java", "selenium")
        for (tag in tags) {
            TagPage(driver).open(tag)
            wait.until(ExpectedConditions.urlContains("$URL_FRAGMENT_TAGGED$tag"))
            assertTrue(
                driver.currentUrl.contains("/tagged/$tag"),
                "Should be on the '$tag' tag page"
            )
        }
    }
}
