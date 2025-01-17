package com.browserstack.page;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.browserstack.base.TestBase;

public class OpinionPage extends TestBase {

	/* ####### Page Factory ########## */

	@FindBy(css = "div.z.z-hi")
	WebElement parentElement;

	// Initialising Page Objects
	public OpinionPage() {
		PageFactory.initElements(driver, this);
	}

	/* ####### Actions ########## */

	// get all articles
	public List<WebElement> getArticles() {
		return parentElement.findElements(By.cssSelector("h2.c_t"));
	}

	
	// Get all article texts and return a List<String>
	public List<String> getAllArticleTexts() {
	    List<WebElement> articles = getArticles();
	    List<String> articleTexts = new ArrayList<>();

	    if (articles.isEmpty()) {
	        System.out.println("No articles found");
	    } else {
	        for (WebElement article : articles) {
	            articleTexts.add(article.getText());
	        }
	    }

	    return articleTexts;
	}
	

}
