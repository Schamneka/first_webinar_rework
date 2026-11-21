package org.example.common.api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.common.config.ConfigParser;

public final class RestApiBuilder {

    public static final String BASE_URL = ConfigParser.getProperty("base.url");
    public static final String GOODS_LIST = "/goods/list";
    public static final String GOODS_ADD = "/goods/add";
    public static final String GOODS_BY_ID = "/goods/{id}";
    public static final String LOGIN = "/login";

    private static final String USER = ConfigParser.getProperty("admin.login");
    private static final String PASSWORD = ConfigParser.getProperty("admin.password");

    private static volatile String sessionCookie;

    private RestApiBuilder() {
    }

    /** Логинится по форме один раз и кэширует сессионную куку. */
    @Step("API: авторизация на /login (один раз, куки кэшируются)")
    private static synchronized String session() {
        if (sessionCookie == null) {
            String cookie = io.restassured.RestAssured.given()
                    .baseUri(BASE_URL)
                    .redirects().follow(false)
                    .contentType(ContentType.URLENC)
                    .formParam("username", USER)
                    .formParam("password", PASSWORD)
                    .post(LOGIN)
                    .then()
                    .statusCode(302)
                    .extract().response()
                    .getCookie("JSESSIONID");

            if (cookie == null) {
                throw new IllegalStateException(
                        "Логин на /login не вернул JSESSIONID — проверьте поля формы и имя куки");
            }
            sessionCookie = cookie;
        }
        return sessionCookie;
    }

    @Step("API: подготовка спецификации запроса с сессией")
    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addCookie("JSESSIONID", session())
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }
}
