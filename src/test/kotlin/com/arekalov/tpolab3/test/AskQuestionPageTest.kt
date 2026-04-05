package com.arekalov.tpolab3.test

import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.page.AskQuestionPage
import com.arekalov.tpolab3.page.MainPage
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assumptions.assumeFalse
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

/**
 * UC-05: Создание нового вопроса.
 * Актор: Авторизованный пользователь.
 */
class AskQuestionPageTest : BaseTest() {

    companion object {
        private val faker = Faker()
    }

    private lateinit var askPage: AskQuestionPage

    @BeforeEach
    fun loginAndOpenAskPage() {
        askPage = MainPage(driver).open()
            .also {
                it.acceptCookies()
                it.closeGoogle()
            }
            .clickLogin()
            .fillEmail(Config.email)
            .fillPassword(Config.password)
            .submitLogin()
            .also { it.closeGoogle() }
            .navigateToAskQuestion()
        waitForUrl(AskQuestionPage.URL_FRAGMENT)
        skipIfBanned()
    }

    private fun skipIfBanned() {
        try {
            val banBanner = WebDriverWait(driver, Duration.ofSeconds(3)).until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(text(),\"You can’t post new questions right now\")]")
                )
            )
            assumeFalse(banBanner.isDisplayed, "Аккаунт заблокирован для публикации вопросов — тест пропущен")
        } catch (ex: TimeoutException) {
            // баннера нет — продолжаем тест
        }
    }

    @Test
    fun `ask question form has title body and tags fields`() {

        assertTrue(askPage.isTitleInputVisible(), "Поле Title должно быть видимо")
        assertTrue(askPage.isBodyEditorVisible(), "Редактор тела вопроса должен быть видим")
        assertTrue(askPage.isTagsInputVisible(), "Поле Tags должно быть видимо")
    }

    @Test
    fun `submitting without title shows validation error`() {
        askPage
            .fillBody("Some body text for the question that is long enough")
            .clickNext()
        assertTrue(askPage.hasTitleError(), "При пустом Title должна появиться ошибка валидации")
    }

    @Test
    fun `filling title moves past title validation`() {
        askPage
            .fillTitle("How to use Kotlin coroutines with Android ViewModel properly? ${faker.lorem().word()}")
            .fillBody(faker.lorem().sentence(40))
            .fillTag("kotlin")
        assertTrue(
            askPage.isNextButtonClickable(),
            "После заполнения Title ошибка валидации не должна отображаться, кнопка продолжения должна быть активной"
        )
    }

    @Test
    fun `create question`() {
        askPage
            .fillTitle("How to use Kotlin coroutines with Android ViewModel properly? ${faker.lorem().word()}")
            .fillBody(faker.lorem().sentence(40))
            .fillTag("kotlin")
            .also {
                assertTrue(
                    it.isNextButtonClickable(),
                    "После заполнения Title ошибка валидации не должна отображаться, кнопка продолжения должна быть активной"
                )
            }
            .clickNext()
    }
}
