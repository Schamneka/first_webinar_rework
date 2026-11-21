package org.example.common.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class ApiAssert {

    private ApiAssert() {
    }

    @Step("Проверка: статус-код равен {expected}")
    public static void statusCode(Response response, int expected) {
        assertEquals(expected, response.statusCode(),
                "Ожидался статус-код " + expected + ", получен " + response.statusCode()
                        + ". Тело ответа: " + response.asString());
    }

    @Step("Проверка: тело ответа содержит «{expected}»")
    public static void bodyContains(Response response, String expected) {
        assertTrue(response.asString().contains(expected),
                "В теле ответа нет «" + expected + "». Ответ: " + response.asString());
    }

    @Step("Проверка: поле «{jsonPath}» присутствует в ответе")
    public static void jsonFieldPresent(Response response, String jsonPath) {
        assertTrue(response.jsonPath().get(jsonPath) != null,
                "Поле «" + jsonPath + "» отсутствует в ответе: " + response.asString());
    }
}
