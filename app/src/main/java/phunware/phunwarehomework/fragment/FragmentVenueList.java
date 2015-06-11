package phunware.phunwarehomework.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.List;

import phunware.phunwarehomework.ProjectApplication;
import phunware.phunwarehomework.R;
import phunware.phunwarehomework.adapter.recyclerVenueAdapter;
import phunware.phunwarehomework.model.Venue;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Juan Hurtado on 6/8/2015.
 */
public class FragmentVenueList extends Fragment {
    public static final String HOME = "home";
    private RelativeLayout mBackground;
    private Context mContext;
    private List<Venue> mPersistentList;
    private RecyclerView mVenuesList;
    private OnItemClickListener mClickListener;
    private ProgressBar mProgressBar;

    public static FragmentVenueList getInstance(){
        return new FragmentVenueList();
    }

    public interface OnItemClickListener {
        public void onItemClick(Venue venue);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venue_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getApplicationContext();
        setRetainInstance(true);

        mBackground = injectView(R.id.rl_list_background);
        mProgressBar = injectView(R.id.pg_loading);
        mVenuesList = injectView(R.id.recycler_venues);
        mVenuesList.setLayoutManager(new LinearLayoutManager(mContext));

        testConnection();
    }

    private void testConnection(){
        if(haveNetworkConnection()){
            if(retriveList() == null){
                downloadData();
            } else {
                setVenueList(retriveList());
            }
        } else {
            mProgressBar.setVisibility(View.GONE);
            mBackground.bringToFront();
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.antena));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mClickListener = (OnItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDetailClicked");
        }
    }

    private void downloadData(){
        ProjectApplication.getInstance().getEndPoints().getVenues(new Callback<List<Venue>>() {
            @Override
            public void success(List<Venue> venues, Response response) {
                setSaveList(venues);
                setVenueList(venues);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("retrofit error", error.getMessage());
            }
        });
    }

    private void setVenueList(final List<Venue> venues){
        recyclerVenueAdapter mAdapter = new recyclerVenueAdapter(mContext, venues, new recyclerVenueAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mClickListener.onItemClick(venues.get(position));
            }
        });

        mVenuesList.setAdapter(mAdapter);
        mProgressBar.setVisibility(View.GONE);
    }

    private void setSaveList(List<Venue> list){
        this.mPersistentList = list;
    }

    private List<Venue> retriveList(){
        return mPersistentList;
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private <T> T injectView(int viewId) {
        return (T) getView().findViewById(viewId);
    }

}
