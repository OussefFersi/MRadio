package fatalgamer.com.mradioto.fragments;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import fatalgamer.com.mradioto.R;
import fatalgamer.com.mradioto.activities.RadioDetail2Activity;
import fatalgamer.com.mradioto.adapters.radioViewHolder;
import fatalgamer.com.mradioto.entities.Radio;

import static fatalgamer.com.mradioto.activities.listcountryActivity.mRadioManager;

//import co.mobiwise.library.RadioListener;
//import co.mobiwise.library.RadioManager;


public class RadioListFragment extends Fragment  {
    private static final String TAG = "radioListFragment";
    private  int playingitem;
    boolean lol;
    DatabaseReference myRef;


    DatabaseReference mdatabaseRef;
    DatabaseReference mdatabaseRef2;


    private FirebaseRecyclerAdapter<Radio, radioViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private   FirebaseUser user;
    public RadioListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment_radio_list,container,false);
        //




        //[START create_database_reference]

      user = FirebaseAuth.getInstance().getCurrentUser();

        mdatabaseRef=FirebaseDatabase.getInstance().getReference("/favoritesR/");
      mdatabaseRef2=FirebaseDatabase.getInstance().getReference();

        myRef = FirebaseDatabase.getInstance().getReference("/radios");

        // [END create_database_reference]

        mRecycler = (RecyclerView) fragmentLayout.findViewById(R.id.radio_list);
        mRecycler.setHasFixedSize(true);

        // Inflate the layout for this fragment
        return fragmentLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //

        //

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);

        mRecycler.setLayoutManager(mManager);


        mAdapter=new FirebaseRecyclerAdapter<Radio, radioViewHolder>(
                Radio.class,
                R.layout.item_radio,
                radioViewHolder.class,
                myRef


        ) {


            @Override
            protected void populateViewHolder(final radioViewHolder viewHolder, final Radio model, final int position) {
                final DatabaseReference radioRef = getRef(position);


                final String radioKey = radioRef.getKey();

                checkFav(viewHolder,radioKey);
              //  Intent intent=getActivity().getIntent();

                viewHolder.bindToRadio(position, model, getContext(), radioKey, new View.OnClickListener() {

                    //emtyHeartIcon
                    @Override
                    public void onClick(View starView) {


                        viewHolder.heartView.setVisibility(View.INVISIBLE);
                        viewHolder.heartViewfill.setVisibility(View.VISIBLE);


                        mdatabaseRef.child(user.getUid()).child(radioKey).setValue("lol");
                       // Snackbar.make(getView(), "Replace with your own action", Snackbar.LENGTH_LONG)
                              //  .setAction("Action", null).show();


                    }

                }, new View.OnClickListener() {

                    //filledHeartIcon
                    @Override
                    public void onClick(View starView) {


                        mdatabaseRef.child(user.getUid()).child(radioKey).removeValue();
                        //listFavFragment.favList.remove(listFavFragment.favList.indexOf(model));
                        for (int i = 0; i < listFavFragment.favList.size(); i++) {
                            if (listFavFragment.favList.get(i).getName() == model.getName()) {
                                listFavFragment.favList.remove(i);
                            }
                        }
                        //Log.d("listfavindex", "onClick: "+listFavFragment.favList.indexOf(model));
                        //Log.d("istfalvindex", "onClick: "+model.getName());
                        //Log.d("favlistsize", "onClick: "+listFavFragment.favList.size());

                        viewHolder.heartViewfill.setVisibility(View.INVISIBLE);

                        viewHolder.heartView.setVisibility(View.VISIBLE);


                    }

                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String txt = model.getFlux();
                        String streamUrl = txt;
                        int prevposition;
                        prevposition=playingitem;
                        if (!mRadioManager.isPlaying()) {//

                            mRadioManager.startRadio(txt);
                            playingitem=position;
                            notifyItemChanged(prevposition);
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

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.w(TAG, "country key= "+radioKey +"p:"+position);

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
    public void onResume() {
        super.onResume();
        mRadioManager.connect();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //mRadioManager.connect();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }



    public void checkFav(final radioViewHolder viewHolder,final String radioKey){



        mdatabaseRef2.child("favoritesR/").child(user.getUid()).child(radioKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()){


                    viewHolder.heartView.setVisibility(View.INVISIBLE);
                    viewHolder.heartViewfill.setVisibility(View.VISIBLE);

                    }else{

                    viewHolder.heartView.setVisibility(View.VISIBLE);
                    viewHolder.heartViewfill.setVisibility(View.INVISIBLE);

                    }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
         //deal with any changes in favorites data
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());


                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());




                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                if(dataSnapshot.getKey()==radioKey){

                viewHolder.heartView.setVisibility(View.VISIBLE);
                viewHolder.heartViewfill.setVisibility(View.INVISIBLE);
                }
                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());

            }
        };
        mdatabaseRef2.child("favoritesR/").child(user.getUid()).addChildEventListener(childEventListener);

    }

    ////




}
