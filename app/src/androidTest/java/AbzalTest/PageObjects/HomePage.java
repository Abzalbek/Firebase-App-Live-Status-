package AbzalTest.PageObjects;

import com.example.abzalbekissabekov.abzalapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by abzalbekissabekov on 1/29/18.
 */

public class HomePage {

    public HomePage() {
        // Need to add Idling resource
        try {
            Thread.sleep(15000);
        } catch (Exception e) {
        }

        /** shows mistake , can find the element in this page **/
        onView(withId(R.id.addNewMenu))
                .check(matches(isDisplayed()));
    }

    public PostPage clickOnAddNew() {
        onView(withId(R.id.addNewMenu)).check(matches(isDisplayed())).perform(click());
        return new PostPage();
    }

    public MyProfilePage clickOnMyProfileMenu() {
        onView(withId(R.id.myProfileMenu)).check(matches(isDisplayed())).perform(click());
        return new MyProfilePage();
    }

}
