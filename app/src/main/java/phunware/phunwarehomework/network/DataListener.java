package phunware.phunwarehomework.network;
import retrofit.RetrofitError;

/**
 * @author Juan Hurtado on 6/8/2015.
 * @param <T> the returned type of the response
 */
public interface DataListener<T>{
    /**
     *
     * @param response callback returning response object
     */
    void onDownloadComplete(Object response);
    /**
     *
     * @param error callback response error
     */
    void onDownloadFailed(RetrofitError error);
}
