package phunware.phunwarehomework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import phunware.phunwarehomework.ProjectApplication;
import phunware.phunwarehomework.R;
import phunware.phunwarehomework.fragment.FragmentVenueDetail;
import phunware.phunwarehomework.fragment.FragmentVenueList;
import phunware.phunwarehomework.model.Schedule;
import phunware.phunwarehomework.model.Venue;

public class ItemListActivity extends ActionBarActivity implements FragmentVenueList.OnItemClickListener, FragmentVenueDetail.OnBackButton{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);

        getSupportActionBar().setIcon(R.drawable.ic_home);

        if(savedInstanceState == null){
            replace(FragmentVenueList.getInstance(), R.id.phone_main_container, FragmentVenueList.HOME);

            if(ProjectApplication.getInstance().isTablet()) {
                replace(FragmentVenueDetail.getInstance(getDummyVenue()),R.id.tablet_auxiliary_layout, FragmentVenueDetail.DETAIL );
            }
        }
    }

    private void replace(Fragment fragment, int container, String tag) {
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(container, fragment, tag).commit();
    }

    @Override
    public void onItemClick(Venue venue) {
        if(ProjectApplication.getInstance().isTablet()){
            replace(FragmentVenueDetail.getInstance(venue), R.id.tablet_auxiliary_layout, FragmentVenueList.HOME );
        } else {
            Intent intent = new Intent(ItemListActivity.this, ItemDetailActivity.class);
            intent.putExtra(FragmentVenueDetail.ARG_VENUE, venue);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(ProjectApplication.getInstance().isTablet()){
            getMenuInflater().inflate(R.menu.menu_phunware_home_work, menu);
        }

        return true;
    }

    private Venue getDummyVenue(){
        List<Schedule> availableHours = new ArrayList<>();
        Venue venue = new Venue();
        Schedule schedule = new Schedule();

        schedule.setmEndDatel();
        schedule.setmStartDate();
        availableHours.add(schedule);

        venue.setmAddress(getString(R.string.default_address));
        venue.setmImageUrl(getString(R.string.default_imae_url));
        venue.setmName(getString(R.string.default_name));
        venue.setmTicketLink();
        venue.setmAvailableHours(availableHours);

        return venue;
    }

    @Override
    public void onBackButton() {
        onBackPressed();
    }
}
