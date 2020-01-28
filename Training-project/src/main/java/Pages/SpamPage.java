package Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class SpamPage {
    private WebDriver driver;
    private JavascriptExecutor executor;
    private static final int TIMEOUT = 10;
    private String idAddedToSpamLetter = null;
    private String idLetterReturnedFromSpam = null;
    @FindBy(xpath = ".//span[@class='ll-av__checkbox']")
    private WebElement letterCheckbox;

    @FindBy(xpath = ".//span[@class='ll-av__checkbox']")
    private List<WebElement> letterCheckboxes;

    @FindBy(xpath = ".//span[@title='Спам']")
    private WebElement spamButton;

    @FindBy(xpath = ".//div[contains(text(),'Спам')]")
    private WebElement spamFolder;

    @FindBy(xpath = ".//span[@title='Не спам'] | // span[@data-title='Не спам']")
    private WebElement notSpamButton;

    @FindBy(xpath = ".//a[@class='link link_secondary']")
    private WebElement spamFolderText;

    @FindBy(xpath = ".//div[contains(text(),'Входящие')]")
    private WebElement inboxFolder;

    @FindBy(xpath = ".//a[@data-uidl-id]")
    private WebElement letterID;

    @FindBy(xpath = ".//a[@data-uidl-id]")
    private List<WebElement> listLetterID;

    public SpamPage(WebDriver driver) {
        this.driver = driver;
        this.executor = (JavascriptExecutor) this.driver;
        PageFactory.initElements(driver, this);
    }

    public void addLetterToSpam() {
        Assert.assertFalse(letterCheckboxes.isEmpty(), "No letters found!");
        executor.executeScript("arguments[0].click();", letterCheckbox);
        idAddedToSpamLetter = listLetterID.get(0).getAttribute("data-uidl-id");
        spamButton.click();
    }

    public boolean letterIsAddedToSpam() {
        waitSpamFolderClickable();
        spamFolder.click();
        waitSpamFolderAlert();
        waitFillingListID();
        return listLetterID.stream().map(w -> w.getAttribute("data-uidl-id"))
                .anyMatch(idAddedToSpamLetter::equals);
    }

    public void returnLetterFromSpam() {
        spamFolder.click();
        waitSpamFolderAlert();
        Assert.assertFalse(letterCheckboxes.isEmpty(), "No letters found!");
        executor.executeScript("arguments[0].click();", letterCheckbox);
        idLetterReturnedFromSpam = listLetterID.get(0).getAttribute("data-uidl-id");
        notSpamButton.click();
    }

    public boolean letterIsReturnedFromSpam() {
        waitInboxFolderClickable();
        inboxFolder.click();
        inboxFolder.click();
        waitFillingListID();
        return listLetterID.stream().map(w -> w.getAttribute("data-uidl-id"))
                .anyMatch(idLetterReturnedFromSpam::equals);
    }

    private void waitSpamFolderClickable() {
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(spamFolder));
    }

    private void waitSpamFolderAlert() {
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOf(spamFolderText));
    }

    private void waitInboxFolderClickable() {
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(inboxFolder));
    }

    private void waitFillingListID() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(listLetterID));
    }
}