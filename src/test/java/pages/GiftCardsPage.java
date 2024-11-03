package pages;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.ScreenshotUtility;

public class GiftCardsPage extends BasePage {

    public GiftCardsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@data-test='giftCard']")
    private WebElement giftCardLink;

    @FindBy(xpath = "//img[@alt='Pepperfry Birthday Gift Card']")
    private WebElement birthdayGiftCard;

    @FindBy(xpath = "//input[@formcontrolname='rname']")
    private WebElement recipientName;

    @FindBy(xpath = "//input[@formcontrolname='rmob']")
    private WebElement recipientMobile;

    @FindBy(xpath = "//input[@formcontrolname='rmail']")
    private WebElement recipientEmail;

    @FindBy(xpath = "//textarea[@formcontrolname='rmsg']")
    private WebElement giftMessage;

    @FindBy(xpath = "//input[@formcontrolname='sname']")
    private WebElement senderName;

    @FindBy(xpath = "//input[@formcontrolname='smob']")
    private WebElement senderMobile;

    @FindBy(xpath = "//input[@formcontrolname='smail']")
    private WebElement senderEmail;

    @FindBy(xpath = "/html/body/app-root/main/pf-gc-product-page/div/div/div/div/div[1]/section/div/div[2]/div[2]/pf-gc-denomination-card/div/div[2]/div[1]/a") // Updated XPath for robustness
    private WebElement denominations;

    @FindBy(xpath = "//div[contains(text(), 'Email ID Cannot Be Empty')]")
    private WebElement emptyEmailError;

    @FindBy(xpath = "//div[contains(text(), 'Enter a Valid Email ID')]")
    private WebElement invalidEmailError;


    public boolean isGiftCardLinkDisplayed() {
		try {
            WebElement filterBadge = driver.findElement(By.xpath("//*[@data-test='giftCard']"));
            return filterBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
	}
    public void clickGiftCardLink() throws IOException {                               
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='33%'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        ScreenshotUtility.captureScreenshot(driver, "PepperfryHomePage");

        wait.until(ExpectedConditions.elementToBeClickable(giftCardLink)).click();
        js.executeScript("document.body.style.zoom='50%'");
    }
    
    public boolean isHbdGiftCardDisplayed() {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='33%'");
    	try {
    		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement filterBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Pepperfry Birthday Gift Card']")));
            return filterBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
	}

    public void selectBirthdayGiftCard() throws IOException {                          
    	
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        ScreenshotUtility.captureScreenshot(driver, "GiftCardPage");

        ScreenshotUtility.captureScreenshot(driver, "HappyBirthdayGiftCardSelect");

        wait.until(ExpectedConditions.elementToBeClickable(birthdayGiftCard)).click();
    }

    public boolean isFormtextboxDisplayed() {
    	try {
            WebElement filterBadge = driver.findElement(By.xpath("//input[@formcontrolname='rname']"));
            return filterBadge.isDisplayed();
        } catch (Exception e) {
            return false;
        }
	}
    public void fillRecipientDetails(String name, String mobile, String email, String message) {    
     	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("document.body.style.zoom='50%'");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOf(recipientName)).sendKeys(name);
        recipientMobile.sendKeys(mobile);
        recipientEmail.sendKeys(email);
        giftMessage.sendKeys(message);
    }

    public void fillSenderDetails(String name, String mobile, String email) throws IOException {          
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOf(senderName)).sendKeys(name);
        senderMobile.sendKeys(mobile);
        senderEmail.sendKeys(email);
      
        ScreenshotUtility.captureScreenshot(driver, "GiftCardDetailsFilled");
    }

    public void selectDenominations() throws InterruptedException {                     
    	JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/main/pf-gc-product-page/div/div/div/div/div[1]/section/div/div[2]/div[2]/pf-gc-denomination-card/div/div[2]/div[1]/a")));

        js.executeScript("arguments[0].scrollIntoView(true);", denominations);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(denominations)).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", denominations);
        }
    }

    public String getEmptyEmailError() {                    
        return emptyEmailError.getText();
    }

    public String getInvalidEmailError() {                          
        return invalidEmailError.getText();
    }
	
}


