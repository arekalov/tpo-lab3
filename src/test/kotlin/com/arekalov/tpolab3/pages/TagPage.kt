package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

class TagPage(private val driver: WebDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    private val tagTitle = By.xpath("//h1[contains(@class,'fs-headline')]")
    private val questionItems = By.xpath("//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]")
    private val tagDescription = By.xpath("//div[contains(@class,'js-tag-description') or @itemprop='description']")
    private val paginationNext = By.xpath("//a[@rel='next']")

    fun open(tag: String): TagPage {
        driver.get("https://stackoverflow.com/questions/tagged/$tag")
        return this
    }

    fun getTagName(): String {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tagTitle)).text.trim()
    }

    fun getQuestionCount(): Int {
        return driver.findElements(questionItems).size
    }

    fun hasQuestions(): Boolean {
        return driver.findElements(questionItems).isNotEmpty()
    }

    fun getFirstQuestionTitle(): String {
        val el = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                    "(//div[contains(@class,'s-post-summary') or contains(@class,'question-summary')]//h3)[1]"
                )
            )
        )
        return el.text.trim()
    }

    fun clickNextPage(): TagPage {
        wait.until(ExpectedConditions.elementToBeClickable(paginationNext)).click()
        return this
    }

    fun hasNextPage(): Boolean {
        return driver.findElements(paginationNext).isNotEmpty()
    }
}
