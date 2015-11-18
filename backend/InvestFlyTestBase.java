package com.strategyard.tests.api.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.strategyard.backend.model.Portfolio;
import com.strategyard.backend.model.User;
import com.strategyard.backend.service.InvestflyService;
import com.strategyard.backend.service.StrategyService;
import com.strategyard.backend.service.UserService;
import com.strategyard.common.test.TestContextLoader;
import com.strategyard.common.test.TestProfileResolver;
import com.strategyard.common.util.SpringUtil;
import com.strategyard.commons.exception.NotAuthenticatedException;
import com.strategyard.commons.exception.NotAuthorizedException;
import com.strategyard.commons.exception.NotFoundException;
import com.strategyard.commons.exception.StrategyardException;
import com.strategyard.stockmarket.service.MarketDataService;
import com.strategyard.stockmarket.service.StockAlertService;
import com.strategyard.stockmarket.service.StockScreenerService;

@ActiveProfiles(resolver = TestProfileResolver.class)
@ContextConfiguration(loader = TestContextLoader.class)
public class InvestFlyTestBase extends AbstractTestNGSpringContextTests {
    
    public static final String SUITE_NAME = "InvestflyServiceTests";
       
    protected static final String USERNAME = "luckytrader123";
    protected static final String PASSWORD = "UITestPassword";
    protected static final String EMAIL = "luckytrader123@yopmail.com";
    
    protected static final String USERNAME2 = "robin123";
    protected static final String PASSWORD2 = "UITestPassword";
    protected static final String EMAIL2 = "robin123@yopmail.com";
    
    @Autowired
    protected UserService userService;
    
    @Autowired
    protected StrategyService strategyService;
    
    @Autowired
    protected InvestflyService investflyService;
    
    @Autowired
    protected MarketDataService marketService;
    
    @Autowired
    protected StockScreenerService screenerService;
    
    @Autowired
    protected StockAlertService alertService;
    
    protected User testUser;
    protected Portfolio strategy;
    
    protected User testUser2;
    
       
    @BeforeClass
    public void startNewTestClass(){
    }
    
    @AfterClass
    public void cleanupTestUser(){
    }
    
    @BeforeSuite
    public void setAppName(){
        System.setProperty("test.app.name", "/testapp");
    }
    
    
    @AfterSuite
    public void closeContext(){
        //TODO- error occurs if application context is closed
        if(TestProfileResolver.profile.equals("intg"))
            return;
        
        try{
            SpringUtil.closeApplicationContext(super.applicationContext);
            
        }catch(Exception e){}
    }
       
    protected void assertBadRequest(Exception e) {
        Assert.assertTrue(e instanceof StrategyardException, e.getMessage());
    }
    
    protected void assertNotFound(Exception e) {
        Assert.assertTrue(e instanceof NotFoundException, e.getMessage());
    }
    
    protected void assertNotAuthorized(Exception e) {
        Assert.assertTrue(e instanceof NotAuthorizedException, e.getMessage());
    }
    
    
    protected void assertNotAuthenticated(Exception e) {
        Assert.assertTrue(e instanceof NotAuthenticatedException, e.getMessage());
    }

}
