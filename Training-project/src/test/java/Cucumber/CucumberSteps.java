package Cucumber;

import DB.DBCWorker;
import DB.User;
import Pages.FlagsLettersPage;
import Pages.LoginPage;
import Pages.SendLettersPage;
import Pages.SpamPage;
import core.browser.DriverManager;
import core.browser.DriverManagerFactory;
import core.configuration.Configuration;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class CucumberSteps {
    private DBCWorker worker = new DBCWorker();
    private int userId = 1;
    private User user = worker.getUserInfo(userId);
    private final String LOGIN = user.getLogin();
    private final String PASSWORD = user.getPassword();
    private final String DOMAIN_NAME = user.getDomain();
    private String mail1 = user.getMail1();
    private String mail2 = user.getMail2();
    private DriverManager driverManager;
    private WebDriver driver;
    private String mainURL = Configuration.getMainUrl();
    private LoginPage loginPage;
    private FlagsLettersPage flagsLettersPage;
    private SendLettersPage sendLettersPage;
    private SpamPage spamPage;

    public CucumberSteps() {
        driverManager = DriverManagerFactory.getManager(Configuration.getDriverType());
        driver = driverManager.getDriver();
        loginPage = new LoginPage(driver);
        flagsLettersPage = new FlagsLettersPage(driver);
        sendLettersPage = new SendLettersPage(driver);
        spamPage = new SpamPage(driver);
    }

    @Given("^I am on main application page$")
    public void loadMainPage() {
        driver.get(mainURL);
    }

    @When("^I login as correct user$")
    public void loginAsCorrectUser() {
        loginPage.signIn(LOGIN, PASSWORD, DOMAIN_NAME);
    }

    @When("^I login as user with \"(.*)\" and \"(.*)\" and \"(.*)\"$")
    public void signInTryAnyValidCredentials(String login, String password, String domain) {
        loginPage.signIn(login, password, domain);
    }
    @When("^I login as user with \"(.*)\" and INVALID \"(.*)\" and \"(.*)\"$")
    public void loginWithInvalidPassword(String login, String password, String domain) {
        loginPage.loginWithInvalidPassword(login, password, domain);
    }
    @Then("^I see error alert$")
    public void errorAlert() {
        Assert.assertTrue(loginPage.errorAlertIsPresents());
    }

    @Then("^I see logout link$")
    public void seeLogoutLink() {
        Assert.assertTrue(loginPage.logoutLinkIsPresents());
    }

    @When("^I add letter to spam$")
    public void addLetterToSpam() {
        spamPage.addLetterToSpam();
    }

    @Then("^I see letter in spam folder$")
    public void letterIsAddedToSpam() {
        spamPage.letterIsAddedToSpam();
    }

    @When("^I return letter from spam$")
    public void returnLetterFromSpam() {
        spamPage.returnLetterFromSpam();
    }

    @Then("^I see letter in inbox folder$")
    public void letterIsReturnedFromSpam() {
        spamPage.letterIsReturnedFromSpam();
    }

    @When("^I select (-?\\d+) letters$")
    public void selectLetters(int qtySelectLetters) {
        flagsLettersPage.selectLetters(qtySelectLetters);
    }

    @And("^I flag selected letters$")
    public void flagSelectedLetters() {
        flagsLettersPage.flagSelectedLetters();
    }

    @Then("^I see selected (-?\\d+) letters are flagged$")
    public void lettersAreFlagged(int qtySelectLetters) {
        flagsLettersPage.lettersIsFlagged(qtySelectLetters);
    }

    @When("^I deflag all letters$")
    public void deflagAllLetters() {
        flagsLettersPage.deFlagAllLetters();
    }

    @Then("^I see all letters are deflaged$")
    public void allLettersAreDeflaged() {
        flagsLettersPage.allLettersAreDeflaged();
    }

    @When("^I send letter for group$")
    public void sendLetterForGroup() {
        sendLettersPage.sendLetterForGroup(mail1, mail2);
    }

    @Then("^I see window that letters are sent$")
    public void lettersAreSent() {
        sendLettersPage.letterIsSent();
    }

    @After
    public void afterClass() {
        driverManager.quitDriver();
    }
}