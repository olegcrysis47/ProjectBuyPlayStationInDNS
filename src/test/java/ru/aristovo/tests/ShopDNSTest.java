package ru.aristovo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ru.aristovo.base.BaseTests;

@DisplayName("Тестируем магазин DNS")
public class ShopDNSTest extends BaseTests {

    @Test
    @DisplayName("Тест покупки PlayStation и пары дисков с играми")
    void buyPlayStationAndGames() {

        //здесь будет выполнен поэтапно тест
        // 2. в поиске найти playstation
        productSearchOnSite("playstation");

        sleepMyThread(5000);

        productSearchOnSite("detroit");

        sleepMyThread(6000);

        /*

        3. кликнуть по playstation 4 slim black
        4. запомнить цену
        5. Доп.гарантия - выбрать 2 года
        6. дождаться изменения цены и запомнить цену с гарантией
        7. Нажать Купить
        8. выполнить поиск Detroit
        9. запомнить цену
        10. нажать купить
        11. проверить что цена корзины стала равна сумме покупок
        12. перейри в корзину
        13. проверить, что для приставки выбрана гарантия на 2 года
        14. проверить цену каждого из товаров и сумму
        15. удалить из корзины Detroit
        16. проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
        17. добавить еще 2 playstation (кнопкой +) и проверить что сумма верна (равна трем ценам playstation)
        18. нажать вернуть удаленный товар, проверить что Detroit появился в корзине и сумма увеличилась на его значение
         */

    }

    /**
     * Искусственная задержка, чтобы посмотреть результат работы теста на сайте, либо притормозить драйвер.
     * @param i - время в миллисекундах для задержки.
     */
    void sleepMyThread(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод на сайте вводит в поисковое поле наименование товара.
     * Затем проверяет, правильно ли поле было заполнено.
     * В конце нажимает Enter.
     * @param productName - подается товар, который требуется найти
     */
    void productSearchOnSite(String productName) {
        String productSearchFieldXPath = "//input[@placeholder='Поиск по сайту']";
        WebElement productSearchField = driver.findElement(By.xpath(productSearchFieldXPath));
        productSearchField.clear();
        productSearchField.click();
        productSearchField.sendKeys(productName);

        Assertions.assertEquals(productName,
                productSearchField.getAttribute("value"),
                "В поле поиска пришла НЕ ПРАВИЛЬНАЯ строка");

        productSearchField.sendKeys(Keys.ENTER);
    }

}