package com.example.dipan.mobiled2gis;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button clearBtn,heatmapBtn,markerBtn;
    private GoogleMap mMap;
    List <Double>signal = new ArrayList<>();
    List<LatLng> listPoints = new ArrayList<>();
    String ARRAY_JSON = "coordinates";
    String LONGITUDE = "long";
    String LATITUDE = "lat";
    String SIGNAL = "sigstrength";
    int temporal = 1;
    TextView sig;
    private static final int MENU_ITEM_ITEM1 = 1;
    private static final int MENU_ITEM_ITEM2 = 2;
    private static final int MENU_ITEM_ITEM3 = 3;
    private static final int MENU_ITEM_ITEM4 = 4;
    private static final int MENU_ITEM_ITEM5 = 5;
    private static final int MENU_ITEM_ITEM6 = 6;
    private static final int MENU_ITEM_ITEM7 = 7;
    private static final int MENU_ITEM_ITEM8 = 8;
    private static final int MENU_ITEM_ITEM9 = 9;
    private static final int MENU_ITEM_ITEM10 = 10;
    private static final int MENU_ITEM_ITEM11 = 11;
    private static final int MENU_ITEM_ITEM12 = 12;
    private static final int MENU_ITEM_ITEM13 = 13;
    private static final int MENU_ITEM_ITEM14 = 14;
    private static final int MENU_ITEM_ITEM15 = 15;
    private static final int MENU_ITEM_ITEM16 = 16;
    private static final int MENU_ITEM_ITEM17 = 17;
    private static final int MENU_ITEM_ITEM18 = 18;
    private static final int MENU_ITEM_ITEM19 = 19;
    private static final int MENU_ITEM_ITEM20 = 20;
    private static final int MENU_ITEM_ITEM21 = 21;
    private static final int MENU_ITEM_ITEM22 = 22;
    private static final int MENU_ITEM_ITEM23 = 23;
    private static final int MENU_ITEM_ITEM24 = 24;
    private static final int MENU_ITEM_ITEM25 = 25;
    private static final int MENU_ITEM_ITEM26 = 26;

    MapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        isInternetOn();

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.MapFragment2);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setVisibility(View.INVISIBLE);


        clearBtn = (Button) findViewById(R.id.button);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearLists();
                mMap.clear();
            }
        });

        heatmapBtn = (Button) findViewById(R.id.heatmapbutton);
        heatmapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temporal == listPoints.size()){
                    //Snackbar.make(view, "Το κατέβασμα της πληροφορίας τελείωσε. Παρακαλώ επιλέξτε πάλι απο το μενού.", Snackbar.LENGTH_LONG)
                    //  .setAction("Action", null).show();
                    setMapVisible(mMap);
                }
                else {Snackbar.make(view, "Δοκιμάστε ξανά η πληροφορία δεν κατέβηκε ακόμα", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();}
            }
        });

        markerBtn = (Button) findViewById(R.id.markerbutton);
        markerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clearLists();
                //mapFragment.getView().setVisibility(View.VISIBLE);
                //sig.setVisibility(View.INVISIBLE);
                if(temporal == listPoints.size()){
                    //Snackbar.make(view, "Το κατέβασμα της πληροφορίας τελείωσε. Παρακαλώ επιλέξτε πάλι απο το μενού.", Snackbar.LENGTH_LONG)
                    //  .setAction("Action", null).show();
                    //setMapVisible(mMap);
                    MarkersDraw();
                }
                else {Snackbar.make(view, "Δοκιμάστε ξανά η πληροφορία δεν κατέβηκε ακόμα", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();}
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //Add options to the app menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(Menu.NONE, MENU_ITEM_ITEM1, Menu.NONE, "LTE σημεία σήματος στην Αττική.");
        menu.add(Menu.NONE, MENU_ITEM_ITEM2, Menu.NONE, "Σημεία σήματος άνω του 50% ισχύος.");
        menu.add(Menu.NONE, MENU_ITEM_ITEM3, Menu.NONE, "Σημεία παρατηρησης σήματος Vodafone");
        menu.add(Menu.NONE, MENU_ITEM_ITEM4, Menu.NONE, "Σημεία παρατηρησης σήματος Cosmote");
        menu.add(Menu.NONE, MENU_ITEM_ITEM5, Menu.NONE, "Σημεία παρατηρησης σήματος Wind");
        menu.add(Menu.NONE, MENU_ITEM_ITEM6, Menu.NONE, "Σημεία παρατηρησης σήματος CU");
        menu.add(Menu.NONE, MENU_ITEM_ITEM7, Menu.NONE, "Σημεία σήματος τύπου GPRS");
        menu.add(Menu.NONE, MENU_ITEM_ITEM8, Menu.NONE, "Σημεία σήματος τύπου EDGE");
        menu.add(Menu.NONE, MENU_ITEM_ITEM9, Menu.NONE, "Σημεία σήματος τύπου HSDPA");
        menu.add(Menu.NONE, MENU_ITEM_ITEM10, Menu.NONE, "Σημεία σήματος τύπου UMTS");
        menu.add(Menu.NONE, MENU_ITEM_ITEM11, Menu.NONE, "Περίοδος καλοκαίρι 2014,Cosmote");
        menu.add(Menu.NONE, MENU_ITEM_ITEM12, Menu.NONE, "Περίοδος φθινόπωρο 2014,Cosmote");
        menu.add(Menu.NONE, MENU_ITEM_ITEM13, Menu.NONE, "Περίοδος χειμώνας 2015,Cosmote");
        menu.add(Menu.NONE, MENU_ITEM_ITEM14, Menu.NONE, "Περίοδος άνοιξη 2015,Cosmote");
        menu.add(Menu.NONE, MENU_ITEM_ITEM15, Menu.NONE, "Περίοδος καλοκαίρι 2015,Cosmote");
        menu.add(Menu.NONE, MENU_ITEM_ITEM16, Menu.NONE, "Περίοδος καλοκαίρι 2014,Vodafone");
        menu.add(Menu.NONE, MENU_ITEM_ITEM17, Menu.NONE, "Περίοδος φθινόπωρο 2014,Vodafone");
        menu.add(Menu.NONE, MENU_ITEM_ITEM18, Menu.NONE, "Περίοδος χειμώνας 2015,Vodafone");
        menu.add(Menu.NONE, MENU_ITEM_ITEM19, Menu.NONE, "Περίοδος άνοιξη 2015,Vodafone");
        menu.add(Menu.NONE, MENU_ITEM_ITEM20, Menu.NONE, "Περίοδος καλοκαίρι 2015,Vodafone");
        menu.add(Menu.NONE, MENU_ITEM_ITEM21, Menu.NONE, "Περίοδος καλοκαίρι 2014,Wind");
        menu.add(Menu.NONE, MENU_ITEM_ITEM22, Menu.NONE, "Περίοδος φθινόπωρο 2014,Wind");
        menu.add(Menu.NONE, MENU_ITEM_ITEM23, Menu.NONE, "Περίοδος χειμώνας 2015,Wind");
        menu.add(Menu.NONE, MENU_ITEM_ITEM24, Menu.NONE, "Περίοδος άνοιξη 2015,Wind");
        menu.add(Menu.NONE, MENU_ITEM_ITEM25, Menu.NONE, "Περίοδος καλοκαίρι 2015,Wind");


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == MENU_ITEM_ITEM1) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                //Snackbar.make(view, "Please wait until the download was finished", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                new getData().execute("http://83.212.84.208/Api/LTE_Points.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                //Snackbar.make(view, "Download finished. You may load the map", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                //MarkerClusterShowFunction();
                //clearLists();
                MarkersDraw();
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM2) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                //Snackbar.make(view, "Please wait until the download was finished", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                new getData().execute("http://83.212.84.208/Api/MaxStr.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                //Snackbar.make(view, "Download finished. You may load the map", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                //MarkerClusterShowFunction();
                MarkersDraw();
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM3) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/vodafonehsdpa.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    //if(listPoints.size() == temporal)
                        break;

                }
            }
            else{
                setMapVisible(mMap);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM4) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/cosmotepoints.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //MarkerClusterShowFunction();
                //addHeatMap(listPoints);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);

            }

            return true;
        }

        if (id == MENU_ITEM_ITEM5) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/windpoints.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                //MarkerClusterShowFunction();
                setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM6) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                //Snackbar.make(view, "Please wait until the download was finished", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                new getData().execute("http://83.212.84.208/Api/cupoints.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                //Snackbar.make(view, "Download finished. You may load the map", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
                //MarkerClusterShowFunction();
                MarkersDraw();
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM7) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/gprs.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                //MarkerClusterShowFunction();
                setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM8) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/edge.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                //MarkerClusterShowFunction();
                setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM9) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/hsdpa.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                //MarkerClusterShowFunction();
                setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM10) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/umts.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                //MarkerClusterShowFunction();
                setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM11) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/summer2014cosmote.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM12) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/autumn2014.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM13) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/winter2015cosmote.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM14) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/spring2015cosmote.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM15) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/summer2015cosmote.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM16) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/vodafone2014summer.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM17) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/autumn2014vodafone.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM18) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/winter2015vodafone.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM19) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/spring2015vodafone.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM20) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/summer2015vodafone.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM21) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/summer2014wind.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM22) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/autumn2014wind.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM23) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/winter2015wind.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM24) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/spring2015wind.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        if (id == MENU_ITEM_ITEM25) {
            //clearLists();
            if (listPoints.isEmpty() == true){
                new getData().execute("http://83.212.84.208/Api/summer2015wind.php");
                while(listPoints.isEmpty() == true){
                    Context context = getApplicationContext();
                    CharSequence text = "Παρακαλώ περιμένετε να κατέβει η πληροφορία ";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }
            }
            else{
                MarkersDraw();
                //drawline(listPoints);
                //MarkerClusterShowFunction();
                //setMapVisible(mMap);
                //sig = (TextView) findViewById(R.id.textView);
                //sig.setText("Ο μέσος όρος του σήματος που παρατηρήθηκε είναι: " + Mean(signal).intValue());
                //sig.setVisibility(View.VISIBLE);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        //setUpClustering();
    }

    public  void clearLists(){
        listPoints = new ArrayList<>();
        signal = new ArrayList<>();
        //listHeat.clear();
    }

    //Points where signal is showing.
    /*Συναρτηση που δείχνει στο χάρτη ολα τα σημεία που πρεπει να απεικονίζονται στο χαρτη*/

    public void MarkerClusterShowFunction(){
        mMap.clear();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.MapFragment2);
        mapFragment.getView().setVisibility(View.VISIBLE);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                //UiSettings.setZoomControlsEnabled(true);
                setUpClustering();

            }
        });
    }
    // Method to setup the Android Cluster Markers
    private void setUpClustering() {

        // Declare a variable for the cluster manager.
        ClusterManager<AppClusterItem> mClusterManager;

        // Position the map in Attica.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.99, 23.70), 10));

        // Initialize the manager with the context and the map.
        mClusterManager = new ClusterManager<AppClusterItem>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster manager.
        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        addClusterMarkers(mClusterManager);
        clearLists();
    }

    private void addClusterMarkers(ClusterManager<AppClusterItem> mClusterManager) {
        if (listPoints.size() == listPoints.size()) {
            for (int i = 0; i < listPoints.size(); i++) {
                AppClusterItem offsetItem = new AppClusterItem(listPoints.get(i).latitude,listPoints.get(i).longitude,signal.get(i).toString(),mMap);
                mClusterManager.addItem(offsetItem);
            }

        }
    }

    private void addHeatMap(List<LatLng> list,List<Double> list1) {
        mMap.clear();
        List<WeightedLatLng> weightList = new ArrayList<>();
        for (int i = 0;i<list.size();i++){
            //WeightedLatLng t = new WeightedLatLng(list.get(i),list1.get(i));
            weightList.add(new WeightedLatLng(list.get(i),list1.get(i)));

        }
        // Create a heat map tile provider, passing it the latlngs of the police stations.
        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                .weightedData(weightList)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        TileOverlay mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        listPoints = new ArrayList<>();
    }

    public void drawline(final List<LatLng> list){
        mMap.clear();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.MapFragment2);
        mapFragment.getView().setVisibility(View.VISIBLE);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                //setUpClustering();

                for (int i = 0; i < list.size() - 1; i++) {
                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(list.get(i), list.get(i + 1))
                            .width(5)
                            .color(Color.RED));
                }

            }
        });
    }

    public void MarkersDraw(){
        mMap.clear();
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.MapFragment2);
        mapFragment.getView().setVisibility(View.VISIBLE);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.99, 23.70), 10));
        Random randomizer = new Random();
        //String random = list.get(randomizer.nextInt(list.size()));
        if(listPoints.size()<50){
            for (int i = 0; i < listPoints.size(); i++) {
                //int rnd = randomizer.nextInt(listPoints.size());
                LatLng temp = new LatLng(listPoints.get(i).latitude, listPoints.get(i).longitude);
                Marker info = mMap.addMarker(new MarkerOptions()
                        .position(temp)
                        .title("Η ισχύς του σήματος στο σημείο αυτό είναι :")
                        .snippet(signal.get(i).toString()));
            }
        }
        else {
            for (int i = 0; i < 50; i++) {
                int rnd = randomizer.nextInt(listPoints.size());
                LatLng temp = new LatLng(listPoints.get(rnd).latitude, listPoints.get(rnd).longitude);
                Marker info = mMap.addMarker(new MarkerOptions()
                        .position(temp)
                        .title("Η ισχύς του σήματος στο σημείο αυτό είναι :")
                        .snippet(signal.get(i).toString()));
            }
        }

        clearLists();
    }

    public void setMapVisible(GoogleMap googleMap){
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.MapFragment2);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setVisibility(View.VISIBLE);
        mMap.clear();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                //setUpClustering();

                addHeatMap(listPoints,signal);
                clearLists();
            }
        });

    }

    //Internet check
    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            //Toast.makeText(this, " Σύνδεση στο διαδύκτιο επιτυχής. ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

            Toast.makeText(this, " Σύνδεση μη διαθέσιμη. Παρακαλώ συνδεθείτε στο διαδύκτιο. ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    //****Connection to the php file at server****//
    public class getData extends AsyncTask<String, String, String> {

        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... args) {

            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(args[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);

                }

            }catch( Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }




            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            //************************
            try {
                JSONObject jsonRootObject = new JSONObject(result);

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonRootObject.getJSONArray(ARRAY_JSON);

                //Iterate the jsonArray and get info from JSONObjects
                temporal = jsonArray.length();
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //Get data from json object
                    Double longi = jsonObject.getDouble(LONGITUDE);
                    Double lat = jsonObject.getDouble(LATITUDE);
                    Double sig = jsonObject.getDouble(SIGNAL);
                    signal.add(i,sig);
                    listPoints.add(new LatLng(lat,longi));
                }

            } catch (JSONException e) {e.printStackTrace();}

        }
    }//****//

}

