package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {

    private WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[1]/div/div/input") // поле для ввода имени
    private WebElement nameInput;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[2]/div/div/input") // поле для ввода email
    private WebElement emailInput;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[3]/div/div/input") // поле для ввода пароля
    private WebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/button") // кнопка "Зарегистрироваться"
    private WebElement registerButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/form/fieldset[3]/div/p") // подсказка "Некорректный пароль"
    private WebElement errorPassword;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/div/p/a") // гиперссылка "Войти"
    private WebElement loginLink;

    public void setName(String name) {
        nameInput.sendKeys(name);
    }

    public void setEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void setPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickRegisterButton() {
        registerButton.click();
    }

    public boolean isErrorVisible() {
        return errorPassword.isDisplayed();
    }

    public String getErrorText() {
        return errorPassword.getText();
    }

    public void clickLoginLink() {
        loginLink.click();
    }
}
