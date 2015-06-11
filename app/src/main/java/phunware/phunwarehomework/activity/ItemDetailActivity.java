package phunware.phunwarehomework.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import phunware.phunwarehomework.R;
import phunware.phunwarehomework.fragment.FragmentVenueDetail;
import phunware.phunwarehomework.model.Venue;

/**
 * Created by Juan Hurtado on 6/10/2015.
 */
public class ItemDetailActivity extends ActionBarActivity implements FragmentVenueDetail.OnBackButton{
    private Venue mCurrentVenue;
    private ActionBar mSupportActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mSupportActionBar = getSupportActionBar();

        mSupportActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
        mSupportActionBar.setIcon(R.drawable.ic_home);
        mSupportActionBar.setDisplayHomeAsUpEnabled(true);
        mSupportActionBar.setHomeButtonEnabled(true);

        mCurrentVenue = (Venue) getIntent().getExtras().getSerializable(FragmentVenueDetail.ARG_VENUE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.phone_main_container, FragmentVenueDetail.getInstance(mCurrentVenue), FragmentVenueDetail.DETAIL)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_phunware_home_work, menu);
        return true;
    }

    @Override
    public void onBackButton() {
        onBackPressed();
    }
}
