package phunware.phunwarehomework.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.text.ParseException;
import java.util.List;

import phunware.phunwarehomework.R;
import phunware.phunwarehomework.ProjectApplication;
import phunware.phunwarehomework.model.Schedule;
import phunware.phunwarehomework.model.Venue;
import phunware.phunwarehomework.util.DateParser;

/**
 * Created by Juan Hurtado on 6/8/2015.
 */
public class FragmentVenueDetail extends Fragment {
    public static final String DETAIL = "detail";
    public static final String ARG_VENUE = "venue";
    private OnBackButton mBackButton;
    private DateParser mParser;
    private Venue mCurrentVenue;
    private Button mOrderTicketsButton;
    private ImageView mVenuePhoto;
    private TextView mAddress;
    private TextView mAddress2;
    private TextView mSchedules;


    public interface OnBackButton{
        public void onBackButton();
    }


    public static FragmentVenueDetail getInstance(Venue venue){
        FragmentVenueDetail detail = new FragmentVenueDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_VENUE, venue);
        detail.setArguments(bundle);
        return detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mCurrentVenue = (Venue) arguments.getSerializable(ARG_VENUE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venue_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mOrderTicketsButton = injectView(R.id.btn_pre_order);
        mVenuePhoto = injectView(R.id.img_venue_detail);
        mAddress = injectView(R.id.txt_detail_name);
        mAddress2 = injectView(R.id.txt_detail_name_2);
        mSchedules = injectView(R.id.txt_detail_shedules);
        mParser = ProjectApplication.getInstance().getParser();

        setRetainInstance(true);
        setHasOptionsMenu(true);

        setData(mCurrentVenue);

        mOrderTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webIntent(mCurrentVenue.getmTicketLink());
            }
        });
    }

    public void setData(Venue venue){
        Glide.with(getActivity().getApplicationContext())
                .load(venue.getmImageUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(100, 100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        mVenuePhoto.setImageBitmap(resource);
                    }
                });

        mAddress.setText(venue.getmName());
        mAddress2.setText(venue.getmAddress());
        mSchedules.setText(getSchedules(venue.getmAvailableHours()));
    }


    public String getSchedules(List<Schedule> schedules){
        StringBuilder builder = new StringBuilder();

        for (Schedule schedule : schedules)
            if (schedule != null)
                try {
                    builder.append(mParser.getParsedDate(schedule.getmStartDate(), schedule.getmEndDatel())).append(DateParser.LINE_SEPARATOR);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

        return builder.toString();
    }

    private <T> T injectView(int viewId) {
        return (T) getView().findViewById(viewId);
    }

    private void webIntent(String url) {
        if (!url.startsWith("https://") && !url.startsWith("http://")){
            url = "http://" + url;
        }
        Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(openUrlIntent);
    }

    public void shareIntent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mCurrentVenue.getmAddress() + DateParser.WHITE_SPACE + mCurrentVenue.getmCity()
                + "\n\n" + mCurrentVenue.getmTicketLink() + "\n\n" + getSchedules(mCurrentVenue.getmAvailableHours()));
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mCurrentVenue.getmName());
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mBackButton.onBackButton();
                break;
            case R.id.share_item:
                shareIntent();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mBackButton = (OnBackButton) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDetailClicked");
        }
    }

}
