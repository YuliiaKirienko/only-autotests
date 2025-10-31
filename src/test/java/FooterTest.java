import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FooterTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private final String BASE_URL = "https://only.digital/";

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void testFooterPresence() {
        // Проверяем что футер есть на главной странице
        driver.get(BASE_URL);
        WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));
        assertTrue(footer.isDisplayed(), "Футер должен отображаться на главной странице");
        System.out.println("✓ Футер присутствует на главной странице");
    }

    @Test
    public void testFooterElements() {
        // Проверяем элементы в футере
        driver.get(BASE_URL);
        WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));

        // 1. Кнопка "Начать проект" -
        WebElement startProjectButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//footer//button[contains(text(), 'Начать проект')]")));
        assertTrue(startProjectButton.isDisplayed(), "В футере должна быть кнопка 'Начать проект'");
        assertTrue(startProjectButton.isEnabled(), "Кнопка 'Начать проект' должна быть кликабельной");
        System.out.println("✓ Кнопка 'Начать проект': найдена и активна");

        // 2. E-mail
        WebElement email = footer.findElement(By.xpath(
                ".//a[text()='hello@only.digital']"));
        assertTrue(email.isDisplayed(), "В футере должен быть email hello@only.digital");
        System.out.println("✓ Email: " + email.getText());

        // 3. Телефон
        WebElement phone = footer.findElement(By.xpath(
                ".//a[@href='tel:+7 (953) 062 21 85']"));
        assertTrue(phone.isDisplayed(), "В футере должен быть телефон +7 (953) 062 21 85");
        System.out.println("✓ Телефон: " + phone.getText());

        // 4. Telegram
        WebElement telegram = footer.findElement(By.xpath(
                ".//a[@href='https://t.me/onlydigitalagency' and contains(@class, 'TextLink_link')]"));
        assertTrue(telegram.isDisplayed(), "В футере должен быть Telegram @onlydigitalagency");
        System.out.println("✓ Telegram: " + telegram.getText());

        // 5. Текст "Telegram для связи"
        WebElement telegramTitle = footer.findElement(By.xpath(
                ".//p[text()='Telegram для связи']"));
        assertTrue(telegramTitle.isDisplayed(), "В футере должен быть текст 'Telegram для связи'");
        System.out.println("✓ Заголовок Telegram: " + telegramTitle.getText());

        // 6. Описание компании
        WebElement description = footer.findElement(By.xpath(
                ".//p[contains(@class, 'text2') and contains(text(), 'Создаем')]"));
        assertTrue(description.isDisplayed(), "В футере должно быть описание компании");
        System.out.println("✓ Описание: " + description.getText());

        // 7. Кнопка vk
        WebElement vkButton = footer.findElement(By.xpath(
                ".//a[@href='https://vk.com/onlydigitalagency']"));
        assertTrue(vkButton.isDisplayed(), "Должна быть кнопка vk");
        assertEquals("vk", vkButton.getText(), "Текст кнопки должен быть 'vk'");
        System.out.println("✓ Кнопка vk: найдена, href: " + vkButton.getAttribute("href"));

        System.out.println("✓ Все элементы футера присутствуют");
    }

    @Test
    public void testFooterOnOtherPages() {
        // Проверяем футер на других страницах
        String[] pages = {BASE_URL + "cases", BASE_URL + "contacts"};

        for (String page : pages) {
            driver.get(page);
            List<WebElement> footers = driver.findElements(By.tagName("footer"));
            if (!footers.isEmpty() && footers.get(0).isDisplayed()) {
                System.out.println("✓ Футер присутствует на: " + page);
            } else {
                System.out.println("ℹ Футер отсутствует на: " + page);
            }
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}