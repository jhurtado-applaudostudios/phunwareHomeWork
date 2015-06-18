package phunware.phunwarehomework.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import phunware.phunwarehomework.R;
import phunware.phunwarehomework.model.Venue;

/**
 * @author Juan Hurtado on 6/8/2015.
 */
public class RecyclerVenueAdapter extends RecyclerView.Adapter<RecyclerVenueAdapter.ViewHolder> {
    /**
     * Current data list
     */
    private List<Venue> mDataSet;
    /**
     * On list item click listener
     */
    private final onItemClickListener mClickCallback;
    private final Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mAddress;
        private final TextView mAddress2;
        private final ImageView mVenuePhoto;
        final LinearLayout mItem;

        public ViewHolder(View convertView) {
            super(convertView);
            mAddress = (TextView) convertView.findViewById(R.id.txt_detail_address);
            mAddress2 = (TextView) convertView.findViewById(R.id.txt_detail_address2);
            mVenuePhoto = (ImageView) convertView.findViewById(R.id.img_venue);
            mItem = (LinearLayout) convertView.findViewById(R.id.item_layout);
        }

        public TextView getAddress() {
            return mAddress;
        }

        public TextView getAddress2() {
            return mAddress2;
        }

        public ImageView getVenuePhoto() {
            return mVenuePhoto;
        }

        public LinearLayout getItem(){
            return mItem;
        }
    }

    public RecyclerVenueAdapter(Context context, List<Venue> dataSet, onItemClickListener listener) {
        mContext = context;
        mClickCallback = listener;
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.venue_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        /**
         * Recycle the views
         */
        viewHolder.getAddress().setText(mDataSet.get(position).getmName());
        viewHolder.getAddress2().setText(mDataSet.get(position).getmAddress());
        Glide.with(mContext)
                .load(mDataSet.get(position).getmImageUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(100, 100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        viewHolder.getVenuePhoto().setImageBitmap(resource);
                    }
                });


        viewHolder.getItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickCallback.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public interface onItemClickListener {
        public void onItemClick(int position);
    }

    public List<Venue> getDataSet(){
        return mDataSet;
    }

    public void updateData(List<Venue> dataSet){
        mDataSet = dataSet;
    }

}
