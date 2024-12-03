Run inctructions:
Chromedriver was added to project because Selenide 5.21.0 was loading chromedriver 114, which was not compatible with installed browser version. Note! If your system does not support this version, please, replace it with newer one.

In terminal:
mvn clean compile
mvn test

In IDE (tested on IDEA): 
Right click on TravelPoliceTest file
Click Run

Test passed 03.12.2024, 20:00.
Environment: 
	System: Windows 11 Home, Chrome Version 131.0.6778.86 (Official Build) (64-bit)
	IDE: IntelliJ IDEA 2024.3 (Community Edition)

Structure:
drivers - contains chromedriver.exe, version 131.0.6778.85. 
java
	base
		BasePage - base page class for cunctions, common for all pages.
		BaseTest - base test class, including browser setup and other common functions
		TranslationHelper - class to read translation files and providing translation, when required. Added since site is in three languages and sometimes dealing with non-standart characters may be challenging.
	data
		InsuredPerson - added for insured data, to make field check on last step more universal.
	pageobjects
		TravelPolice - page object with functions, related to travel police.
	testcases
		TravelPoliceTest - test case itself
resources
	translations
		strings_lv.properties - necessary texts in Latvian
