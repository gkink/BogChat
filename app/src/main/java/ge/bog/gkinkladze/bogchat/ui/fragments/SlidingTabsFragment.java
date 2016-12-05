package ge.bog.gkinkladze.bogchat.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ge.bog.gkinkladze.bogchat.R;
import ge.bog.gkinkladze.bogchat.view.SlidingTabLayout;


public class SlidingTabsFragment extends Fragment{

    private FragmentActivity parent;

    // Called when the Fragment is attached to its parent Activity.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = (FragmentActivity)context;
    }

    /**
     * Inflates the {@link View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sliding_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new FreeuniChatFragmentPagerAdapter(parent.getSupportFragmentManager(),
                parent));

        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);

        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        //set custom view for tabs with icons
        slidingTabLayout.setCustomTabView(R.layout.icon_tab_layout, 0);
        //set viewpager
        slidingTabLayout.setViewPager(viewPager);
    }
}
