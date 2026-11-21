plugins {
    `java-library`
}

dependencies {
    api("io.rest-assured:rest-assured:${project.property("restAssuredVersion")}")
    api("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    api("io.qameta.allure:allure-java-commons:${project.property("allureVersion")}")
    api("io.qameta.allure:allure-rest-assured:${project.property("allureVersion")}")
    api("org.junit.jupiter:junit-jupiter-api:${project.property("junitVersion")}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

