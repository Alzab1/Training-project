package Pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class FlagsLettersPage {
    private WebDriver driver;
    private JavascriptExecutor executor;
    private static final int TIMEOUT = 10;
    private static final String PATH_SCREENSHOTS = "TestResultScreenshots/FlagLettersPageTestScreenshots/%s";
    @FindBy(xpath = ".//span[@title='Ещё']")
    private WebElement buttonMore;

    @FindBy(xpath = ".//button[@title='Пометить флажком']")
    private WebElement flagFree;

    @FindBy(xpath = ".//button[@title='Пометить флажком']")
    private List<WebElement> flagsFreeLetters;

    @FindBy(xpath = ".//span[@title='Выделить все']")
    private WebElement selectAllLettersButton;

    @FindBy(xpath = ".//span[contains(text(),'Снять флажок')]")
    private WebElement deFlag;

    @FindBy(xpath = ".//button[@title='Снять флажок']")
    private List<WebElement> flaggedLetters;

    @FindBy(xpath = ".//button[@title]")
    private List<WebElement> flagStatus;

    @FindBy(xpath = ".//button[contains(@title,'флаж')]")
    private WebElement flagSign;

    @FindBy(xpath = ".//span[@title='Снять выделение'] | //span[@data-title='Снять выделение']")
    private WebElement deselectAllLetters;

    @FindBy(xpath = ".//span[@class='ll-av__checkbox']")
    private WebElement letterCheckbox;

    @FindBy(xpath = ".//span[@class='ll-av__checkbox']")
    private List<WebElement> letterCheckboxes;

    @FindBy(xpath = ".//div[contains(text(),'Входящие')]")
    private WebElement inboxFolder;

    @FindBy(xpath = ".//span[contains(text(),'Пометить флажком')]")
    private WebElement flag;

    public FlagsLettersPage(WebDriver driver) {
        this.driver = driver;
        this.executor = (JavascriptExecutor) this.driver;
        PageFactory.initElements(driver, this);
    }

    public void selectLetters(int qtySelectLetters) {
        waitLetterCheckboxes();
        Assert.assertFalse(letterCheckboxes.size() < qtySelectLetters, "No so many letters!");
        for (int i = 0; i < qtySelectLetters; i++) {
            executor.executeScript("arguments[0].click();", letterCheckboxes.get(i));
        }
    }

    public void flagSelectedLetters() {
        executor.executeScript("arguments[0].click();", buttonMore);
        flag.click();
    }

    public boolean lettersIsFlagged(int qtySelectLetters) {
        return flagStatus.stream().limit(qtySelectLetters).map((w) -> w.getAttribute("title"))
                .allMatch("Снять флажок"::equals);
    }

    public void deFlagAllLetters() {
        waitSelectAllButton();
        selectAllLettersButton.click();
        executor.executeScript("arguments[0].click();", buttonMore);
        deFlag.click();
        executor.executeScript("arguments[0].click();", deselectAllLetters);
    }

    public boolean allLettersAreDeflaged() {
        return flagStatus.stream().map((w) -> w.getAttribute("title")).allMatch("Пометить флажком"::equals);
    }

    private void waitLetterCheckboxes() {
        new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10)).ignoring(java.lang.AssertionError.class);
    }

    private void waitSelectAllButton() {
        new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions
                .elementToBeClickable(selectAllLettersButton));
    }

    public void screenShot(String fileName) throws IOException {
        TakesScreenshot scr = ((TakesScreenshot) driver);
        File file = scr.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(String.format(PATH_SCREENSHOTS,fileName)));
    }
}