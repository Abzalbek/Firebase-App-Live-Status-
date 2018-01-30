package AbzalTest.PageObjects;


import android.support.annotation.NonNull;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.abzalbekissabekov.abzalapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by abzalbekissabekov on 1/29/18.
 */

public class HomePage {

    public HomePage (){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /** shows mistake , can find the element in this page **/
        onView(withId(R.id.addNewMenu))
                .check(matches(isDisplayed()));
    }

    public PostPage clickOnAddNew(){
        onView(withId(R.id.addNewMenu)).perform(click());
        return new PostPage();
    }

    public MyProfilePage clickOnMyProfileMenu(){
        onView(withId(R.id.myProfileMenu)).perform(click());
        return new MyProfilePage();
    }

//    public HomePage checkAddedPost(String post){
//        onView(withId(R.id.homeRecylerView))
//
//        return this;
//    }

//    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
//        checkNotNull(itemMatcher);
//        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("has item at position " + position + ": ");
//                itemMatcher.describeTo(description);
//            }
//
//            @Override
//            protected boolean matchesSafely(final RecyclerView view) {
//                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
//                if (viewHolder == null) {
//                    // has no item on such position
//                    return false;
//                }
//                return itemMatcher.matches(viewHolder.itemView);
//            }
//        };
//    }

}
