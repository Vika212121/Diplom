package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DBHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.HomePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DBHelper.deleteAllDB;
import static ru.netology.data.DataHelper.*;


public class TestCreditCard {

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
    @DisplayName("Должен успешно оплатить с одобренной кредитной картой в форме кредита")
    void shouldSuccessPayWithApprovedCreditCard() {
        successCreditPage().enterValidUserWithApprovedCard();
        assertEquals("APPROVED", DBHelper.getStatusCreditCard());
    }

    @Test
    @DisplayName("Должен вернуть ошибку с отклоненной кредитной картой")
    void shouldReturnFailWithDeclinedCreditCard() {
        successCreditPage().enterValidUserWithIncorrectCard(ValidData(getDeclinedCard()));
        assertEquals("DECLINED", DBHelper.getStatusCreditCard());
    }

    @Test
    @DisplayName("Должен вернуть ошибку со всеми пустыми полями")
    void shouldReturnErrorsWithEmptyAllCredit() {
        successCreditPage().enterInputs(BlankFields());
        errorsDisplayCredit();
    }

    @Test
    @DisplayName("Должен вернуть ошибку с незаполненным полем карта")
    void shouldReturnErrorWithEmptyCreditCard() {
        successCreditPage().enterIncorrectCardInput(CardNumberIsNotFilledIn(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со значением поля Карты менее 16 цифр")
    void shouldReturnErrorWithCardUnderLimitCredit() {
        successCreditPage().enterIncorrectCardInput(TheCardNumberIsLessThanSixteenDigits(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с незаполненным полем месяц")
    void shouldReturnErrorWithEmptyMonthCreditCard() {
        successCreditPage().enterIncorrectMonthInput(BlankFieldForTheMonth(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со значением '4' в поле месяц")
    void shouldReturnErrorsWithSingleDigitInMonthFieldCreditCard() {
        successCreditPage().enterIncorrectMonthInput(TheMonthFieldContainsOneDigit(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем месяц")
    void shouldReturnErrorWithInvalidMonthCreditCard() {
        successCreditPage().enterIncorrectMonthInput(TheMonthFieldContainsNumberGreaterThanTwelve(), "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с нулевым месяцем")
    void shouldReturnErrorWithMonthZeroCreditCard() {
        successCreditPage().enterIncorrectMonthInput(TheMonthFieldContainsTwoZeros(), "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с незаполненным полем год")
    void shouldReturnErrorWithEmptyYearCreditCard() {
        successCreditPage().enterIncorrectYearInput(BlankYearField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с некорректным полем год")
    void shouldReturnErrorWithInvalidYearCreditCard() {
        successCreditPage().enterIncorrectYearInput(TheYearFieldContainsOneArabicDigit(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с ушедшим годом")
    void shouldReturnErrorWithYearGoneCreditCard() {
        successCreditPage().enterIncorrectYearInput(TheYearFieldContainsThePastYear(), "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с будущим годом")
    void shouldReturnErrorWithYearNextCreditCard() {
        successCreditPage().enterIncorrectYearInput(TheYearFieldContainsTheNextYear(), "Неверно указан срок действия карты");
    }


    @Test
    @DisplayName("Должен вернуть ошибку со значением '00' поля год")
    void shouldReturnErrorsWithYearZeroInputsCreditCard() {
        successCreditPage().enterIncorrectYearInput(TheYearFieldContainsZeros(), "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем имени")
    void shouldReturnErrorWithEmptyNameCreditCard() {
        successCreditPage().enterIncorrectNameInput(BlankOwnerField(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Должен вернуть ошибку со множеством букв при заполнении поля Владелец")
    void shouldReturnErrorUserWithOneWordInOwnerFieldCreditCard() {
        successCreditPage().enterIncorrectNameInput(TheOwnerFieldContainsManyLetters(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с большим количеством пробелов в  поле Владелец")
    void shouldReturnErrorUserWithThreeWordsInOwnerFieldCreditCard() {
        successCreditPage().enterIncorrectNameInput(TheOwnerFieldContainsLotOfSpaces(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с именем на русском языке")
    void shouldReturnErrorWithNameInRussianInOwnerFieldCreditCard() {
        successCreditPage().enterIncorrectNameInput(TheOwnerFieldContainsRussianLetters(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с цыфрами в поле владелец")
    void shouldReturnFailWithNumbersInOwnerFieldDCreditCard() {
        successCreditPage().enterIncorrectNameInput(TheOwnerFieldContainsArabicNumerals(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку при заполнении спец.символами в поле владелец")
    void shouldReturnFailWithCharactersInOwnerFieldDCreditCard() {
        successCreditPage().enterIncorrectNameInput(TheOwnerFieldContainsSpecialCharacters(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку с пустым полем CVC/CVV")
    void shouldReturnErrorWithEmptyCodeCreditCard() {
        successCreditPage().enterIncorrectCodeInput(BlankCvcField(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку при заполнении одной цифрой в поле CVC/CVV")
    void shouldReturnErrorWithSingleDigitInFieldCvcCreditCard() {
        successCreditPage().enterIncorrectCodeInput(TheCvcFieldContainsOneArabicDigit(), "Неверный формат");
    }

    @Test
    @DisplayName("Должен вернуть ошибку при заполнении двумя цифрами в поле CVC/CVV")
    void shouldReturnErrorWithTwoDigitsInFieldCvcDCreditCard() {
        successCreditPage().enterIncorrectCodeInput(TheCvcFieldContainsTwoArabicNumerals(), "Неверный формат");
    }

    private CreditPage successCreditPage() {
        new HomePage().openCreditForm().successOpenPage();
        return new CreditPage();
    }

    private void errorsDisplayCredit() {
        new CreditPage().errorsDisplay("Неверный формат", "Неверный формат", "Неверный формат", "Поле обязательно для заполнения", "Неверный формат");
    }

}

