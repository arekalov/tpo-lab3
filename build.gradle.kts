plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.arekalov.tpolab3"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
    testImplementation("net.datafaker:datafaker:2.4.2")

    // Source: https://mvnrepository.com/artifact/com.alibaba/fastjson
    implementation("com.alibaba:fastjson:2.0.61")
    implementation("org.slf4j:slf4j-simple:2.0.9")

    // Selenium Java (без дочерних модулей)
    implementation("org.seleniumhq.selenium:selenium-java:4.20.0") {
        exclude(group = "org.seleniumhq.selenium", module = "selenium-api")
        exclude(group = "org.seleniumhq.selenium", module = "selenium-chrome-driver")
        exclude(group = "org.seleniumhq.selenium", module = "selenium-support")
        exclude(group = "org.seleniumhq.selenium", module = "selenium-ie-driver")
        exclude(group = "org.seleniumhq.selenium", module = "selenium-edge-driver")
        exclude(group = "org.seleniumhq.selenium", module = "selenium-firefox-driver")
        exclude(group = "org.seleniumhq.selenium", module = "selenium-remote-driver")
        exclude(group = "org.seleniumhq.selenium", module = "selenium-safari-driver")
    }
//    implementation("org.seleniumhq.selenium:selenium-devtools-v146:4.20.0")

    // Selenium API
    implementation("org.seleniumhq.selenium:selenium-api:4.20.0")

    // Chrome Driver
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.20.0") {
        exclude(group = "org.seleniumhq.selenium", module = "selenium-api")
    }

    // Selenium Support
    implementation("org.seleniumhq.selenium:selenium-support:4.20.0") {
        exclude(group = "org.seleniumhq.selenium", module = "selenium-api")
    }

    // IE Driver
    implementation("org.seleniumhq.selenium:selenium-ie-driver:4.20.0") {
        exclude(group = "org.seleniumhq.selenium", module = "selenium-api")
    }

    // Edge Driver
    implementation("org.seleniumhq.selenium:selenium-edge-driver:4.20.0") {
        exclude(group = "org.seleniumhq.selenium", module = "selenium-api")
    }

    // Firefox Driver
    implementation("org.seleniumhq.selenium:selenium-firefox-driver:4.20.0") {
        exclude(group = "org.seleniumhq.selenium", module = "selenium-api")
    }

    // Remote Driver
    implementation("org.seleniumhq.selenium:selenium-remote-driver:4.20.0") {
        exclude(group = "org.seleniumhq.selenium", module = "selenium-api")
    }

    // Safari Driver
    implementation("org.seleniumhq.selenium:selenium-safari-driver:4.20.0") {
        exclude(group = "org.seleniumhq.selenium", module = "selenium-api")
    }
}

tasks.test {
    useJUnitPlatform()
    systemProperty("browser", System.getProperty("browser", "chrome"))
}

val testFilter = System.getProperty("tests")

val testChrome by tasks.registering(Test::class) {
    useJUnitPlatform()
    systemProperty("browser", "chrome")
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    if (testFilter != null) filter { includeTestsMatching(testFilter) }
    outputs.upToDateWhen { false }
}

val testFirefox by tasks.registering(Test::class) {
    useJUnitPlatform()
    systemProperty("browser", "firefox")
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
    if (testFilter != null) filter { includeTestsMatching(testFilter) }
    outputs.upToDateWhen { false }
    mustRunAfter() // нет зависимостей от testChrome
}

// таск для параллельного запуска обоих браузеров
tasks.register("testAllBrowsers") {
    dependsOn(testChrome, testFirefox)
}

kotlin {
    jvmToolchain(17)
}