package com.udacity.jwdnd.course1.cloudstorage.pages;

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

        sleep(5000);

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

    public void createNote(WebDriver driver,String title, String description)throws InterruptedException{

        sleep(5000);

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

        sleep(5000);

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

        sleep(5000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        jse.executeScript("arguments[0].click()", notesTab);

        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();

    }


}
