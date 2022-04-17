package ru.evot;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FirstParamsTests {

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void testSetUp() {
        open("/text-box");
    }

    Faker faker = new Faker();

    @ParameterizedTest(name = "Проверка ввода валидного имени {0}")
    @ValueSource(strings = {"Petya", "Маша", "PETYA", "маша"})
    void inputName(String setName) {
        $("#userName").setValue(setName);
        $("#submit").click();
        $("#output").$("#name").shouldHave(text(setName));
    }

    @ParameterizedTest(name = "Проверка ввода невалидного имени {0}")
    @ValueSource(strings = {"Petya1%", "1245%#", " "})
    void inputIncorrectName(String setIncorrectName) {
        $("#userName").setValue(setIncorrectName);
        $("#submit").click();
        $("#output").shouldNotBe(visible);
    }

    @ParameterizedTest(name = "Проверка валидного e-mail {0}. Выводится {1}")
    @CsvSource(value = {"test@gmail.com, Email:test@gmail.com",
            "test-test@gmail.com, Email:test-test@gmail.com",
            "test.test@gmail.com, Email:test.test@gmail.com"})
    void inputEmail(String setEmail, String outEmail) {
        $("#userEmail").setValue(setEmail);
        $("#submit").click();
        $("#output").$("#email").shouldHave(text(outEmail));
    }

    @ParameterizedTest(name = "Проверка невалидного e-mail {0}." +
            "Поле подсвечено красным и указан невалидный Email {1}")
    @CsvSource(value = {"testgmail.com, testgmail.com", "test@gmail, test@gmail",
            "@gmail.com, @gmail.com"})
    void inputIncorrectEmail(String setEmail, String outEmail) {
        $("#userEmail").setValue(setEmail);
        $("#submit").click();
        $(".field-error").shouldHave(value(outEmail));
        $("#output").shouldNotBe(visible);
    }

    @ParameterizedTest(name = "Проверка ввода и вывода полного адреса латиницей {0}")
    @MethodSource("AddressSource")
    void inputAddress(String setAddress, String outAddress) {
        $("#currentAddress").setValue(setAddress);
        $("#submit").click();
        $("#output").$("#currentAddress").shouldHave(text(outAddress));
    }

    static Stream<Arguments> AddressSource() {
        return Stream.of(
                Arguments.of("USA, Tillmanbury, 97024 Mohammed Meadow",
                        "Current Address :USA, Tillmanbury, 97024 Mohammed Meadow"),
                Arguments.of("Spain, Cervántez de San Pedro, Calle Saldaña, 536, Bajo 3º",
                        "Current Address :Spain, Cervántez de San Pedro, Calle Saldaña, 536, Bajo 3º"));
    }

    @ParameterizedTest(name = "Проверка ввода и вывода полного адреса кириллицей {0}")
    @MethodSource("AddressSourceRus")
    void inputAddressRus(String setAddress, String outAddress) {
        $("#currentAddress").setValue(setAddress);
        $("#submit").click();
        $("#output").$("#currentAddress").shouldHave(text(outAddress));
    }

    static Stream<Arguments> AddressSourceRus() {
        return Stream.of(
                Arguments.of("Российская Федерация, г. Москва, " +
                                "ул. Фрунзе, д. 15, индекс 300000",
                        "Current Address :Российская Федерация, г. Москва, " +
                                "ул. Фрунзе, д. 15, индекс 300000"),
                Arguments.of("Российская Федерация, г. Казань, " +
                                "ул. Аметхана Султана, д. 25, индекс 500000",
                        "Current Address :Российская Федерация, г. Казань, " +
                                "ул. Аметхана Султана, д. 25, индекс 500000"));
    }

}
