plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.arekalov.tpolab3"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.20.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.8.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.2")
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