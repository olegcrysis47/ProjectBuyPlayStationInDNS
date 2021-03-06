package ru.aristovo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.aristovo.base.BaseTests;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Тестируем магазин DNS")
public class ShopDNSTest extends BaseTests {

    // переменные
    static int sumBasket;           // сумма корзины
    static int sumPSNotGuarantee;   // сумма PS без гарантии
    static int sumPSWithGuarantee;  // сумма PS с гарантией
    static int sumDetroit;          // сумма диска Детроит
    static int sumBloodborne;       // сумма диска bloodborne


    @Test
    @DisplayName("Тест покупки PlayStation и пары дисков с играми")
    void buyPlayStationAndGames() {

        //здесь будет выполнен поэтапно тест
        // 2. в поиске найти playstation
        productSearchOnSite("playstation");

        // 3. кликнуть по playstation 4 slim black
        productSelectOnSite("playstation 4 slim black");

        // ПОКА ИДЕМ СТРОГО ПО ШАБЛОНУ
        // ВОЗМОЖНО можно будет создать дополнительный класс "ТОВАР"
        // 4. запомнить цену
        String psPriceXPath = "//div//span[@class='product-card-price__current']";
        WebElement psPrice = driver.findElement(By.xpath(psPriceXPath));
        waitUtilElementToBeVisible(psPrice);
        sumPSNotGuarantee = Integer.parseInt(psPrice.getText().replaceAll("\\W", ""));

        // 5. Доп.гарантия - выбрать 2 года
        String guaranteeTwoYearXPath = "//select//option[@value='1']";
        WebElement guaranteeTwoYear = driver.findElement(By.xpath(guaranteeTwoYearXPath));
        waitUtilElementToBeVisible(guaranteeTwoYear);
        waitUtilElementToBeClickable(guaranteeTwoYear);
        guaranteeTwoYear.click();

        // 6. дождаться изменения цены и запомнить цену с гарантией
        String psPriceWithGurXPath =
                "//div//span[@class='product-card-price__current product-card-price__current_active']";
        WebElement psPriceWithGur = driver.findElement(By.xpath(psPriceWithGurXPath));
        waitUtilElementToBeVisible(psPriceWithGur);
        sumPSWithGuarantee = Integer.parseInt(psPriceWithGur.getText().replaceAll("\\W", ""));

        // проверка, что цена с гарантией отличается без гарантии
        Assertions.assertNotEquals(sumPSWithGuarantee, sumPSNotGuarantee,
                "После включения гарантии сумма НЕ ИЗМЕНИЛАСЬ!");

        // 7. Нажать Купить
        String buttonBuyPSXPath = "//button[contains(.,'Купить')]";
        WebElement buttonBuyPS = driver.findElement(By.xpath(buttonBuyPSXPath));
        waitUtilElementToBeVisible(buttonBuyPS);
        waitUtilElementToBeClickable(buttonBuyPS);
        buttonBuyPS.click();

        sumBasket += sumPSWithGuarantee;

        // 8. выполнить поиск Detroit
        productSearchOnSite("Detroit");

        // 9. запомнить цену Detroit
        String detPriceXPath = "//div//span[@class='product-card-price__current']";
        WebElement detPrice = driver.findElement(By.xpath(detPriceXPath));
        waitUtilElementToBeVisible(detPrice);
        sumDetroit = Integer.parseInt(detPrice.getText().replaceAll("\\W", ""));

        // 10. нажать купить Detroit
        String buttonBuyDetXPath = "//button[contains(.,'Купить')]";
        WebElement buttonBuyDet = driver.findElement(By.xpath(buttonBuyDetXPath));
        waitUtilElementToBeVisible(buttonBuyDet);
        waitUtilElementToBeClickable(buttonBuyDet);
        buttonBuyDet.click();

        sumBasket += sumDetroit;

        // дополнительно покупка bloodborne
        // поиск bloodborne
        productSearchOnSite("bloodborne");
        // выбираем версию игры
        productSelectOnSite("Хиты PlayStation");
        // запоминаем цену bloodborne
        String blPriceXPath = "//div//span[@class='product-card-price__current']";
        WebElement blPrice = driver.findElement(By.xpath(blPriceXPath));
        waitUtilElementToBeVisible(blPrice);
        sumBloodborne = Integer.parseInt(blPrice.getText().replaceAll("\\W", ""));
        // покупаем bloodborne
        String buttonBuyBlXPath = "//button[contains(.,'Купить')]";
        WebElement buttonBuyBl = driver.findElement(By.xpath(buttonBuyBlXPath));
        waitUtilElementToBeVisible(buttonBuyBl);
        waitUtilElementToBeClickable(buttonBuyBl);
        buttonBuyBl.click();

        sumBasket += sumBloodborne;

        sleepMyThread(2000); // задержка, чтобы корзина на экране успела обновиться

        // 11. проверить что цена корзины стала равна сумме покупок
        String basketPriceXPath = "//a//span[@class='cart-link__price']";
        WebElement basketPrice = driver.findElement(By.xpath(basketPriceXPath));

        Assertions.assertEquals(sumBasket,
                Integer.parseInt(basketPrice.getText().replace(" ", "")),
                "Сумма в корзине не соответствует сумме добавленных товаров");

        // 12. перейри в корзину
        String selectButtonBasketXPath = "//a[@data-commerce-target='CART']";
        WebElement selectButtonBasket = driver.findElement(By.xpath(selectButtonBasketXPath));
        waitUtilElementToBeVisible(selectButtonBasket);
        selectButtonBasket.click();

        // формируем лист с товарами из корзины
        String basketListProductXPath = "//div[@class='cart-items__product']";
        List<WebElement> basketListProduct =
                (ArrayList<WebElement>) driver.findElements(By.xpath(basketListProductXPath));
        for (WebElement w : basketListProduct) {
            WebElement titleProduct = w.findElement(By.xpath(".//div[@class='cart-items__product-name']//a"));
            if (titleProduct.getText().toLowerCase().contains("playstation")) {
                WebElement gurTwoYears = w.findElement(By.xpath(".//div[@data-commerce-target='basket_additional_warranty_24']"));
                waitUtilElementToBeVisible(gurTwoYears);
                Assertions.assertEquals("base-ui-radio-button__icon base-ui-radio-button__icon_checked",
                        gurTwoYears.getAttribute("class"),
                        "Кнопка НЕ УСТАНОВЛЕНА на гарантии 2 года!");
            }
        }

        sleepMyThread(5000);

        /*
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
     * Метод ждет когда WEB-элемент станет кликабельным.
     * @param element - WEB-элемент, на который нужно будет кликнуть.
     */
    void waitUtilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void waitUtilElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void waitUtilElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Метод для выбора нужной позиции, после поиска на сайте, когда мы видим список доступных товаров.
     * @param productName - частичное или полное наименование искомого продукта.
     */
    void productSelectOnSite(String productName) {
        WebElement titleProduct;
        String productSelectXPath = "//div[@class='n-catalog-product__main']";
        List<WebElement> webList = new ArrayList<WebElement>();
        webList = (ArrayList<WebElement>) driver.findElements(By.xpath(productSelectXPath));
        for (WebElement w : webList) {
            titleProduct = w.findElement(By.xpath(".//a[@class='ui-link']"));
            if (titleProduct.getText().toLowerCase().contains(productName.toLowerCase())) {
                waitUtilElementToBeClickable(titleProduct);
                titleProduct.click();
                break;
            }
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
        waitUtilElementToBeVisible(productSearchField);
        productSearchField.clear();
        productSearchField.click();
        productSearchField.sendKeys(productName);

        Assertions.assertEquals(productName,
                productSearchField.getAttribute("value"),
                "В поле поиска пришла НЕ ПРАВИЛЬНАЯ строка");

        productSearchField.sendKeys(Keys.ENTER);
    }

}