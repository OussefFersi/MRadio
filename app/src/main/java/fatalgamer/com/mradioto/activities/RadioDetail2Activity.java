package fatalgamer.com.mradioto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import fatalgamer.com.mradioto.R;

public class RadioDetail2Activity extends AppCompatActivity {
    String WebcamURL;
    private ImageView RSS;
    private ImageView Webcam;
    String RSSURL;
    //  String WebcamURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_detail2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView logoImage = (ImageView) findViewById(R.id.big);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            RSSURL = bundle.getString("RSS");

            WebcamURL = bundle.getString("StreamingVideoLink");
            Picasso.with(getApplicationContext()).load(bundle.getString("Logo")).into(logoImage);
            //logoImage.setBackgroundResource(bundle.getInt("Logo"));

        } else {
            WebcamURL = "rtmp://196.203.16.78:1936/rliveedge/streamnat//streamnat";
        }
        RSS = (ImageView) findViewById(R.id.southwest);
        Webcam = (ImageView) findViewById(R.id.northwest);
        RSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RadioDetail2Activity.this, NewsActivity.class);
                i.putExtra("RSS", RSSURL);
                // i.putExtra("RSS", RSS);
                startActivity(i);
            }
        });

        Webcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RadioDetail2Activity.this, WebActivity.class);
                i.putExtra("StreamingVideoLink", WebcamURL);
                //   i.putExtra("RSS", RSS);
                startActivity(i);
            }
        });
    }
}


