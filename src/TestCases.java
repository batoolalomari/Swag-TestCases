import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
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
		Assert.assertEquals(ActualTitle, expectedTitle,"The system display another word");

	}

	@Test(priority = 3, description = "The system should sort the products from lowest to highest price")
	public void testPriceFilter() {
//		best practice to use Select	
		WebElement selectedElement = driver.findElement(By.className("product_sort_container"));
		Select selector = new Select(selectedElement);
		selector.selectByIndex(2);

	}

	@Test(priority = 4, description = "Verify that the system sort the products from lowest to highest")
	public void testLowestPriceFilter() {
		List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
		List<Double> pricesAsNumber = new ArrayList<Double>();
		Double ActuallowestPrice = 0.0;

		for (int i = 0; i < prices.size(); i++) {
			pricesAsNumber.add((Double.parseDouble(prices.get(i).getText().replace("$", ""))));
		}

		for (int j = 1; j < pricesAsNumber.size(); j++) {
			ActuallowestPrice = pricesAsNumber.get(0);
			System.out.println(ActuallowestPrice + " > " + pricesAsNumber.get((j)));
			Assert.assertEquals(ActuallowestPrice < pricesAsNumber.get((j)), true, "The First Price is not the lowest price");

		}

	}

}
