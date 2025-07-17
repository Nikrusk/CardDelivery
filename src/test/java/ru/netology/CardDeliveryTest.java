package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    private String generateData(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateData(3);

    @Test
    void ShouldCardDelivery() {
        $("[data-test-id=city] input").setValue("Челябинск");
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Иванов-Петров Николай");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $x(".//span[contains(text(),'Забронировать')]").click();
        $("[data-test-id=notification]").should(visible, ofSeconds(15));
        $("[data-test-id=notification]").$x(".//div[@class='notification__title']").should(text("Успешно!"));
        $("[data-test-id=notification]").$x(".//div[@class='notification__content']").should(text("Встреча успешно забронирована на " + planningDate));
        $("[data-test-id=notification]").$x(".//button").click();
        $("[data-test-id=notification]").should(hidden);
    }
}