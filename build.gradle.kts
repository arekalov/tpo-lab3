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
    testImplementation("org.seleniumhq.selenium:selenium-java:4.41.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
    testImplementation("com.github.bramar2:undetectedselenium:d7d9b0ff42")
    testImplementation("com.google.code.gson:gson:2.10.1")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.13")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("browser", System.getProperty("browser", "chrome"))
}

val testChrome by tasks.registering(Test::class) {
    useJUnitPlatform()
    systemProperty("browser", "chrome")
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
}

val testFirefox by tasks.registering(Test::class) {
    useJUnitPlatform()
    systemProperty("browser", "firefox")
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
}

kotlin {
    jvmToolchain(17)
}