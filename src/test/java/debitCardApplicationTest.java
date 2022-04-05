import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class debitCardApplicationTest {

    WebDriver driver;


    @BeforeAll
    public static void setUpAll() { // тут устанавливаем проперти с путем до хром драйвер
         WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void setupTest() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
//        driver = new ChromeDriver(options);
         driver = new ChromeDriver();

    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }


    }
    @Test //
    public void shouldSendForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[name='name']")).sendKeys("Фамилия Имя");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79998886677");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
       String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
       String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
       assertEquals(expected, actual);
    }
    @Test
    public void shouldValidateIfNoFieldIsFilled() {
    driver.get("http://localhost:9999/");
    driver.findElement(By.cssSelector("[type='button']")).click();
    String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
    String expected = "Поле обязательно для заполнения";
    assertEquals(expected, actual);
    }

    @Test
    public void shouldValidateIfUseLatin()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[name='name']")).sendKeys("Surname Name");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79998886677");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldValidateWhithoutPlus()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[name='name']")).sendKeys("Фамилия Имя");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("79998886677");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type='button']")).click();
        String actual =driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldValidateWhithoutCheckBox()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[name='name']")).sendKeys("Фамилия Имя");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79998886677");
        driver.findElement(By.cssSelector("[type='button']")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='agreement'] .input_invalid")).getAttribute("class");
        String expected = "input_invalid";
        assertEquals(expected, actual);
    }

}
