package seleniumObjects;

import models.ExceptionWrapperRule;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CommonPage {
	private static CommonPage instance=null;
	private WebDriver driver;
	private WebDriver Mozilla=null;
	
	@Rule
	public ExceptionWrapperRule ewr = new ExceptionWrapperRule();
	
	protected CommonPage(){
		
	}
	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @param driver the driver to set
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	public WebDriver openBrowser(){
		if(Mozilla==null){
		driver=new FirefoxDriver();
		Mozilla = driver;
		}else if(Mozilla!=null){
			driver=Mozilla;
		}
		driver.manage().window().maximize();
		return driver;
	}
	
	public WebDriver closeBrowser(){
		if(Mozilla==null){
			driver=Mozilla;
		}else if(Mozilla!=null){
			driver.quit();
			driver=null;
			Mozilla = null;
		}
		return driver;
	}
	public CommonPage loadPage(String url)
	{
		driver.get(url);
		return CommonPage.instance;
	}
	
	public static CommonPage getInstance(){
		if(instance==null){
			instance = new CommonPage();
		}
		return instance;
	}
}
