package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.page.DebitPage;
import ru.netology.page.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DBHelper.deleteAllDB;
import static ru.netology.data.DataHelper.*;


public class TestDebitCard {

    @BeforeAll
    static void addListenerAndHeadless() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
    }

    @AfterAll
    static void removeListener() {
        SelenideLogger.removeListener("AllureSelenide");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
        new HomePage().checkHomePageIsOpened();
    }

    @AfterEach
    void setDown() {
        deleteAllDB();
    }


    @Test
    @DisplayName("Должен успешно оплатить с одобренной дебетовой картой")
    void shouldSuccessPayWithApprovedDebitCard() {
        successDebitPage().enterValidUserWithApprovedCard();
        assertEquals("APPROVED", DBHelper.getStatusDebitCard());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с отклоненной дебетовой картой")
    void shouldReturnFailWithDeclinedDebitCard() {
        successDebitPage().enterValidUserWithIncorrectCard(ValidData(getDeclinedCard()));
        assertEquals("DECLINED", DBHelper.getStatusDebitCard());
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми пустыми полями")
    void shouldReturnErrorsWithEmptyAllDebit() {
        successDebitPage().enterInputs(BlankFields());
        errorsDisplayDebit();
    }

    @Test
    @DisplayName("Должен вернуть ошибку с незаполненным полем карта")
    void shouldReturnErrorWithEmptyDebitCard() {
        successDebitPage().enterIncorrectCardInput(CardNumberIsNotFilledIn(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со значением поля Карты менее 16 цифр")
    void shouldReturnErrorWithCardUnderLimitDebit() {
        successDebitPage().enterIncorrectCardInput(TheCardNumberIsLessThanSixteenDigits(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с незаполненным полем месяц")
    void shouldReturnErrorWithEmptyMonthDebit() {
        successDebitPage().enterIncorrectMonthInput(BlankFieldForTheMonth(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со значением '4' в поле месяц")
    void shouldReturnErrorsWithSingleDigitInMonthFieldDebit() {
        successDebitPage().enterIncorrectMonthInput(TheMonthFieldContainsOneDigit(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем месяц")
    void shouldReturnErrorWithInvalidMonthDebit() {
        successDebitPage().enterIncorrectMonthInput(TheMonthFieldContainsNumberGreaterThanTwelve(), "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с нулевым месяцем")
    void shouldReturnErrorWithMonthZeroDebit() {
        successDebitPage().enterIncorrectMonthInput(TheMonthFieldContainsTwoZeros(), "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с незаполненным полем год")
    void shouldReturnErrorWithEmptyYearDebit() {
        successDebitPage().enterIncorrectYearInput(BlankYearField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем год")
    void shouldReturnErrorWithInvalidYearDebit() {
        successDebitPage().enterIncorrectYearInput(TheYearFieldContainsOneArabicDigit(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с ушедшим годом")
    void shouldReturnErrorWithYearGoneCreditCard() {
        successDebitPage().enterIncorrectYearInput(TheYearFieldContainsThePastYear(), "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с будущим годом")
    void shouldReturnErrorWithYearNextCreditCard() {
        successDebitPage().enterIncorrectYearInput(TheYearFieldContainsTheNextYear(), "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со значением '00' поля год")
    void shouldReturnErrorsWithYearZeroInputsDebit() {
        successDebitPage().enterIncorrectYearInput(TheYearFieldContainsZeros(), "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем имени")
    void shouldReturnErrorWithEmptyNameDebit() {
        successDebitPage().enterIncorrectNameInput(BlankOwnerField(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со множеством букв при заполнении поля Владелец")
    void ShouldReturnAnErrorWithMultipleLetters() {
        successDebitPage().enterIncorrectNameInput(TheOwnerFieldContainsManyLetters(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с большим количеством пробелов в  поле Владелец")
    void ShouldReturnAnErrorWithALotOfSpacesInTheOwnerField() {
        successDebitPage().enterIncorrectNameInput(TheOwnerFieldContainsLotOfSpaces(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с именем на русском языке")
    void shouldReturnErrorWithNameInRussianInOwnerFieldDebit() {
        successDebitPage().enterIncorrectNameInput(TheOwnerFieldContainsRussianLetters(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с цыфрами в поле владелец")
    void shouldReturnFailWithTsyfryInOwnerFieldDebit() {
        successDebitPage().enterIncorrectNameInput(TheOwnerFieldContainsArabicNumerals(), "Неверный формат");
    }


    @Test
    @DisplayName("Должен вернуть ошибку при заполнении спец.символами в поле владелец")
    void shouldReturnFailWithCharactersInOwnerFieldDebit() {
        successDebitPage().enterIncorrectNameInput(TheOwnerFieldContainsSpecialCharacters(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем CVC/CVV")
    void shouldReturnErrorWithEmptyCodeDebit() {
        successDebitPage().enterIncorrectCodeInput(BlankCvcField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку при заполнении одной цифрой в поле CVC/CVV")
    void shouldReturnErrorWithSingleDigitInFieldCvcDebit() {
        successDebitPage().enterIncorrectCodeInput(TheCvcFieldContainsOneArabicDigit(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку при заполнении двумя цифрами в поле CVC/CVV")
    void shouldReturnErrorWithTwoDigitsInFieldCvcDebit() {
        successDebitPage().enterIncorrectCodeInput(TheCvcFieldContainsTwoArabicNumerals(), "Неверный формат");
    }

    private DebitPage successDebitPage() {
        new HomePage().openDebitForm().successOpenPage();
        return new DebitPage();
    }

    private void errorsDisplayDebit() {
        new DebitPage().errorsDisplay("Неверный формат", "Неверный формат", "Неверный формат", "Поле обязательно для заполнения", "Неверный формат");
    }
}


