package phunware.phunwarehomework.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import phunware.phunwarehomework.R;
import phunware.phunwarehomework.fragment.FragmentVenueDetail;
import phunware.phunwarehomework.model.Venue;

/**
 * Created by Juan Hurtado on 6/10/2015.
 */
public class ItemDetailActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        /**
         * Get support actionbar
         */
        ActionBar actionbar = getSupportActionBar();
        /**
         * Set custom icon in the top left corner of the actionbar &
         * Enable back button in the actionbar
         */
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
        actionbar.setIcon(R.drawable.ic_home_action);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        /**
         * Obtain current venue from intent
         */
        Venue venue = getIntent().getExtras().getParcelable(FragmentVenueDetail.ARG_VENUE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.phone_main_container, FragmentVenueDetail.getInstance(venue), FragmentVenueDetail.DETAIL)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_phunware_home_work, menu);
        return true;
    }

}
