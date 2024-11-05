package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import drivers.BaseClass;
import pages.MenusPage;
import utility.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Menus  extends BaseClass{
	private static final Logger logger = LogManager.getLogger(Menus.class);
    WebDriver driver;
    MenusPage menusPage;

    @BeforeClass
    public void setup() throws IOException {
  
    	logger.info("Initializing WebDriver and setting up page objects");
    	driver = initializeBrowser();
        menusPage = new MenusPage(driver);
    }

    @Test(priority = 1,groups = {"smoke"})
    public void luxuruMenuhover() throws InterruptedException {
    	logger.info("Starting test: MenuTest");
    	Assert.assertTrue(menusPage.isLuxuryMenuDisplayed(),"Luxury menu not Displayed");
    	menusPage.hoverOverLuxuryMenu();
    }
    
    
    @Test(priority = 2,groups = {"smoke"})
    public void sofaMenuhover() throws InterruptedException {
    	Assert.assertTrue(menusPage.isSofaMenuDisplayed(),"Sofa submenu not Displayed");
    	menusPage.hoverAndClickSofasMenu();
    	
    }
    @Test(priority = 3, groups = {"smoke"})
    public void extractMenuItemsToExcel() throws InterruptedException, IOException {
    	logger.info("Starting test: extractMenuItemsToExcel");
  
        List<WebElement> menuItems = menusPage.getMenuItems();
        System.out.println("Extracted Menu Items:");
        logger.info("Retrieved sub menu data successfully");

        List<List<String>> excelData = new ArrayList<>();

        for (int i = 0; i < menuItems.size(); i++) {
            String menuItemName = menuItems.get(i).getText();
            System.out.println("Menu Item " + (i + 1) + ": " + menuItemName);

            List<String> rowData = new ArrayList<>();
            rowData.add("Menu Item " + (i + 1));
            rowData.add(menuItemName);
            excelData.add(rowData);  
        }

        String filePath = "Output_Excel/MenuData.xlsx";
        excel.writeToExcel(filePath, excelData);
        logger.info("Data written to Excel file: " + filePath);
        Assert.assertTrue(new File(filePath).exists(),"Excel file was not created successfully");
        System.out.println("Menu data written to Excel: " + filePath);
        logger.info("Excel file Verified Successfully");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
        	logger.info("Closing the WebDriver");
            driver.quit();
        }
    }
}



