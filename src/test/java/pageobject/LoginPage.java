package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[1]/div/div/input") // поле для ввода email
    private WebElement emailInput;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[2]/div/div/input") // поле для ввода пароля
    private WebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/button") // кнопка "Войти"
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/p[1]/a") // гиперссылка "Зарегистрироваться"
    private WebElement registerLink;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/p[2]/a") // гиперссылка "Восстановить пароль"
    private WebElement forgotPasswordLink;

    public void setEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void setPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void clickRegisterLink() {
        registerLink.click();
    }

    public void clickForgotPasswordLink() {
        forgotPasswordLink.click();
    }
}
