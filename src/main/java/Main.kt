
import driver.ChromeDriverBuilder
import org.openqa.selenium.chrome.ChromeOptions

fun main() {
    // Путь к chromedriver
    val driverHome = "/usr/local/bin/chromedriver"
    val chromeOptions = ChromeOptions().apply {
        addArguments("--user-data-dir=/Users/arekalov/Library/Application Support/Google/Chrome")
        addArguments("--user-data-dir=/tmp/my-chrome-profile")
    }

    val driver2 = ChromeDriverBuilder()
        .build(chromeOptions, driverHome)

    driver2.get("https://www.vindecoderz.com/EN/check-lookup/ZDMMADBMXHB001652")
//    driver2.get("https://intoli.com/blog/not-possible-to-block-chrome-headless/chrome-headless-test.html")
    println("Title: ${driver2.title}")
    Thread.sleep(100000)
    driver2.quit()
}
