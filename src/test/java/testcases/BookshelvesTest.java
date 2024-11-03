package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import drivers.BaseClass;
import pages.BookshelvesPage;
import utility.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookshelvesTest extends BaseClass{
    private static final Logger logger = Logger.getLogger(BookshelvesTest.class);
    WebDriver driver;
    BookshelvesPage bookshelvesPage;

    @BeforeClass
    public void setup() {
    	logger.info("Initializing the WebDriver and setting up the page objects");
    	driver = initializeBrowser();
    	bookshelvesPage = new BookshelvesPage(driver);
    }

    @Test(priority = 1, groups = {"smoke"})
    public void searchBookshelvesTest() throws InterruptedException, IOException  {
    	logger.info("Starting test: searchBookshelvesTest");
    	Assert.assertTrue(bookshelvesPage.isSearchtextDisplayed(),"Search not Displayed");
        bookshelvesPage.searchForBookshelves("bookshelves");
        logger.info("Search for 'bookshelves' performed successfully");
        
    }

    @Test(priority = 2, groups = {"smoke"})
    public void applyFilterTest() throws InterruptedException, IOException {
    	logger.info("Starting test: applyFilterTest");
        bookshelvesPage.applyFilter();
    }

    @Test(priority = 3, groups = {"smoke"})
    public void applyPriceFilterTest() throws InterruptedException, IOException {
    	Assert.assertTrue(bookshelvesPage.isPriceFilterDisplayed(),"Price Filter was not Displayed");
    	Assert.assertTrue(bookshelvesPage.isApplybtnDisplayed(),"Apply button was not Displayed");
        bookshelvesPage.applyPriceFilter("15000");
        logger.info("Price Filter applied successfully");   
    }

    @Test(priority = 4, groups = {"smoke"})
    public void sortByLowestPriceTest() throws InterruptedException, IOException {
    	logger.info("Starting test: sortByLowestPriceTest");
    	Assert.assertTrue(bookshelvesPage.isSortbyfilterDisplayed(),"Sort by Filter was not Displayed");
        bookshelvesPage.sortByLowestPrice();
        logger.info("Sort by Filter applied successfully");
    }

    @Test(priority = 5, groups = {"regression"})
    public void extractBookshelfDetailsToExcelTest() throws Exception {
    	logger.info("Starting test: extractBookshelfDetailsToExcelTest");
        List<String> productNames = bookshelvesPage.getProductNames();
        List<String> productPrices = bookshelvesPage.getProductPrices();
        logger.info("Retrieved product names and prices successfully");

        List<List<String>> excelData = new ArrayList<>();
        List<String> header = new ArrayList<>();
        header.add("Product Name");
        header.add("Product Price");
        excelData.add(header);
        logger.info("Header added to excelData");

        for (int i = 0; i < Math.min(3, Math.min(productNames.size(), productPrices.size())); i++) {
            List<String> row = new ArrayList<>();
            String productName = productNames.get(i);
            String productPrice = productPrices.get(i);
            row.add(productName);
            row.add(productPrice);
            excelData.add(row);
            logger.info("Added product: " + productNames.get(i)   +  " with price: " + productPrices.get(i));
            
            System.out.println("Product " + (i + 1) + ": Name = " + productName + ", Price = " + productPrice);
        }

        String filePath = "Output_Excel/BookshelvesData.xlsx";
        excel.writeToExcel(filePath, excelData);
        logger.info("Data written to Excel file: " + filePath);
        System.out.println("Bookshelves data written to Excel file: " + filePath);
        
        Assert.assertTrue(new File(filePath).exists(),"Excel file was not created successfully");
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
