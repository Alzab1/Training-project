package Pages;

import DB.DBCWorker;
import DB.User;
import core.browser.DriverManager;
import core.browser.DriverManagerFactory;
import core.configuration.Configuration;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SendLettersPageTest {
    private DriverManager driverManager;
    private WebDriver driver;
    private SendLettersPage sendLettersPage;
    private String mainURL = Configuration.getMainUrl();
    private DBCWorker worker = new DBCWorker();
    private int userId = 1;
    private User user = worker.getUserInfo(userId);
    private String login = user.getLogin();
    private String password = user.getPassword();
    private String domainName = user.getDomain();
    private String mail1 = user.getMail1();
    private String mail2 = user.getMail2();

    @BeforeTest
    public void beforeTest() {
        driverManager = DriverManagerFactory.getManager(Configuration.getDriverType());
    }


    @BeforeMethod
    public void beforeMethod() {
        driver = driverManager.getDriver();
        driver.get(mainURL);
        sendLettersPage = new SendLettersPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.signIn(login, password, domainName);
    }
    @Test
    public void sendLettersTest() {
        sendLettersPage.sendLetterForGroup(mail1, mail2);
        Assert.assertTrue(sendLettersPage.letterIsSent());
    }

    @AfterMethod
    public void afterMethod() {
        driverManager.quitDriver();
    }
}