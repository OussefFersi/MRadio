package fatalgamer.com.mradioto.fragments;


import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.truizlop.fabreveallayout.FABRevealLayout;
import com.truizlop.fabreveallayout.OnRevealChangeListener;

import java.util.List;

import fatalgamer.com.mradioto.R;
import fatalgamer.com.mradioto.entities.Radio;

import static fatalgamer.com.mradioto.activities.listcountryActivity.mRadioManager;

//import static fatalgamer.com.mradioto.adapters.radioViewHolder.mPlayer;
//
/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {


 //
 //static MediaPlayer mPlayer2 = new MediaPlayer();
 //

    private static int count;
    private FABRevealLayout fabRevealLayout;
    private TextView albumTitleText;
    private TextView artistNameText;
    private SeekBar songProgress;
    private TextView songTitleText;
    private ImageView radImage;
    private ImageView prev;
    private ImageView stop;
    private ImageView next;
    private View mView;
   // private FloatingActionButton pl;
    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       mView= inflater.inflate(R.layout.fragment_player, container, false);
     findViews();
     configureFABReveal();
     for(int i=0;i<listFavFragment.favList.size();i++){
      Log.d("listfav", "radio : "+listFavFragment.favList.get(i).getName());
     }

     return mView;
    }

 private void findViews() {
  //mPlayer2.setAudioStreamType(AudioManager.STREAM_MUSIC);

  fabRevealLayout = (FABRevealLayout) mView.findViewById(R.id.fab_reveal_layout);
  radImage=(ImageView)mView.findViewById(R.id.album_cover_image);
  albumTitleText = (TextView) mView.findViewById(R.id.album_title_text);
  artistNameText = (TextView) mView.findViewById(R.id.artist_name_text);
  songProgress = (SeekBar) mView.findViewById(R.id.song_progress_bar);
  styleSeekbar(songProgress);

  songTitleText = (TextView) mView.findViewById(R.id.song_title_text);
  prev = (ImageView) mView.findViewById(R.id.previous);
  stop = (ImageView) mView.findViewById(R.id.stop);
  next = (ImageView) mView.findViewById(R.id.next);
  //pl=(FloatingActionButton) mView.findViewById(R.id.pl);
  //pl.setOnClickListener(new View.OnClickListener() {
  // @Override
  // public void onClick(View v) {
    //if (!mPlayer.isPlaying()) {
   //  playR();

   // }


   next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     if(listFavFragment.favList.size()!=0){
     Log.d("count",""+count);
if(count==listFavFragment.favList.size()){
 count=0;
 playR(listFavFragment.favList,0);
}else{
     playR(listFavFragment.favList,count++);
    }
     }
    }
   });

  prev.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    //int count=0;
    if(listFavFragment.favList.size()!=0){
    if(count==0){
     count=listFavFragment.favList.size()-1;
     playR(listFavFragment.favList,count);
    }else{
    playR(listFavFragment.favList,count--);
   }
   }
   }
  });
   //}
 // });
  stop.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View v) {
    if (mRadioManager.isPlaying()) {
     mRadioManager.stopRadio();
    }


   }
  });
 }

 private void styleSeekbar(SeekBar songProgress) {
  int color = getResources().getColor(R.color.background);
  songProgress.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
  songProgress.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
 }

 private void configureFABReveal() {
  fabRevealLayout.setOnRevealChangeListener(new OnRevealChangeListener() {
   @Override
   public void onMainViewAppeared(FABRevealLayout fabRevealLayout, View mainView) {
    showMainViewItems();


   }

   @Override
   public void onSecondaryViewAppeared(final FABRevealLayout fabRevealLayout, View secondaryView) {
    showSecondaryViewItems();
    prepareBackTransition(fabRevealLayout);
    if (!mRadioManager.isPlaying()&&listFavFragment.favList.size()!=0) {
      playR(listFavFragment.favList,0);

      }
   }
  });
 }

 private void showMainViewItems() {
  scale(albumTitleText, 50);
  scale(artistNameText, 150);
 }

 private void showSecondaryViewItems() {
  scale(songProgress, 0);
  animateSeekBar(songProgress);
  scale(songTitleText, 100);
  scale(prev, 150);
  scale(stop, 100);
  scale(next, 200);
 }

 private void prepareBackTransition(final FABRevealLayout fabRevealLayout) {
  new Handler().postDelayed(new Runnable() {
   @Override
   public void run() {
    fabRevealLayout.revealMainView();
   }
  }, 8000);
 }

 private void scale(View view, long delay){
  view.setScaleX(0);
  view.setScaleY(0);
  view.animate()
          .scaleX(1)
          .scaleY(1)
          .setDuration(500)
          .setStartDelay(delay)
          .setInterpolator(new OvershootInterpolator())
          .start();
 }

 private void animateSeekBar(SeekBar seekBar){
  seekBar.setProgress(15);
  ObjectAnimator progressAnimator = ObjectAnimator.ofInt(seekBar, "progress", 15, 0);
  progressAnimator.setDuration(300);
  progressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
  progressAnimator.start();
 }

 public void playR(List<Radio> lol, int index){



 // String txt = "http://radio.mosaiquefm.net:8000/mosalive";
  String flux = lol.get(index).getFlux();
  String streamUrl = flux;

  albumTitleText.setText(lol.get(index).getName());
  Picasso.with(getContext()).load(lol.get(index).getLogo()).into(radImage);


  if (mRadioManager.isPlaying()) {
   mRadioManager.stopRadio();
   mRadioManager.startRadio(streamUrl);
  }else
   mRadioManager.startRadio(streamUrl);

/*
  try {
   mPlayer.setDataSource(streamUrl);
   Toast.makeText(getContext(), "Stream is playing!", Toast.LENGTH_LONG).show();
  } catch (IllegalArgumentException e) {
   Toast.makeText(getContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
  } catch (SecurityException e) {
   Toast.makeText(getContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
  } catch (IllegalStateException e) {
   Toast.makeText(getContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
  } catch (IOException e) {
   e.printStackTrace();
  }
  try {
   mPlayer.prepare();
  } catch (IllegalStateException e) {
   Toast.makeText(getContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
  } catch (IOException e) {
   Toast.makeText(getContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
  }
  Toast.makeText(getContext(), flux, Toast.LENGTH_LONG).show();

  mPlayer.start();*/
 }

}
