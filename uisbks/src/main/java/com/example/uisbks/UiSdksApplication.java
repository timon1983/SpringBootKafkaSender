package com.example.uisbks;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Setter
@Getter
public class UiSdksApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiSdksApplication.class, args);
//        WebElement button = driver.findElement(By.xpath("/html/body/form[3]/button"));
//        button.click();
//
//        WebElement sendName = driver.findElement(By.xpath("/html/body/table/tbody/tr/td/form[1]/input[1]"));
//        sendName.sendKeys("tom", Keys.ENTER);
//
//        Actions actions = new Actions(driver);
//        actions.contextClick().build().perform();

    }
}