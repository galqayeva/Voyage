package g.y.v.vyg.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.location.Location;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import g.y.v.vyg.Constants;
import g.y.v.vyg.R;
import g.y.v.vyg.Utils.DatabaseHelper;
import g.y.v.vyg.Utils.GPSTracker;
import g.y.v.vyg.Utils.Model;
import g.y.v.vyg.Utils.MyAdapter;
import g.y.v.vyg.Utils.MySingleTon;


public class FilteringActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private GPSTracker gpsTracker;
    private Location mLocation;
    double latitude, longitude;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Model> modelList;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    DatabaseHelper myDB;
    String url,Tag="salus",type,radius="5000";
    Button btnFilter;
    Spinner spinnertype,spinnerRadius;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtering);

        btnFilter=(Button)findViewById(R.id.button4);
        spinnerRadius=(Spinner)findViewById(R.id.spinner);
        spinnertype=(Spinner)findViewById(R.id.spinner3);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(FilteringActivity.this);

        recyclerView=(RecyclerView)findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        myDB = new DatabaseHelper(getApplicationContext());
        modelList=new ArrayList<>();

        gpsTracker = new GPSTracker(getApplicationContext());
        latitude = gpsTracker.getLocation().getLatitude();
        longitude = gpsTracker.getLocation().getLongitude();

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radius=spinnerRadius.getSelectedItem().toString();
                type=spinnertype.getSelectedItem().toString();
                url= Constants.gmApi(Double.toString(latitude),Double.toString(longitude),radius,type);
                myDB.deleteAll();
                loadRestaurants();

            }
        });


    }


    public void loadRestaurants(){

        Cursor data = myDB.getAlldata();

        if(data.getCount() == 0)
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Getting Places");
            progressDialog.show();

            StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                JSONArray jsonArrayResult=jsonObject.getJSONArray("results");

                                for (int i=0;i<jsonArrayResult.length();i++)
                                {
                                    JSONObject jsonobject=jsonArrayResult.getJSONObject(i);
                                    String lat = jsonobject.getJSONObject("geometry").getJSONObject("location").getString("lat");
                                    String lng = jsonobject.getJSONObject("geometry").getJSONObject("location").getString("lng");
                                    String restaurantName=jsonobject.getString("name");

                                    boolean insertData = myDB.addData(restaurantName,lng,lat);
                                    if(!insertData==true)
                                        Log.d(Tag,"Inserting problem");
                                }

                                modelList.clear();
                                loadListview();
                                progressDialog.dismiss();

                            } catch (JSONException e) {

                                Log.d(Tag, "json parse error");
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d(Tag,"String request Response error ----"+ error.getMessage());
                        }
                    }

            );
            MySingleTon.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            if(swipeRefreshLayout.isRefreshing())
            {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }


    public void loadListview(){

        Cursor data = myDB.getAlldata();

        if(data.getCount() != 0)
        {
            Log.d(Tag,"----------"+data.getCount());
            while(data.moveToNext())
            {
                Model item=new Model(data.getString(1),data.getString(2),data.getString(3));
                modelList.add(item);
            }

            adapter=new MyAdapter(modelList,getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
    }


    @Override
    public void onRefresh() {
        myDB.deleteAll();
        loadRestaurants();
    }
}
