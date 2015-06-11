package phunware.phunwarehomework;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import phunware.phunwarehomework.network.EndPoints;
import phunware.phunwarehomework.util.DateParser;
import retrofit.RestAdapter;

/**
 * Created by Juan Hurtado on 6/8/2015.
 */
public class ProjectApplication extends Application {
    private static ProjectApplication mInstance;
    private DateParser mParser;
    private boolean isTablet;
    private EndPoints mApi;

    public static ProjectApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mParser = new DateParser();
        mInstance = this;
        isTablet = getDeviceType();

        String mEndPoint = "http://s3.amazonaws.com";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setEndpoint(mEndPoint).build();

        mApi = restAdapter.create(EndPoints.class);
    }

    public DateParser getParser() {
        return mParser;
    }

    public EndPoints getEndPoints() {
        return mApi;
    }

    private boolean getDeviceType() {
        TelephonyManager manager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE;
    }

    public boolean isTablet(){
        return isTablet;
    }

}
