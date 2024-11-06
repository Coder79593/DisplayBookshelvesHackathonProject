package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MenusPage extends BasePage {
    Actions actions;

    public MenusPage(WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
    }

    @FindBy(xpath = "//*[@data-category='2981' and contains(text(), 'Luxury')]")
    private WebElement luxuryMenu;

    @FindBy(xpath = "//*[@data-id='Sofas' and contains(text(), 'Sofas')]")
    private WebElement sofasMenu;

    @FindBy(xpath = "//*[@class='clip-catg-title text-sm font-medium text-truncate-2 ctg-white-space paddingTop-8']")
    private List<WebElement> menuItems;

    
    public boolean isLuxuryMenuDisplayed() {
    	try {
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    		WebElement filterBadg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-category='2981' and contains(text(), 'Luxury')]")));
    		return filterBadg.isDisplayed();
    	} catch(Exception e) {
    		return false;
    	}
	}
    public void hoverOverLuxuryMenu() throws InterruptedException {                    
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(luxuryMenu));
        actions.moveToElement(luxuryMenu).perform();
    }
    
    public boolean isSofaMenuDisplayed() {
    	try {
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    		WebElement filterBadg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-id='Sofas' and contains(text(), 'Sofas')]")));
    		return filterBadg.isDisplayed();
    	} catch(Exception e) {
    		return false;
    	}
	}

    public void hoverAndClickSofasMenu() throws InterruptedException {                  
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        

        wait.until(ExpectedConditions.visibilityOf(sofasMenu));
        actions.moveToElement(sofasMenu).perform();
        
        wait.until(ExpectedConditions.elementToBeClickable(sofasMenu)).click();
    }

    public List<WebElement> getMenuItems() {         
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='33%'");
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
    	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class='clip-catg-title text-sm font-medium text-truncate-2 ctg-white-space paddingTop-8']")));
    	wait.until(ExpectedConditions.visibilityOfAllElements(menuItems));
        return menuItems;
    }	
}

