package ge.bog.gkinkladze.bogchat.ui.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import ge.bog.gkinkladze.bogchat.R;

public class FreeuniChatFragmentPagerAdapter extends FragmentPagerAdapter{

    private final int PAGE_COUNT = 3;
    private Context context;

    private int[] imageResId = {
            R.drawable.ic_home_white_36dp,
            R.drawable.ic_people_white_36dp,
            R.drawable.ic_account_circle_white_36dp
    };

    public FreeuniChatFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RecentChats();
            case 1:
                return new ContactList();
            case 2:
                return new Settings();
            default:  throw new RuntimeException();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    //es kodi gadasaxediaaaaaaaaaaaaaaaaaaaaa!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //TODO
    //TODO
    //TODO
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        // return tabTitles[position];
        Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
