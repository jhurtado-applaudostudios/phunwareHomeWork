package phunware.phunwarehomework.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import phunware.phunwarehomework.R;
import phunware.phunwarehomework.model.Schedule;
import phunware.phunwarehomework.model.Venue;
/**
 * @author Juan Hurtado on 6/8/2015.
 */
public class FragmentVenueDetail extends Fragment {
    /**
     * Fragment venue detail TAG
     */
    public static final String DETAIL = "detail";
    /**
     * Bundle parcelable argument name
     */
    public static final String ARG_VENUE = "venue";
    /**
     * Current venue object on detail
     */
    private Venue mCurrentVenue;
    /**
     * UI
     */
    @InjectView(R.id.btn_pre_order) Button orderTicketsButton;
    @InjectView(R.id.img_venue_detail) ImageView venuePhoto;
    @InjectView(R.id.txt_detail_name) TextView address;
    @InjectView(R.id.txt_detail_name_2) TextView address2;
    @InjectView(R.id.txt_detail_schedules) TextView scheduleList;

    public static FragmentVenueDetail getInstance(Venue venue){
        FragmentVenueDetail detail = new FragmentVenueDetail();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_VENUE, venue);
        detail.setArguments(bundle);
        return detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        // receive current venue
        mCurrentVenue = arguments.getParcelable(ARG_VENUE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venue_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);
        setDataToUi();
        setHasOptionsMenu(true);
        /**
         * Click listener for the button that launches
         * a web intent to a browser with the ticket link
         */
        orderTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webIntent(mCurrentVenue.getmTicketLink());
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem shareItem = menu.findItem(R.id.venue_detail_share_menu);
        ShareActionProvider shareActionProvider =
                (ShareActionProvider) MenuItemCompat
                        .getActionProvider(shareItem);
        shareActionProvider.setShareIntent(getShareIntent());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Sets required data to components on the ui (e.g image view )
     */
    void setDataToUi(){
        Glide.with(getActivity().getApplicationContext())
                .load(mCurrentVenue.getmImageUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        venuePhoto.setImageBitmap(resource);
                    }
                });

        address.setText(mCurrentVenue.getmName());
        address2.setText(mCurrentVenue.getmAddress());
        scheduleList.setText(getSchedulesList(mCurrentVenue.getmAvailableHours(), getActivity().getApplicationContext()));

    }
    /**
     * return a string with all the available schedules for
     * the current venue
     *
     * @param list available schedules from the current venue
     * @param context reference
     * @return a string containing all schedules
     */
    private String getSchedulesList(List<Schedule> list, Context context){
        StringBuffer scheduleText = new StringBuffer();
        for (Schedule schedule : list){
            scheduleText.append(schedule.getDisplayText(context) + " \n\n");
        }

        return scheduleText.toString();
    }
    /**
     * make an intent to open the ticket url on a browser
     *
     * @param url from the ticket link
     * Handles url and performs a browser intent
     */
    private void webIntent(String url) {
        if (!url.startsWith("https://") && !url.startsWith("http://")){
            url = "http://" + url;
        }
        Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(openUrlIntent);
    }

    /**
     * Create and return a Share Intent.
     *
     * @return The Share Intent with the Venue Data
     */
    private Intent getShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (mCurrentVenue != null) {
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    mCurrentVenue.getmName());
            intent.putExtra(Intent.EXTRA_TEXT, mCurrentVenue.getShareText());
        }

        return intent;
    }

}
