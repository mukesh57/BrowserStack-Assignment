package com.browserstack.page;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.browserstack.base.TestBase;

public class HomePage extends TestBase {

	/* ####### Page Factory ########## */

	@FindBy(xpath = "//*[@id='didomi-notice-agree-button']")
	WebElement btnCookies;
	
	@FindBy(xpath = "//*[@id='edition_head' and @class='ed_a']/a/span")
	WebElement linkSelection;

	@FindBy(xpath = "//*[@id='csw']/div[1]/nav/div/a[2]")
	WebElement linkOpinion;

	// Initialising Page Objects
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	
	
	/* ####### Actions ########## */

	public void acceptCookies() {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(btnCookies));
		btnCookies.click();
	}
	
	public String languageSelected() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(linkSelection));
		return linkSelection.getText();
	}

	public OpinionPage naviagteToOpinionPage() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.elementToBeClickable(linkOpinion));
		linkOpinion.click();
		return new OpinionPage();
	}

}
