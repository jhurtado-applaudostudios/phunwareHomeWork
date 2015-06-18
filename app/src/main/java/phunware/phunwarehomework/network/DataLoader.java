package phunware.phunwarehomework.network;

import java.util.List;

import phunware.phunwarehomework.ProjectApplication;
import phunware.phunwarehomework.model.Venue;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Juan Hurtado on 6/16/2015.
 *
 * The class data loader is the one that manages the activation of the dataListener interface
 *
 */
public class DataLoader{
    static DataListener dataListener;
    /**
     *
     * @param host the class that is implementing DataListener
     * It also load retries data from the server using retrofit
     * it triggers the method on download complete or on download
     * failed depending on the situation
     */
    public static void loadData(DataListener host){
        dataListener = host;

        ProjectApplication.getInstance().getNetworkEndPoints().getVenues(new Callback<List<Venue>>() {
            @Override
            public void success(List<Venue> venues, Response response) {
                dataListener.onDownloadComplete(venues);
            }

            @Override
            public void failure(RetrofitError error) {
                dataListener.onDownloadFailed(error);
            }
        });
    }
}
