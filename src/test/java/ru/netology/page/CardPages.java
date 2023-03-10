package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static java.time.Duration.ofSeconds;
import static ru.netology.data.DataHelper.*;

public abstract class CardPages {

    protected final SelenideElement header = $x(".//h3[contains(text(), 'карт')]");
    private final SelenideElement cardInput = $x(".//span[contains(text(), 'Номер карты')]/..//input[@class='input__control']");
    private final SelenideElement errorCardInput = $x(".//span[contains(text(), 'Номер карты')]/../span[@class='input__sub']");
    private final SelenideElement monthInput = $x(".//span[contains(text(), 'Месяц')]/..//input[@class='input__control']");
    private final SelenideElement errorMonthInput = $x(".//span[contains(text(), 'Месяц')]/../span[@class='input__sub']");
    private final SelenideElement yearInput = $x(".//span[contains(text(), 'Год')]/..//input[@class='input__control']");
    private final SelenideElement errorYearInput = $x(".//span[contains(text(), 'Год')]/../span[@class='input__sub']");
    private final SelenideElement nameInput = $x(".//span[contains(text(), 'Владелец')]/..//input[@class='input__control']");
    private final SelenideElement errorNameInput = $x(".//span[contains(text(), 'Владелец')]/../span[@class='input__sub']");
    private final SelenideElement codeInput = $x(".//span[contains(text(), 'CVC')]/..//input[@class='input__control']");
    private final SelenideElement errorCodeInput = $x(".//span[contains(text(), 'CVC')]/../span[@class='input__sub']");
    private final SelenideElement buttonForm = $x(".//form//button");
    private final SelenideElement successNotification = $x(".//div[contains(@class, 'notification_status_ok')]");
    private final SelenideElement errorNotification = $x(".//div[contains(@class, 'notification_status_error')]");

    public void enterInputs(CardInfo info) {
        cardInput.setValue(info.getNumber());
        monthInput.setValue(String.valueOf(info.getMonth()));
        yearInput.setValue(String.valueOf(info.getYear()));
        nameInput.setValue(info.getHolder());
        codeInput.setValue(String.valueOf(info.getCvc()));
        buttonForm.click();
    }

    public void errorsDisplay(String errorCard, String errorMonth, String errorYear, String errorName, String errorCode) {
        errorCardInput.shouldBe(visible).shouldHave(text(errorCard));
        errorMonthInput.shouldBe(visible).shouldHave(text(errorMonth));
        errorYearInput.shouldBe(visible).shouldHave(text(errorYear));
        errorNameInput.shouldBe(visible).shouldHave(text(errorName));
        errorCodeInput.shouldBe(visible).shouldHave(text(errorCode));
    }

    public void enterValidUserWithApprovedCard() {
        enterInputs(ValidData(getApprovedCard()));
        successNotification.shouldBe(visible, ofSeconds(15));
    }

    public void enterValidUserWithIncorrectCard(CardInfo info) {
        enterInputs(info);
        errorNotification.shouldBe(visible, ofSeconds(15));
    }

    public void enterIncorrectCardInput(CardInfo info, String errorText) {
        enterInputs(info);
        errorCardInput.shouldBe(visible).shouldHave(text(errorText));
    }

    public void enterIncorrectMonthInput(CardInfo info, String errorText) {
        enterInputs(info);
        errorMonthInput.shouldBe(visible).shouldHave(text(errorText));
    }

    public void enterIncorrectYearInput(CardInfo info, String errorText) {
        enterInputs(info);
        errorYearInput.shouldBe(visible).shouldHave(text(errorText));
    }

    public void enterIncorrectNameInput(CardInfo info, String errorText) {
        enterInputs(info);
        errorNameInput.shouldBe(visible).shouldHave(text(errorText));
    }

    public void enterIncorrectCodeInput(CardInfo info, String errorText) {
        enterInputs(info);
        errorCodeInput.shouldBe(visible).shouldHave(text(errorText));
    }
}