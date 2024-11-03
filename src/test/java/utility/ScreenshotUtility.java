package utility;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;


public class ScreenshotUtility {
	
	
    public static String captureScreenshot(WebDriver driver, String screenshotName) throws IOException {
            
    	
        	File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        	
        
    		FileUtils.copyFile(screenshotFile, new File(".//screenshot/"+screenshotName+".png"));
    		
    	
			return screenshotName;
    }
}
