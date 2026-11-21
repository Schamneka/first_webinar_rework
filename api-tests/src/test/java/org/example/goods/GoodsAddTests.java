package org.example.goods;

import org.example.BaseApiTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.example.common.api.ApiAssert.jsonFieldPresent;
import static org.example.common.api.ApiAssert.statusCode;

public class GoodsAddTests extends BaseApiTest {

    @Test
    @Tag("smoke")
    void add_validGood_returns200AndId() {
        var response = api.addGood(GoodsApi.uniqueName("Ноутбук"), 1500.0);

        statusCode(response, 200);
        jsonFieldPresent(response, "data");
        jsonFieldPresent(response, "message");
    }

    /** Валидация: без имени товар не создаётся. */
    @Test
    void add_missingName_returns400() {
        var response = api.post(RestApiBuilderPaths.GOODS_ADD, java.util.Map.of("price", 100.0));

        statusCode(response, 400);
    }

    /** price имеет ограничение minimum: 0 — отрицательная цена должна отклоняться. */
    @Test
    void add_negativePrice_returns400() {
        var response = api.addGood(GoodsApi.uniqueName("Отрицательный"), -5.0);

        statusCode(response, 400);
    }

    /** Утилитарный класс путей */
    private static final class RestApiBuilderPaths {
        static final String GOODS_ADD = org.example.common.api.RestApiBuilder.GOODS_ADD;
    }
}
