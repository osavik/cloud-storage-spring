package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;

public class HomePage {

    @FindBy(id = "logout")
    private WebElement logout;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credsTab;

    @FindBy(id="create-note")
    private WebElement createNoteButton;

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    @FindBy(id="submit-note-button")
    private WebElement submitNote;

    @FindBy(id="edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id="delete-note-button")
    private WebElement deleteNoteButton;

    @FindBy(id="create-credential")
    private WebElement createCredentialButton;

    @FindBy(id="credential-url")
    private WebElement credentialUrl;

    @FindBy(id="credential-username")
    private WebElement credentialUsername;

    @FindBy(id="credential-password")
    private WebElement credentialPassword;

    @FindBy(id="submit-credential-button")
    private WebElement submitCredential;

    @FindBy(id="credentialTable")
    WebElement credsTable;

    @FindBy(id="edit-cred-button")
    private WebElement editCredButton;

    @FindBy(id="delete-cred-button")
    private WebElement deleteCredButton;

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void logout(){
        logout.click();
    }

    public void goNotesTab(){
        notesTab.click();
    }

    public void goCredsTab(){
        credsTab.click();
    }

    public void createNoteButton(){
        createNoteButton.click();
    }

    public void createCredential(WebDriver driver,String url, String username, String password)throws InterruptedException{

        sleep(3000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        jse.executeScript("arguments[0].click()", credsTab);

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(createCredentialButton)).click();

        new WebDriverWait(driver, 5).until(ExpectedConditions
                .visibilityOf(credentialUrl)).sendKeys(url);

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(credentialUsername))
                .sendKeys(username);

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(credentialPassword))
                .sendKeys(password);

        submitCredential.click();
    }

    public void editCredential(WebDriver driver, Credential credentialOld, Credential credentialNew) throws InterruptedException {

        sleep(3000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        jse.executeScript("arguments[0].click()", credsTab);

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(editCredButton)).click();

        sleep(3000);
        // verify old current credential
        this.verifyCredentialInModal(credentialOld);

        new WebDriverWait(driver, 5).until(ExpectedConditions
                .visibilityOf(credentialUrl)).clear();
        new WebDriverWait(driver, 5).until(ExpectedConditions
                .visibilityOf(credentialUrl)).sendKeys(credentialNew.getUrl());

        new WebDriverWait(driver, 5).until(ExpectedConditions
                .visibilityOf(credentialUsername)).clear();
        new WebDriverWait(driver, 5).until(ExpectedConditions
                .visibilityOf(credentialUsername)).sendKeys(credentialNew.getUsername());

        new WebDriverWait(driver, 5).until(ExpectedConditions
                .visibilityOf(credentialPassword)).clear();
        new WebDriverWait(driver, 5).until(ExpectedConditions
                .visibilityOf(credentialPassword)).sendKeys(credentialNew.getPassword());

        submitCredential.click();

    }

    private void verifyCredentialInModal(Credential credential){
        Assertions.assertTrue(credentialUrl.getAttribute("value").equals(credential.getUrl()));
        Assertions.assertTrue(credentialUsername.getAttribute("value").equals(credential.getUsername()));
        Assertions.assertTrue(credentialPassword.getAttribute("value").equals(credential.getPassword()));
    }

    public void createNote(WebDriver driver,String title, String description)throws InterruptedException{

        sleep(3000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        jse.executeScript("arguments[0].click()", notesTab);

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(createNoteButton)).click();

        new WebDriverWait(driver, 5).until(ExpectedConditions
                .visibilityOf(noteTitle)).sendKeys(title);

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteDescription))
                .sendKeys(description);
        submitNote.click();
    }

    public void editNote(WebDriver driver, String title, String description)throws InterruptedException{

        sleep(3000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        jse.executeScript("arguments[0].click()", notesTab);

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();

        new WebDriverWait(driver, 5).until(ExpectedConditions
                .visibilityOf(noteTitle)).clear();

        new WebDriverWait(driver, 5).until(ExpectedConditions
                .visibilityOf(noteTitle)).sendKeys(title);

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteDescription))
                .clear();

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteDescription))
                .sendKeys(description);
        submitNote.click();
    }

    public void deleteNote(WebDriver driver)throws InterruptedException{

        sleep(3000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        jse.executeScript("arguments[0].click()", notesTab);

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();

    }

    public void verifyCredentialInTable(Credential credentialFromDB, Credential credentialFromListToAdd) {
        Assertions.assertTrue(credsTable.getText().contains(credentialFromDB.getUrl()));
        Assertions.assertTrue(credsTable.getText().contains(credentialFromDB.getUsername()));
        Assertions.assertTrue(credsTable.getText().contains(credentialFromDB.getPassword()));
        Assertions.assertFalse(credsTable.getText().contains(credentialFromListToAdd.getPassword()));
    }

    public void deleteCredential(WebDriver driver) throws InterruptedException {
        sleep(3000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        jse.executeScript("arguments[0].click()", credsTab);

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(deleteCredButton)).click();
    }

    public void verifyThereIsNoCredential(Credential credential) {
        Assertions.assertFalse(credsTable.getText().contains(credential.getUrl()));
        Assertions.assertFalse(credsTable.getText().contains(credential.getUsername()));
        Assertions.assertFalse(credsTable.getText().contains(credential.getPassword()));
    }
}
