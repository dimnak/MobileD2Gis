package com.example.dipan.mobiled2gis;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;

public class AppClusterItem implements ClusterItem {

    private final LatLng mPosition;

    public AppClusterItem(double latitude, double longitude, String signal, GoogleMap mMap) {
        mPosition = new LatLng(latitude, longitude);
        Marker info = mMap.addMarker(new MarkerOptions()
                .position(mPosition)
                .title("Η ισχύς του σήματος στο σημείο αυτό είναι :")
                .snippet(signal));


    }



    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
