package fatalgamer.com.mradioto.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.library.radio.RadioListener;
import co.mobiwise.library.radio.RadioManager;
import fatalgamer.com.mradioto.R;
import fatalgamer.com.mradioto.fragments.PlayerFragment;
import fatalgamer.com.mradioto.fragments.RadioListFragment;
import fatalgamer.com.mradioto.fragments.listFavFragment;

public class listcountryActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,RadioListener {

    //new updates 12/6/2016
    public static final String NOTE_FRAGMENT_TO_LOAD_EXTRA="fatalgamer.com.mradioto.Fragment_To_Load";
    public static  final String COUNTRY_ID_EXTRA = "fatalgamer.com.mradioto.Identifier";
    public static final String MOVE_FROM_FAV="fatalgamer.com.mradioto.Move_from_fav";
    //
    public static RadioManager mRadioManager;
    //
    //ProgressDialog progressDialog;
    public enum FragmentToLaunch{RADIOS,PLAYER,RADIODETAIL}
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_favorite_white_48dp,
            R.drawable.ic_all_inclusive_white_48dp,
            R.drawable.ic_play_circle_filled_white_48dp
    };
    //
    private String name;
    private String email;
    private Uri photoUrl;
    //private Button mLogOutBtn;
    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcountry_activity);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(listcountryActivity.this,LoginActivity.class));
                }
            }
        };
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
               // providerId = profile.getProviderId();

                // UID specific to the provider
                //String uid = profile.getUid();

                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
                email = profile.getEmail();
                photoUrl = profile.getPhotoUrl();
            }
        }

        //Log.w("user",name);
//
        mRadioManager = RadioManager.with(getApplicationContext());
        mRadioManager.registerListener(this);
        mRadioManager.setLogging(true);

        //

         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        TextView pntv = (TextView) findViewById(R.id.profileNameNav);
        TextView petv = (TextView) findViewById(R.id.profileEmailNav);
        ImageView piv=(ImageView) findViewById(R.id.navimg);
        //mLogOutBtn = (Button) findViewById(R.id.logOutBtn);

        pntv.setText(name);
        petv.setText(email);

         Picasso.with(this.getApplicationContext()).load(photoUrl).into(piv);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {
            mAuth.signOut();

        } else if (id == R.id.nav_share) {

            String url = "https://twitter.com/FataLGamer1";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.nav_send) {
            String url = "https://web.facebook.com/mednour.bg";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new listFavFragment(), "Favorite");
        adapter.addFragment(new RadioListFragment(), "ALL");
        adapter.addFragment(new PlayerFragment(), "Player");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onRadioLoading() {
        Log.d("Radio", "onRadioLoading: ");
       // progressDialog = new ProgressDialog(getApplicationContext());
       // progressDialog.setMessage("Loading...");
       // progressDialog.show();
        //Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRadioConnected() {
       // Log.d("Radio", "onRadioConnected: ");
    }

    @Override
    public void onRadioStarted() {
       // progressDialog.dismiss();
        //Log.d("Radio", "onRadioStarted: ");

    }

    @Override
    public void onRadioStopped() {
        //Log.d("Radio", "onRadioStopped: ");
    }

    @Override
    public void onMetaDataReceived(String s, String s1) {
        //Log.d("Radio", "onMetaDataReceived: ");
    }

    @Override
    public void onError() {
        //Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
