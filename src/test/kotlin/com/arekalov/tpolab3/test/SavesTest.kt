package com.arekalov.tpolab3.test

import com.arekalov.tpolab3.Config
import com.arekalov.tpolab3.page.MainPage
import com.arekalov.tpolab3.page.ProfilePage
import com.arekalov.tpolab3.page.QuestionPage
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * UC-10: Просмотр сохранённых вопросов.
 * Актор: Авторизованный пользователь.
 */
class SavesTest : BaseTest() {

    companion object {
        private const val QUESTION_URL =
            "https://stackoverflow.com/questions/11227809/why-is-processing-a-sorted-array-faster-than-processing-an-unsorted-array"
    }

    private lateinit var profilePage: ProfilePage

    @BeforeEach
    fun loginAndOpenProfile() {
        profilePage = MainPage(driver).open()
            .also {
                it.acceptCookies()
                it.closeGoogle()
            }
            .clickLogin()
            .fillEmail(Config.email)
            .fillPassword(Config.password)
            .submitLogin()
            .also { it.closeGoogle() }
            .clickProfile()
        waitForUrl(ProfilePage.URL_FRAGMENT)
    }

    @Test
    fun `profile page is opened`() {
        assertTrue(
            driver.currentUrl.contains(ProfilePage.URL_FRAGMENT),
            "После клика по аватару должна открыться страница профиля"
        )
    }

    @Test
    fun `saves tab is accessible and shows saved items`() {
        profilePage.clickSavesTab()
        waitForUrl("saves")
        assertTrue(
            driver.currentUrl.contains("saves"),
            "После клика по вкладке Saves URL должен содержать saves"
        )
    }

    @Test
    fun `saving question makes it appear in saves tab`() {
        driver.get(QUESTION_URL)
        val questionPage = QuestionPage(driver).apply { closeGoogle() }
        if (questionPage.isSaved()) questionPage.clickSave()
        questionPage.clickSave()
        profilePage.clickSavesTab()
        waitForUrl("saves")
        assertTrue(profilePage.hasSavedItems(), "В Saves должен быть хотя бы один сохранённый вопрос")
        assertTrue(
            profilePage.getFirstSavedTitle().isNotBlank(),
            "Заголовок сохранённого вопроса не должен быть пустым"
        )
    }

    @Test
    fun `removing saved question from profile saves`() {
        driver.get(QUESTION_URL)
        val questionPage = QuestionPage(driver).apply { closeGoogle() }
        if (!questionPage.isSaved()) {
            questionPage.clickSave()
        }

        profilePage.clickSavesTab()
        waitForUrl("saves")
        assertTrue(profilePage.hasSavedItems(), "В Saves должен быть хотя бы один вопрос перед удалением")
        profilePage
            .unsaveFirst()

        assertTrue(
            profilePage.isDeletedTooltipShown(),
            "После удаления вопрос должен исчезнуть из списка Saves"
        )
    }
}
