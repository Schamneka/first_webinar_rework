
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("java")
    id("io.qameta.allure")
}

repositories {
    mavenCentral()
}

allure {
    version.set("2.29.0")
    adapter {
        aspectjWeaver.set(true)
    }
}

dependencies {
    implementation(project(":common"))

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

tasks.register<Test>("smokeApiTests") {
    group = "verification"
    description = "Smoke-прогон API-тестов (тег smoke)"
    tagged("smoke")
}

tasks.register<Test>("runAutotests") {
    group = "verification"
    description = "Полный прогон API-тестов (тег autotests)"
    tagged("autotests")
}

