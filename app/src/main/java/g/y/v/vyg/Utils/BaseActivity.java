package g.y.v.vyg.Utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import g.y.v.vyg.Activities.FilteringActivity;
import g.y.v.vyg.Activities.LoginActivity;
import g.y.v.vyg.R;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import g.y.v.vyg.Activities.MainActivity;


public abstract class BaseActivity extends ActionBarActivity {


    private TextView textViewUserName;
    private ImageView imageViewUserPhoto;
    private Intent intent;
    private SharedPreferences prospectPreferences;
    private SharedPreferences.Editor editorPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void toolBarInitialize(int toolBarId) {
        Toolbar toolbar = (Toolbar) findViewById(toolBarId);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        PrimaryDrawerItem item0 = new PrimaryDrawerItem().withIdentifier(1).withName("Timeline");
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(2).withName("Plan tour");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(3).withName("Filter Places");
        PrimaryDrawerItem item3=  new PrimaryDrawerItem().withIdentifier(4).withName("Reserve");
        PrimaryDrawerItem item4=  new PrimaryDrawerItem().withIdentifier(4).withName("Activities");
        PrimaryDrawerItem item5=  new PrimaryDrawerItem().withIdentifier(4).withName("Settings");
        PrimaryDrawerItem item6=  new PrimaryDrawerItem().withIdentifier(4).withName("Log out");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .build();
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withSliderBackgroundColorRes(R.color.white)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item0,item1,item2,item3,item4,item5,
                        new DividerDrawerItem(),item6
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position){
                            case 1:
                                Intent intent0=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent0);
                                break;
                            case 8:
                                Intent intent1=new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent1);
                                break;

                        }

                        return true;
                    }
                })
                .build();

    }

}