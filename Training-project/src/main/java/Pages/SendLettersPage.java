package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SendLettersPage {
    private WebDriver driver;
    private static final int TIMEOUT = 10;
    private String text = "Hello, Guys!";
    @FindBy(xpath = ".//span[contains(text(),'Написать письмо')]")
    private WebElement createLetterButton;

    @FindBy(xpath = "(//input[contains(@class,'container')])[1]")
    private WebElement toWhomField;

    @FindBy(xpath = ".//span[@title='Отправить']")
    private WebElement sendLetterButton;

    @FindBy(xpath = ".//div[@role='textbox']/div[1]")
    private WebElement letterBody;

    @FindBy(xpath = ".//div[@class='layer-sent-page']")
    private WebElement alertLetterSent;

    public SendLettersPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void sendLetterForGroup(String mail1, String mail2) {
        createLetterButton.click();
        waitToWhomFieldClickable();
        toWhomField.sendKeys(mail1, ",", mail2);
        letterBody.sendKeys(text);
        sendLetterButton.click();
    }

    public boolean letterIsSent() {
        waitAlertLetterSent();
        return alertLetterSent.isDisplayed();
    }

    private void waitToWhomFieldClickable() {
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(toWhomField));
    }

    private void waitAlertLetterSent() {
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(alertLetterSent));
    }
}