package phunware.phunwarehomework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import java.util.ArrayList;

import phunware.phunwarehomework.R;
import phunware.phunwarehomework.fragment.FragmentVenueDetail;
import phunware.phunwarehomework.fragment.FragmentVenueList;
import phunware.phunwarehomework.model.Schedule;
import phunware.phunwarehomework.model.Venue;

public class ItemListActivity extends ActionBarActivity implements FragmentVenueList.OnItemClickListener{
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Enable custom icon to be displayed
        //On the top left cornet

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setIcon(R.drawable.ic_home_action);

        if(savedInstanceState == null){
            if(findViewById(R.id.tablet_auxiliary_layout) != null) {
                mTwoPane = true;
                replace(FragmentVenueDetail.getInstance(getDummyVenue()),R.id.tablet_auxiliary_layout, FragmentVenueDetail.DETAIL );
            }
            // fragment needs to be added either way
            replace(FragmentVenueList.getInstance(), R.id.phone_main_container, FragmentVenueList.HOME);
        }
    }
    /**
     *
     * @param venue object returned on fragments list item click
     */
    @Override
    public void onItemClick(Venue venue) {
        if(mTwoPane){
            replace(FragmentVenueDetail.getInstance(venue), R.id.tablet_auxiliary_layout, FragmentVenueList.HOME );
        } else {
            Intent intent = new Intent(ItemListActivity.this, ItemDetailActivity.class);
            intent.putExtra(FragmentVenueDetail.ARG_VENUE, venue);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mTwoPane){
            getMenuInflater().inflate(R.menu.menu_phunware_home_work, menu);
        }

        return true;
    }
    /**
     *
     * @param fragment who is going to be replaced
     * @param container in with the fragment is going to be
     * @param tag fragments tag
     */
    private void replace(Fragment fragment, int container, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(container, fragment, tag).commit();
    }
    /**
     * Create a dummy venue to set on the detail in case is two pane
     * @return Dummy venue
     */
    private Venue getDummyVenue(){
        ArrayList<Schedule> availableHours = new ArrayList<>();
        Venue venue = new Venue();
        Schedule schedule = new Schedule();

        schedule.setDates(null,null);
        availableHours.add(schedule);

        venue.setmAddress(getString(R.string.default_address));
        venue.setmImageUrl(getString(R.string.default_image_url));
        venue.setmName(getString(R.string.default_name));
        venue.setmTicketLink("");
        venue.setmAvailableHours(availableHours);

        return venue;
    }

}
