package com.example.sbks.autotest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;


public class SbksAutoTest {

    WebDriver driver;

    @BeforeEach
    void initWebDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\selenium-java-4.1.0\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    @Disabled
    void firstTest() {
        driver.get("http://localhost:9090");
        //стартовая страница
        driver.findElement(By.xpath("/html/body/form[3]/button")).click();

        //страница регитсрации
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/form[1]/input[1]")).sendKeys("test");
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/form[1]/input[2]")).sendKeys("test@mail.ru");
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/form[1]/input[3]")).sendKeys("testtest");
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/form[1]/input[4]")).sendKeys("testtest");
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/form[1]/input[5]")).click();//отправить данные для регистрациии
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/form[2]/button")).click();//перейти на страницу авторизации

        //страница авторизации
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/form[1]/input[1]")).sendKeys("admin@gmail.com");
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/form[1]/input[2]")).sendKeys("admin");
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td/form[1]/input[3]")).click();//отправить данные для авторизации

        //страница с токеном
        driver.findElement(By.xpath("/html/body/form[1]/button")).click();//перейти на страницу всех файлов

        //страница списка всех файлов
        driver.findElement(By.xpath("/html/body/form[4]/button")).click();//перейти на страницу добавления файла

        //страница добавления нового файла
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/input[1]")).sendKeys("testfile");
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/input[2]")).sendKeys("test");
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/input[3]"))
                .sendKeys("C:\\Users\\Admin1\\Downloads\\АхуновТимурРифгатович.pdf");
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[1]/form/input[4]")).click();//добавить файл
        driver.findElement(By.xpath("/html/body/table/tbody/tr/td[2]/form[1]/button")).click();//перейти на страницу добавления файла

        //страница списка всех файлов
        driver.findElement(By.xpath("/html/body/form[2]/input")).sendKeys("АхуновТимурРифгатович.pdf");
        driver.findElement(By.xpath("/html/body/form[2]/button")).click(); //открыть файл по имени

        //страница успешного выполнения операции
        driver.findElement(By.xpath("/html/body/form[1]/button")).click();//перейти на страницу всех файлов

        //страница списка всех файлов
        driver.findElement(By.xpath("/html/body/form[3]/input")).clear();
        driver.findElement(By.xpath("/html/body/form[3]/input")).sendKeys("5");
        driver.findElement(By.xpath("/html/body/form[3]/button")).click();//получить историю загрузки файла

        //страница истории загрузки файла
        driver.findElement(By.xpath("/html/body/form[1]/button")).click();//перейти на страницу всех файлов

        //страница списка всех файлов
        driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[9]/a")).click();//удалить файл
        driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[9]/a")).click();//удалить файл
        driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[9]/a")).click();//удалить файл
        driver.findElement(By.xpath("/html/body/form[6]/button")).click();//получить список удаленных файлов

        //страница списка удаленных файлов
        driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[10]/a")).click();//полное удаление файла
        driver.findElement(By.xpath("/html/body/table/tbody/tr[2]/td[11]/a")).click();//восстановление файла
        driver.findElement(By.xpath("/html/body/form[2]/button")).click();//очистить корзину
        driver.findElement(By.xpath("/html/body/form[1]/button")).click();//перейти на страницу всех файлов

        //страница списка всех файлов
        driver.findElement(By.xpath("/html/body/form[7]/button")).click();//очистить кеш
        driver.findElement(By.xpath("/html/body/form[8]/button")).click();//выйти
    }
}
