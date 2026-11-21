plugins {
    id("io.qameta.allure") version "2.12.0"
}

allprojects {
    group = "org.example"
    version = "1.0-SNAPSHOT"
    repositories { mavenCentral() }
}

/* --- Алиасы на задачи подмодулей: источник truth — в самих модулях --- */

tasks.register("smokeApiTests") {
    group = "verification"
    dependsOn(":api-tests:smokeApiTests")
}

tasks.register("smokeUiTests") {
    group = "verification"
    dependsOn(":ui-tests:smokeUiTests")
}

tasks.register("runSelenideTests") {
    group = "verification"
    dependsOn(":ui-tests:runSelenideTests")
}

tasks.register("runSeleniumTests") {
    group = "verification"
    dependsOn(":ui-tests:runSeleniumTests")
}

tasks.register("runAutotests") {
    group = "verification"
    dependsOn(":api-tests:runAutotests", ":ui-tests:runAutotests")
}

/* --- Агрегация allure-results обоих модулей в общий отчёт --- */

val mergedResults = layout.buildDirectory.dir("allure-results")

tasks.register<Copy>("collectAllureResults") {
    group = "reporting"
    doFirst { delete(mergedResults) }
    from(project(":api-tests").layout.buildDirectory.dir("allure-results"))
    from(project(":ui-tests").layout.buildDirectory.dir("allure-results"))
    into(mergedResults)
}

tasks.matching { it.name == "allureReport" }.configureEach {
    dependsOn("collectAllureResults")
    outputs.upToDateWhen { false }
}
