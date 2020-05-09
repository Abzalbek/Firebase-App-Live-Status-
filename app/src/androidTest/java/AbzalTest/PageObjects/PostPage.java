package AbzalTest.PageObjects;

import com.example.abzalbekissabekov.abzalapp.R;


import AbzalTest.ToastMatcher;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by abzalbekissabekov on 1/30/18.
 */

public class PostPage {
    public PostPage() {
        onView(withId(R.id.postEditText))
                .check(matches(isDisplayed()));
    }

    public PostPage typeTextToPost(String text) {
        onView(withId(R.id.postEditText)).check(matches(isDisplayed())).perform(typeText(text));
        return this;
    }

    public PostPage clickOnPostButton() {
        onView(withId(R.id.postButton)).check(matches(isDisplayed())).perform(click());
        return this;
    }

    public PostPage checkToast(String toast){
        onView(withText(toast)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        return this;
    }

    public HomePage clickBackButton(){
        closeSoftKeyboard();
        pressBack();
        return new HomePage();
    }

}
