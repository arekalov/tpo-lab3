package com.arekalov.tpolab3.test

import com.arekalov.tpolab3.page.MainPage
import com.arekalov.tpolab3.page.SearchPage
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * UC-02: Поиск вопроса по ключевым словам.
 * Актор: Гость / Авторизованный пользователь.
 */
class SearchPageTest : BaseTest() {

    private lateinit var searchPage: SearchPage

    companion object {
        private const val QUESTIONS_URL_FRAGMENT = "/questions/"
    }

    @BeforeEach
    fun openMainAndSearch() {
        searchPage = MainPage(driver).open()
            .searchFor("kotlin coroutines")
            .also { it.acceptCookies() }
    }

    @Test
    fun `search navigates to search results page`() {
        waitForUrl(SearchPage.URL_FRAGMENT)
        assertTrue(
            driver.currentUrl.contains(SearchPage.URL_FRAGMENT),
            "После поиска URL должен содержать /search"
        )
    }

    @Test
    fun `search query is present in url`() {
        waitForUrl("q=")
        assertTrue(
            driver.currentUrl.contains("kotlin") || driver.currentUrl.contains("coroutines"),
            "URL должен содержать поисковый запрос"
        )
    }

    @Test
    fun `search returns results for known query`() {
        assertTrue(searchPage.hasResults(), "Поиск по 'kotlin coroutines' должен вернуть результаты")
    }

    @Test
    fun `first result title is not blank`() {
        assertTrue(searchPage.hasResults(), "Должны быть результаты поиска")
        assertFalse(
            searchPage.getFirstResultTitle().isBlank(),
            "Заголовок первого результата не должен быть пустым"
        )
    }

    @Test
    fun `clicking first result opens question page`() {
        assertTrue(searchPage.hasResults(), "Должны быть результаты поиска")
        searchPage.clickFirstResult()
        waitForUrl(QUESTIONS_URL_FRAGMENT)
        assertTrue(
            driver.currentUrl.contains(QUESTIONS_URL_FRAGMENT),
            "Клик по результату должен открывать страницу вопроса /questions/{id}"
        )
        Thread.sleep(10000)
    }

    @Test
    fun `search with no results shows no results message`() {
        searchPage.hasResults()
        val noResultsPage = searchPage.searchFor("xyzzy123абвгд_нет_такого")
        assertTrue(searchPage.hasNoResultsMessage(), "Поиск несуществующего запроса должен отображать сообщение об отсутствии результатов")
        assertFalse(
            noResultsPage.hssNoResults(),
            "Поиск несуществующего запроса не долджен отображать результаты"
        )
    }
}
