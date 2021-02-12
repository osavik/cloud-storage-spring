package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement inputButton;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void setInputUsername(String name){
        inputUsername.sendKeys(name);
    }

    public void setInputPassword(String password){
        inputPassword.sendKeys(password);
    }

    public void loginButton(){
        inputButton.click();
    }

    public void loginProcess(String username, String password){
        setInputUsername(username);
        setInputPassword(password);
        loginButton();
    }

}
