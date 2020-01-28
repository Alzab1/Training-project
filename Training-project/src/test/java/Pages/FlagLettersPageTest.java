package Pages;

import DB.DBCWorker;
import DB.User;
import core.browser.DriverManager;
import core.browser.DriverManagerFactory;
import core.configuration.Configuration;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class FlagLettersPageTest {
    private DriverManager driverManager;
    private WebDriver driver;
    private FlagsLettersPage flagsLettersPage;
    private String mainURL = Configuration.getMainUrl();
    private LoginPage loginPage;
    private DBCWorker worker = new DBCWorker();
    private int userId = 1;
    private User user = worker.getUserInfo(userId);
    private String login = user.getLogin();
    private String password = user.getPassword();
    private String domainName = user.getDomain();

    @BeforeTest
    public void beforeTest() {
        driverManager = DriverManagerFactory.getManager(Configuration.getDriverType());
    }

    @BeforeClass
    public void beforeClass() {
        driver = driverManager.getDriver();
        driver.get(mainURL);
        flagsLettersPage = new FlagsLettersPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.signIn(login, password, domainName);
    }

    @Test
    public void flagLetters() throws IOException {
        int qtySelectLetters = 3;
        flagsLettersPage.selectLetters(qtySelectLetters);
        flagsLettersPage.flagSelectedLetters();
        flagsLettersPage.screenShot("flag.png");
        Assert.assertTrue(flagsLettersPage.lettersIsFlagged(qtySelectLetters));
    }

    @Test(dependsOnMethods = "flagLetters")
    void deflagAllLetters() throws IOException {
        flagsLettersPage.deFlagAllLetters();
        flagsLettersPage.screenShot("deflag.png");
        Assert.assertTrue(flagsLettersPage.allLettersAreDeflaged());
    }

    @AfterClass
    public void afterMethod() {
        driverManager.quitDriver();
    }
}