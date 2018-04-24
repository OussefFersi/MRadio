package fatalgamer.com.mradioto.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fatalgamer.com.mradioto.R;
import fatalgamer.com.mradioto.activities.RadioDetail2Activity;
import fatalgamer.com.mradioto.adapters.radioViewHolder;
import fatalgamer.com.mradioto.entities.Radio;

import static fatalgamer.com.mradioto.activities.listcountryActivity.mRadioManager;


public class listFavFragment extends Fragment {
    private  int playingitem;
    private static final String TAG = "radioFavFragment";
    boolean lol;
    DatabaseReference myRef;
    Query myReffav;

    public static List<Radio> favList;
    DatabaseReference mdatabaseRef;
    DatabaseReference mdatabaseRef2;


    private FirebaseIndexRecyclerAdapter<Radio, radioViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FirebaseUser user;
    public listFavFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_list_fav,container,false);




        //[START create_database_reference]

        user = FirebaseAuth.getInstance().getCurrentUser();

        mdatabaseRef= FirebaseDatabase.getInstance().getReference("/favoritesR/");
        mdatabaseRef2=FirebaseDatabase.getInstance().getReference();

        myRef = FirebaseDatabase.getInstance().getReference("/radios");

        myReffav=FirebaseDatabase.getInstance().getReference("/favoritesR/").child(user.getUid());
        // [END create_database_reference]

        mRecycler = (RecyclerView) fragmentLayout.findViewById(R.id.radio_list_fav);
        mRecycler.setHasFixedSize(true);
        Log.d(TAG,"on create");
        // Inflate the layout for this fragment
        return fragmentLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);

        mRecycler.setLayoutManager(mManager);
        favList=new ArrayList<>();

        mAdapter=new FirebaseIndexRecyclerAdapter<Radio, radioViewHolder>(
                Radio.class,
                R.layout.item_radio,
                radioViewHolder.class,
                myReffav,
                myRef


        ) {
            @Override
            protected void populateViewHolder(final radioViewHolder viewHolder,final Radio model, int position) {
                final DatabaseReference radioRef = getRef(position);


                final String radioKey = radioRef.getKey();

                checkFav(position,viewHolder,radioKey,model,getContext()) ;


                favList.add(model);





                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.w(TAG, "country key= "+radioKey);
                        //Toast.makeText(getContext(),model.getName(), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getContext(), RadioDetail2Activity.class);
                        i.putExtra("StreamingVideoLink", model.getWebcam_URL());
                        i.putExtra("RSS", model.getRSS());
                        i.putExtra("Logo", model.getLogo());
                        getContext().startActivity(i);

                    }
                });

            }

            @Override
            public void onBindViewHolder(radioViewHolder holder, int position, List<Object> payloads) {
                super.onBindViewHolder(holder, position, payloads);
                if(playingitem!=position){
                    holder.button_play.setVisibility(View.VISIBLE);
                    holder.button_stop.setVisibility(View.GONE);

                }
                else {
                    holder.button_play.setVisibility(View.GONE);
                    holder.button_stop.setVisibility(View.VISIBLE);
                }

            }


        };

        mRecycler.setAdapter(mAdapter);





    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }



    public void checkFav(final int position,final radioViewHolder viewHolder, final String radioKey, final  Radio radio,final Context context){



        mdatabaseRef2.child("favoritesR/").child(user.getUid()).child(radioKey).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //favList.clear();
                if(dataSnapshot.exists()){



                    viewHolder.heartView.setVisibility(View.INVISIBLE);
                    viewHolder.heartViewfill.setVisibility(View.VISIBLE);


                    viewHolder.bindToRadio(position, radio, context, radioKey, new View.OnClickListener() {


                        //emtyHeartIcon
                        @Override
                        public void onClick(View starView) {


                            viewHolder.heartView.setVisibility(View.INVISIBLE);
                            viewHolder.heartViewfill.setVisibility(View.VISIBLE);


                            mdatabaseRef.child(user.getUid()).child(radioKey).setValue("lol");
                            //

                            //Snackbar.make(getView(), "Replace with your own action", Snackbar.LENGTH_LONG)
                                   // .setAction("Action", null).show();


                        }

                    }, new View.OnClickListener() {

                        //filledHeartIcon
                        @Override
                        public void onClick(View starView) {

                            //Intent intent = new Intent(getActivity(),listcountryActivity.class);
                            // intent.putExtra(listcountryActivity.MOVE_FROM_FAV,radioKey);
                            mdatabaseRef.child(user.getUid()).child(radioKey).removeValue();
                            Log.d("favlist", "onClick: " + favList.indexOf(radio) + " name :" + favList.get(favList.indexOf(radio)).getName());
                            favList.remove(favList.indexOf(radio));
                            viewHolder.heartViewfill.setVisibility(View.INVISIBLE);

                            viewHolder.heartView.setVisibility(View.VISIBLE);


                        }

                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String txt = radio.getFlux();
                            String streamUrl = txt;
                            int prevposition;
                            prevposition=playingitem;
                            if (!mRadioManager.isPlaying()) {//

                                mRadioManager.startRadio(txt);
                                playingitem=position;
                                mAdapter.notifyItemChanged(prevposition);
                                viewHolder.button_play.setVisibility(View.GONE);
                                viewHolder.button_stop.setVisibility(View.VISIBLE);

                            } else {


                                mRadioManager.stopRadio();
                                viewHolder.button_play.setVisibility(View.VISIBLE);
                                viewHolder.button_stop.setVisibility(View.GONE);

                            }

                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (mRadioManager.isPlaying()) {
                                mRadioManager.stopRadio();
                                viewHolder.button_play.setVisibility(View.VISIBLE);
                                viewHolder.button_stop.setVisibility(View.GONE);

                            }
                            viewHolder.button_play.setVisibility(View.VISIBLE);
                            viewHolder.button_stop.setVisibility(View.GONE);
                        }
                    });

                }else{
                    Log.w(TAG,"not in favory");


                }




            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }


}
