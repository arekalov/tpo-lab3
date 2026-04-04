package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.Keys.BACK_SPACE
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

internal class AskQuestionPage(private val driver: WebDriver) : BasePage(driver) {

    companion object {
        private const val XPATH_TITLE = "//input[@id='post-title-input']"
        private const val XPATH_BODY = "//div[@role='textbox']"
        private const val XPATH_TAGS = "//input[@id='filterInput']"
        private const val XPATH_SUBMIT = "//button[normalize-space()='Submit to Staging Ground']"
        private const val XPATH_TITLE_ERROR =
            "//div[contains(text(),'Title is missing.')] | //div[contains(text(),'Body is missing.')] | //div[contains(@class,'js-warnings-and-errors-container-019d5948-8528-7698-a7d0-3d2d4dba59b2')]//div[@class='s-input-message fc-error s-anchors s-anchors__underlined']"
        private const val XPATH_TAGS_SPAN = "//*[@id=\"text-input-container\"]/div[1]/div/span/span/*"
    }

    private val titleField = By.xpath(XPATH_TITLE)
    private val bodyEditor = By.xpath(XPATH_BODY)
    private val tagsField = By.xpath(XPATH_TAGS)
    private val submitButton = By.xpath(XPATH_SUBMIT)

    fun enterTitle(title: String): AskQuestionPage {
        val field = wait.until(ExpectedConditions.elementToBeClickable(titleField))
        field.clear()
        field.sendKeys(title)
        return this
    }

    fun enterBody(body: String): AskQuestionPage {
        val editor = wait.until(ExpectedConditions.elementToBeClickable(bodyEditor))
        editor.click()
        editor.sendKeys(body)
        return this
    }

    fun enterTag(tag: String): AskQuestionPage {
        val field = wait.until(ExpectedConditions.elementToBeClickable(tagsField))
        field.click()
        while (field.findElements(By.xpath(XPATH_TAGS_SPAN)).isNotEmpty()) {
            field.sendKeys(BACK_SPACE)
        }
        field.sendKeys(tag)
        field.sendKeys(Keys.ENTER)
        clickInBody()
        return this
    }

    fun submitQuestion(): QuestionPage {
        driver.findElements(submitButton)
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click()
        return QuestionPage(driver)
    }

    fun isTitleErrorVisible(): AskQuestionPage {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(XPATH_TITLE_ERROR))).isNotEmpty()
        return this
    }

    fun clickInBody(): AskQuestionPage {
        driver.findElement(By.tagName("body")).click()
        return this
    }
}
