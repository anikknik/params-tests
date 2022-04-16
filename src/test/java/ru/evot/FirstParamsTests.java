package ru.evot;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FirstParamsTests {

    @BeforeAll
    static void setUp() {
        Configuration.holdBrowserOpen = true;
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }


    @ParameterizedTest(name =  "Проверка e-mail с {0}, ожидаем успешную регистрацию")
    @ValueSource(strings = {"Petya", "Vasya", "Galya", "Unknown"})
    void inputName (String setData) {
        open("/automation-practice-form");
        $("#firstName").setValue(setData);
        $("#submit").click();
        $(".was-validated").shouldHave(text(setData));
    }

    @CsvSource(value = {"Vasya, Vasya"})
    @ParameterizedTest(name =  "Проверка e-mail с {0}, ожидаем результат с {1} и успешную регистрацию")
    void inputName2 (String setData, String userName) {
        open("/automation-practice-form");
        $("#firstName").setValue(setData);
        $("#submit").click();
        $(".was-validated").shouldHave(text(setData));
    }

    static Stream<Arguments> methodSourceName() {
        return Stream.of(
                Arguments.of("Vasya", "Vasya"));
    }

    @MethodSource("methodSourceName")
    @ParameterizedTest(name =  "Проверка e-mail с {0}, ожидаем результат с {1} и успешную регистрацию")
    void methodSourceName (String setData, String userName) {
        open("/automation-practice-form");
        $("#firstName").setValue(setData);
        $("#submit").click();
        $(".was-validated").shouldHave(text(setData));
    }
}
