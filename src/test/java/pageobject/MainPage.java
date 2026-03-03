package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Objects;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/section[2]/div/button") // кнопка "Войти в аккаунт"
    private WebElement loginButtonMainPage;

    @FindBy(xpath = "//*[@id=\"root\"]/div/header/nav/a/p") // кнопка "Личный кабинет"
    private WebElement personalAccountButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/section[1]/h1") // заголовок "Соберите бургер"
    private WebElement constructorTitle;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/section[1]/div[1]/div[1]") // вкладка "Булки"
    private WebElement bunsTab;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/section[1]/div[1]/div[2]") // вкладка "Соусы"
    private WebElement saucesTab;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/section[1]/div[1]/div[3]") // вкладка "Начинки"
    private WebElement fillingsTab;

    public void clickLoginButtonMainPage() {
        loginButtonMainPage.click();
    }

    public void clickPersonalAccount() {
        personalAccountButton.click();
    }

    public String getConstructorTitle() {
        return constructorTitle.getText();
    }

    public void clickBunsTab() {
        bunsTab.click();
    }

    public void clickSaucesTab() {
        saucesTab.click();
    }

    public void clickFillingsTab() {
        fillingsTab.click();
    }

    public boolean isBunsTabActive() {
        return Objects.requireNonNull(bunsTab.getAttribute("class")).contains("tab_tab_type_current");
    }

    public boolean isSaucesTabActive() {
        return Objects.requireNonNull(saucesTab.getAttribute("class")).contains("tab_tab_type_current");
    }

    public boolean isFillingsTabActive() {
        return Objects.requireNonNull(fillingsTab.getAttribute("class")).contains("tab_tab_type_current");
    }
}
