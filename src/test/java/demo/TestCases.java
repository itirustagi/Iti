package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.decorators.WebDriverDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */
   

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    public void logstatus(String step, String message, String status){ 
        System.out.println(String.format("%s | %s | %s",step,message,status));
    }
    
    @Test(enabled = true, description = "Search \"Search Washing Machine \".Sort by popularity and print the count of items with rating less than or equal to 4 stars.")
    public void testCase01() throws InterruptedException{
        logstatus("Start TestCAse01 :", "Search and Sort \"Washing Machine \"", "DONE");
        boolean status = false;
        logstatus("Step 1", "Go to www.flipkart.com", "Done");
        
        // Navigate to "https://www.flipkart.com/"
        driver.get("https://www.flipkart.com/");
        
        //crate instance of WebDriverWait
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        logstatus("Step 2", "search \"Washing Machine\"", "Done");
        //search "Washing Machine" 
        WebElement seachBar = driver.findElement(By.xpath("//input[@class='Pke_EE']"));
        //wait.until(ExpectedConditions.visibilityOf(seachBar));
        seachBar.sendKeys("Washing Machine");

        WebElement searchButton = driver.findElement(By.xpath("//button[@class='_2iLD__']"));
        //wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();

        logstatus("Step 3", "Sort by popularity", "Done");
        //Sort by popularity 
        WebElement popularityElement = driver.findElement(By.xpath("//div[@class='sHCOk2']/div[text()='Popularity']"));
       // wait.until(ExpectedConditions.elementToBeClickable(popularityElement));
        popularityElement.click();

        logstatus("Step 4", "Print the count of items with rating less than or equal to 4 stars.", "Done");
        //print the count of items with rating less than or equal to 4 stars.
        int count =0;   

        Thread.sleep(1000); 
        List<WebElement> ratingElement = driver.findElements(By.xpath("//div[@class='XQDdHH']"));  
        //wait.until(ExpectedConditions.visibilityOfAllElements(ratingElement));
        for(WebElement rate : ratingElement ){  
            float ratingText = Float.parseFloat(rate.getText()); 
            System.out.println(ratingText);           
            if(ratingText<=4.0){
                count ++;
                status = true;              
            }
        }
        System.out.println("Count is :"+count);        
        
        if(!status){
        logstatus("End TestCase01", "Search and Sort Unsuccessful", "FAIL");
        }
        else{
            logstatus("End TestCase01", "Search and Sort Successful", "PASS");
        }
    }

    @Test(enabled = true,description = "Search \"iPhone\", print the Titles and discount % of items with more than 17% discount")
    public void testCase02() throws InterruptedException{
        logstatus("Start TestCAse02 :", "Search \"iPhone\", print the Titles and discount % of items with more than 17% discount", "DONE");
        boolean status = false;

       // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
     
        logstatus("Step 1", "Go to www.flipkart.com", "Done");
        driver.get("https://www.flipkart.com/");

        //alert
        WebElement closeButton =driver.findElement(By.xpath("//span[@role='button']"));
        if(closeButton.isDisplayed()){
        closeButton.click();
        }

        logstatus("Step 2", "Search \"iphone\" ", "Done");
        // search search bar
        WebElement searchBar =driver.findElement(By.xpath("//input[@class='Pke_EE']"));
        searchBar.sendKeys("iPhone");

        //select search button
        WebElement searchButton = driver.findElement(By.xpath("//button[@class='_2iLD__']"));
        searchButton.click();

        logstatus("Step 3", "print the title having discount>17%", "Done");
        //Locate parents containg children title and discount
        List<WebElement> parentElement = driver.findElements(By.xpath("//a[@class='CGtC98']"));
        for(WebElement childElement : parentElement){
            List<WebElement> discounts = childElement.findElements(By.xpath("./div[2]/div[2]/div/div/div[3]/span"));
            for(WebElement discount : discounts){
                String discountText = discount.getText().replaceAll("[^0-9]","");
                int dis = Integer.parseInt(discountText);
                //System.out.println("discount is :"+dis);
                if(dis>17){
                    //print titles
                    List<WebElement> titles = childElement.findElements(By.xpath("./div[2]/div[1]/div[@class='KzDlHZ']"));
                    for(WebElement title :titles){
                        String titleText = title.getText();
                        System.out.println("Title is :"+titleText+ " - "+dis+"%");                        
                    }
                    status=true;                   
                }
            }
        }
        if(status){
            logstatus("End TestCase02", "Search and print Successful", "PASS");
        }
        else{
            logstatus("End TestCase02", "Search and print Unsuccessful", "FAIL");
        }        
    }

    @Test(enabled = true, description="Search \"Coffee Mug\", select 4 stars and above, and print the Title and image URL of the 5 items with highest number of reviews")
    public void testCase03() throws InterruptedException{
        logstatus("Start TestCAse03 :", "Search \"iPhone\", print the Titles and discount % of items with more than 17% discount", "DONE");
        boolean status = false;

        WebDriverWait wait =  new WebDriverWait(driver, Duration.ofSeconds(10));

        logstatus("Step 1", "navigate to flipkart", "Done");
        driver.get("https://www.flipkart.com/");

        //alert
        // WebElement closeButton =driver.findElement(By.xpath("//span[@role='button']"));
        // wait.until(ExpectedConditions.visibilityOf(closeButton));
        // if(closeButton.isDisplayed()){
        // closeButton.click();
        // }

        logstatus("Step 2", "Search \"Coffee Mug\" ", "Done");
        //search Searchbar
        WebElement searchbar = driver.findElement(By.xpath("//input[@class='Pke_EE']"));
        searchbar.sendKeys("Coffee Mug");

        //Locate searchButton
        WebElement searchButton = driver.findElement(By.xpath("//button[@class='_2iLD__']"));
        wait.until(ExpectedConditions.visibilityOf(searchButton));
        searchButton.click();
        //Thread.sleep(3000);  

        logstatus("Step 3", "select 4 stars and above" , "Done");
        //Locate 4 stars and above
        List<WebElement> parWebElements = driver.findElements(By.xpath("//div[contains(@style,'width: 25%;') and .//div[contains(@class,'XQDdHH')]]"));
        WebElement star = null;
        WebElement title = null;
        String titleText = null;
        Float starRate = null;
        List<List<Object>> productsList = new ArrayList<>();      
              
        for(WebElement parElement :parWebElements){
            star = parElement.findElement(By.xpath(".//div[contains(@class,'XQDdHH')]"));
           // Thread.sleep(2000);
            title = parElement.findElement(By.xpath("./div/a[2]"));
            titleText = title.getText().replaceAll("[^a-zA-Z0-9]", "");
           // Thread.sleep(2000);
            starRate = Float.parseFloat(star.getText());
            if(starRate>=4.0){            
                //Locate review and store in a list             
                WebElement review = star.findElement(By.xpath(".//parent::span//following-sibling::span"));
               // Thread.sleep(1000);
                String text= review.getText().replaceAll("[^0-9]", "");
                int reviewText = Integer.parseInt(text);
                //System.out.println(starRate +" - " +titleText+" - "+reviewText);  
                List<Object> productDetails = new ArrayList<>();               
                productDetails.add(reviewText);   

                //locate image and store in a list
                WebElement imageElement = driver.findElement(By.xpath(".//parent::div/preceding-sibling::a/div/div/div/img"));
                String imageSrc = imageElement.getAttribute("src");              
                productDetails.add(imageSrc);

                //locate title and store in a list
                //int titleNumber = Integer.parseInt(titleText);
                productDetails.add(titleText);

                // Add productDetails to the productList
                 productsList.add(productDetails);    
            }              
           
        }
        // Sort products by review count in descending order         
         Collections.sort(productsList, new Comparator<List<Object>>() {
        @Override
        public int compare(List<Object> p1, List<Object> p2) {
            Integer reviewCount1 = (Integer) p1.get(0); // The first element is the review count
            Integer reviewCount2 = (Integer) p2.get(0);
            return reviewCount2.compareTo(reviewCount1); // Sort in descending order
        }
        });
       
        // Print the top 5 products (or fewer if there are less than 5)
        for(int i=0;i<5;i++){            
            System.out.println(productsList.get(i));            
            System.out.println();   
            status = true;        
        }      

        if(!status){
            logstatus("End TestCase02", "Search and print Unsuccessful", "FAIL");
        }
        else{
            logstatus("End TestCase02", "Search and print Successful", "PASS");
        }

    }

    
    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}