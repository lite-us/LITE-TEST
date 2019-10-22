package wallet.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

import wallet.UITest.base.Base;

public class GuidePage extends AbstractPage {

    public AndroidDriver<?> driver;

    public GuidePage(AndroidDriver<?> driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(id = "com.tronlink.wallet:id/tv_import")
    public WebElement impAccount;


    public UserAgreementPage enterUserAgreementPage() {
        try {
            impAccount.click();
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e){
            new Base().log("impAccount button not found");
        }
        return new UserAgreementPage(driver);
    }





}
