package org.example.goods;

import org.example.BaseApiTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.example.common.api.ApiAssert.jsonFieldPresent;
import static org.example.common.api.ApiAssert.statusCode;

public class GoodsListTests extends BaseApiTest {

    @Test
    @Tag("smoke")
    void list_withoutParams_returns200() {
        var response = api.listGoods();

        statusCode(response, 200);
        jsonFieldPresent(response, "goods");
    }

    @Test
    void list_withPagination_returns200() {
        var response = api.listGoods(0, 5);

        statusCode(response, 200);
        jsonFieldPresent(response, "goods");
    }
}
