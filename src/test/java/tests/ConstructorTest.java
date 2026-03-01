package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pageobject.MainPage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(AllureJunit5.class)
@Feature("Конструктор бургеров")
public class ConstructorTest extends BaseTest {

    private MainPage mainPage;

    @BeforeEach
    void setUp() {

        mainPage = new MainPage(driver);
        driver.get(BASE_TEST_URL);
    }

    @AfterEach
    public void tearDown() {

        // Закрываем браузер
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Story("Вкладки")
    @DisplayName("Активна вкладка «Булки» при открытии")
    void shouldBunsTabBeActiveByDefault() {
        assertThat(mainPage.isBunsTabActive(), is(true));
        assertThat(mainPage.isSaucesTabActive(), is(false));
        assertThat(mainPage.isFillingsTabActive(), is(false));
    }

    @Test
    @Story("Вкладки")
    @DisplayName("Переход к вкладке «Соусы»")
    void shouldSwitchToSaucesTab() {
        mainPage.clickSaucesTab();
        assertThat(mainPage.isSaucesTabActive(), is(true));
        assertThat(mainPage.isBunsTabActive(), is(false));
        assertThat(mainPage.isFillingsTabActive(), is(false));
    }

    @Test
    @Story("Вкладки")
    @DisplayName("Переход к вкладке «Начинки»")
    void shouldSwitchToFillingsTab() {
        mainPage.clickFillingsTab();
        assertThat(mainPage.isFillingsTabActive(), is(true));
        assertThat(mainPage.isBunsTabActive(), is(false));
        assertThat(mainPage.isSaucesTabActive(), is(false));
    }

    @Test
    @Story("Вкладки")
    @DisplayName("Возврат к «Булкам» после «Соусов»")
    void shouldReturnToBunsAfterSauces() {
        mainPage.clickSaucesTab();
        mainPage.clickBunsTab();
        assertThat(mainPage.isBunsTabActive(), is(true));
        assertThat(mainPage.isSaucesTabActive(), is(false));
    }
}
