package com.nullclass.internship.tests;

import org.testng.annotations.*;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.nullclass.internship.utils.WebDriverManager;
import com.nullclass.internship.utils.TimeValidator;
import com.nullclass.internship.utils.ProductFilter;
import com.nullclass.internship.pages.AmazonHomePage;
import com.nullclass.internship.pages.AmazonSearchResultsPage;
import com.nullclass.internship.pages.AmazonProductDetailsPage;

/**
 * TASK 3: PRODUCT SELECTION WITH FILTERS
 * 
 * Product selection with specific filtering criteria
 * Requirements: Non-electronic, not starting with A-D, Time: 3 PM - 6 PM
 * 
 * @author Vinaykumar Nakka
 */
public class Task3_ProductSelection {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private AmazonHomePage homePage;
    private AmazonSearchResultsPage searchResultsPage;
    private AmazonProductDetailsPage productDetailsPage;
    
    @BeforeClass
    public void setUp() {
        System.out.println("================================================================================");
        System.out.println("TASK 3: PRODUCT SELECTION WITH FILTERS");
        System.out.println("Author: Vinaykumar Nakka");
        System.out.println("Requirements: Non-electronic, not starting with A-D, Time: 3 PM - 6 PM");
        System.out.println("================================================================================");
    }
    
    @BeforeMethod
    public void setUpTest() {
        driver = WebDriverManager.initializeChromeDriver();
        wait = WebDriverManager.getWait();
        
        // Initialize page objects
        homePage = new AmazonHomePage(driver);
        searchResultsPage = new AmazonSearchResultsPage(driver);
        productDetailsPage = new AmazonProductDetailsPage(driver);
    }
    
    @Test(priority = 1)
    public void testTimeValidation() {
        System.out.println("\n--- TEST: Time Validation for Task 3 ---");
        
        boolean isValidTime = TimeValidator.isTask3ExecutionTime();
        
        if (!isValidTime) {
            String timeMessage = TimeValidator.getTask3TimeRemaining();
            System.out.println("⚠️  SKIPPING TEST: " + timeMessage);
            throw new org.testng.SkipException("Task 3 can only run between 3 PM to 6 PM. " + timeMessage);
        }
        
        System.out.println("✅ Time validation passed - proceeding with Task 3");
        Assert.assertTrue(isValidTime, "Task 3 should only run between 3 PM to 6 PM");
    }
    
    @Test(priority = 2, dependsOnMethods = "testTimeValidation")
    public void testProductFilterValidation() {
        System.out.println("\n--- TEST: Product Filter Validation ---");
        
        try {
            // Test valid search terms (non-electronic, not starting with A-D)
            String[] validTerms = {"furniture", "kitchen utensils", "home decor", "garden tools", "sports equipment"};
            
            for (String term : validTerms) {
                boolean isValid = ProductFilter.isValidSearchTerm(term);
                System.out.println("Testing term: " + term + " - Valid: " + isValid);
                Assert.assertTrue(isValid, "Term '" + term + "' should be valid for Task 3");
            }
            
            // Test invalid search terms (electronic or starting with A-D)
            String[] invalidTerms = {"laptop", "mobile", "camera", "apple", "books", "computer"};
            
            for (String term : invalidTerms) {
                boolean isValid = ProductFilter.isValidSearchTerm(term);
                System.out.println("Testing term: " + term + " - Valid: " + isValid);
                Assert.assertFalse(isValid, "Term '" + term + "' should be invalid for Task 3");
            }
            
            System.out.println("✅ Product filter validation completed");
            
        } catch (Exception e) {
            System.err.println("❌ Product filter validation failed: " + e.getMessage());
            Assert.fail("Product filter validation failed: " + e.getMessage());
        }
    }
    
