package org.example.goods;

import org.example.BaseApiTest;
import org.junit.jupiter.api.Test;

import static org.example.common.api.ApiAssert.statusCode;

public class GoodsPatchTests extends BaseApiTest {

    @Test
    void patch_existingGood_returns200() {
        long id = api.createGood(GoodsApi.uniqueName("До патча"), 100.0);

        var response = api.patchGood(id, GoodsApi.uniqueName("После патча"), 200.0);

        statusCode(response, 200);
    }

    /** PATCH проверяет уникальность имени — дубль должен давать 400. */
    @Test
    void patch_duplicateName_returns400() {
        String takenName = GoodsApi.uniqueName("Занятое имя");
        api.createGood(takenName, 50.0);
        long otherId = api.createGood(GoodsApi.uniqueName("Второй товар"), 60.0);

        var response = api.patchGood(otherId, takenName, 60.0);

        statusCode(response, 400);
    }

    @Test
    void patch_nonExistentGood_returns404() {
        var response = api.patchGood(999_999_999, GoodsApi.uniqueName("Никто не найдёт"), 10.0);

        statusCode(response, 404);
    }
}
