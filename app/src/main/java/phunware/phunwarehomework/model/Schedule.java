package phunware.phunwarehomework.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * Created by Juan Hurtado on 6/8/2015.
 */
public class Schedule implements SerializedName, Serializable {

    @SerializedName("end_date")
    private String mEndDatel;

    @SerializedName("start_date")
    private String mStartDate;

    public String getmStartDate() {
        return mStartDate;
    }

    public String getmEndDatel() {
        return mEndDatel;
    }

    public void setmEndDatel() {
        this.mEndDatel = "";
    }

    public void setmStartDate() {
        this.mStartDate = "";
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
