package Pages;

import DB.DBCWorker;
import DB.User;
import core.browser.DriverManager;
import core.browser.DriverManagerFactory;
import core.configuration.Configuration;
import core.parser.Parser;
import core.parser.UserXML;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class LoginPageTest {
    private DriverManager driverManager;
    private WebDriver driver;
    private LoginPage loginPage;
    private String mainURL = Configuration.getMainUrl();
    private DBCWorker worker = new DBCWorker();
    private int userId = 1;
    private User user = worker.getUserInfo(userId);
    private String login = user.getLogin();
    private String password = user.getPassword();
    private String domainName = user.getDomain();
    private Parser parser = new Parser();
    private int userXMLIndex = 4;
    private UserXML userXML = parser.parse(userXMLIndex);
    private String wrongPassword = userXML.getPassword();

    public LoginPageTest() throws IOException, SAXException, ParserConfigurationException {
    }

    @BeforeTest
    public void beforeTest() {
        driverManager = DriverManagerFactory.getManager(Configuration.getDriverType());
    }


    @BeforeMethod
    public void beforeMethod() {
        driver = driverManager.getDriver();
        driver.get(mainURL);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void loginTest() {
        loginPage.signIn(login, password, domainName);
        Assert.assertTrue(loginPage.logoutLinkIsPresents());
    }

    @Test
    public void loginInvalidPasswordTest() {
        loginPage.loginWithInvalidPassword(login, wrongPassword, domainName);
        Assert.assertTrue(loginPage.errorAlertIsPresents());
    }

    @AfterMethod
    public void afterMethod() {
        driverManager.quitDriver();
    }
}