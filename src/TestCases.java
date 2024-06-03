import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TestCases {

//	Global declaration
	WebDriver driver = new ChromeDriver();
	String webUrl;

	@BeforeTest
	public void Setup() {

		webUrl = "https://www.saucedemo.com/";

	}

	@Test(priority = 1)
	public void Login() throws InterruptedException {

		driver.get(webUrl);
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();

		Thread.sleep(1500);
	}

	@Test(priority = 2, description = "A word 'Products' should be appear after login")
	public void testProductTitle() {
		String expectedTitle = "Products";

//		no id for the the element so i used the best practice for xpath
		String ActualTitle = driver.findElement(By.xpath("//span[@data-test='title']")).getText();

		System.out.println("Actual Title: " + ActualTitle);
		System.out.println("Expected Title: " + expectedTitle);
		Assert.assertEquals(ActualTitle, expectedTitle);

	}

	@Test(priority = 3, description = "The system should sort the products from lowest to highest price")
	public void testPriceFilter() {

//		no id for the the element so i used the best practice for xpath
//		do not use this way because it select the first match one
//		driver.findElement(By.xpath("//select[@data-test='product-sort-container']")).click();
//		driver.findElement(By.xpath("//option[@value='lohi']")).click();
		
//		best practice to use xpath
//		WebElement selectedElement=driver.findElement(By.xpath("//select[@data-test='product-sort-container']"));
		
//		best practice to use Select	
		WebElement selectedElement=driver.findElement(By.className("product_sort_container"));
		Select selector=new Select(selectedElement);
		selector.selectByIndex(2);
//		selector.selectByValue("lohi");
//		selector.selectByVisibleText("Price (low to high)");
		
	}

	@Test(priority = 4, description = "Verify that the system sort the products from lowest to highest")
	public void testLowestPriceFilter() {
		String expectedLowestPrice = "$7.99";
		List<WebElement> prices=driver.findElements(By.className("inventory_item_price"));
		
		String ActualLowestPrice="" ;
		for (int i = 0; i < prices.size(); i++) {
			
			ActualLowestPrice=prices.get(0).getText();
			
		}
		
		System.out.println("Actual Price " + expectedLowestPrice);
		System.out.println("Expected Price " + ActualLowestPrice);
		Assert.assertEquals(ActualLowestPrice, expectedLowestPrice);

	}

}
