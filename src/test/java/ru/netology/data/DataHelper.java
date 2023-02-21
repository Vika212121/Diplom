package ru.netology.data;

import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {
    private static final String approvedCard = "4444 4444 4444 4441";
    private static final String declinedCard = "4444 4444 4444 4442";
    private static final String holder = "Pupkina Kati";
    private static final String cvc = "123";

    @Value
    public static class CardInfo {
        String number;
        String year;
        String month;
        String holder;
        String cvc;
    }



    //Валидные данные
    public static CardInfo ValidData(String cardType) {
        return new CardInfo(
                cardType, // approvedCard, declinedCard
                getGenerateDateYear(), // Год
                getGenerateDateMonth(), // месяц
                getHolder(),
                getCvc()
        );
    }

    // Незаполненные поля
    public static CardInfo BlankFields() {
        return new CardInfo(
                "",
                "",
                "",
                "",
                ""
        );
    }

    //Номер карты содержит меньше 16 арабских цифр
    public static CardInfo TheCardNumberIsLessThanSixteenDigits() {
        return new CardInfo(
                "4444 4444 4444 444",
                getGenerateDateYear(),
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //Незаполненное поле для ввода номера карты
    public static CardInfo CardNumberIsNotFilledIn() {
        return new CardInfo(
                "",
                getGenerateDateYear(),
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //Незаполненное поле для ввода Месяца
    public static CardInfo BlankFieldForTheMonth() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                "",
                getHolder(),
                getCvc()
        );
    }

    //Поле Месяц содержит одну арабскую цифру
    public static CardInfo TheMonthFieldContainsOneDigit() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                "4",
                getHolder(),
                getCvc()
        );
    }

    //Поле Месяц содержит число больше 12
    public static CardInfo TheMonthFieldContainsNumberGreaterThanTwelve() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                "13",
                getHolder(),
                getCvc()
        );
    }

    //Поле Месяц содержит два нуля
    public static CardInfo TheMonthFieldContainsTwoZeros() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                "00",
                getHolder(),
                getCvc()
        );
    }

    //Незаполненное поле Год
    public static CardInfo BlankYearField() {
        return new CardInfo(
                getApprovedCard(),
                "",
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //Поле Год содержит одну арабскую цифру
    public static CardInfo TheYearFieldContainsOneArabicDigit() {
        return new CardInfo(
                getApprovedCard(),
                "4",
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //Поле Год содержит прошедший год
    public static CardInfo TheYearFieldContainsThePastYear() {
        return new CardInfo(
                getApprovedCard(),
                "20",
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //Поле Год содержит нули
    public static CardInfo TheYearFieldContainsZeros() {
        return new CardInfo(
                getApprovedCard(),
                "00",
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //Поле Год содержит будущий год
    public static CardInfo TheYearFieldContainsTheNextYear() {
        return new CardInfo(
                getApprovedCard(),
                "50",
                getGenerateDateMonth(),
                getHolder(),
                getCvc()
        );
    }

    //Незаполненное поле Владелец
    public static CardInfo BlankOwnerField() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "",
                getCvc()
        );
    }

    //Поле Владелец содержит 20 и более буквенных символов латинского алфавита
    public static CardInfo TheOwnerFieldContainsManyLetters() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "Katiiiiiiiiiiiiiiiiiiiiiiiiiii",
                getCvc()
        );
    }

    //Поле Владелец содержит большое колличество пробелов
    public static CardInfo TheOwnerFieldContainsLotOfSpaces() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "Pupkina  Kati",
                getCvc()
        );
    }

    //Поле Владелец содержит буквеннные символы русского алфавита
    public static CardInfo TheOwnerFieldContainsRussianLetters() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "Пупкина",
                getCvc()
        );
    }

    //Поле Владелец содержит арабские цифры
    public static CardInfo TheOwnerFieldContainsArabicNumerals() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "1234567",
                getCvc()
        );
    }

    //Поле Владелец содержит специальные символы
    public static CardInfo TheOwnerFieldContainsSpecialCharacters() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                "#%%***",
                getCvc()
        );
    }

    //Незаполненное поле CVC/CVV
    public static CardInfo BlankCvcField() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                getHolder(),
                ""
        );
    }

    //Поле CVC/CVV содержит одну арабскую цифру
    public static CardInfo TheCvcFieldContainsOneArabicDigit() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                getHolder(),
                "4"
        );
    }

    //Поле CVC/CVV содержит две арабских цифры
    public static CardInfo TheCvcFieldContainsTwoArabicNumerals() {
        return new CardInfo(
                getApprovedCard(),
                getGenerateDateYear(),
                getGenerateDateMonth(),
                getHolder(),
                "44"
        );
    }

    private static String getGenerateDateYear() {
        return LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getGenerateDateMonth() {
        return LocalDate.now().plusMonths(5).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getApprovedCard() {

        return approvedCard;
    }

    public static String getDeclinedCard() {

        return declinedCard;
    }

    public static String getHolder() {

        return holder;
    }

    public static String getCvc() {
        return cvc;
    }
}






