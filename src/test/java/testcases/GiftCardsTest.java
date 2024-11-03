package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import drivers.BaseClass;
import pages.GiftCardsPage;
import utility.excel;
import utility.readexcel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GiftCardsTest extends BaseClass{
	private static final Logger logger = Logger.getLogger(GiftCardsTest.class);

    WebDriver driver;
    GiftCardsPage giftCardsPage;
    @BeforeClass
    public void setup() throws IOException {
    	logger.info("Initializing WebDriver and setting up the page objects");
    	driver = initializeBrowser();
    	giftCardsPage = new GiftCardsPage(driver);
    }

    @Test(priority = 1, groups = {"smoke"})
    public void selectGiftCardTest() throws InterruptedException, IOException {
    	logger.info("Starting test: selectGiftCardTest");
    	Assert.assertTrue(giftCardsPage.isGiftCardLinkDisplayed(),"GiftCard menu not Displayed");
        giftCardsPage.clickGiftCardLink();
        Assert.assertTrue(giftCardsPage.isHbdGiftCardDisplayed(),"Happy Birthday GiftCard not Displayed");
        giftCardsPage.selectBirthdayGiftCard();
        logger.info("Selected gift card successfully");
    }

    @Test(priority = 2, groups = {"regression"})
    public void fillGiftDetailsTest() throws InterruptedException, IOException {
    	logger.info("Starting test: fillGiftDetailsTest");
        String filePath = "Input_Excel/GiftCardInputData.xlsx";
        String sheetName = "Sheet1";
        String[][] data = readexcel.readExcelData(filePath, sheetName);
        Assert.assertTrue(giftCardsPage.isFormtextboxDisplayed(),"Form not Displayed");
        giftCardsPage.fillRecipientDetails(data[0][0], data[0][1],data[0][2],data[0][3]);
        giftCardsPage.fillSenderDetails(data[1][0], data[1][1],data[1][2]);
        
    }
    
    @Test(priority = 3, groups = {"regression"})
    public void selectdenomination() throws InterruptedException {
    	giftCardsPage.selectDenominations();
    	logger.info("All data filled successfully");
    }
    
    @Test(priority = 4, groups = {"regression"})
    public void extractErrorMessagesToExcelTest() throws Exception {
    	logger.info("Starting test: extractErrorMessagesToExcelTest");
        List<List<String>> errorData = new ArrayList<>();
        logger.info("Retrieved error messages successfully");

        String emptyEmailError = giftCardsPage.getEmptyEmailError();
        Assert.assertEquals(emptyEmailError, "Sender's Email ID Cannot Be Empty","Empty email error message is incorrect");
        
        String invalidEmailError = giftCardsPage.getInvalidEmailError();
        Assert.assertEquals(invalidEmailError, "Enter a Valid Email ID","Invalid email error message is incorrect");
        
        List<String> header = new ArrayList<>();
        header.add("Error Type");
        header.add("Error Message");
        errorData.add(header);
        logger.info("Header added to excelData succesfully");

        
        List<String> row1 = new ArrayList<>();
        row1.add("Empty Email Error");
        row1.add(giftCardsPage.getEmptyEmailError());
        errorData.add(row1);

        List<String> row2 = new ArrayList<>();
        row2.add("Invalid Email Error");
        row2.add(giftCardsPage.getInvalidEmailError());
        errorData.add(row2);

        System.out.println("Extracted Error Messages:");
        System.out.println("Error Type: " + row1.get(0) + " | Message: " + row1.get(1));
        System.out.println("Error Type: " + row2.get(0) + " | Message: " + row2.get(1));
        
        String filePath = "Output_Excel/GiftCardErrors.xlsx";
        excel.writeToExcel(filePath, errorData);
        logger.info("Data written to Excel file: " + filePath);
        
        Assert.assertTrue(new File(filePath).exists(),"Excel file was not created successfully");
        System.out.println("Error messages written to Excel: " + filePath);
        logger.info("Excel file verified successfully");
    }

    
    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
        	logger.info("Closing the WebDriver");
            driver.quit();
        }
    }
}

