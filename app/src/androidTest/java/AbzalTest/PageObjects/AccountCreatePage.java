package AbzalTest.PageObjects;

import com.example.abzalbekissabekov.abzalapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by abzalbekissabekov on 1/30/18.
 */

public class AccountCreatePage {
    public AccountCreatePage() {
        onView(withId(R.id.nameEditTextRegister)).check(matches(isDisplayed()));
    }

    public AccountCreatePage fillName(String name) {
        onView(withId(R.id.nameEditTextRegister)).check(matches(isDisplayed())).perform(typeText(name));
        return this;
    }

    public AccountCreatePage fillEmail(String email) {
        onView(withId(R.id.emailEditTextRegister)).check(matches(isDisplayed())).perform(typeText(email));
        return this;
    }

    public AccountCreatePage fillPassword(String password) {
        onView(withId(R.id.passwordEditTextRegister)).check(matches(isDisplayed())).perform(typeText(password));
        return this;
    }

    public void clickOnSignUp() {
        onView(withId(R.id.signUpButton)).check(matches(isDisplayed())).perform(click());
    }

    public HomePage clickOnSignUpButton() {
        clickOnSignUp();
        return new HomePage();
    }


}
