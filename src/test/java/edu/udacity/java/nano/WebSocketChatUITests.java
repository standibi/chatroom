package edu.udacity.java.nano;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketChatUITests {
    @LocalServerPort()
    private int port;
    WebDriver driver;

    @Before
    public void setUp() throws Exception{
        System.setProperty("webdriver.gecko.driver", "C:\\geckodriver\\geckodriver.exe");
        driver =  new FirefoxDriver();
    }

    @After
    public void tearDown() throws Exception{
        driver.quit();
    }

    @Test
    public void performLogin() throws Exception{
        //Go to login page
        driver.get("http://localhost:"+port);

        //We should see username input and submit button
        WebElement loginButton = driver.findElement(By.id("submit-button"));
        WebElement usernameInput = driver.findElement(By.id("username"));
        assertThat(loginButton.isDisplayed()).isTrue();

        //When we fill username field and click login button
        usernameInput.sendKeys("Stanislas");
        loginButton.click();

        //Then we should be in the chat room and see The welcome message
        assertThat(driver.getTitle()).isEqualTo("Chat Room");
        assertThat(driver.findElement(By.tagName("body")).getText()).contains("Welcome");
        assertThat(driver.findElement(By.tagName("body")).getText()).contains("Stanislas");
    }
    @Test
    public void sendChatMessage() throws Exception{
        //When we login
        performLogin();

        //And fill the message textarea, and press send message button
        String msg = "Hi From Stan";
        WebElement msgInput = driver.findElement(By.id("msg"));
        msgInput.sendKeys(msg);
        WebElement sendMessageButton = driver.findElement(By.id("send-message-button"));
        sendMessageButton.click();
        //Then we should see the submitted message in the message container
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertThat(driver.findElement(By.id("message-container")).getText()).contains(msg);

    }

    @Test
    public void performLogout() throws  Exception{
        //Login
        performLogin();
        //Click logout button
        WebElement logoutButton = driver.findElement(By.id("logout-button"));
        logoutButton.click();

        //Then we should be on login page
        WebElement loginButton = driver.findElement(By.id("submit-button"));
        assertThat(loginButton.isDisplayed()).isTrue();
    }
}
