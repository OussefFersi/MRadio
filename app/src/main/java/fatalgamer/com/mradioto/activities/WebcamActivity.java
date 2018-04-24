package fatalgamer.com.mradioto.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import fatalgamer.com.mradioto.R;


public class WebcamActivity extends AppCompatActivity {

    private static final String TAG = "WebcamActivity";
    private String path;
    //private HashMap<String, String> options;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (!LibsChecker.checkVitamioLibs(this))
        //return;
        setContentView(R.layout.activity_webcam);
        mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            path = bundle.getString("StreamingVideoLink");


        } else {
            path = "rtmp://196.203.16.78:1936/rliveedge/streamnat//streamnat";
        }
        // tv.setText(Extras.setText(extras.getString("choix")));

        /*options = new HashMap<>();
        options.put("rtmp_playpath", "");
        options.put("rtmp_swfurl", "");
        options.put("rtmp_live", "1");
        options.put("rtmp_pageurl", "");*/
        mVideoView.setVideoPath(path);
        //mVideoView.setVideoURI(Uri.parse(path), options);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
               // mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
    }
}