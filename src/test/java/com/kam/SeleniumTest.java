/*
 * Copyright (c) 2022 - 2023 [Fanclash.in].
 * All rights reserved.
 */
package com.kam;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * @author kamlesh
 *
 */
public class SeleniumTest {

	WebDriver driver = null;

	@Test
	private void test01() {
		// Initi browser
		ChromeOptions options = new ChromeOptions();
		options.setAcceptInsecureCerts(true);
		options.addArguments("--remote-allow-origins=*");
		options.setBrowserVersion("latest");
		// init driver
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//
		driver.manage().window().maximize();
		driver.get("https://paytm.com/");
		hover(driver.findElement(By.linkText("Paytm for Consumer")));
		hover(driver.findElement(By.linkText("Payments")));
		WebElement logo = driver.findElement(By.linkText("Payments"));
		File file = logo.getScreenshotAs(OutputType.FILE);
		File destFile = new File("logo.png");
		try {
			FileUtils.copyFile(file, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// click(driver.findElement(By.linkText("Bill Payment & Recharges").to));
		click(driver.findElement(
				RelativeLocator.with(By.linkText("Bill Payment & Recharges")).toRightOf(By.linkText("Payments"))));
		String url = driver.getCurrentUrl();
		System.out.println(url);
		scrollIntoView(driver.findElement(By.xpath("//span[text()='Mobile Recharge']")));
		click(driver.findElement(By.xpath("//span[text()='Mobile Recharge']")));
		System.out.println(driver.getCurrentUrl());
	}

	/**
	 * @param findElement
	 */
	private void scrollIntoView(final WebElement findElement) {
		Actions action = new Actions(driver);
		action.scrollToElement(findElement);
		action.build().perform();
	}

	/**
	 * @param findElement
	 */
	private void click(final WebElement findElement) {
		waitUntillElementIsClickable(findElement);
		findElement.click();
	}

	private void waitUntillElementIsClickable(final WebElement findElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.ignoring(StaleElementReferenceException.class);
	}

	private void waitUntillElementIsClickable2(final WebElement findElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.ignoring(StaleElementReferenceException.class);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(findElement)));
	}

	private void waitUntillElementIsVisible(final WebElement findElement) {
		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		// wait.until(driver -> findElement.getAttribute("class").contains("ui-ele"));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(findElement)));
	}

	private void waitUntillElementIsVisibleFW(final WebElement findElement) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofMillis(500))
				.withTimeout(Duration.ofSeconds(30)).ignoring(StaleElementReferenceException.class);
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(findElement)));
	}

	private void waitUntillElementIsVisibleFW2(final WebElement findElement) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofMillis(500))
				.withTimeout(Duration.ofSeconds(30)).ignoring(StaleElementReferenceException.class);
		wait.until(driver -> findElement.getAttribute("class").contains("ui-ele"));

	}

	/**
	 * @param findElement
	 */
	private void hover(final WebElement findElement) {
		waitUntillElementIsVisible(findElement);
		Actions action = new Actions(driver);
		action.moveToElement(findElement);
		action.build().perform();
	}

	@AfterClass

	private void teardown() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
	}

}
