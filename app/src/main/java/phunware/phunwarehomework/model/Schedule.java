package phunware.phunwarehomework.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import phunware.phunwarehomework.R;

/**
 * @author Juan Hurtado on 6/8/2015.
 */
public class Schedule implements Parcelable {

    /**
     * Start date format to show.
     */
    private static final String START_DATE_FORMAT = "cccc M'/'d h:mma";
    /**
     * End date format to show.
     */
    private static final String END_DATE_FORMAT = "h:mma";

    @SerializedName("end_date")
    private Date mEndDate;

    @SerializedName("start_date")
    private Date mStartDate;

    public void setDates(Date startDate, Date endDate){
        this.mStartDate = startDate;
        this.mEndDate = endDate;
    }

    /**
     * @param context reference
     * @return the display text to be shown
     */
    public final String getDisplayText(final Context context) {
        String displayToFormat = context
                .getString(R.string.venue_detail_schedule_format);
        SimpleDateFormat simpleStartDateFormat = new SimpleDateFormat(
                START_DATE_FORMAT, Locale.US);
        simpleStartDateFormat.setTimeZone(TimeZone.getDefault());

        SimpleDateFormat simpleEndDateFormat = new SimpleDateFormat(
                END_DATE_FORMAT, Locale.US);
        simpleEndDateFormat.setTimeZone(TimeZone.getDefault());

        return String.format(displayToFormat,
                simpleStartDateFormat.format(mStartDate),
                simpleEndDateFormat.format(mEndDate));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mEndDate != null ? mEndDate.getTime() : -1);
        dest.writeLong(mStartDate != null ? mStartDate.getTime() : -1);
    }

    public Schedule() {
    }

    private Schedule(Parcel in) {
        long tmpMEndDate = in.readLong();
        this.mEndDate = tmpMEndDate == -1 ? null : new Date(tmpMEndDate);
        long tmpMStartDate = in.readLong();
        this.mStartDate = tmpMStartDate == -1 ? null : new Date(tmpMStartDate);
    }

    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
        public Schedule createFromParcel(Parcel source) {
            return new Schedule(source);
        }

        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
}
