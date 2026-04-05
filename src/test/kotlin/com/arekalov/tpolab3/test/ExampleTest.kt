package com.arekalov.tpolab3.test

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Демонстрационные тесты для https://se.ifmo.ru/en/courses/testing
 * Показывают работу Selenium без блокировки Cloudflare.
 *  ./test-parallel.sh "com.arekalov.tpolab3.test.ExampleTest"
 */
class ExampleTest : BaseTest() {

    companion object {
        private const val URL = "https://se.ifmo.ru/en/courses/testing"
    }

    @BeforeEach
    fun openPage() {
        driver.get(URL)
    }

    @Test
    fun `page title is not blank1`() {
        assertFalse(driver.title.isBlank(), "Заголовок страницы не должен быть пустым")
    }

    @Test
    fun `page title is not blan2k`() {
        assertFalse(driver.title.isBlank(), "Заголовок страницы не должен быть пустым")
    }

    @Test
    fun `page title is not blank`() {
        assertFalse(driver.title.isBlank(), "Заголовок страницы не должен быть пустым")
    }

    @Test
    fun `page url matches expected`() {
        assertTrue(
            driver.currentUrl.contains("se.ifmo.ru"),
            "URL должен содержать se.ifmo.ru"
        )
    }

}
