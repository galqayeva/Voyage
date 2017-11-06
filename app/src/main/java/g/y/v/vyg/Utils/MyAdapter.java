package g.y.v.vyg.Utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import g.y.v.vyg.R;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Model> modelList;
    private Context context;
    String url;

    String registerId,name,lon,lat;
    int ok=1;

    public MyAdapter(List<Model> modelList, Context context) {
        this.modelList=modelList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview,parent,false);

        registerId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Model model=modelList.get(position);

        holder.textViewFriend.setText(model.getrName());



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewFriend;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewFriend=(TextView)itemView.findViewById(R.id.restaurantName);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.linearLayout);
        }
    }
}