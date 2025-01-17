package com.browserstack.tests;

import java.util.*;
import org.testng.Assert;
import org.testng.annotations.*;
import com.browserstack.base.TestBase;
import com.browserstack.page.HomePage;
import com.browserstack.page.OpinionPage;
import com.browserstack.utils.TranslationAPI;
import io.qameta.allure.Description;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AssignmentTests extends TestBase {

	HomePage homePage;
	OpinionPage opinionPage;
	
	private static final Logger logger = LogManager.getLogger(AssignmentTests.class);

	public AssignmentTests() {
		super();
	}

	@BeforeMethod
	public void setup() {
		initialization();
		homePage = new HomePage();
		homePage.acceptCookies();
	}

	@Test(priority = 1,enabled = true)
	@Description("Validate Language selected is Spanish")
	public void websiteTextValidationTest() {
		String selectedTxt = homePage.languageSelected();
		Assert.assertEquals(selectedTxt.toUpperCase(), "ESPAÑA", "website's text is not in Spanish");
	}

	@Test(priority = 2,enabled = true)
	@Description("Print first five article texts")
	public void printFirstFiveArticleTextsTest() {
		homePage.naviagteToOpinionPage();
		opinionPage = new OpinionPage();

		// Get all article texts from OpinionPage
		List<String> allArticleTexts = opinionPage.getAllArticleTexts();

		Assert.assertFalse(allArticleTexts.isEmpty(), "No articles found on Opinion Page");

		// first five articles
		int limit = Math.min(5, allArticleTexts.size());
		for (int i = 0; i < limit; i++) {
			logger.info("Article " + (i + 1) + ": " + allArticleTexts.get(i));
			Assert.assertNotNull(allArticleTexts.get(i), "Article text is null");
			Assert.assertFalse(allArticleTexts.get(i).trim().isEmpty(), "Article text is empty");
		}
	}

	@Test(priority = 3,enabled = true)
	@Description("Translating title of each article to English and printing")
	public void translateToEnglishAndPrintTest() throws Exception {
		homePage.naviagteToOpinionPage();
		opinionPage = new OpinionPage();

		List<String> allArticleTexts = opinionPage.getAllArticleTexts();

		Assert.assertFalse(allArticleTexts.isEmpty(), "No articles found on the Opinion Page");

		int limit = Math.min(5, allArticleTexts.size());
		for (int i = 0; i < limit; i++) {
			String toEnglish = TranslationAPI.getTranslation(allArticleTexts.get(i), "es", "en");
			logger.info("Spanish: " + allArticleTexts.get(i) + " -> to English: " + toEnglish);

			Assert.assertNotNull(toEnglish, "Translation failed: result is null");
			Assert.assertFalse(toEnglish.trim().isEmpty(), "Translation failed: result is empty");
		}
	}

	@Test(priority = 4, enabled =true)
	@Description("Analyze translated headers and find repeated words")
	public void analyzeTranslatedHeaders() throws Exception {
		homePage.naviagteToOpinionPage();
		opinionPage = new OpinionPage();

		List<String> allArticleTexts = opinionPage.getAllArticleTexts();

		Assert.assertFalse(allArticleTexts.isEmpty(), "No articles found on the Opinion Page.");

		Map<String, Integer> wordCount = new HashMap<>();

		// Translate headers 
		int limit = Math.min(5, allArticleTexts.size());
		for (int i = 0; i < limit; i++) {
			String spanishText = allArticleTexts.get(i);
			String translatedText = TranslationAPI.getTranslation(spanishText, "es", "en");

			Assert.assertNotNull(translatedText, "Translation failed: result is null.");
			Assert.assertFalse(translatedText.trim().isEmpty(), "Translation failed: result is empty.");

			// Break translated text into words
			StringTokenizer tokenizer = new StringTokenizer(translatedText.toLowerCase());
			while (tokenizer.hasMoreTokens()) {
				String word = tokenizer.nextToken().replaceAll("[^a-zA-Z]", "");
				if (!word.isEmpty()) {
					wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
				}
			}
		}

		// Check for repeated words
		boolean wordsFound = false;
		logger.info("Words repeated more than twice:");

		for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
			if (entry.getValue() > 2) {
				logger.info(entry.getKey() + " -> " + entry.getValue() + " times");
				wordsFound = true;
			}
		}
		if(!wordsFound)
		{
			logger.info("No words repeated more than twice");
		}
		
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
