package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement inputButton;

    public SignupPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void setInputFirstName(String firstName) {
        this.inputFirstName.sendKeys(firstName);
    }

    public void setInputLastName(String lastName) {
        this.inputLastName.sendKeys(lastName);
    }

    public void setInputUsername(String name){
        inputUsername.sendKeys(name);
    }

    public void setInputPassword(String password){
        inputPassword.sendKeys(password);
    }

    public void signUpButton(){
        this.inputButton.click();
    }

    public void signupProcess(String firstName, String lastName, String username, String password){
        setInputFirstName(firstName);
        setInputLastName(lastName);
        setInputUsername(username);
        setInputPassword(password);
        signUpButton();
    }
}
