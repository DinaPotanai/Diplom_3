package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage {

    private WebDriver driver;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/nav/ul/li[3]/button") // кнопка "Выход"
    private WebElement logoutButton;

    @FindBy(xpath = "//*[@id=\"root\"]/div/main/div/nav/ul/li[1]/a") // заголовок "Профиль"
    private WebElement profile;

    @FindBy(xpath = "//*[@id=\"root\"]/div/header/nav/ul/li[1]/a/p") // кнопка "Конструктор"
    private WebElement constructorLink;

    @FindBy(xpath = ".//div[contains(@class, 'AppHeader_header__logo__2D0X2')]") // логотип Stellar Burgers
    private WebElement logo;

    public void clickLogout() {
        logoutButton.click();
    }

    public void clickConstructor() {
        constructorLink.click();
    }

    public void clickLogo() {
        logo.click();
    }
}
