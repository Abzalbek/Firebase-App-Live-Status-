package AbzalTest.PageObjects;

import com.example.abzalbekissabekov.abzalapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by abzalbekissabekov on 1/30/18.
 */

public class MyProfilePage {

    public MyProfilePage() {
        onView(withId(R.id.userImageViewProfile)).check(matches(isDisplayed()));
    }
}