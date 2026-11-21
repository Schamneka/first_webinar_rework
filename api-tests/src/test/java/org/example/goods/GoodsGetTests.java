package org.example.goods;

import org.example.BaseApiTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.example.common.api.ApiAssert.bodyContains;
import static org.example.common.api.ApiAssert.statusCode;

public class GoodsGetTests extends BaseApiTest {

    @Test
    @Tag("smoke")
    void get_existingGood_returns200WithSameName() {
        String name = GoodsApi.uniqueName("Товар для GET");
        long id = api.createGood(name, 100.0);

        var response = api.getGood(id);

        statusCode(response, 200);
        bodyContains(response, name);
    }

    @Test
    @Tag("smoke")
    void get_nonExistentGood_returns404() {
        var response = api.getGood(999_999_999);
        statusCode(response, 404);
    }
}
