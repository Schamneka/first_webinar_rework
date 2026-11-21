package org.example.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.example.common.config.ConfigParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Tag("autotests")
@Tag("selenide")
public abstract class BaseSelenideTest {

    protected static final String BASE_URL;

    /* --- Локаторы витрины и корзины --- */

    protected final SelenideElement productCard = $("#products-list .product-card");
    protected final SelenideElement firstAddToCartBtn =
            $("#products-list .product-card button[data-action='add-to-cart']");
    protected final SelenideElement cartButton = $("#open-cart-btn");
    protected final SelenideElement cartItemsContainer = $("#cart-items");
    protected final SelenideElement cartCounter = $("#cart-count");

    static {
        BASE_URL = ConfigParser.getProperty("base.url");

        Configuration.baseUrl = BASE_URL;
        Configuration.timeout = Long.parseLong(ConfigParser.getProperty("element.timeout"));
    }

    @BeforeAll
    static void setupAllureListener() {
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
    }

    @BeforeAll
    static void printConfig() {
        System.out.println("=== Параметры тестового окружения ===");
        System.out.println("URL стенда:        " + ConfigParser.getProperty("base.url"));
        System.out.println("Тайм-аут, мс:      " + ConfigParser.getProperty("element.timeout"));
        System.out.println("Режим логирования: " + ConfigParser.getProperty("logging.mode"));
        System.out.println("Стартовый товар:   «" + ConfigParser.getProperty("product.name")
                + "», цена " + ConfigParser.getProperty("product.price"));
    }

    protected int cartItemsCount() {
        return $$("#cart-items .cart-item").size();
    }
}
