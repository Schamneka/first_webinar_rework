package org.example.goods;

import org.example.BaseApiTest;
import org.junit.jupiter.api.Test;

import static org.example.common.api.ApiAssert.statusCode;

public class GoodsDeleteTests extends BaseApiTest {

    @Test
    void delete_existingGood_returns200AndReallyDeletes() {
        long id = api.createGood(GoodsApi.uniqueName("На удаление"), 30.0);

        var deleteResponse = api.deleteGood(id);
        statusCode(deleteResponse, 200);

        // контроль: после удаления товар недоступен
        var getResponse = api.getGood(id);
        statusCode(getResponse, 404);
    }

    @Test
    void delete_nonExistentGood_returns404() {
        var response = api.deleteGood(999_999_999);

        statusCode(response, 404);
    }
}
