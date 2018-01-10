package selenium
.study.frame;

import static org.testng.Assert.expectThrows;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.awt.AWTException;
import java.awt.Robot;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public  class WrapperClass extends Main {

	public static Properties prop=new Properties();
	public WrapperClass() {


		try {

			prop.load(new FileInputStream("C:\\Users\\alsamraj\\workspace\\OwnFrame\\Properties.properties"));

		} catch (FileNotFoundException e) {

			System.out.println("Properties File is not available");

		} catch (IOException e) {

			e.printStackTrace();

		}
		System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriver_Location"));
		driver =new ChromeDriver();
		driver.manage().window().maximize();
	}

	public void click(String Attribute, String AttributeValue){

		WebElement clickable=LocateHereDefault(Attribute,AttributeValue);
		clickable.click();

	}


	public void write(String Attribute, String AttributeValue,String textvalue){

		WebElement Writable=LocateHereDefault(Attribute,AttributeValue);
		Writable.sendKeys(textvalue);

	}

	public void dragndrop(String srcAttribute, String srcAttributeValue, String destAttribute, String destAttributeValue){

		WebElement src=LocateHereDefault(srcAttribute, srcAttributeValue);
		WebElement dest=LocateHereDefault(destAttribute, destAttributeValue);
		Actions cursor =new Actions(driver);
		cursor.dragAndDrop(src, dest);

	}

	public void selectFile(String Locationoffile) throws InterruptedException, AWTException{

		Robot robot = new Robot();
		StringSelection selecttext =new StringSelection(Locationoffile);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selecttext, null);
		Thread.sleep(3000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.delay(1500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(4000);

	}

	public void scroll(String scrollvalue){

		JavascriptExecutor jsx=(JavascriptExecutor)driver;
		jsx.executeScript("window.scrollBy(0,"+scrollvalue+")", "");

	}

	public void doubleClick(String Attribute, String AttributeValue){

		Actions cursor= new Actions(driver);
		WebElement locatedElement=LocateHereDefault(Attribute, AttributeValue);
		cursor.doubleClick(locatedElement).perform();

	}
	

	public void acceptAlert(){

		WebDriverWait waiting= new WebDriverWait(driver,30);
		waiting.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();

	}

	public void frameSwitch(String resultframe){

		WebDriverWait waiting= new WebDriverWait(driver,30);
		waiting.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(resultframe));
	}

	public void screenshot(String filename, String filetype) throws Throwable{

		File pic=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(pic, new File("D:\\own\\Automated PAs\\"+filename+"."+filetype));
	}

	public void selectThat(String Attribute, String AttributeValue,String selectby,String value){

		WebElement selectable=LocateHereDefault(Attribute,AttributeValue);
		Select dropDown = new Select(selectable);
		if(selectby.equalsIgnoreCase("By value")){
			dropDown.selectByValue(value);
		}
		else if(selectby.equalsIgnoreCase("By Visibletext")){
			dropDown.selectByVisibleText(value);
		}
		else if(selectby.equalsIgnoreCase("By Index")){
			dropDown.selectByIndex(Integer.parseInt(value));
		}
		else {
			System.out.println("Please select valid Selectors");
		}
	}

	public void switchWindow(String method, String elementval){

		Set <String> windowhandle=driver.getWindowHandles();
		if(method.equalsIgnoreCase("by handle")){
			for( String loopwin:windowhandle){
				driver.switchTo().window(loopwin);
				String currenthandle=driver.getWindowHandle();
				if(currenthandle.equals(elementval)){
					break;
				}
			}
		}
		else if(method.equalsIgnoreCase("By URL")){
			for( String loopwin:windowhandle){
				driver.switchTo().window(loopwin);	
				String url=driver.getCurrentUrl();
				if(url.equals(elementval)){
					break;
				}
			}
		}
		else if(method.equalsIgnoreCase("By Title")){
			for( String loopwin:windowhandle){
				driver.switchTo().window(loopwin);
				String Title=driver.getTitle();
				if(Title.contains(elementval)){
					break;
				}
			}
		}
		else if(method.equalsIgnoreCase("Lookupwindow"))
		{
			for(String loopWindow:windowhandle)
			{
				if(!(elementval.equals(loopWindow)))
				{
					driver.switchTo().window(loopWindow);
				}
			}
		}
	}

	void waitForNow(String type, String value){

		WebDriverWait waiting= new WebDriverWait(driver,30);
		if(type.equalsIgnoreCase("Xpath")){

			waiting.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(value)));

		}
		if(type.equalsIgnoreCase("id")){

			waiting.until(ExpectedConditions.visibilityOfElementLocated(By.id(value)));

		}
		if(type.equalsIgnoreCase("classname")){

			waiting.until(ExpectedConditions.visibilityOfElementLocated(By.className(value)));

		}
		if(type.equalsIgnoreCase("partial link text")){

			waiting.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(value)));

		}
		if(type.equalsIgnoreCase("link text")){

			waiting.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(value)));

		}
		if(type.equalsIgnoreCase("name")){

			waiting.until(ExpectedConditions.visibilityOfElementLocated(By.name(value)));

		}
		if(type.equalsIgnoreCase("cssselector")){

			waiting.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(value)));

		}
		if(type.equalsIgnoreCase("tag name")){

			waiting.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(value)));

		}
	}

	private WebElement LocateHereDefault(String type, String Value){



		WebElement Action = null;
		if(type.equalsIgnoreCase("id")){

			waitForNow(type,Value);
			Action=driver.findElement(By.id(Value));

		}
		else if(type.equalsIgnoreCase("name")){

			waitForNow(type,Value);
			Action=driver.findElement(By.name(Value));

		}
		else if(type.equalsIgnoreCase("classname")){

			waitForNow(type,Value);
			Action=driver.findElement(By.className(Value));

		}
		else if(type.equalsIgnoreCase("linktext")){

			waitForNow(type,Value);
			Action=driver.findElement(By.linkText(Value));

		}
		else if(type.equalsIgnoreCase("partiallinktext")){

			waitForNow(type,Value);
			Action=driver.findElement(By.partialLinkText(Value));

		}
		else if(type.equalsIgnoreCase("Xpath")){

			waitForNow(type,Value);
			Action=driver.findElement(By.xpath(Value));

		}
		else if(type.equalsIgnoreCase("CssSelector")){

			waitForNow(type,Value);
			Action=driver.findElement(By.cssSelector(Value));

		}
		else if(type.equalsIgnoreCase("Tagname")){

			waitForNow(type,Value);
			Action=driver.findElement(By.tagName(Value));

		}
		return Action;
	}

}