    @Test(priority = 3, dependsOnMethods = "testTimeValidation")
    public void testValidSearchTerms() {
        System.out.println("\n--- TEST: Valid Search Terms ---");
        
        try {
            // Generate valid search terms for Task 3
            String[] searchTerms = ProductFilter.getValidSearchTermsForTask3();
            
            System.out.println("Valid search terms for Task 3:");
            for (String term : searchTerms) {
                System.out.println("- " + term);
                Assert.assertTrue(ProductFilter.isValidSearchTerm(term), 
                    "Generated term should be valid: " + term);
            }
            
            Assert.assertTrue(searchTerms.length > 0, "Should generate at least one valid search term");
            System.out.println("✅ Valid search terms test completed");
            
        } catch (Exception e) {
            System.err.println("❌ Valid search terms test failed: " + e.getMessage());
            Assert.fail("Valid search terms test failed: " + e.getMessage());
        }
    }
    
    @Test(priority = 4, dependsOnMethods = "testTimeValidation")
    public void testProductSelectionAndVerification() {
        System.out.println("\n--- TEST: Product Selection and Verification ---");
        
        try {
            // Step 1: Navigate to Amazon
            System.out.println("\n🔸 Step 1: Navigating to Amazon India");
            homePage.navigateToAmazon();
            Assert.assertTrue(homePage.isHomePageLoaded(), "Amazon homepage should load");
            
            // Step 2: Search for valid products
            System.out.println("\n🔸 Step 2: Searching for valid products");
            String[] validSearchTerms = ProductFilter.getValidSearchTermsForTask3();
            boolean productFound = false;
            
            for (String searchTerm : validSearchTerms) {
                System.out.println("Searching for: " + searchTerm);
                homePage.searchProduct(searchTerm);
                searchResultsPage.waitForSearchResults();
                
                // Step 3: Select valid product based on Task 3 criteria
                System.out.println("\n🔸 Step 3: Selecting product based on Task 3 criteria");
                if (searchResultsPage.selectValidProductForTask3()) {
                    productFound = true;
                    break;
                }
                
                // Go back to home page for next search
                driver.navigate().back();
                Thread.sleep(2000);
            }
            
            Assert.assertTrue(productFound, "Should find at least one valid product for Task 3");
            System.out.println("✅ Product selection completed successfully");
            
        } catch (Exception e) {
            System.err.println("❌ Product selection test failed: " + e.getMessage());
            Assert.fail("Product selection test failed: " + e.getMessage());
        }
    }
    
    @Test(priority = 5, dependsOnMethods = "testProductSelectionAndVerification")
    public void testProductDetailsComprehensiveVerification() {
        System.out.println("\n--- TEST: Product Details Comprehensive Verification ---");
        
        try {
            // Step 1: Verify product details page
            System.out.println("\n🔸 Step 1: Verifying product details page");
            productDetailsPage.waitForProductDetailsPage();
            Assert.assertTrue(productDetailsPage.isProductDetailsPageLoaded(), 
                "Product details page should load");
            
            // Step 2: Verify product information
            System.out.println("\n🔸 Step 2: Verifying product information");
            String productTitle = productDetailsPage.getProductTitle();
            String productPrice = productDetailsPage.getProductPrice();
            
            Assert.assertNotNull(productTitle, "Product title should not be null");
            Assert.assertNotNull(productPrice, "Product price should not be null");
            Assert.assertFalse(productTitle.trim().isEmpty(), "Product title should not be empty");
            
            System.out.println("Product Title: " + productTitle);
            System.out.println("Product Price: " + productPrice);
            
            // Step 3: Verify product meets Task 3 criteria
            System.out.println("\n🔸 Step 3: Verifying product meets Task 3 criteria");
            boolean meetsNameCriteria = ProductFilter.isValidProductName(productTitle);
            Assert.assertTrue(meetsNameCriteria, 
                "Product name should meet Task 3 criteria (not starting with A-D)");
            
            System.out.println("✅ Product details verification completed successfully");
            
        } catch (Exception e) {
            System.err.println("❌ Product details verification failed: " + e.getMessage());
            Assert.fail("Product details verification failed: " + e.getMessage());
        }
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            WebDriverManager.quitDriver();
            System.out.println("WebDriver quit successfully");
        }
    }
    
    @AfterClass
    public void cleanUp() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TASK 3 EXECUTION COMPLETED");
        System.out.println("Time: " + TimeValidator.getCurrentTimeFormatted());
        System.out.println("Status: All tests executed within required time window (3 PM - 6 PM)");
        System.out.println("=".repeat(80));
    }
}
