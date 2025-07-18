package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    String planningDate = generateData(3);

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    private String generateData(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldCardDelivery() {
        $("[data-test-id='city'] input").setValue("Челябинск");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Иванов-Петров Николай");
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }
}