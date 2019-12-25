package android.com.wallet.pages;

import android.com.utils.Helper;
import io.appium.java_client.android.AndroidDriver;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;


/**
 * 转帐页
 */

public class SendTrxPage extends AbstractPage {

    public AndroidDriver<?> driver;

    public SendTrxPage(AndroidDriver<?> driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(id = "com.tronlink.wallet:id/tv_common_title")
    public WebElement transferTtile_btn;

    @FindBy(id = "com.tronlink.wallet:id/et_address")
    public WebElement receiveAddress_text;


    @FindBy(id = "com.tronlink.wallet:id/et_count")
    public WebElement tranferCount_text;

    @FindBy(id = "com.tronlink.wallet:id/send")
    public WebElement send_btn;


    @FindBy(id = "com.tronlink.wallet:id/bt_go")
    public WebElement transferNow_btn;


    @FindBy(id = "com.tronlink.wallet:id/et_new_password")
    public WebElement InputPasswordConfim_btn;


    @FindBy(id = "com.tronlink.wallet:id/bt_send")
    public WebElement confirm_btn;


    @FindBy(id = "com.tronlink.wallet:id/tv_error")
    public WebElement formatErrorHits_text;


    @FindBy(id = "com.tronlink.wallet:id/tv_note")
    public WebElement note_text;


    @FindBy(id = "com.tronlink.wallet:id/tv_balance")
    public WebElement balance_text;


    @FindBy(id = "com.tronlink.wallet:id/tv_max")
    public WebElement tvMax_btn;


    @FindBy(id = "com.tronlink.wallet:id/rl_token")
    public WebElement token_btn;


    @FindBy(id = "com.tronlink.wallet:id/ll_common_left")
    public WebElement back_bt;

    @FindBy(xpath = "//*[@text='(1000042)']")
    public WebElement trc10_btn;


    @FindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.ScrollView/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.support.v7.widget.RecyclerView/android.widget.LinearLayout[3]")
    public WebElement trc20_btn;



    public void swip(){
        Helper.swipScreen(driver);
    }


    @FindBy(id = "com.tronlink.wallet:id/rl_bottom_next")
    public WebElement next_btn;




    public SendTrxSuccessPage enterSendTrxSuccessPage(){
        confirm_btn.click();
        return new SendTrxSuccessPage(driver);
    }


    public String trxCount = "";



    public SendTrxSuccessPage normalSendTrx() throws Exception {
        receiveAddress_text.sendKeys("TG5wFVvrJiTkBA1WaZN3pzyJDfkgHMnFrp");
        tranferCount_text.sendKeys("1");
        swip();
        send_btn.click();
        transferNow_btn.click();
        InputPasswordConfim_btn.sendKeys("Test0001");
        confirm_btn.click();
        TimeUnit.SECONDS.sleep(1);
        return new SendTrxSuccessPage(driver);
    }

    public SendTrxSuccessPage normalSendTrc10(String number) throws Exception {
        receiveAddress_text.sendKeys("TG5wFVvrJiTkBA1WaZN3pzyJDfkgHMnFrp");
        token_btn.click();
        TimeUnit.SECONDS.sleep(1);
        trc10_btn.click();
        tranferCount_text.sendKeys(number);
        swip();
        send_btn.click();
        transferNow_btn.click();
        InputPasswordConfim_btn.sendKeys("Test0001");
        confirm_btn.click();
        TimeUnit.SECONDS.sleep(1);
        back_bt.click();
        return new SendTrxSuccessPage(driver);
    }

    public SendTrxSuccessPage normalSendTrc20(String number) throws Exception {
        receiveAddress_text.sendKeys("TG5wFVvrJiTkBA1WaZN3pzyJDfkgHMnFrp");
        token_btn.click();
        TimeUnit.SECONDS.sleep(1);
        trc20_btn.click();
        tranferCount_text.sendKeys(number);
        swip();
        send_btn.click();
        transferNow_btn.click();
        InputPasswordConfim_btn.sendKeys("Test0001");
        confirm_btn.click();
        TimeUnit.SECONDS.sleep(1);
        back_bt.click();
        return new SendTrxSuccessPage(driver);
    }

    public double getTrc10Amount() throws Exception {
        token_btn.click();
        TimeUnit.SECONDS.sleep(1);
        trc10_btn.click();
        String balance = balance_text.getText();
        double trc10Amount = 0;
        Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
        Matcher matcher = pattern.matcher(balance);
        if(matcher.find())
            trc10Amount = Double.valueOf(matcher.group(0));
        return trc10Amount;
    }

    public double getTrc20Amount() throws Exception {
        token_btn.click();
        TimeUnit.SECONDS.sleep(1);
        trc20_btn.click();
        String balance = balance_text.getText();
        double trc10Amount = 0;
        Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
        Matcher matcher = pattern.matcher(balance);
        if(matcher.find())
            trc10Amount = Double.valueOf(matcher.group(0));
        return trc10Amount;
    }


    public void sendKey(WebElement el,String value) throws Exception {
        el.sendKeys(value);
        TimeUnit.SECONDS.sleep(2);
    }


    public void sendAllTrx(String value) throws Exception {
        receiveAddress_text.sendKeys("TG5wFVvrJiTkBA1WaZN3pzyJDfkgHMnFrp");
        //calculate trx
        switch(value){
            case "max":
//                String current = balance_text.getText();
//                int  index = current.lastIndexOf(" ");
//                current = current.substring(index + 1,current.length());
//                tranferCount_text.sendKeys(current);
                tvMax_btn.click();
                break;
            case "mix":
                tranferCount_text.sendKeys("0");
                break;
            case "tooMuch":
                tranferCount_text.sendKeys("9999999999");
                break;
        }
        send_btn.click();
        TimeUnit.SECONDS.sleep(1);
    }

    public void sendAllTrc10(String value) throws Exception {
        receiveAddress_text.sendKeys("TG5wFVvrJiTkBA1WaZN3pzyJDfkgHMnFrp");
        token_btn.click();
        TimeUnit.SECONDS.sleep(1);
        trc10_btn.click();
        //calculate trx
        switch(value){
            case "max":
//                String current = balance_text.getText();
//                int  index = current.lastIndexOf(" ");
//                current = current.substring(index + 1,current.length());
//                tranferCount_text.sendKeys(current);
                tvMax_btn.click();
                break;
            case "mix":
                tranferCount_text.sendKeys("0");
                break;
            case "tooMuch":
                tranferCount_text.sendKeys("9999999999");
                break;
        }
        Helper.swipScreen(driver);
        send_btn.click();
        TimeUnit.SECONDS.sleep(1);
    }

    public void sendAllTrc20(String value) throws Exception {
        receiveAddress_text.sendKeys("TG5wFVvrJiTkBA1WaZN3pzyJDfkgHMnFrp");
        token_btn.click();
        TimeUnit.SECONDS.sleep(1);
        trc20_btn.click();
        //calculate trx
        switch(value){
            case "max":
//                String current = balance_text.getText();
//                int  index = current.lastIndexOf(" ");
//                current = current.substring(index + 1,current.length());
//                tranferCount_text.sendKeys(current);
                tvMax_btn.click();
                break;
            case "mix":
                tranferCount_text.sendKeys("0");
                break;
            case "tooMuch":
                tranferCount_text.sendKeys("9999999999");
                break;
        }
        Helper.swipScreen(driver);
        send_btn.click();
        TimeUnit.SECONDS.sleep(1);
    }



    public AssetPage sendRamonTrxSuccess() throws Exception {
        receiveAddress_text.sendKeys("TS9XrumdDFBs5bQkVnhFTexoqwqaxUVG8v");
        Random random = new Random();
        float count = random.nextFloat();
        DecimalFormat df = new DecimalFormat( "0.00" );
        String str = df.format(count);
        trxCount = str;
        tranferCount_text.sendKeys(str);
        swip();
        send_btn.click();
        transferNow_btn.click();
        TimeUnit.SECONDS.sleep(2);
        next_btn.click();
        InputPasswordConfim_btn.sendKeys("Test0001");
        confirm_btn.click();
        TimeUnit.SECONDS.sleep(1);
        back_bt.click();
        back_bt.click();
        return new AssetPage(driver);
    }


    public String getTrxCount() {
        return trxCount;
    }



}
