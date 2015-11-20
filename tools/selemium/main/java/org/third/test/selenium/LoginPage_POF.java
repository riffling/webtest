package org.third.test.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

//id=usernameId
//name=username
//css=#usernameId
//DomIndex:  document.forms['loginForm'].elements[1]
//DomName:   document.forms['loginForm'].password
//Css:       css=input[name="password"]
//XpathAttr: //input[@name='password']  //input[@id='usernameId']
//XpathIdx:  //tr[2]/td[2]/input
public class LoginPage_POF {
    final WebDriver webDriver;
    @FindBy(how = How.XPATH, using = "//input[@name='username']")
    public WebElement userElement;
    @FindBy(how = How.CSS, using = "document.forms['loginForm'].password")
    public WebElement passwordElement;

    @FindBy(how = How.ID, using = "submit")
    public WebElement btnSubmit;

    public LoginPage_POF(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void login(String userName, String password) {
        userElement.sendKeys(userName);
        passwordElement.sendKeys(password);
        // btnSubmit.click();
    }

    public static void main(String[] args) {
        // DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        // WebDriver wd = new RemoteWebDriver(desiredCapabilities);

        WebDriver fwd = new FirefoxDriver();
        fwd.manage().window().maximize();
        fwd.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        fwd.get("http://localhost:8080/webtest/security/admin");
        PageFactory.initElements(fwd, LoginPage_POF.class);

        LoginPage_POF lp = new LoginPage_POF(fwd);
        lp.login("test01", "password");
    }
}
