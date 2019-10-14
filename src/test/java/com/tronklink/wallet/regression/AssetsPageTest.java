package com.tronklink.wallet.regression;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import wallet.UITest.base.Base;
import org.testng.annotations.*;
/**
 * 资产页面测试
 */
public class AssetsPageTest extends Base {

//    @BeforeClass
//    public void setUpBeforeClass() throws Exception {
//        setUp();
//        //Base.getSign(privateKey);
//    }

    @Parameters({"privateKey"})
    @BeforeMethod()
    public void setUpBefore(String privateKey) throws Exception{
        DRIVER.closeApp();
        DRIVER.launchApp();
        getSign(privateKey);
    }

    @AfterClass
    public void tearDownAfterClass() {
        //Base.tearDownAfterClass();
        DRIVER.quit();
    }



}