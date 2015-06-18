package phunware.phunwarehomework.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Juan Hurtado on 6/8/2015.
 */
public class Venue implements Parcelable {

    @SerializedName("ticket_link")
    private String mTicketLink;

    @SerializedName("schedule")
    private ArrayList<Schedule> mAvailableHours = new ArrayList<>();

    @SerializedName("address")
    private String mAddress;

    @SerializedName("image_url")
    private String mImageUrl;

    @SerializedName("name")
    private String mName;

    public void setmImageUrl(String url) {
        this.mImageUrl = url;
    }

    public void setmName(String name) {
        this.mName = name;
    }

    public void setmTicketLink(String ticketLink) {
        this.mTicketLink = ticketLink;
    }

    public void setmAddress(String address) {
        this.mAddress = address;
    }

    public void setmAvailableHours(ArrayList<Schedule> availableHours) {
        this.mAvailableHours = availableHours;
    }

    public String getmTicketLink() {
        return mTicketLink;
    }

    public ArrayList<Schedule> getmAvailableHours() {
        return mAvailableHours;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmName() {
        return mName;
    }
    /**
     * @return the shared text to be shown
     */
    public final String getShareText() {
        return mName + " " + mAddress;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTicketLink);
        dest.writeList(mAvailableHours);
        dest.writeString(this.mAddress);
        dest.writeString(this.mImageUrl);
        dest.writeString(this.mName);
    }

    public Venue() {
    }

    private Venue(Parcel in) {
        this.mTicketLink = in.readString();
        in.readTypedList(mAvailableHours, Schedule.CREATOR);
        this.mAddress = in.readString();
        this.mImageUrl = in.readString();
        this.mName = in.readString();
    }

    public static final Creator<Venue> CREATOR = new Creator<Venue>() {
        public Venue createFromParcel(Parcel source) {
            return new Venue(source);
        }

        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };
}