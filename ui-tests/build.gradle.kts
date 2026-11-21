import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
    id("io.qameta.allure")
}

repositories {
    mavenCentral()
}

allure {
    version.set("2.29.0")          // версия allure-java (зависимостей и генератора)
    adapter {
        aspectjWeaver.set(true)    // вплетает аспекты @Step
    }
}

dependencies {
    implementation("com.codeborne:selenide:${project.property("selenideVersion")}")
    implementation("io.qameta.allure:allure-java-commons:${project.property("allureVersion")}")
    implementation("io.qameta.allure:allure-selenide:${project.property("allureVersion")}")

    testImplementation(project(":common"))
    testImplementation(project(":api-tests"))
    testImplementation(platform("org.junit:junit-bom:${project.property("junitVersion")}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.qameta.allure:allure-junit5:${project.property("allureVersion")}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

fun Test.tagged(includeTag: String) {
    useJUnitPlatform { includeTags(includeTag) }
    outputs.upToDateWhen { false }
    testLogging {
        events("passed", "failed", "skipped")
        exceptionFormat = TestExceptionFormat.FULL
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
        exceptionFormat = TestExceptionFormat.FULL
    }
}

tasks.register<Test>("smokeUiTests") {
    group = "verification"
    description = "Smoke-прогон UI-тестов (тег smoke)"
    tagged("smoke")
}

tasks.register<Test>("runAutotests") {
    group = "verification"
    description = "Полный прогон UI-тестов (тег autotests)"
    tagged("autotests")
}

tasks.register<Test>("runSelenideTests") {
    group = "verification"
    description = "Только Selenide-тесты (тег selenide)"
    tagged("selenide")
}

tasks.register<Test>("runSeleniumTests") {
    group = "verification"
    description = "Только Selenium-тесты (тег selenium)"
    tagged("selenium")
}


