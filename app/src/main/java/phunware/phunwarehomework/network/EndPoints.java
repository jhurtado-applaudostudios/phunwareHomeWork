package phunware.phunwarehomework.network;

import java.util.List;

import phunware.phunwarehomework.model.Venue;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * @author Juan Carlos Hurtado
 */
public interface EndPoints {
    /**
     *
     * @param response return a list of Venue type objects>
     */
    @GET("/jon-hancock-phunware/nflapi-static.json")
    public void getVenues(Callback<List<Venue>> response);
}
