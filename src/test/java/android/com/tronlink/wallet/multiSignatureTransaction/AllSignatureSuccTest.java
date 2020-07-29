package android.com.tronlink.wallet.multiSignatureTransaction;

import android.com.utils.Configuration;
import android.com.utils.Helper;
import android.com.wallet.UITest.base.Base;
import android.com.wallet.pages.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AllSignatureSuccTest extends Base {


    public String sourceWallet = "";
    public String fromAccountPrivateKey1 = "dad5b1d416822eb02e79bb818c35411e58b88db85562bcc8e71cac2c1ffa441c";
    //public String fromAccountAddress = "TMx13rffk9sFto1LYv42wh9WmFYpYoKRcS";
    public String signatureAccountPrivateKey2 = "451a602d36e0158b5d642daca47e01ec5abdc96ec67a9f88dbc165c7dbb2a08a";
    public String signatureAccountAddress = "TS9XrumdDFBs5bQkVnhFTexoqwqaxUVG8v";

    static String unActiveAddress = Configuration.getByPath("testng.conf")
            .getString("unActiveAddressInNile.Address1");

    @Parameters({"ownerPrivateKey"})
    @BeforeClass(alwaysRun = true)
    public void setUpBefore(String ownerPrivateKey) throws Exception {
        new Helper().getSign(ownerPrivateKey, DRIVER);

    }


    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        try {
            DRIVER.closeApp();
            DRIVER.activateApp("com.tronlink.wallet");
        } catch (Exception e) {
            try {
                DRIVER.closeApp();
                DRIVER.activateApp("com.tronlink.wallet");
            } catch (Exception e1) {

            }
        }

    }


    @AfterClass(alwaysRun = true)
    public void tearDownAfterClass() {
        try {
            DRIVER.quit();
        } catch (Exception e) {
        }
    }


    //import two privateKay(wallet)
    public AssetPage importTwoPrivateKay() throws Exception {
        AssetPage asset = new AssetPage(DRIVER);
        //MinePage mine = asset.enterMinePage();
        //MyPursePage myPursePage = mine.enterMyPursePage();
        MyPursePage myPursePage = asset.enterMyPursePage();
        AddwalletPage walletPage = myPursePage.enterAddwalletPage();
        ImportPrivateKeyPage importPrivateKey = walletPage.enterImportPrivateKeyPage();
        PrivateKeySetNamePage privateKeySetNamePage = importPrivateKey.enterPrivateKeySetNamePage(fromAccountPrivateKey1);
        PrivateKeySetPwdPage privateKeySetPwd = privateKeySetNamePage.enterPrivateKeySetPwdPage("FromAccount");
        try {
            if (privateKeySetPwd.error_hits.getText().equals("钱包已存在")) {
                privateKeySetPwd.back_btn.click();
                TimeUnit.SECONDS.sleep(1);
                importPrivateKey.back_btn.click();
                TimeUnit.SECONDS.sleep(1);
                walletPage.back_btn.click();
                TimeUnit.SECONDS.sleep(1);
                myPursePage.back_btn.click();
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            PrivateKeySetPwdAgainPage privateKeySetPwdAgain = privateKeySetPwd.enterPrivateKeySetPwdAgainPage("Test0001");
            asset = privateKeySetPwdAgain.enterAssetPage("Test0001");
        }
        TimeUnit.SECONDS.sleep(2);
        //mine = asset.enterMinePage();
        //myPursePage = mine.enterMyPursePage();
        myPursePage = asset.enterMyPursePage();
        walletPage = myPursePage.enterAddwalletPage();
        importPrivateKey = walletPage.enterImportPrivateKeyPage();
        privateKeySetNamePage = importPrivateKey.enterPrivateKeySetNamePage(signatureAccountPrivateKey2);
        privateKeySetPwd = privateKeySetNamePage.enterPrivateKeySetPwdPage("SignAccount");
        try {
            if (privateKeySetPwd.error_hits.getText().equals("钱包已存在")) {
                TimeUnit.SECONDS.sleep(1);
                privateKeySetPwd.back_btn.click();
                TimeUnit.SECONDS.sleep(1);
                importPrivateKey.back_btn.click();
                TimeUnit.SECONDS.sleep(1);
                walletPage.back_btn.click();
                TimeUnit.SECONDS.sleep(1);
                myPursePage.back_btn.click();
            }
        } catch (Exception e) {
            PrivateKeySetPwdAgainPage privateKeySetPwdAgain = privateKeySetPwd.enterPrivateKeySetPwdAgainPage("Test0001");
            asset = privateKeySetPwdAgain.enterAssetPage("Test0001");
        }
        return asset;
    }


    @Parameters({"multiSignAddress","address"})
    @Test(description = "test001_sendTrxMultiSignActiveFeeCheck ", alwaysRun = true)
    public void test001_sendTrxMultiSignActiveFeeCheck(String multiSignAddress,String address) throws Exception {

        AssetPage asset = new AssetPage(DRIVER);
        SendTrxPage SendTrx = asset.enterSendTrxPage();
        SendTrx.inputFormAddress(multiSignAddress);
        TimeUnit.SECONDS.sleep(3);
        SendTrx.receiveAddress_text.sendKeys(address);
        TimeUnit.SECONDS.sleep(3);
        Random random = new Random();
        float count = random.nextFloat();
        DecimalFormat df = new DecimalFormat( "0.00" );
        String str = df.format(count);
        SendTrx.trxCount = str;
        SendTrx.tranferCount_text.sendKeys(str);
        Helper.swipScreen(DRIVER);
        TimeUnit.SECONDS.sleep(3);
        log(SendTrx.fee_text.getText());
        Assert.assertTrue(SendTrx.fee_text.getText().contains("1.0"));
        Assert.assertTrue(SendTrx.fee_text.getText().contains("TRX"));
        SendTrx.send_btn.click();
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue(SendTrx.fee_text.getText().contains("1"));
    }



    @Parameters({"multiSignAddress"})
    @Test(description = "test002_sendTrxMultiSignUnActiveFeeCheck ", alwaysRun = true)
    public void test002_sendTrxMultiSignUnActiveFeeCheck(String multiSignAddress) throws Exception {

        AssetPage asset = new AssetPage(DRIVER);
        SendTrxPage SendTrx = asset.enterSendTrxPage();
        SendTrx.inputFormAddress(multiSignAddress);
        TimeUnit.SECONDS.sleep(3);
        SendTrx.receiveAddress_text.sendKeys(unActiveAddress);
        TimeUnit.SECONDS.sleep(3);
        Random random = new Random();
        float count = random.nextFloat();
        DecimalFormat df = new DecimalFormat( "0.00" );
        String str = df.format(count);
        SendTrx.trxCount = str;
        SendTrx.tranferCount_text.sendKeys(str);
        Helper.swipScreen(DRIVER);
        TimeUnit.SECONDS.sleep(3);
        log(SendTrx.fee_text.getText());
        Assert.assertTrue(SendTrx.fee_text.getText().contains("1.1"));
        Assert.assertTrue(SendTrx.fee_text.getText().contains("TRX"));
        SendTrx.send_btn.click();
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue(SendTrx.fee_text.getText().contains("1.1"));

    }

    @Parameters({"address"})
    @Test(description = "test003_sendTrxMultiSignUnActiveBandWidthFeeCheck ", alwaysRun = true)
    public void test003_sendTrxMultiSignUnActiveBandWidthFeeCheck(String address) throws Exception {

        AssetPage asset = new AssetPage(DRIVER);
        SendTrxPage SendTrx = asset.enterSendTrxPage();
        SendTrx.inputFormAddress(address);
        TimeUnit.SECONDS.sleep(3);
        SendTrx.receiveAddress_text.sendKeys(unActiveAddress);
        TimeUnit.SECONDS.sleep(3);
        Random random = new Random();
        float count = random.nextFloat();
        DecimalFormat df = new DecimalFormat( "0.00" );
        String str = df.format(count);
        SendTrx.trxCount = str;
        SendTrx.tranferCount_text.sendKeys(str);
        Helper.swipScreen(DRIVER);
        TimeUnit.SECONDS.sleep(3);
        log(SendTrx.fee_text.getText());
        Assert.assertTrue(SendTrx.fee_text.getText().contains("1.0"));
        Assert.assertTrue(SendTrx.fee_text.getText().contains("TRX"));
        SendTrx.send_btn.click();
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue(SendTrx.fee_text.getText().contains("1"));
    }

    @Parameters({"multiSignAddress","address"})
    @Test(description = "test004_sendTrc20MultiSignActiveFeeCheck ", alwaysRun = true)
    public void test004_sendTrc20MultiSignActiveFeeCheck(String multiSignAddress,String address) throws Exception {

        AssetPage asset = new AssetPage(DRIVER);
        SendTrxPage SendTrx = asset.enterSendTrxPage();
        SendTrx.inputFormAddress( address);
        TimeUnit.SECONDS.sleep(3);
        SendTrx.receiveAddress_text.sendKeys(multiSignAddress);
        TimeUnit.SECONDS.sleep(3);
        SendTrx.selectCoinType("trc20");
        Random random = new Random();
        float count = random.nextFloat();
        DecimalFormat df = new DecimalFormat( "0.00" );
        String str = df.format(count);
        SendTrx.trxCount = str;
        SendTrx.tranferCount_text.sendKeys(str);
        Helper.swipScreen(DRIVER);
        TimeUnit.SECONDS.sleep(3);
        log(SendTrx.fee_text.getText());
        Assert.assertTrue(SendTrx.fee_text.getText().contains("1.0"));
        Assert.assertTrue(SendTrx.fee_text.getText().contains("TRX"));
        SendTrx.send_btn.click();
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue(SendTrx.fee_text.getText().contains("1"));
        Assert.assertTrue(SendTrx.no_bandwidth.getText().contains("执行智能合约")&&SendTrx.no_bandwidth.getText().contains("燃烧")&&SendTrx.no_bandwidth.getText().contains("TRX"));

    }

    @Parameters({"address"})
    @Test(description = "test005_sendTrc20MultiSignUnActiveFeeCheck ", alwaysRun = true)
    public void test005_sendTrc20MultiSignUnActiveFeeCheck(String address) throws Exception {

        AssetPage asset = new AssetPage(DRIVER);
        SendTrxPage SendTrx = asset.enterSendTrxPage();
        SendTrx.inputFormAddress(address);
        TimeUnit.SECONDS.sleep(3);
        SendTrx.receiveAddress_text.sendKeys(unActiveAddress);
        TimeUnit.SECONDS.sleep(3);
        SendTrx.selectCoinType("trc20");
        Random random = new Random();
        float count = random.nextFloat();
        DecimalFormat df = new DecimalFormat( "0.00" );
        String str = df.format(count);
        SendTrx.trxCount = str;
        SendTrx.tranferCount_text.sendKeys(str);
        Helper.swipScreen(DRIVER);
        TimeUnit.SECONDS.sleep(3);
        Assert.assertTrue(SendTrx.note_text.getText().contains("账户未激活"));
        log(SendTrx.fee_text.getText());
        Assert.assertTrue(SendTrx.fee_text.getText().contains("1.0"));
        Assert.assertTrue(SendTrx.fee_text.getText().contains("TRX"));
        SendTrx.send_btn.click();
        TimeUnit.SECONDS.sleep(1);
        waiteTime();
        Assert.assertTrue(SendTrx.fee_text.getText().contains("1"));
        Assert.assertTrue(SendTrx.no_bandwidth.getText().contains("执行智能合约")&&SendTrx.no_bandwidth.getText().contains("燃烧")&&SendTrx.no_bandwidth.getText().contains("TRX"));

    }


    @Parameters({"multiSignAddress"})
    @Test(groups = {"P0"},description = "Invalid Time is exist", alwaysRun = true)
    public void test006_invalidTimeIsExists(String multiSignAddress) throws Exception {
        AssetPage asset = new AssetPage(DRIVER);
        SendTrxPage SendTrx = asset.enterSendTrxPage();
        SendTrx.receiveAddress_text.sendKeys(multiSignAddress);
        Random random = new Random();
        float count = random.nextFloat();
        DecimalFormat df = new DecimalFormat( "0.00" );
        String str = df.format(count);
        SendTrx.trxCount = str;
        SendTrx.tranferCount_text.sendKeys(str);
        Helper.swipScreen(DRIVER);
        SendTrx.send_btn.click();
        SendTrx.transferNow_btn.click();
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue(SendTrx.invalidTime_input.isDisplayed());
    }




    @Parameters({"ownerPrivateKey","multiSignAddress"})
    @Test(groups = {"P0"},description = "Sign Address Is Exists", alwaysRun = true)
    public void test007_signAddressIsExists(String ownerPrivateKey,String multiSignAddress) throws Exception {
        //new Helper().getSign(ownerPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        SendTrxPage SendTrx = asset.enterSendTrxPage();
        SendTrx.receiveAddress_text.sendKeys(multiSignAddress);
        Random random = new Random();
        float count = random.nextFloat();
        DecimalFormat df = new DecimalFormat( "0.00" );
        String str = df.format(count);
        SendTrx.trxCount = str;
        SendTrx.tranferCount_text.sendKeys(str);
        Helper.swipScreen(DRIVER);
        SendTrx.send_btn.click();
        SendTrx.transferNow_btn.click();
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue(SendTrx.signAddress_input.get(0).getText().length() == 34);
    }




    @Parameters({"ownerPrivateKey","multiSignAddress"})
    @Test(groups = {"P0"},description = "WaitSign Address Is Exists", alwaysRun = true)
    public void test008_waitSignAddressIsExists(String ownerPrivateKey,String multiSignAddress) throws Exception {
        //new Helper().getSign(ownerPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        SendTrxPage SendTrx = asset.enterSendTrxPage();
        SendTrx.receiveAddress_text.sendKeys(multiSignAddress);
        Random random = new Random();
        float count = random.nextFloat();
        DecimalFormat df = new DecimalFormat( "0.00" );
        String str = df.format(count);
        SendTrx.trxCount = str;
        SendTrx.tranferCount_text.sendKeys(str);
        Helper.swipScreen(DRIVER);
        SendTrx.send_btn.click();
        SendTrx.transferNow_btn.click();
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue(SendTrx.signAddress_input.get(1).isDisplayed());
    }




    @Parameters({"ownerPrivateKey","multiSignAddress"})
    @Test(groups = {"P0"},description = "WaitSign Address Is Exists", alwaysRun = true)
    public void test009_signNameCheck(String ownerPrivateKey,String multiSignAddress) throws Exception {
        //new Helper().getSign(ownerPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        SendTrxPage SendTrx = asset.enterSendTrxPage();
        SendTrx.receiveAddress_text.sendKeys(multiSignAddress);
        Random random = new Random();
        float count = random.nextFloat();
        DecimalFormat df = new DecimalFormat( "0.00" );
        String str = df.format(count);
        SendTrx.trxCount = str;
        SendTrx.tranferCount_text.sendKeys(str);
        Helper.swipScreen(DRIVER);
        SendTrx.send_btn.click();
        SendTrx.transferNow_btn.click();
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue(SendTrx.selectSignName_text.isDisplayed());
    }


    @Parameters({"ownerPrivateKey", "multiSignAddress"})
    @Test(groups = {"P0"}, description = "send trx sign success options Test", alwaysRun = true)
    public void test010_sendTrxOptions(String ownerPrivateKey, String multiSignAddress) throws Exception {
        //new Helper().getSign(ownerPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        //MyPursePage myPurse = asset.enterMyPursePage();
        //myPurse.changeWalletAccount("FromAccount");
        SendTrxPage SendTrx = asset.enterSendTrxPage();
        asset = SendTrx.sendRamonTrxSuccess(multiSignAddress);
    }


    @Parameters({"ownerPrivateKey", "multiSignAddress"})
    @Test(groups = {"P0"}, description = "send trx sign success two times options Test", alwaysRun = true)
    public void test011_sendTrxTwoTimesOptions(String ownerPrivateKey, String multiSignAddress) throws Exception {
        //new Helper().getSign(ownerPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        //MyPursePage myPurse = asset.enterMyPursePage();
        //myPurse.changeWalletAccount("FromAccount");
        SendTrxPage SendTrx = asset.enterSendTrxPage();
        asset = SendTrx.sendRamonTrxSuccess(multiSignAddress);
    }


    @Test(groups = {"P0"}, description = "test007_multiAlreadySignSendTrxNotesCheck", alwaysRun = true)
    public void test012_multiAlreadySignSendTrxNotesCheck() throws Exception {
        //new Helper().getSign(ownerPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        MinePage minePage = asset.enterMinePage();
        MyPursePage myPurse = minePage.enterMyPursePage();
        MultiSignTransactionPage multiSignTransactionPage = myPurse.enterMultiSignTransactionPage();
        TimeUnit.SECONDS.sleep(3);
        multiSignTransactionPage.transactionAlreadySign_text.click();
        TimeUnit.SECONDS.sleep(3);
        Assert.assertEquals(multiSignTransactionPage.tv_note.getText(),"I'm multiSign notes");
        waiteTime();
        multiSignTransactionPage.tv_note_more.click();
        Assert.assertEquals(multiSignTransactionPage.tv_note.getText(),"I'm multiSign notes");
        Assert.assertTrue(multiSignTransactionPage.tv_cancle.getText().contains("我知道了"));

    }

    @Parameters({"multiSignPrivateKey"})
    @Test(groups = {"P0"}, description = "test008_multiSignWaitSendTrxNotesCheck", alwaysRun = true)
    public void test013_multiSignWaitSendTrxNotesCheck(String multiSignPrivateKey) throws Exception {
        DRIVER.resetApp();
        new Helper().getSign(multiSignPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        MinePage minePage = asset.enterMinePage();
        MyPursePage myPurse = minePage.enterMyPursePage();
        MultiSignTransactionPage multiSignTransactionPage = myPurse.enterMultiSignTransactionPage();
        TimeUnit.SECONDS.sleep(3);
        Assert.assertEquals(multiSignTransactionPage.tv_note.getText(),"I'm multiSign notes");
        waiteTime();
        multiSignTransactionPage.tv_note_more.click();
        Assert.assertEquals(multiSignTransactionPage.tv_note.getText(),"I'm multiSign notes");
        Assert.assertTrue(multiSignTransactionPage.tv_cancle.getText().contains("我知道了"));

    }

    @Parameters({"multiSignPrivateKey"})
    @Test(groups = {"P0"},description = "sign options Test", alwaysRun = true)
    public void test014_signOptions(String multiSignPrivateKey) throws Exception {
        DRIVER.resetApp();
        new Helper().getSign(multiSignPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        //MyPursePage myPurse = asset.enterMyPursePage();
        //myPurse.changeWalletAccount("FromAccount");
        MinePage minePage = asset.enterMinePage();
        MyPursePage myPurse = minePage.enterMyPursePage();
        MultiSignTransactionPage multiSignTransactionPage = myPurse.enterMultiSignTransactionPage();
        multiSignTransactionPage.sign();
        TimeUnit.SECONDS.sleep(5);
    }



    @Parameters({"multiSignPrivateKey"})
    @Test(groups = {"P0"},description = "sign options Test check TRX", alwaysRun = true)
    public void test015_signPageCheckTrx(String multiSignPrivateKey) throws Exception {
        DRIVER.resetApp();
        new Helper().getSign(multiSignPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        MinePage minePage = asset.enterMinePage();
        MyPursePage myPurse = minePage.enterMyPursePage();
        MultiSignTransactionPage multiSignTransactionPage = myPurse.enterMultiSignTransactionPage();
        //multiSignTransactionPage.sign();
        Assert.assertTrue(multiSignTransactionPage.transConten_text.getText() != null);
    }




    @Parameters({"multiSignPrivateKey"})
    @Test(groups = {"P0"},description = "sign options Test check TransFrom", alwaysRun = true)
    public void test016_signPageCheckTransFrom(String multiSignPrivateKey) throws Exception {
        DRIVER.resetApp();
        new Helper().getSign(multiSignPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        MinePage minePage = asset.enterMinePage();
        MyPursePage myPurse = minePage.enterMyPursePage();
        MultiSignTransactionPage multiSignTransactionPage = myPurse.enterMultiSignTransactionPage();
        //multiSignTransactionPage.sign();
        Assert.assertTrue(multiSignTransactionPage.transFrom_text.getText().length() == 34);
    }



    @Parameters({"multiSignPrivateKey"})
    @Test(groups = {"P0"},description = "sign options Test check TransTo", alwaysRun = true)
    public void test017_signPageCheckTransTo(String multiSignPrivateKey) throws Exception {
        DRIVER.resetApp();
        new Helper().getSign(multiSignPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        MinePage minePage = asset.enterMinePage();
        MyPursePage myPurse = minePage.enterMyPursePage();
        MultiSignTransactionPage multiSignTransactionPage = myPurse.enterMultiSignTransactionPage();
        //multiSignTransactionPage.sign();
        Assert.assertTrue(multiSignTransactionPage.transTo_text.getText().length() == 34);
    }



    @Parameters({"multiSignPrivateKey"})
    @Test(groups = {"P0"},description = "sign options Test check InvaTime", alwaysRun = true)
    public void test018_signPageCheckInvaTime(String multiSignPrivateKey) throws Exception {
        DRIVER.resetApp();
        new Helper().getSign(multiSignPrivateKey, DRIVER);
        AssetPage asset = new AssetPage(DRIVER);
        MinePage minePage = asset.enterMinePage();
        MyPursePage myPurse = minePage.enterMyPursePage();
        MultiSignTransactionPage multiSignTransactionPage = myPurse.enterMultiSignTransactionPage();
        //multiSignTransactionPage.sign();
        Assert.assertTrue(multiSignTransactionPage.invaTime_text.isDisplayed());
    }

    @Parameters({"ownerPrivateKey"})
    @Test(groups = {"P0"},description = "test012_modifyMultiSignFeeCheck TR-1066", alwaysRun = true)
    public void test019_modifyMultiSignFeeCheck(String ownerPrivateKey) throws Exception {
        DRIVER.resetApp();
        new Helper().getSign(ownerPrivateKey, DRIVER);
        MultiSignManagerPage multiSignManager = enterMultiSignManagerPage();
        ModifyPermissionPage modifyPermission = multiSignManager.enterModifyPage();
        Helper.swipScreen(modifyPermission.driver);
        modifyPermission.confirm_btn.click();
        modifyPermission.nextBtnClick();
        TimeUnit.SECONDS.sleep(2);
        Assert.assertTrue(modifyPermission.feetext.getText().contains("TRX"));
        Assert.assertTrue(modifyPermission.feetext.getText().contains("101"));
    }




    public MultiSignManagerPage enterMultiSignManagerPage() throws Exception {
        AssetPage asset = new AssetPage(DRIVER);
        MinePage mine = asset.enterMinePage();
        MyPursePage myPursePage = mine.enterMyPursePage();
        MultiSignManagerPage MultiSignManager = myPursePage.enterMultiSignManagerPage();
        return MultiSignManager;
    }


//    @Test(description = "send trx sign options Test", alwaysRun = true,enabled = false)
//    public void test001_sendTrxMultSignOptions() throws Exception {
//        AssetPage asset = importTwoPrivateKay();
////        AssetPage asset = new AssetPage(DRIVER);
////        MyPursePage myPurse = asset.enterMyPursePage();
////        AssetPage asset = new AssetPage(DRIVER);
//        MyPursePage myPurse = asset.enterMyPursePage();
//        myPurse.changeWalletAccount("FromAccount");
//        SendTrxPage SendTrx = asset.enterSendTrxPage();
//        asset = SendTrx.sendRamonTrxSuccess();
//        myPurse = asset.enterMyPursePage();
//        asset = myPurse.changeWalletAccount("SignAccount");
//        MinePage minePage = asset.enterMinePage();
//        myPurse = minePage.enterMyPursePage();
//        MultiSignTransactionPage multiSignTransactionPage = myPurse.enterMultiSignTransactionPage();
//        multiSignTransactionPage.sign();
//        myPurse = multiSignTransactionPage.enterMyPursePage();
//        Assert.assertTrue(myPurse.address_text.isDisplayed());
////        myPurse = multiSignTransactionPage.enterMyPursePage();
////        minePage = myPurse.enterMinePage();
////        asset = minePage.enterAssetPage();
////        myPurse = asset.enterMyPursePage();
////        myPurse.changeWalletAccount("FromAccount");
////        minePage = asset.enterMinePage();
////        myPurse = minePage.enterMyPursePage();
////        multiSignTransactionPage = myPurse.enterMultiSignTransactionPage();
////        TimeUnit.SECONDS.sleep(2);
////        multiSignTransactionPage.signSuccess_tab.click();
////        Assert.assertTrue(multiSignTransactionPage.signSuccess_tab.isDisplayed());
////        System.out.println("transContent_text = " + multiSignTransactionPage.transContent_text.getText());
////        System.out.println("asset = " + SendTrx.getTrxCount());
//
//    }
//
//
//
//
//    @Test(description = "change account", alwaysRun = true,enabled = false)
//    public void test002_swipChangeAccountSuccess() throws Exception {
//        AssetPage asset = new AssetPage(DRIVER);
//        MyPursePage myPursePage = asset.enterMyPursePage();
//        myPursePage.changeWalletAccount("FromAccount");
//        Assert.assertTrue(asset.walletName_text.getText().equals("FromAccount"));
//    }
//
//
//
//    @Test(description = "swip account address is change", alwaysRun = true,enabled = false)
//    public void test003_swipAccountAddressChange() throws Exception {
//        AssetPage asset = new AssetPage(DRIVER);
//        MyPursePage myPursePage = asset.enterMyPursePage();
//        myPursePage.swipToChangeAddress("TMx13rffk9sFto1LYv42wh9WmFYpYoKRcS");
//        Assert.assertTrue(myPursePage.address_text.getText().equals("TMx13rffk9sFto1LYv42wh9WmFYpYoKRcS"));
//    }
//
//
//
//    @Test(description = "change account", alwaysRun = true,enabled = false)
//    public void test004_swipChangeAccountOtherSuccess() throws Exception {
//        AssetPage asset = new AssetPage(DRIVER);
//        MyPursePage myPursePage = asset.enterMyPursePage();
//        myPursePage.changeWalletAccount("SignAccount");
//        Assert.assertTrue(asset.walletName_text.getText().equals("SignAccount"));
//    }
//
//
//
//    @Test(description = "send trx account check", alwaysRun = true,enabled = false)
//    public void test005_sendTrxAccountCheck() throws Exception {
//        AssetPage asset = new AssetPage(DRIVER);
//        MyPursePage myPursePage = asset.enterMyPursePage();
//        myPursePage.changeWalletAccount("FromAccount");
//        SendTrxPage SendTrx = asset.enterSendTrxPage();
//        SendTrx.receiveAddress_text.sendKeys("TS9XrumdDFBs5bQkVnhFTexoqwqaxUVG8v");
//        Random random = new Random();
//        float count = random.nextFloat();
//        DecimalFormat df = new DecimalFormat( "0.00" );
//        String str = df.format(count);
//        SendTrx.tranferCount_text.sendKeys(str);
//        Helper.swipScreen(DRIVER);
//        SendTrx.send_btn.click();
//        SendTrx.transferNow_btn.click();
//        TimeUnit.SECONDS.sleep(1);
//        int enableTime = Integer.valueOf(SendTrx.enableTime_text.getText());
//        Assert.assertTrue(enableTime>=0 && enableTime<=24);
//    }
//
//
//
//    @Test(description = "send trx account check address", alwaysRun = true,enabled = false)
//    public void test005_sendTrxAccountCheckAddress() throws Exception {
//        AssetPage asset = new AssetPage(DRIVER);
//        MyPursePage myPursePage = asset.enterMyPursePage();
//        myPursePage.changeWalletAccount("FromAccount");
//        SendTrxPage SendTrx = asset.enterSendTrxPage();
//        SendTrx.receiveAddress_text.sendKeys("TS9XrumdDFBs5bQkVnhFTexoqwqaxUVG8v");
//        Random random = new Random();
//        float count = random.nextFloat();
//        DecimalFormat df = new DecimalFormat( "0.00" );
//        String str = df.format(count);
//        SendTrx.tranferCount_text.sendKeys(str);
//        Helper.swipScreen(DRIVER);
//        SendTrx.send_btn.click();
//        SendTrx.transferNow_btn.click();
//        TimeUnit.SECONDS.sleep(1);
//        Assert.assertTrue(SendTrx.signAddress_text.size()>=1);
//    }
//
//
//
//    @Test(description = "send trx account check transaction address", alwaysRun = true,enabled = false)
//    public void test006_checktransactionFromAddress() throws Exception {
//        AssetPage asset = new AssetPage(DRIVER);
//        MyPursePage myPurse = asset.enterMyPursePage();
//        myPurse.changeWalletAccount("SignAccount");
//        MinePage minePage = asset.enterMinePage();
//        myPurse = minePage.enterMyPursePage();
//        MultiSignTransactionPage multiSignTransactionPage = myPurse.enterMultiSignTransactionPage();
//        multiSignTransactionPage.signSuccess_tab.click();
//        TimeUnit.SECONDS.sleep(2);
//        Assert.assertTrue(multiSignTransactionPage.transactionSuc_text.isDisplayed());
//    }
//
//
//
//
//    @Test(description = "send trx account check transaction address", alwaysRun = true,enabled = false)
//    public void test007_checktransactionToAddress() throws Exception {
//        AssetPage asset = new AssetPage(DRIVER);
//        MyPursePage myPurse = asset.enterMyPursePage();
//        myPurse.changeWalletAccount("SignAccount");
//        MinePage minePage = asset.enterMinePage();
//        myPurse = minePage.enterMyPursePage();
//        MultiSignTransactionPage multiSignTransactionPage = myPurse.enterMultiSignTransactionPage();
//        multiSignTransactionPage.signSuccess_tab.click();
//        TimeUnit.SECONDS.sleep(2);
//        Assert.assertTrue(multiSignTransactionPage.transactionAlreadySign_text.isDisplayed());
//    }


}
