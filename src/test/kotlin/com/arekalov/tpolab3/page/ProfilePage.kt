package com.arekalov.tpolab3.page

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

internal class ProfilePage(driver: WebDriver) : BasePage(driver) {

    companion object {
        const val URL_FRAGMENT = "/users/"

        private const val XPATH_SAVES_TAB = "//a[contains(@href,'saves') or normalize-space()='Saves']"
        private const val XPATH_SAVED_ITEMS =
            "//div[contains(@class,'s-post-summary')] | //div[contains(@class,'saves-list')]//div"
        private const val XPATH_FIRST_SAVED_TITLE = "(//div[contains(@class,'s-post-summary')]//h3/a)[1]"
        private const val XPATH_FIRST_ITEM_MENU_BUTTON =
            "(//div[contains(@class,'s-post-summary')]//button[contains(@class,'js-saves-item-actions') or @aria-label='more options' or (@class and contains(@class,'s-btn__muted') and not(contains(@class,'js-saves-btn')))])[1]"
        private const val XPATH_UNSAVE_OPTION =
            "//li[normalize-space()='Unsave'] | //button[normalize-space()='Unsave']"
        private const val XPATH_PROFILE_USERNAME =
            "//div[contains(@class,'grid--cell')]//div[contains(@class,'fs-title')]"
    }


    fun isDeletedTooltipShown(): Boolean = waitVisible(By.xpath("//p[@id='notice-toast-message']")).isDisplayed

    fun clickSavesTab(): ProfilePage {
        waitClickable(By.xpath(XPATH_SAVES_TAB)).click()
        return this
    }

    fun hasSavedItems(): Boolean =
        waitVisibleElements(By.xpath(XPATH_SAVED_ITEMS)).isNotEmpty()

    fun getFirstSavedTitle(): String =
        waitVisible(By.xpath(XPATH_FIRST_SAVED_TITLE)).text.trim()

    fun unsaveFirst(): ProfilePage {
        val menuBtn = waitClickable(By.xpath(XPATH_FIRST_ITEM_MENU_BUTTON))
        scrollTo(menuBtn)
        jsClick(menuBtn)
        waitClickable(By.xpath(XPATH_UNSAVE_OPTION)).click()
        return this
    }
}
