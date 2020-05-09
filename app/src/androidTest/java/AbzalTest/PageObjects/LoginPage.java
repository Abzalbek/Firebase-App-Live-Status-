package AbzalTest.PageObjects;

import android.support.annotation.NonNull;

import com.example.abzalbekissabekov.abzalapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by abzalbekissabekov on 1/29/18.
 */

public class LoginPage {

    public LoginPage() {
        onView(withId(R.id.emailEditTextLogin)).check(matches(isDisplayed()));
    }

    public LoginPage fillEmail(@NonNull String userEmail) {
        onView(withId(R.id.emailEditTextLogin)).check(matches(isDisplayed())).perform(
                typeText(userEmail)
        );
        return this;
    }

    public LoginPage fillPassword(@NonNull String userPassword) {
        onView(withId(R.id.passwordEditTextLogin)).check(matches(isDisplayed())).perform(
                typeText(userPassword)
        );
        return this;
    }

    public void clickOnLoginButton() {
        onView(withId(R.id.loginButton)).check(matches(isDisplayed())).perform(
                click()
        );
    }

    public HomePage clickOnButton() {
        clickOnLoginButton();
        return new HomePage();
    }

    public LoginPage checkAlertOfEmptyEmailTextField() {
        onView(withText("Email can not be empty")).check(matches(isDisplayed()));
        return this;
    }

    public LoginPage checkAlertOfEmptyPasswrodTextField() {
        onView(withText("Password can not be empty")).check(matches(isDisplayed()));
        return this;
    }

    /**
     * need to ask someone to cover that point. now Espresso doesnt cover system popup windows
     **/
    public LoginPage checkAlertOfWrongUser() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withText("There is no user record corresponding to this identifier. The user may have been deleted."))
                .check(matches(isDisplayed()));
        return this;
    }

    public AccountCreatePage clickOnCreateAccount() {
        onView(withId(R.id.createAccountMenu)).check(matches(isDisplayed())).perform(click());
        return new AccountCreatePage();
    }
}


