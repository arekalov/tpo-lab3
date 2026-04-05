package com.arekalov.tpolab3.test

import com.arekalov.tpolab3.page.MainPage
import com.arekalov.tpolab3.page.QuestionPage
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * UC-03: Просмотр карточки вопроса.
 * Актор: Гость / Авторизованный пользователь.
 */
class QuestionPageTest : BaseTest() {

    private lateinit var questionPage: QuestionPage

    @BeforeEach
    fun openFirstQuestion() {
        questionPage = MainPage(driver).open()
            .also { it.acceptCookies() }
            .clickFirstQuestion()
    }

    @Test
    fun `question page url contains questions fragment`() {
        waitForUrl(QuestionPage.URL_FRAGMENT)
        assertTrue(
            driver.currentUrl.contains(QuestionPage.URL_FRAGMENT),
            "URL страницы вопроса должен содержать /questions/"
        )
    }

    @Test
    fun `question title is displayed and not blank and question body is visible`() {
        val title = questionPage.getTitle()
        assertFalse(title.isBlank(), "Заголовок вопроса должен быть отображён и не пустым")
        assertTrue(questionPage.isQuestionBodyVisible(), "Тело вопроса должно быть видимо")
    }

    @Test
    fun `question has at least one tag`() {
        val tags = questionPage.getTags()
        assertTrue(tags.isNotEmpty(), "У вопроса должен быть хотя бы один тег")
        assertTrue(tags.all { it.isNotBlank() }, "Теги не должны быть пустыми")
    }

    @Test
    fun `answers section is present`() {
        assertTrue(questionPage.hasAnswers(), "На странице вопроса должны быть ответы")
    }
}
