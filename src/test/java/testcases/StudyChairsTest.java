package testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import org.testng.Assert;

import drivers.BaseClass;
import pages.StudyChairsPage;
import utility.excel;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudyChairsTest extends BaseClass{
	private static final Logger logger = Logger.getLogger(StudyChairsTest.class);

    WebDriver driver;
    StudyChairsPage studyChairsPage;

    @BeforeClass
    public void setup() throws IOException {
    	logger.info("Initializing the WebDriver and setting up the page objects");
    	driver = initializeBrowser();
        studyChairsPage = new StudyChairsPage(driver);
    }

    @Test(priority = 1, groups = {"smoke"})
    public void searchStudyChairs() throws InterruptedException {
    	logger.info("Starting test: searchStudyChairs");
    	Assert.assertTrue(studyChairsPage.isSearchtextDisplayed(),"Search not Displayed");
        studyChairsPage.searchForStudyChairs("study chairs");
        logger.info("Search for 'study chairs' performed successfully");
    }

    @Test(priority = 2, groups = {"smoke"})
    public void applyFilters() throws InterruptedException { //assert
    	logger.info("Starting test: applyFiltersTest");
        studyChairsPage.applyFilter();
    }

    @Test(priority = 3, groups = {"smoke"})
    public void setPriceAndApply() throws InterruptedException {
    	Assert.assertTrue(studyChairsPage.isPriceFilterDisplayed(),"Price Filter was not Displayed");
    	Assert.assertTrue(studyChairsPage.isApplybtnDisplayed(),"Apply button was not Displayed");
        studyChairsPage.setPriceFilter("15000");
        logger.info("Price Filters applied successfully");
    }

    @Test(priority = 4, groups = {"smoke"})
    public void sortByCustomerRating() throws InterruptedException, IOException {   
    	logger.info("Starting test: sortByCustomerRating");
    	Assert.assertTrue(studyChairsPage.isSortbyfilterDisplayed(),"Sort by Filter was not Displayed");
        studyChairsPage.sortByCustomerRatings();
        logger.info("Sort By Filters applied successfully");
    }

    @Test(priority = 5, groups = {"regression"})
    public void extractStudyChairsDetails() throws IOException {  //assert
    	logger.info("Starting test: extractStudyChairsDetails");
    	List<String> productName = studyChairsPage.getProductName();
        List<String> productPrice = studyChairsPage.getProductPrice();
        logger.info("Product names and prices Retrieved successfully");

    
        List<List<String>> excelData = new ArrayList<>();
        List<String> header = new ArrayList<>();
        header.add("Product Name");
        header.add("Product Price");
        excelData.add(header);
        logger.info("Added Header to excelData");

 
        for (int i = 0; i < Math.min(3, Math.min(productName.size(), productPrice.size())); i++) {
            List<String> row = new ArrayList<>();
            String Name = productName.get(i);
            String Price = productPrice.get(i);
            row.add(Name);
            row.add(Price);
            excelData.add(row);
            logger.info("Product: " + productName.get(i)   +  " with price: " + productPrice.get(i));
            
            System.out.println("Product " + (i + 1) + ": Name = " + Name + ", Price = " + Price);
        }

        String filePat = "Output_Excel/StudyChairsData.xlsx";
        excel.writeToExcel(filePat, excelData);
        logger.info("Written Data to Excel file: " + filePat);

        Assert.assertTrue(new File(filePat).exists(), "Excel file was not created successfully");
        logger.info("Successfully verified Excel file");

        System.out.println("Study chairs data written to Excel: " + filePat);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
        	logger.info("Closing the WebDriver");
            driver.quit();
        }
    }
}



