package com.arekalov.tpolab3.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.concurrent.TimeoutException

internal open class BasePage(private val driver: WebDriver) {
    companion object {
        const val COOKIES_ACCEPT_BUTTON_XPATH = "//*[@id=\"onetrust-accept-btn-handler\"]"
        private const val XPATH_RECAPTCHA_ANCHOR_IFRAME = "//iframe[contains(@src,'recaptcha/api2/anchor')]"
        private const val XPATH_RECAPTCHA_BFRAME = "//iframe[contains(@src,'recaptcha/api2/bframe')]"
    }

    protected val wait = WebDriverWait(driver, Duration.ofSeconds(15))

    fun cloudFlare() {
        Thread.sleep(5000)
    }

    fun acceptCookies() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COOKIES_ACCEPT_BUTTON_XPATH))).click()
    }

    fun waitUntilRecaptchaResolved(timeout: Duration = Duration.ofMinutes(5)) {
        println("\n🔍 [DEBUG] Проверяем наличие reCAPTCHA...")

        if (!isRecaptchaPresent()) {
            println("ℹ️ [DEBUG] reCAPTCHA НЕ НАЙДЕНА — пропускаем")
            return
        }

        println("🔐 [DEBUG] reCAPTCHA НАЙДЕНА! Ждём РУЧНОГО решения...")
        val deadline = System.currentTimeMillis() + timeout.toMillis()
        val pollInterval = 500L
        var iteration = 0

        while (System.currentTimeMillis() < deadline) {
            iteration++
            switchToDefaultContent()

            println("\n⏳ [DEBUG] Итерация #$iteration (${(deadline - System.currentTimeMillis()) / 1000}s)")
            println("   📋 Popup: ${isPopupVisible()}")
            println("   📋 Challenge: ${isBframeChallengeVisible()}")
            println("   ☑️  Token ready: ${isRecaptchaTokenReady()}")

            if (isRecaptchaSolved()) {
                println("🎉 [SUCCESS] reCAPTCHA решена досрочно!")
                return
            }

            println("   ⏳ Ждём...")
            Thread.sleep(pollInterval)
            print(".")
        }

        switchToDefaultContent()
        throw TimeoutException("reCAPTCHA не решена за ${timeout.seconds}с")
    }

    /** 🔥 ПРЯМАЯ ПРОВЕРКА ПО TEXTAREA ТОКЕНА (НАДЁЖНО!) */
    private fun isRecaptchaSolved(): Boolean {
        return !isBframeChallengeVisible() && isRecaptchaTokenReady()
    }

    private fun isRecaptchaTokenReady(): Boolean {
        return try {
            val responseField = driver.findElement(By.id("g-recaptcha-response"))
            val token = responseField.getAttribute("value")
            val ready = token != null && token.isNotEmpty() && token.length > 10
            println("   📝 Token: ${token?.length ?: 0} chars ${if (ready) "✅" else "❌"}")
            ready
        } catch (e: Exception) {
            println("   📝 Token field: НЕ НАЙДЕН")
            false
        }
    }

    /** 🔥 БЕЗОПАСНЫЙ ПОИСК reCAPTCHA (CSS + XPath) */
    private fun isRecaptchaPresent(): Boolean {
        val cssSelectors = listOf(
            "#no-captcha-here",
            ".g-recaptcha",
            "[data-sitekey*='6Lc5vwgk']",
            ".captcha-popup[data-controller='se-draggable']"
        )

        cssSelectors.forEach { selector ->
            val count = driver.findElements(By.cssSelector(selector)).size
            if (count > 0) {
                println("   🎯 CSS '$selector': $count")
                return true
            }
        }

        // XPath без точек в начале!
        val xpaths = listOf(
            "//iframe[contains(@src, 'recaptcha')]",
            "//iframe[@title='reCAPTCHA']",
            "//div[contains(@id, 'captcha')]",
            "//*[contains(@class, 'captcha-popup')]"
        )

        xpaths.forEach { xpath ->
            val count = driver.findElements(By.xpath(xpath)).size
            if (count > 0) {
                println("   🔍 XPath '$xpath': $count")
                return true
            }
        }

        println("   ❌ reCAPTCHA элементы не найдены")
        return false
    }

    private fun isPopupVisible(): Boolean {
        val selector = ".captcha-popup[style*='display: block'], [data-controller='se-draggable']"
        val count = driver.findElements(By.cssSelector(selector)).size
        println("   🎪 Popup: $count")
        return count > 0
    }

    private fun isBframeChallengeVisible(): Boolean {
        val xpaths = listOf(
            "//div[contains(@class, 'rc-challenge')]",
            "//*[contains(@class, 'challenge')]",
            ".rc-challenge-main"
        )

        val total = xpaths.sumOf { driver.findElements(By.xpath(it)).size }
        println("   📋 Challenge frames: $total")
        return total > 0
    }

    protected fun switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent()
        } catch (e: Exception) {
            println("   ⚠️  switchToDefault: ${e.message}")
        }
    }
}