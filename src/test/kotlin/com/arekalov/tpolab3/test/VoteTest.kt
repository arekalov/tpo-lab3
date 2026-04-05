package com.arekalov.tpolab3.test

import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.page.MainPage
import com.arekalov.tpolab3.page.QuestionPage
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * UC-07: Голосование за вопрос/ответ.
 * Актор: Авторизованный пользователь / Гость.
 */
class VoteTest : BaseTest() {

    companion object {
        private const val QUESTION_URL =
            "https://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-processing-an-unsorted-array"
    }

    private lateinit var questionPage: QuestionPage

    @BeforeEach
    fun openQuestion() {
        driver.get(QUESTION_URL)
        questionPage = QuestionPage(driver).apply {
            closeGoogle()
            acceptCookies()
        }
    }

    @Test
    fun `vote buttons are visible on question page`() {
        assertTrue(questionPage.isUpvoteButtonVisible(), "Кнопка upvote должна быть видима")
    }

    @Test
    fun `vote count is displayed`() {
        val count = questionPage.getVoteCount()
        assertFalse(count.isBlank(), "Счётчик голосов должен отображаться")
        assertDoesNotThrow({ count.replace(",", "").toInt() }, "Счётчик голосов должен быть числом")
    }

    @Test
    fun `guest clicking upvote shows tooltip about insufficient reputation`() {
        questionPage.clickUpvote()
        assertTrue(
            questionPage.isSaveLoginTooltipVisible(),
            "Гость при голосовании должен видеть сообщение о необходимости залогироваться"
        )
    }

    @Test
    fun `guest clicking downvote shows tooltip about insufficient reputation`() {
        questionPage.clickDownvote()
        assertTrue(
            questionPage.isSaveLoginTooltipVisible(),
            "Гость при голосовании должен видеть сообщение о необходимости залогироваться"
        )
    }

    @Test
    fun `logged in user clicking upvote sees tooltip about reputation or vote registered`() {
        MainPage(driver).open()
            .also {
                it.acceptCookies()
                it.closeGoogle()
            }
            .clickLogin()
            .fillEmail(Config.email)
            .fillPassword(Config.password)
            .submitLogin()
            .isAskQuestionButtonVisible()
        driver.get(QUESTION_URL)
        questionPage.closeGoogle()
        val countBefore = questionPage.getVoteCount()
        questionPage.clickUpvote()
        assertTrue(
            questionPage.isVoteTooltipVisible() || questionPage.getVoteCount() != countBefore,
            "После upvote авторизованным пользователем счётчик должен измениться или появиться тултип"
        )
    }

    @Test
    fun `logged in user clicking downvote sees tooltip about reputation or vote registered`() {
        MainPage(driver).open()
            .also { it.acceptCookies() }
            .clickLogin()
            .fillEmail(Config.email)
            .fillPassword(Config.password)
            .submitLogin()
            .isAskQuestionButtonVisible()
        driver.get(QUESTION_URL)
        questionPage.closeGoogle()
        val countBefore = questionPage.getVoteCount()
        questionPage.clickDownvote()
        assertTrue(
            questionPage.isVoteTooltipVisible() || questionPage.getVoteCount() != countBefore,
            "После downvote авторизованным пользователем счётчик должен измениться или появиться тултип"
        )
    }
}
