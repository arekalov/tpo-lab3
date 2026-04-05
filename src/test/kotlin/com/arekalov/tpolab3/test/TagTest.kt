package com.arekalov.tpolab3.test

import com.arekalov.tpolab3.page.QuestionPage
import com.arekalov.tpolab3.page.TagPage
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * UC-08: Просмотр тегов и переход по тегу.
 * Актор: Гость / Авторизованный пользователь.
 */
class TagTest : BaseTest() {

    companion object {
        private const val QUESTION_URL =
            "https://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-processing-an-unsorted-array"
    }

    private lateinit var tagPage: TagPage

    @BeforeEach
    fun openQuestionAndClickTag() {
        driver.get(QUESTION_URL)
        tagPage = QuestionPage(driver).apply {
            acceptCookies()
            closeGoogle()
        }.clickFirstTag()
    }

    @Test
    fun `clicking tag navigates to tagged questions page`() {
        waitForUrl(TagPage.URL_FRAGMENT)
        assertTrue(
            driver.currentUrl.contains(TagPage.URL_FRAGMENT),
            "Клик по тегу должен вести на /questions/tagged/"
        )
        Thread.sleep(10000)
    }

    @Test
    fun `tagged page shows list of questions`() {
        assertTrue(tagPage.hasResults(), "Страница тега должна содержать список вопросов")
    }

    @Test
    fun `tag header is visible`() {
        assertTrue(tagPage.isTagHeaderVisible(), "Заголовок страницы тега должен быть виден")
    }

    @Test
    fun `first question title on tag page is not blank`() {
        assertFalse(tagPage.getFirstResultTitle().isBlank(), "Заголовок первого вопроса не должен быть пустым")
    }

    @Test
    fun `clicking question on tag page opens question page`() {
        tagPage.clickFirstResult()
        waitForUrl(QuestionPage.URL_FRAGMENT)
        assertTrue(
            driver.currentUrl.contains(QuestionPage.URL_FRAGMENT),
            "Клик по вопросу должен открывать страницу /questions/{id}"
        )
    }
}
