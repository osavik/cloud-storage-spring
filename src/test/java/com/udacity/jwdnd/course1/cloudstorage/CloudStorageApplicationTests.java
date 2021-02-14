package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private CredentialService credentialService;

	@Autowired
	private UserService userService;

	private WebDriver driver;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;

	public String baseUrl;

	// user credentials
	public static final String firstName = "Jon";
	public static final String lastName = "Dou";
	public static final String username = "foo";
	public static final String password = "veryweakpassword";

	// notes
	public static final Note noteOne = new Note(null, "Note1", "TODO1",null);
	public static final Note noteTwo = new Note(null, "Note2", "TODO2",null);

	private List<Credential> createCredentialList(){
		List<Credential> credentials = new ArrayList<>();

		credentials.add(new Credential(null, "URL1", "username1", null, "password1", null));
		credentials.add(new Credential(null, "URL2", "username2", null, "password2", null));
		credentials.add(new Credential(null, "URL3", "username3", null, "password3", null));

		return credentials;
	}

	private List<Credential> createNewCredentialList(){
		List<Credential> credentials = new ArrayList<>();

		credentials.add(new Credential(null, "URL1new", "username1new", null, "password1new", null));
		credentials.add(new Credential(null, "URL2new", "username2new", null, "password2new", null));
		credentials.add(new Credential(null, "URL3new", "username3new", null, "password3new", null));

		return credentials;
	}

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseUrl = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getLoginPage() {
		driver.get( baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(2)
	public void getSignUpPage() {
		driver.get( baseUrl + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(3)
	public void getUnauthorizedHomePage() {
		driver.get( baseUrl + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	@Order(4)
	public void testSignUpLoginLogout(){
		driver.get(baseUrl + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signupProcess(firstName,lastName,username, password);

		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseUrl + "/login"); // we have to reset driver to new url and populate our WebElements
		loginPage = new LoginPage(driver);
		loginPage.loginProcess(username, password);

		Assertions.assertEquals("Home", driver.getTitle());

		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver);

		homePage.logout();
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseUrl + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	@Order(5)
	public void testCreateNote() throws InterruptedException {
		// login
		driver.get(baseUrl + "/login");
		loginPage = new LoginPage(driver);
		loginPage.loginProcess(username, password);

		// go to note tab
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver);

		// create note and submit
		homePage.createNote(driver, noteOne.getNoteTitle(), noteOne.getNoteDescription());

		// verify it is displayed
		WebElement notesTab = driver.findElement(By.id("nav-notes"));
		Assertions.assertTrue(notesTab.getText().contains(noteOne.getNoteTitle()));
		Assertions.assertTrue(notesTab.getText().contains(noteOne.getNoteDescription()));
	}

	@Test
	@Order(6)
	public void testEditNote() throws InterruptedException {
		// login
		driver.get(baseUrl + "/login");
		loginPage = new LoginPage(driver);
		loginPage.loginProcess(username, password);

		// go to note tab
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver);

		homePage.editNote(driver, noteTwo.getNoteTitle(), noteTwo.getNoteDescription());

		// verify it is displayed: no NoteOne anymore
		WebElement notesTab = driver.findElement(By.id("nav-notes"));
		Assertions.assertFalse(notesTab.getText().contains(noteOne.getNoteTitle()));
		Assertions.assertFalse(notesTab.getText().contains(noteOne.getNoteDescription()));
		Assertions.assertTrue(notesTab.getText().contains(noteTwo.getNoteTitle()));
		Assertions.assertTrue(notesTab.getText().contains(noteTwo.getNoteDescription()));
	}

	@Test
	@Order(7)
	public void testDeleteNote() throws InterruptedException {
		// login
		driver.get(baseUrl + "/login");
		loginPage = new LoginPage(driver);
		loginPage.loginProcess(username, password);

		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver);

		homePage.deleteNote(driver);

		// verify it is displayed
		WebElement notesTab = driver.findElement(By.id("nav-notes"));
		Assertions.assertFalse(notesTab.getText().contains(noteOne.getNoteTitle()));
		Assertions.assertFalse(notesTab.getText().contains(noteOne.getNoteDescription()));
		Assertions.assertFalse(notesTab.getText().contains(noteTwo.getNoteTitle()));
		Assertions.assertFalse(notesTab.getText().contains(noteTwo.getNoteDescription()));
	}

	@Test
	@Order(8)
	public void testCreateCredentialSet() throws InterruptedException {
		// login
		driver.get(baseUrl + "/login");
		loginPage = new LoginPage(driver);
		loginPage.loginProcess(username, password);

		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver);

		List<Credential> credentialListToAdd = this.createCredentialList();

		for (Credential credential : credentialListToAdd){
			homePage.createCredential(driver, credential.getUrl(), credential.getUsername(), credential.getPassword());
		}

		User user = userService.getUser(username);
		List<Credential> credentialListFromDB = credentialService.getAllCredentialByUserId(user.getUserId());


		// verify we have url, username, encrypted password and DO NOT have an original password
		for(int i = 0; i < credentialListFromDB.size(); ++i){
			homePage.verifyCredentialInTable(credentialListFromDB.get(i), credentialListToAdd.get(i));
		}

		// example reading table content
		/*
		WebElement credsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> rows = credsTable.findElements(By.tagName("tr"));
		for (WebElement row : rows) {
			List<WebElement> cols = row.findElements(By.tagName("td"));
			for (WebElement col : cols) {
				System.out.print(col.getText() + "\t");
			}
			System.out.println();
		}
		*/
	}

	@Test
	@Order(9)
	public void testEditCredential() throws InterruptedException {
		// login
		driver.get(baseUrl + "/login");
		loginPage = new LoginPage(driver);
		loginPage.loginProcess(username, password);

		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver);

		// create note and submit
		List<Credential> credentialListOld = this.createCredentialList();
		List<Credential> credentialListNew = this.createNewCredentialList();

		// update credential
		homePage.editCredential(driver, credentialListOld.get(0), credentialListNew.get(0));

		// verify change
		User user = userService.getUser(username);
		List<Credential> credentialListFromDB = credentialService.getAllCredentialByUserId(user.getUserId());

		homePage.verifyCredentialInTable(credentialListFromDB.get(0), credentialListNew.get(0));

	}

	@Test
	@Order(10)
	public void testDeleteCredentialSet() throws InterruptedException {
		// login
		driver.get(baseUrl + "/login");
		loginPage = new LoginPage(driver);
		loginPage.loginProcess(username, password);

		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver);

		User user = userService.getUser(username);
		List<Credential> credentialListFromDB = credentialService.getAllCredentialByUserId(user.getUserId());

		// delete credentials
		for(int i = 0; i<credentialListFromDB.size(); ++ i){
			homePage.deleteCredential(driver);
		}

		// verify there is no more this credentials
		for(Credential credential : credentialListFromDB){
			homePage.verifyThereIsNoCredential(credential);
		}
	}
}
