package phunware.phunwarehomework.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import phunware.phunwarehomework.R;
import phunware.phunwarehomework.adapter.RecyclerVenueAdapter;
import phunware.phunwarehomework.model.Venue;
import phunware.phunwarehomework.network.DataListener;
import phunware.phunwarehomework.network.DataLoader;
import retrofit.RetrofitError;

/**
 * @author Juan Hurtado on 6/8/2015.
 */
public class FragmentVenueList extends Fragment implements DataListener {
    /**
     * Fragment Venue List TAG
     */
    public static final String HOME = "home";
    /**
     * Bundle parcelable argument name
     */
    private static final String ARG_VENUES = "venues";

    @InjectView(R.id.rl_list_background) RelativeLayout listBackground;
    @InjectView(R.id.recycler_venues) RecyclerView venuesRecyclerView;
    @InjectView(R.id.pg_loading) ProgressBar loadingBar;
    /**
     * Click listener Callback
     */
    private OnItemClickListener sClickListener;
    private RecyclerVenueAdapter mRecyclerAdapter;


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
        /**
         * Initialize ButterKnife
         */
        ButterKnife.inject(this, view);
        setRetainInstance(true);
        /**
         * Set recyclers view layout manager
         */
        venuesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        /**
         * Download the data only if saved instance state equals null
         * otherwise use the list already downloaded
         */
        if(savedInstanceState == null){
            DataLoader.loadData(this);
        } else {
            setVenueList(savedInstanceState.<Venue>getParcelableArrayList(ARG_VENUES));
        }
    }

    /**
     *
     * @param response callback returning response object
     */
    @Override
    public void onDownloadComplete(Object response) {
        setVenueList((List<Venue>) response);
    }

    @Override
    public void onDownloadFailed(RetrofitError error) {
        if(error.isNetworkError()){
            loadingBar.setVisibility(View.GONE);
            listBackground.bringToFront();
            listBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.antena));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            sClickListener = (OnItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDetailClicked");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_VENUES, (ArrayList<? extends android.os.Parcelable>) mRecyclerAdapter.getDataSet());
    }

    /**
     *
     * @return New instance of this fragment
     */
    public static FragmentVenueList getInstance(){
        return new FragmentVenueList();
    }
    /**
     *
     * @param venues set current venues to the recycler view
     */
    private void setVenueList(final List<Venue> venues){
        if(mRecyclerAdapter == null){
            mRecyclerAdapter= new RecyclerVenueAdapter(getActivity().getApplicationContext(), venues, new RecyclerVenueAdapter.onItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    sClickListener.onItemClick(venues.get(position));
                }
            });
            venuesRecyclerView.setAdapter(mRecyclerAdapter);
        } else {
            mRecyclerAdapter.updateData(venues);
            venuesRecyclerView.setAdapter(mRecyclerAdapter);
        }

        loadingBar.setVisibility(View.GONE);
    }

}
