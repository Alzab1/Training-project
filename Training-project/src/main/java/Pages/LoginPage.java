package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;

    private static final int TIMEOUT_WAIT_ELEMENT = 120;
    @FindBy(id = "mailbox:login")
    private WebElement loginField;

    @FindBy(id = "mailbox:domain")
    private WebElement mailboxDomain;

    @FindBy(id = "mailbox:password")
    private WebElement passwordField;

    @FindBy(xpath = ".//input[contains(@value,'Ввести пароль')]")
    private WebElement enterButton;

    @FindBy(xpath = ".//a[contains(text(),'выход')]")
    private WebElement logoutLink;

    @FindBy(id = "mailbox:error")
    private WebElement mailboxError;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void signIn(String login, String password, String domainName) {
        selectMailboxDomain(domainName);
        enterLogin(login);
        enterPassword(password);
        waitVisibilityOfElement(logoutLink);
    }

    public boolean logoutLinkIsPresents() {
        return logoutLink.isDisplayed();
    }

    public void loginWithInvalidPassword(String login, String password, String domainName){
        selectMailboxDomain(domainName);
        enterLogin(login);
        enterPassword(password);
        waitVisibilityOfElement(mailboxError);
    }

    public boolean errorAlertIsPresents(){return mailboxError.isDisplayed();}

    private void enterLogin(String login) {
        loginField.clear();
        loginField.sendKeys(login);
        enterButton.click();
    }

    private void enterPassword(String password) {
        waitPasswordFieldClickable();
        passwordField.clear();
        passwordField.sendKeys(password);
        enterButton.click();
    }

    private void selectMailboxDomain(String domainName) {
        new Select(mailboxDomain).selectByVisibleText(domainName);
    }

    private void waitPasswordFieldClickable() {
        new WebDriverWait(driver, TIMEOUT_WAIT_ELEMENT)
                .until(ExpectedConditions.elementToBeClickable(passwordField));
    }

    private void waitVisibilityOfElement(WebElement webElement) {
        new WebDriverWait(driver, TIMEOUT_WAIT_ELEMENT)
                .until(ExpectedConditions.visibilityOf(webElement));
    }
}