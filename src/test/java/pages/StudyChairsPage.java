package pages;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.ElementClickInterceptedException;

import utility.ScreenshotUtility;

public class StudyChairsPage extends BasePage {

    public StudyChairsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@id='search']")
    private WebElement searchBox;

    @FindBy(xpath = "//span[@class='pf-col xs-8 hd-mobile-autosuggest-left text-sm cursor-pointer']")
    private WebElement searchButton;

    @FindBy(css = "span.more-filter.text-md")
    private WebElement filterButton;

    @FindBy(xpath = "//*[contains(text(), ' Price ')]")
    private WebElement priceFilter;

    @FindBy(xpath = "//input[@formcontrolname='inputMax']")
    private WebElement priceInputBox;

    @FindBy(xpath = "//*[contains(text(), 'APPLY')]")
    private WebElement applyButton;

    @FindBy(xpath = "//div[@class='dropdown-btn']")
    private WebElement sortByDropdown;

    @FindBy(xpath = "//li[contains(text(), 'Customer Ratings')]")
    private WebElement customerRatings;                                  

    @FindBy(xpath = "//h2[@class='product-name color-tertiary text-md font-medium ng-star-inserted']")
    private List<WebElement> productName;

    @FindBy(xpath = "//span[@class='product-offer-price font-bold text-xl ng-star-inserted']")
    private List<WebElement> productPrice;

    // Methods to interact with elements
    public void searchForStudyChairs(String keyword) {      
    JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='50%'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        wait.until(ExpectedConditions.visibilityOf(searchBox)).sendKeys(keyword);
        try  {
            wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        } catch (ElementClickInterceptedException e) {

            js.executeScript("arguments[0].click();", searchButton);
        }
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

    public void applyFilter() {                                
    JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='50%'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        
        try {
            wait.until(ExpectedConditions.elementToBeClickable(filterButton)).click();
        } catch (ElementClickInterceptedException e) {

            js.executeScript("arguments[0].click();", filterButton);
        }
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
    
    public boolean isApplybtnDisplayed() {              
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    		WebElement filterBadg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='ng-star-inserted' and contains(text(), 'APPLY')]")));
    		return filterBadg.isDisplayed();
    	} catch(Exception e) {
    		return false;
    	}
	}

    public void setPriceFilter(String maxPrice) {                       
    JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='50%'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
       

        try {
            wait.until(ExpectedConditions.elementToBeClickable(priceFilter)).click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", priceFilter);
        }

        wait.until(ExpectedConditions.visibilityOf(priceInputBox)).clear();
        priceInputBox.sendKeys(maxPrice);

        js.executeScript("arguments[0].scrollIntoView(true);", applyButton);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", applyButton);
        }

        js.executeScript("window.scrollBy(0,650)");
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

    public void sortByCustomerRatings() throws IOException {                     
    JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='33%'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        

        try {
            wait.until(ExpectedConditions.elementToBeClickable(sortByDropdown)).click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", sortByDropdown);
        }


        wait.until(ExpectedConditions.elementToBeClickable(customerRatings)).click();


        ScreenshotUtility.captureScreenshot(driver, "StudyChairsPictures");
    }

    public List<String> getProductName() {                                       
        return productName.stream().map(WebElement::getText).toList();
    }

    public List<String> getProductPrice() {                                      
        return productPrice.stream().map(WebElement::getText).toList();
    }   
}




