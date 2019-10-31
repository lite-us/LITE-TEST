package com.tronklink.wallet.regression;

import common.utils.Helper;
import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import wallet.UITest.base.Base;
import wallet.pages.AssetPage;
import wallet.pages.SendTrxPage;
import wallet.pages.SendTrxSuccessPage;

public class DappSendTrc20 extends Base {
  @Parameters({"privateKey"})
  @BeforeClass()
  public void setUpBefore(String privateKey) throws Exception {
    System.out.println("执行setUpBefore");
    new Helper().getSign(privateKey,DRIVER);
  }



  @AfterMethod
  public void afterMethod(){
    DRIVER.closeApp();
    DRIVER.activateApp("com.tronlink.wallet");
  }



  @AfterClass
  public void tearDownAfterClass() {
    DRIVER.quit();
  }



  public SendTrxPage enterToSendTrxPage(){
    AssetPage asset = new AssetPage(DRIVER);
    SendTrxPage transfer = asset.enterSendTrxPage();
    return transfer;
  }

  @Test(description = "SendTrc20 success test")
  public void tsst001_sendTrc20Success() throws Exception {
    AssetPage asset = new AssetPage(DRIVER);
    SendTrxPage transfer = asset.enterSendTrxPage();
    double trc20Before = transfer.getTrc20Amount();
    String trc20SendAmount = "1";
    SendTrxSuccessPage stsp = transfer.normalSendTrc20(trc20SendAmount);
    TimeUnit.SECONDS.sleep(3);
    transfer = asset.enterSendTrxPage();
    double trc20After = transfer.getTrc20Amount();
    System.out.println(trc20After);
    Assert.assertEquals(trc20Before,trc20After + Integer.valueOf(trc20SendAmount));
  }

  @Test(description = "input max send number")
  public void tsst002_inputMaxSendNumber() throws Exception {
    SendTrxPage transfer = enterToSendTrxPage();
    transfer.sendAllTrc20("max");
    Assert.assertTrue(transfer.transferNow_btn.isDisplayed());
  }



  @Test(description = "input mix send number")
  public void tsst003_inputMixSendNumber() throws Exception {
    SendTrxPage transfer = enterToSendTrxPage();
    transfer.sendAllTrc20("mix");
    String centent = transfer.formatErrorHits_text.getText();
    Assert.assertTrue(centent.equals("转账金额需大于0") || centent.contains("greater than 0"));
  }



  @Test(description = "input too Much trc20 send number")
  public void tsst004_inputTooMuchSendNumber() throws Exception {
    SendTrxPage transfer = enterToSendTrxPage();
    transfer.sendAllTrc20("tooMuch");
    String centent = transfer.formatErrorHits_text.getText();
    Assert.assertTrue(centent.equals("余额不足") || centent.equals("insufficient balance"));
  }


}