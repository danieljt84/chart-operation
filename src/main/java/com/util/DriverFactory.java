package com.util;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;


public abstract class DriverFactory {
	
	public WebDriver driver;
	public JavascriptExecutor js;
	
	public DriverFactory() {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\4P\\Documents\\DRIVER SELENIUM\\geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
		//ProfilesIni listProfiles = new ProfilesIni();
		//FirefoxProfile profile = listProfiles.getProfile("luciene");
		//options.setProfile(profile);
		//options.addArguments("--headless");
		this.driver = new FirefoxDriver(options);
		this.driver.manage().window();
		this.js = (JavascriptExecutor) driver;  
 	}
	
	public WebDriver getDriver() {
		return driver;
	}
	public JavascriptExecutor getJs() {
		return js;
	}

}
