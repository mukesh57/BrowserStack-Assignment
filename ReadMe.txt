###### PROJECT DETAILS ##########

Test covered: 

1. Ensure that the website's text is displayed in Spanish.
	- Here checking if language selected is ESPAÑA or not 
	
	- Test method : websiteTextValidationTest()
	
2. Printing first five title of articles from opinion page
	- Here getting all titles and printing 1st five from it
	- Using logs to get this print
	- Also handle is no title presents
	
	- Test method : printFirstFiveArticleTextsTest()
	
3. Translate the title of each article to English and printing
	- Here I am using rapidapi api's to get Spanish text to English
	- For now passing hard code value of language code as - es| en
	- This is also optimised for other language by replacing the language code
	- Using logs to get this print
	
	NOTE: My account have basic subscription with it max 50 api call can be made, so getting 429 error means limit exited 
	
	- Test method : translateToEnglishAndPrintTest()
	
4. Print words that are repeated more than twice across all headers combined with count
	- Here I am using Map to get this
	- Using logs to get this print
	
	-  Test method : analyzeTranslatedHeaders() 
	
5. Cross-Browser Testing
	- In local Cross-Browser testing can by configuration in browserstack_test.properties file
	- Also can run on Browser stack 
	
	
##### ADDITIONAL IPLIMENTATIONS ############

1. Log4j
2. Allure extend reporting


###### Yet TO IMPLEMENT OR ENHANCMENT #######

1. If available, download and save the cover image of each article to your local machine
	- Tried multiple ways but found some issues, With more time given I will be able to fix this.
2. Some test are failing on Mobile browsers 
	- For now this test are not handled for mobile browsers. needed some more time to fix these.
	







