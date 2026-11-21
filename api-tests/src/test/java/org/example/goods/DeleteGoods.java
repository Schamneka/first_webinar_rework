package org.example.goods;

import org.example.BaseApiTest;
import org.junit.jupiter.api.Test;

public class DeleteGoods extends BaseApiTest {

    /* --- 1.1. Удаление товаров с id < 150 --- */
    @Test
    void deleteGoods() {
        for (int i = 1; i < 150; i++) {
            api.deleteGood(i);
        }
    }
}
