### Selenium Automation
Assignment For OLX- Imdb Test

#### System Requirement:

* JDK 1.8 or above
* Maven 3.1
* Eclipse or IDE of choice in case there is need to update the script. (optional)
* For execution of scripts on Chrome need to have executable files for both drivers respectively and paste them at location "\src\test\resources\drivers" in project folder.
* You can download these executable files from below links
  * Chrome: http://chromedriver.chromium.org/downloads

#### Execution Steps:
- Execute src/test/java/test/testsclasses/ImdbTest.java as testng class or
- Please follow the instructions to execute the tests on local:
 mvn clean verify -Dbrowser="Browser Name, for eg:chrome" -Dtier="Tier Name, for eg: Test" -DtestXml="Test Suite, for eg:TestNG_Assignment.xml"
    
#### Project Structure:
- Page Classses: src\test\java\test\actions
- API Classes: src\test\java\test\actions\RestAPIActions
- Locators: src\test\resources\PageObjectRepository\{tier}
- Test Classes: src/test/java/test/testsclasses

#### Result Files:	
The Test Execution Results will be stored in the following directory once the test has completed

    ./target/test-output/emailable-report.html (for complete test suite)
    ./target/surefire-reports/emailable-report.html (for single test suite)
