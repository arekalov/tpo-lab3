package com.arekalov.tpolab3.test

import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.page.LoginPage
import com.arekalov.tpolab3.page.MainPage
import com.arekalov.tpolab3.page.QuestionPage
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * UC-06: Добавление ответа.
 * Актор: Авторизованный пользователь / Гость.
 */
class AnswerTest : BaseTest() {

    companion object {
        // вопрос с активным обсуждением, гарантированно имеет форму ответа
        private const val QUESTION_URL =
            "https://stackoverflow.com/questions/79920521/permission-denied-when-running-executable-with-systemd"
    }

    private lateinit var questionPage: QuestionPage

    @BeforeEach
    fun openQuestion() {
        driver.get(QUESTION_URL)
        questionPage = QuestionPage(driver).apply {
            acceptCookies()
            closeGoogle()
        }
    }

    @Test
    fun `answer editor is visible on question page for guest`() {
        assertTrue(questionPage.isAnswerEditorVisible(), "Редактор ответа должен быть виден на странице вопроса")
    }

    @Test
    fun `guest clicking post answer redirects to login`() {
        questionPage
            .fillAnswer("This is a test answer text that is long enough to pass validation requirements.")
            .clickPostAnswer()
        assertTrue(
            questionPage.isErrorVisible(),
            "Гость при попытке опубликовать ответ должен быть перенаправлен на страницу входа"
        )
    }

    @Test
    fun `logged in user sees answer editor`() {
        MainPage(driver).open()
            .clickLogin()
            .fillEmail(Config.email)
            .fillPassword(Config.password)
            .submitLogin()
            .isAskQuestionButtonVisible()
        driver.get(QUESTION_URL)
        questionPage.isQuestionBodyVisible()
        assertTrue(questionPage.isAnswerEditorVisible(), "Авторизованный пользователь должен видеть редактор ответа")
    }
}
