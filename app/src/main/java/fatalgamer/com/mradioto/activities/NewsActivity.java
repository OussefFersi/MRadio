package fatalgamer.com.mradioto.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import fatalgamer.com.mradioto.R;
import fatalgamer.com.mradioto.utils.ReadRss;

public class NewsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String address;
    private String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            link = bundle.getString("RSS");
            System.out.println(link);

        } else {
            link = "empty";
        }
      //ReadRss readRss = new ReadRss(this, recyclerView,"https://queryfeed.net/twitter?q=from%3Aradiomfmtunisie&title-type=tweet-text-full.xml");
       ReadRss readRss = new ReadRss(this, recyclerView,link);

        readRss.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
