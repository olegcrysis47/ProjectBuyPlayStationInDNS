package ru.aristovo.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.aristovo.base.BaseTests;

@DisplayName("Тестируем магазин DNS")
public class ShopDNSTest extends BaseTests {

    @Test
    @DisplayName("Тест покупки PlayStation и пары дисков с играми")
    void buyPlayStationAndGames() {
        sleepMyThread(5000);
    }

    void sleepMyThread(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}