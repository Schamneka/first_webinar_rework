package org.example;

import org.example.goods.GoodsApi;
import org.junit.jupiter.api.Tag;

@Tag("autotests")
public abstract class BaseApiTest {

    protected final GoodsApi api = new GoodsApi();
}
