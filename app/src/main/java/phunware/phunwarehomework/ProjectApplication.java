package phunware.phunwarehomework;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import phunware.phunwarehomework.network.EndPoints;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * @author Juan Hurtado on 6/8/2015.
 *
 */
public class ProjectApplication extends Application {

    /**
     * Application instance
     */
    private static ProjectApplication mInstance;

    /**
     *  Reference to network callbacks
     */
    private EndPoints mNetworkAdapter;

    /**
     *  Servers end point url
     */
    private static final String API_URL = "http://s3.amazonaws.com";

    /**
     * Date format to parse
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss Z";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // set Gson dateFormat converter to parse start and end date of each schedule

        Gson gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();

        // creation of network adapter

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setEndpoint(API_URL).build();

        mNetworkAdapter = restAdapter.create(EndPoints.class);
    }

    /**
     *
     * @return Api end points
     */
    public EndPoints getNetworkEndPoints() {
        return mNetworkAdapter;
    }

    /**
     *
     * @return Application instance
     */
    public static ProjectApplication getInstance() {
        return mInstance;
    }

    /**
     *
     * @return Internet connection status
     */
    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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

}
