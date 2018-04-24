package fatalgamer.com.mradioto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import fatalgamer.com.mradioto.R;
import fatalgamer.com.mradioto.entities.Radio;


/**
 * Created by FATALGAMER on 12/7/2016.
 */

public class radioViewHolder extends RecyclerView.ViewHolder {

    //
    //static MediaPlayer mPlayer = new MediaPlayer();
    //static RadioManager mRadioManager;
    //
    public TextView radioN;
    public TextView radioD;
    public ImageView radioL;
    public ImageView heartView;
    public ImageView heartViewfill;
    public ImageButton button_play;
    public ImageButton button_stop;
    public RatingBar ratingBar;
    public View mView;
    private String key;


    public radioViewHolder(View itemView) {

        super(itemView);
        //itemView.setOnClickListener(this);
        mView = itemView;
        radioN = (TextView) itemView.findViewById(R.id.radioName);
        radioD = (TextView) itemView.findViewById(R.id.radioDesc);
        radioL = (ImageView) itemView.findViewById(R.id.radioLogo);
        heartView = (ImageView) itemView.findViewById(R.id.fav);
        heartViewfill = (ImageView) itemView.findViewById(R.id.fav2);
        button_play = (ImageButton) itemView.findViewById(R.id.button_play);
        button_stop = (ImageButton) itemView.findViewById(R.id.button_stop);
        ratingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
    }

    public void bindToRadio(final int position,final Radio radio, final Context context, final String key, View.OnClickListener starClickListener, View.OnClickListener starClickListener2,View.OnClickListener playClickListener,View.OnClickListener stopClickListener) {

/*
        mRadioManager = RadioManager.with(context);
        mRadioManager.registerListener(this);
        mRadioManager.setLogging(true);*/

        this.key = key;
        radioN.setText(radio.getName());
        radioD.setText(radio.getDesc());
        //initialize rating from databse
        if (radio.getNbrR() == 0) {
            ratingBar.setRating(0);
        } else {
            ratingBar.setRating(radio.getSommeR() / radio.getNbrR());
        }


        Picasso.with(context).load(radio.getLogo()).into(radioL);
        heartView.setOnClickListener(starClickListener);
        heartViewfill.setOnClickListener(starClickListener2);
        // mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        button_play.setOnClickListener(playClickListener);
        button_stop.setOnClickListener(stopClickListener);
/*
        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = radio.getFlux();
                String streamUrl = txt;
                Toast.makeText(getApplicationContext(), ""+position, Toast.LENGTH_LONG).show();
                if (!mRadioManager.isPlaying()) {//
                    Log.d("position radio played", "old pos: "+playingitem+" new position"+position);
            playingitem=position;
                    mRadioManager.startRadio(txt);
                    button_play.setVisibility(View.GONE);
                    button_stop.setVisibility(View.VISIBLE);

                } else {


                    mRadioManager.stopRadio();
                    button_play.setVisibility(View.VISIBLE);
                    button_stop.setVisibility(View.GONE);

                }


            }
        });


        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (mPlayer.isPlaying()) {
                //  mPlayer.reset();
                //  }
                if (mRadioManager.isPlaying()) {
                    mRadioManager.stopRadio();


                }
                button_play.setVisibility(View.VISIBLE);
                button_stop.setVisibility(View.GONE);

            }
        });*/

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            // Called when the user swipes the RatingBar
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference RadioRef = dbRef.child("/radios").child(key);
                if (fromUser) {
                    ratingUpdate(key, radio, RadioRef, rating);
                }

                Log.d("rating", "onRatingChanged: " + rating + "user" + fromUser);

            }
        });
    }

    public void ratingUpdate(String key, Radio radio, DatabaseReference radioRef, final float rating) {

        radioRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Radio p = mutableData.getValue(Radio.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                } else {
                    // Star the post and add self to stars
                    p.setNbrR(p.getNbrR() + 1);
                    p.setSommeR(p.getSommeR() + rating);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("rating", "postTransaction:onComplete:" + databaseError);
            }
        });

    }

}
