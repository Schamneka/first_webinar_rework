package org.example.common.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class BasicApi {

    protected final RequestSpecification spec;

    protected BasicApi(RequestSpecification spec) {
        this.spec = spec;
    }

    @Step("API: GET {path}")
    public Response get(String path) {
        return given().spec(spec).when().get(path);
    }

    @Step("API: GET {path} (id={id})")
    public Response get(String path, long id) {
        return given().spec(spec).pathParam("id", id).when().get(path);
    }

    @Step("API: GET {path} с query-параметрами {params}")
    public Response get(String path, Map<String, Object> params) {
        return given().spec(spec).pathParams(params).when().get(path);
    }

    @Step("API: POST {path}")
    public Response post(String path, Object body) {
        return given().spec(spec).body(body).when().post(path);
    }

    @Step("API: PATCH {path} (id={id})")
    public Response patch(String path, long id, Object body) {
        return given().spec(spec).pathParam("id", id).body(body).when().patch(path);
    }

    @Step("API: DELETE {path} (id={id})")
    public Response delete(String path, long id) {
        return given().spec(spec).pathParam("id", id).when().delete(path);
    }
}
