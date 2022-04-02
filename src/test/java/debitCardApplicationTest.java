import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
    @Test
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
}
