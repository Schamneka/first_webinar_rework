package org.example.selenide;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.example.common.config.ConfigParser;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderSelenideTests extends BaseSelenideTest {

    /* --- 2.5. Корзина дороже 300 ₽, «Оформить заказ» -> JS Alert --- */

    @Test
    void orderOver300Roubles_showsAlert() {
        open(BASE_URL);

        int orderLimit = Integer.parseInt(ConfigParser.getProperty("order.limit"));
        int total = 0;
        int addedCount = 0;

        // Добавляем товары по одному, пока сумма не превысит порог
        for (SelenideElement card : $$("#products-list .product-card")) {
            if (total > orderLimit) {
                break;
            }
            total += Integer.parseInt(card.attr("data-price"));
            card.$("button[data-action='add-to-cart']").shouldBe(visible).click();
            addedCount++;
            // ждём, пока счётчик отреагирует на конкретно этот клик
            cartCounter.shouldNotHave(exactText(String.valueOf(addedCount - 1)));
        }

        assertTrue(total > orderLimit,
                "В корзину должно быть добавлено товаров на сумму больше " + orderLimit + " ₽");

        // Открываем корзину
        cartButton.shouldBe(visible).click();
        cartItemsContainer.shouldBe(visible);

        // Нажимаем кнопку «Оформить заказ»
        $(byText("Оформить заказ")).shouldBe(visible).click();

        // Ждём JS Alert, читаем текст и подтверждаем
        String alertText = Selenide.switchTo().alert().getText();
        System.out.println("Текст алерта: " + alertText);
        assertTrue(alertText != null && !alertText.isEmpty(), "Алерт должен появиться с текстом");
        sleep(3000);
        Selenide.switchTo().alert().accept();
    }
}
