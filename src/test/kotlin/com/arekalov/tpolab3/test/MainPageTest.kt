package com.arekalov.tpolab3.test

import com.arekalov.tpolab3.page.MainPage
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * UC-01: Просмотр главной страницы с вопросами.
 * Актор: Гость / Авторизованный пользователь.
 */
class MainPageTest : BaseTest() {
    companion object {
        private const val SEARCH_RESOURCE = "/search"
        private const val LOGIN_RESOURCE = "/users/login"
    }

    private lateinit var mainPage: MainPage

    @BeforeEach
    fun openMainPage() {
        mainPage = MainPage(driver).open()
    }

    @Test
    fun `main page opens on stackoverflow domain and main components displayed`() {
        assertTrue(
            driver.currentUrl.contains(MainPage.URL),
            "URL должен содержать stackoverflow.com"
        )
        assertTrue(mainPage.hasQuestions(), "На главной должен быть список вопросов")
        assertTrue(mainPage.isSearchBarVisible(), "Строка поиска должна быть видима")
        assertTrue(mainPage.isTagsNavVisible(), "Ссылка на теги должна быть в навигации")
        assertTrue(mainPage.isAskQuestionButtonVisible(), "Кнопка Ask Question должна быть на странице")
    }

    @Test
    fun `first question title is not blank`() {
        val title = mainPage.getFirstQuestionTitle()
        assertFalse(title.isBlank(), "Заголовок первого вопроса не должен быть пустым")
    }

    @Test
    fun `clicking Log in navigates to login page`() {
        mainPage.clickLogin()
        waitForUrl(LOGIN_RESOURCE)
        assertTrue(
            driver.currentUrl.contains(LOGIN_RESOURCE),
            "Клик по Log in должен вести на страницу входа"
        )
    }

    @Test
    fun `search from main page navigates to search results`() {
        mainPage
            .searchFor("kotlin coroutines")
            .acceptCookies()
        waitForUrl(SEARCH_RESOURCE)
        assertTrue(
            driver.currentUrl.contains(SEARCH_RESOURCE),
            "Поиск с главной должен открывать страницу результатов"
        )
    }

    @Test
    fun `search query is reflected in url`() {
        mainPage
            .searchFor("java streams")
            .acceptCookies()
        waitForUrl("q=")
        assertTrue(
            driver.currentUrl.contains("java") || driver.currentUrl.contains("streams"),
            "URL должен содержать поисковый запрос"
        )
    }

    @Test
    fun `clicking Ask Question navigates to ask login page`() {
        mainPage
            .navigateToAskQuestion()
            .acceptCookies()
        waitForUrl(LOGIN_RESOURCE)
        assertTrue(
            driver.currentUrl.contains(LOGIN_RESOURCE),
            "Клик по Log in должен вести на страницу входа"
        )
    }
}
