package phunware.phunwarehomework.network;

import java.util.List;

import phunware.phunwarehomework.model.Venue;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Juan Hurtado on 6/8/2015.
 */
public interface EndPoints {

    @GET("/jon-hancock-phunware/nflapi-static.json")
    public void getVenues(Callback<List<Venue>> response);
}
