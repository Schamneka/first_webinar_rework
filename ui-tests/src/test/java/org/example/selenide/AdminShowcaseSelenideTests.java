package org.example.selenide;

import org.example.common.config.ConfigParser;
import org.example.goods.GoodsApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AdminShowcaseSelenideTests extends BaseSelenideTest {

    private final GoodsApi api = new GoodsApi();

    private String createdName = "";
    private Long createdId = null;

    /* --- 2.1. Добавить товар через админку, проверить на витрине --- */

    @Test
    void addGoodViaAdmin_goodIsShownOnShowcase() {
        createdName = ConfigParser.getProperty("product.name") + " " + System.nanoTime();

        open(BASE_URL + "/login");
        $("#username").setValue(ConfigParser.getProperty("admin.login"));
        $("#password").setValue(ConfigParser.getProperty("admin.password"));
        $("button[type='submit']").click();

        $("#n-name").setValue(createdName);
        $("#n-price").setValue(ConfigParser.getProperty("product.price"));
        $("#add-btn").click();

        open(BASE_URL);

        // карточка на витрине: имя в data-name, id в data-id
        String cardId = $("div.product-card[data-name='" + createdName + "']")
                .shouldBe(visible)
                .getAttribute("data-id");
        createdId = Long.parseLong(cardId);
    }

    /* --- 3.6. Удаляется добавленный товар (через API) --- */

    @AfterEach
    void cleanupCreatedGood() {
        if (createdId != null) {
            api.deleteGood(createdId);
        }
    }
}

