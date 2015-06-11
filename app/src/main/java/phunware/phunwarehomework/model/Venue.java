package phunware.phunwarehomework.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by Juan Hurtado on 6/8/2015.
 */
public class Venue implements SerializedName, Serializable {

    @SerializedName("ticket_link")
    private String mTicketLink;

    @SerializedName("city")
    private String mCity;

    @SerializedName("schedule")
    private List<Schedule> mAvailableHours;

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

    public void setmTicketLink() {
        this.mTicketLink = phunware.phunwarehomework.util.DateParser.WHITE_SPACE;
    }

    public void setmAddress(String address) {
        this.mAddress = address;
    }

    public void setmAvailableHours(List<Schedule> availableHours) {
        this.mAvailableHours = availableHours;
    }

    public String getmTicketLink() {
        return mTicketLink;
    }

    public String getmCity() {
        return mCity;
    }

    public List<Schedule> getmAvailableHours() {
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


    @Override
    public String value() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }


}