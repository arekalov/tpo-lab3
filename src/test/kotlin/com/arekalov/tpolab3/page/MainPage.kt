package com.arekalov.tpolab3.page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

internal class MainPage(driver: WebDriver) : BasePage(driver) {

    companion object {
        const val URL = "https://stackoverflow.com"
        private const val XPATH_QUESTION_ITEMS = "//div[@id='questions']//div"
        private const val XPATH_FIRST_QUESTION_TITLE = "(//div[contains(@class,'s-post-summary')]//h3/a)[1]"
        private const val XPATH_SEARCH_INPUT = "//input[@name='q' and @type='text']"
        private const val XPATH_TAGS_NAV = "//ol[@class='nav-links']//ol[@class='nav-links']/*"
        private const val XPATH_ASK_QUESTION = "//a[contains(@href,'/questions/ask')]"
        private const val XPATH_LOGIN_LINK = "//a[@class='s-topbar--item s-topbar--item__unset s-btn s-btn__outlined ws-nowrap js-gps-track']"
        private const val XPATH_PROFILE_LINK = "//a[contains(@href,'/users/') and contains(@class,'s-topbar--item')]"
    }

    fun open(): MainPage {
        driver.get(URL)
        return this
    }

    fun hasQuestions(): Boolean = waitVisible(By.xpath(XPATH_QUESTION_ITEMS)).isDisplayed

    fun isSearchBarVisible(): Boolean = waitVisible(By.xpath(XPATH_SEARCH_INPUT)).isDisplayed

    fun isTagsNavVisible(): Boolean = waitVisible(By.xpath(XPATH_TAGS_NAV)).isDisplayed

    fun isAskQuestionButtonVisible(): Boolean = waitVisible(By.xpath(XPATH_ASK_QUESTION)).isDisplayed

    fun getFirstQuestionTitle(): String =
        waitVisible(By.xpath(XPATH_FIRST_QUESTION_TITLE)).text.trim()

    fun clickLogin(): LoginPage {
        waitClickable(By.xpath(XPATH_LOGIN_LINK)).click()
        return LoginPage(driver)
    }

    fun searchFor(query: String): SearchPage {
        val input = waitClickable(By.xpath(XPATH_SEARCH_INPUT))
        input.clear()
        input.sendKeys(query)
        input.submit()
        return SearchPage(driver)
    }

    fun navigateToAskQuestion(): AskQuestionPage {
        waitClickable(By.xpath(XPATH_ASK_QUESTION)).click()
        return AskQuestionPage(driver)
    }

    fun clickFirstQuestion(): QuestionPage {
        waitClickable(By.xpath(XPATH_FIRST_QUESTION_TITLE)).click()
        return QuestionPage(driver)
    }

    fun clickProfile(): ProfilePage {
        waitClickable(By.xpath(XPATH_PROFILE_LINK)).click()
        return ProfilePage(driver)
    }
}
