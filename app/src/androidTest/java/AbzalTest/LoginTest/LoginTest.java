package AbzalTest.LoginTest;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.example.abzalbekissabekov.abzalapp.LoginActivity;
import com.example.abzalbekissabekov.abzalapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import AbzalTest.BaseTest;
import AbzalTest.PageObjects.AccountCreatePage;
import AbzalTest.PageObjects.HomePage;
import AbzalTest.PageObjects.LoginPage;
import AbzalTest.PageObjects.PostPage;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by abzalbekissabekov on 1/29/18.
 */

public class LoginTest extends BaseTest {

    @Rule
    public final ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class, false, true);

    private Resources resources;

    @Before
    public void setUp() {
        resources = InstrumentationRegistry.getTargetContext().getResources();
    }


    @Test
    public void testLogin() {

        //Log.d("info","Name is "+getProperties().getProperty("email"));

        new LoginPage()
                .fillEmail(getProperties().getProperty("email"))
                .fillPassword(getProperties().getProperty("password"))
                .clickOnButton();
        new HomePage()
                .clickOnAddNew();
        new PostPage()
                .typeTextToPost(getProperties().getProperty("post"))
                .clickOnPostButton()
                .checkToast(getProperties().getProperty("toastMeassage"))
                .clickBackButton();
        new HomePage();
    }

    @Test
    public void testPopUp() {
        LoginPage loginPage = new LoginPage();
        loginPage.clickOnLoginButton();
        loginPage.checkAlertOfEmptyEmailTextField();
    }

    @Test
    public void testPopUpWithWrongData() {
        LoginPage loginPage = new LoginPage()
                .fillEmail(getProperties().getProperty("email"))
                .fillPassword(getProperties().getProperty("passwordW"));
        loginPage.clickOnLoginButton();

        /** need to cover again, handle with popup window**/
        //loginPage.checkAlertOfWrongUser();

    }
        /** following test scenario always requires new email data , can generate method for random emails **/
    @Test
    public void createAccount() {
        new LoginPage()
                .clickOnCreateAccount();
        new AccountCreatePage()
                .fillName(getProperties().getProperty("nameSU"))
                .fillEmail(getProperties().getProperty("emailSU"))
                .fillPassword(getProperties().getProperty("passwordSU"))
                .clickOnSignUpButton();
    }
}
