package com.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.util.DriverFactory;

@Service
public class WhatsappService extends DriverFactory {

	WebElement element;
	DriverFactory driverFactory;

	public void openWhatsapp() {
		driver.get("https://web.whatsapp.com/");
		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean findContact(String contact) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//*[@id=\"side\"]/div[1]/div/div/div[2]/div/div[2]")));
		element.clear();
		element.sendKeys(contact);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		element.sendKeys(Keys.ENTER);
		return checkNameContact(contact);
	}

	public void sendImage(String file) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		try {
			WebElement element = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@title, 'Anexar')]")));
			driver.findElement(By.xpath("//*[contains(@title, 'Anexar')]"));
			js.executeScript("var element = arguments[0];" + "element.click();", element);
			element = driver.findElement(By.cssSelector("input[type='file']"));
			element.sendKeys(file);
			Thread.sleep(2000);
			element = wait.until(ExpectedConditions.elementToBeClickable(By.className("_165_h")));
			element.click();
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Envio as informacaos dos promotores que fizeram o envio das fotos e relat�rio
	// Se retorno igual a verdadeiro, as informa��es foram enviadas completas
	// Se retorno igual a falso, foi enviado apenas as fotos
	

	public void sendTeam(String name) {
		element = driver
				.findElement(By.xpath("/html/body/div[1]/div/div/div[4]/div/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]"));	
		sendTextInElement(name, element);
		element.sendKeys(Keys.CONTROL, Keys.ENTER);
		element.sendKeys(Keys.ENTER);
	}


	public void sendTextInElement(String text, WebElement element) {
	    var char_text = text.toCharArray();
	    for(char _char : char_text) {
	    	element.sendKeys(String.valueOf(_char));
	    }
	}
	
	public boolean checkNameContact(String contact) {
		String name ;
		try {
			name = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[4]/div/header/div[2]/div[1]/div/span"))
					.getText();
		}catch (Exception e) {
			name = " ";
		}
		if (!name.toLowerCase().equals(contact.toLowerCase())) {
			System.out.println("NOME NÃO ENCONTRADO: " + contact);
			return false;
		}
		return true;
	}
}
