package Pages;

import DB.DBCWorker;
import DB.User;
import core.browser.DriverManager;
import core.browser.DriverManagerFactory;
import core.configuration.Configuration;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SpamPageTest {
    private static final Logger log = Logger.getLogger(SpamPageTest.class);
    private DriverManager driverManager;
    private WebDriver driver;
    private SpamPage spamPage;
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
        spamPage = new SpamPage(driver);
        loginPage = new LoginPage(driver);
        loginPage.signIn(login, password, domainName);
    }

    @Test
    public void addLetterToSpamTest() {
        spamPage.addLetterToSpam();
        Assert.assertTrue(spamPage.letterIsAddedToSpam());
        log.info("info message");
    }

    @Test
    public void returnLetterFromSpamTest() {
        spamPage.returnLetterFromSpam();
        Assert.assertTrue(spamPage.letterIsReturnedFromSpam());
        log.error("error message");
    }

    @AfterClass
    public void afterMethod() {
        driverManager.quitDriver();
    }
}