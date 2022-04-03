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
//        ChromeOptions options = new ChromeOptions();      // Включение headless режима
//        options.addArguments("--disable-dev-shm-usage");  // при использовании selenium необходимо
//        options.addArguments("--no-sandbox");             // реализовать в коде во время создания экземпляра
//        options.addArguments("--headless");               // webdriver
//        driver = new ChromeDriver(options);   //инициализируем переменную новым хромдрайвером
          driver = new ChromeDriver();

    }

    @AfterEach                         //  у драйвера будем вызывать те методы, котоые необходимо сделать автотесту, чтобы он вел себя как пользователдь
    public void teardown() {
        if (driver != null) {          // говорим, что в поле у нас null
            driver.quit();             // закрывает все окна браузера
        }

    // @AfterEach                      // пример из лекции
   //  public void tearDown() {
        //  driver.quit();
        //  driver = null;
        //  }

    }
    @Test // Тестируемая функциональность: отправка формы.
    public void shouldSendForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[name='name']")).sendKeys("Фамилия Имя");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79998886677"); //найди поле для ввода телефона и введи номер
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click(); //найди чек-бокс и кликни по нему
        driver.findElement(By.cssSelector("[type='button']")).click();  //найди кнопку "Продолжить" и клтикни по ней
       String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
       String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
       assertEquals(expected, actual);
    }
    @Test
    public void shouldValidateIfNoFieldIsFilled() {
    driver.get("http://localhost:9999/");
    driver.findElement(By.cssSelector("[type='button']")).click();
    WebElement fieldName =driver.findElement(By.cssSelector("[data-test-id='name']"));
    //ищем внутри этого элемента
        String actual = fieldName.findElement(By.cssSelector(".input__sub")).getText().trim();
    String expected = "Поле обязательно для заполнения";
    assertEquals(expected, actual);
    }

    @Test
    public void shouldValidateIfUseLatin()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[name='name']")).sendKeys("Surname Name");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79998886677");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click(); //найди чек-бокс и кликни по нему
        driver.findElement(By.cssSelector("[type='button']")).click();  //найди кнопку "Продолжить" и клтикни по ней
        WebElement fieldName =driver.findElement(By.cssSelector("[data-test-id='name']"));
        String actual = fieldName.findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldValidateWhithoutPlus()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[name='name']")).sendKeys("Фамилия Имя");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("79998886677");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click(); //найди чек-бокс и кликни по нему
        driver.findElement(By.cssSelector("[type='button']")).click();  //найди кнопку "Продолжить" и клтикни по ней
        WebElement fieldPhone =driver.findElement(By.cssSelector("[data-test-id='phone']"));
        String actual = fieldPhone.findElement(By.cssSelector(".input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldValidateWhithoutCheckBox()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[name='name']")).sendKeys("Фамилия Имя");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79998886677");
      //  driver.findElement(By.cssSelector("[data-test-id='agreement']")).click(); //найди чек-бокс и кликни по нему
        driver.findElement(By.cssSelector("[type='button']")).click();  //найди кнопку "Продолжить" и клтикни по ней
        WebElement checkBoxText =driver.findElement(By.cssSelector(".input_invalid"));
        String actual = checkBoxText.getCssValue("color");
        String expected = ("rgba(255, 92, 92, 1)");
        assertEquals(expected, actual);
    }

}
