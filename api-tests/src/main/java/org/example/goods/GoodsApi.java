package org.example.goods;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.common.api.BasicApi;
import org.example.common.api.RestApiBuilder;

import java.util.List;
import java.util.Map;

import static org.example.common.api.ApiAssert.statusCode;

public class GoodsApi extends BasicApi {

    public GoodsApi() {
        super(RestApiBuilder.requestSpecification());
    }

    /* --- Эндпоинты --- */

    @Step("API: получить список товаров")
    public Response listGoods() {
        return get(RestApiBuilder.GOODS_LIST);
    }

    @Step("API: получить список товаров, page={page}, size={size}")
    public Response listGoods(int page, int size) {
        return get(RestApiBuilder.GOODS_LIST, Map.of("page", page, "size", size));
    }

    @Step("API: создать товар «{name}» с ценой {price}")
    public Response addGood(String name, double price) {
        return post(RestApiBuilder.GOODS_ADD, Map.of("name", name, "price", price));
    }

    @Step("API: получить товар id={id}")
    public Response getGood(long id) {
        return get(RestApiBuilder.GOODS_BY_ID, id);
    }

    @Step("API: изменить товар id={id}: имя «{name}», цена {price}")
    public Response patchGood(long id, String name, double price) {
        return patch(RestApiBuilder.GOODS_BY_ID, id, Map.of("name", name, "price", price));
    }

    @Step("API: удалить товар id={id}")
    public Response deleteGood(long id) {
        return delete(RestApiBuilder.GOODS_BY_ID, id);
    }

    /* --- Служебные операции над данными  --- */

    @Step("API: создание товара «{name}» с ценой {price}")
    public long createGood(String name, double price) {
        Response response = addGood(name, price);
        statusCode(response, 200);
        Map<String, Object> data = response.jsonPath().getMap("data");
        return ((Number) data.values().iterator().next()).longValue();
    }

    @Step("API: поиск id товара по имени «{name}»")
    public Long findGoodId(String name) {
        try {
            List<Map<String, Object>> goods = listGoods().then().extract().jsonPath().getList("goods");
            for (Map<String, Object> g : goods) {
                if (name.equals(g.get("name"))) {
                    return ((Number) g.get("id")).longValue();
                }
            }
        } catch (Exception e) {
            System.out.println("findGoodId: не удалось получить список товаров: " + e.getMessage());
        }
        return null;
    }

    @Step("API: очистить список товаров (удалить все)")
    public void deleteAllGoods() {
        List<Map<String, Object>> goods = listGoods().then()
                .statusCode(200)
                .extract().jsonPath().getList("goods");
        for (Map<String, Object> good : goods) {
            deleteGood(((Number) good.get("id")).longValue());
        }
    }

    /** Уникальное имя товара, чтобы тесты не мешали друг другу. */
    public static String uniqueName(String prefix) {
        return prefix + " " + System.nanoTime();
    }
}
