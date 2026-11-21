package org.example.admin;

import org.example.goods.GoodsApi;
import org.example.selenide.BaseSelenideTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.MainPage;
import org.example.common.config.ConfigParser;

public class AdminAddGoodPageObjectTests extends BaseSelenideTest {

    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();
    private final GoodsApi api = new GoodsApi();

    private Long createdId = null;

    /* --- 2.4. Добавить товар через админку, проверить на витрине --- */

    @Test
    void addGoodViaAdmin_goodIsShownOnShowcase() {
        String name = ConfigParser.getProperty("product.name") + " " + System.nanoTime();
        String price = ConfigParser.getProperty("product.price");

        createdId = loginPage.openPage(BASE_URL)
                .enterUsername(ConfigParser.getProperty("admin.login"))
                .enterPassword(ConfigParser.getProperty("admin.password"))
                .clickSignIn()
                .addGood(name, price)
                .goodIdFromTable(name);      // id достаём, пока мы в админке

        mainPage.openPage(BASE_URL)
                .assertThat()
                .productCardVisible(name, price);
    }

    /* --- Удаляется добавленный товар (через API) --- */

    @AfterEach
    void cleanupCreatedGood() {
        if (createdId != null) {
            api.deleteGood(createdId);
        }
    }
}
