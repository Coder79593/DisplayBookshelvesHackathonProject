package pages;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.ScreenshotUtility;

public class BookshelvesPage extends BasePage {

    public BookshelvesPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(id = "search")
    private WebElement searchField;

    @FindBy(xpath = "//span[@class='pf-col xs-8 hd-mobile-autosuggest-left text-sm cursor-pointer']")
    private WebElement searchButton;

    @FindBy(css = "span.more-filter.text-md")
    private WebElement filterButton;

    @FindBy(xpath = "//*[contains(text(), ' Price ')]")
    private WebElement priceFilter;

    @FindBy(xpath = "//input[@formcontrolname='inputMax']")
    private WebElement priceInputBox;

    @FindBy(xpath = "//*[@class='ng-star-inserted' and contains(text(), 'APPLY')]")
    private WebElement applyButton;

    @FindBy(xpath = "//div[@class='dropdown-btn']")
    private WebElement sortDropdown;

    @FindBy(xpath = "//*[contains(text(), 'Lowest Priced First')]")
    private WebElement lowestPriceOption;

    @FindBy(xpath = "//h2[@class='product-name color-tertiary text-md font-medium ng-star-inserted']")
    private List<WebElement> productNames;

    @FindBy(xpath = "//span[@class='product-offer-price font-bold text-xl ng-star-inserted']")
    private List<WebElement> productPrices;

   

    public void searchForBookshelves(String query) throws IOException {            
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='75%'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        wait.until(ExpectedConditions.visibilityOf(searchField)).sendKeys(query);
        ScreenshotUtility.captureScreenshot(driver, "BookshelvesHomePage");
        
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        ScreenshotUtility.captureScreenshot(driver, "BookshelvesSearched");
        
   
        
        js.executeScript("window.scrollBy(0,150)");
    }

    public boolean isSearchtextDisplayed() {                           
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    		WebElement filterBadg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
    		return filterBadg.isDisplayed();
    	} catch(Exception e) {
    		return false;
    	}
	}
    public void applyFilter() throws IOException {    
    	JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(filterButton)).click();
        } catch (ElementClickInterceptedException e) {

            js.executeScript("arguments[0].click();", filterButton);
        }
        ScreenshotUtility.captureScreenshot(driver, "FilterApply");
    }

    
    public boolean isPriceFilterDisplayed() {            
        try {
        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement filterBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), ' Price ')]")));
            return filterBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    

    public void applyPriceFilter(String maxPrice) throws IOException {                      
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        

        wait.until(ExpectedConditions.elementToBeClickable(priceFilter)).click();
        

        wait.until(ExpectedConditions.visibilityOf(priceInputBox)).clear();
        priceInputBox.sendKeys(maxPrice);
        ScreenshotUtility.captureScreenshot(driver, "PriceFilterApply");

        wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
    }
    

    
    public boolean isApplybtnDisplayed() {              
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    		WebElement filterBadg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ng-star-inserted' and contains(text(), 'APPLY')]")));
    		return filterBadg.isDisplayed();
    	} catch(Exception e) {
    		return false;
    	}
	}

    public boolean isSortbyfilterDisplayed() {                                                      
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    		WebElement filterBadg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dropdown-btn']")));
    		return filterBadg.isDisplayed();
    	} catch(Exception e) {
    		return false;
    	}
	}
    public void sortByLowestPrice() throws IOException {                                  
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)).click();
        ScreenshotUtility.captureScreenshot(driver, "LowestPriceSelected");
        
        wait.until(ExpectedConditions.elementToBeClickable(lowestPriceOption)).click();
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
        
        ScreenshotUtility.captureScreenshot(driver, "BookshelvesPictures");
    }
    


    public List<String> getProductNames() {                                      
        return productNames.stream().map(WebElement::getText).toList();
    }
 
    public List<String> getProductPrices() {                                         
        return productPrices.stream().map(WebElement::getText).toList();
    }
	
}

