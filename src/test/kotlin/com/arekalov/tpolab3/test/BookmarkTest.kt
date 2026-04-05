package com.arekalov.tpolab3.test

import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.page.MainPage
import com.arekalov.tpolab3.page.QuestionPage
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * UC-09: Добавление вопроса в избранное.
 * Актор: Авторизованный пользователь / Гость.
 */
class BookmarkTest : BaseTest() {

    companion object {
        private const val QUESTION_URL =
            "https://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-processing-an-unsorted-array"
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
    fun `save button is visible on question page`() {
        assertTrue(questionPage.isSaveButtonVisible(), "Кнопка Save должна быть видима на странице вопроса")
    }

    @Test
    fun `guest clicking save sees login tooltip`() {
        questionPage.clickSave()
        assertTrue(
            questionPage.isSaveLoginTooltipVisible(),
            "Гость при нажатии Save должен видеть предложение войти в аккаунт"
        )
    }

    @Test
    fun `logged in user can click save button`() {
        MainPage(driver).open()
            .clickLogin()
            .fillEmail(Config.email)
            .fillPassword(Config.password)
            .submitLogin()
            .isAskQuestionButtonVisible()
        driver.get(QUESTION_URL)

        questionPage
            .also {
                it.closeGoogle()
                assertTrue(
                    questionPage.isSaveButtonVisible(),
                    "Кнопка Save должна быть видима для авторизованного пользователя"
                )
            }.clickSave()
            .also {
                assertTrue(it.isSaveButtonVisible(), "Кнопка Save должна остаться видимой после нажатия")
            }
    }

    @Test
    fun `logged in user can click unsave button`() {
        MainPage(driver).open()
            .clickLogin()
            .fillEmail(Config.email)
            .fillPassword(Config.password)
            .submitLogin()
            .isAskQuestionButtonVisible()
        driver.get(QUESTION_URL)

        questionPage
            .also {
                it.closeGoogle()
                assertTrue(
                    questionPage.isSaveButtonVisible(),
                    "Кнопка Save должна быть видима для авторизованного пользователя"
                )
            }.clickUnsave()
            .also {
                assertTrue(it.isSaveButtonVisible(), "Кнопка Save должна остаться видимой после нажатия")
            }
    }
}
