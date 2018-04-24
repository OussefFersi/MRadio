package fatalgamer.com.mradioto.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fatalgamer.com.mradioto.entities.Country;
import fatalgamer.com.mradioto.R;
import fatalgamer.com.mradioto.utils.SimpleListDividerDecorator;
import fatalgamer.com.mradioto.activities.RadiosDetailActivity;
import fatalgamer.com.mradioto.activities.listcountryActivity;
import fatalgamer.com.mradioto.adapters.countriesViewHolder;


public class listcountryFragment extends Fragment {

    private static final String TAG = "CountryListFragment";
    DatabaseReference myRef;
    //ListView mlistview;
    private FirebaseRecyclerAdapter<Country, countriesViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.all_counties_fragment, container, false);

        // [START create_database_reference]
        myRef = FirebaseDatabase.getInstance().getReference("/countries");
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.country_list);
        mRecycler.setHasFixedSize(true);
        Log.d(TAG,"on create");
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);

        mRecycler.setLayoutManager(mManager);

        Log.d(TAG,"onactivityCreated1");
        mAdapter=new FirebaseRecyclerAdapter<Country, countriesViewHolder>(
                Country.class,
                R.layout.item_country,
                countriesViewHolder.class,
                myRef

        ) {
            @Override
            protected void populateViewHolder(countriesViewHolder viewHolder, Country model, int position) {
                final DatabaseReference countryRef = getRef(position);


                final String countryKey = countryRef.getKey();
                Log.d(TAG,countryKey);
                viewHolder.bindToCountry(model,getContext(),countryKey);

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.w(TAG, "country key= "+countryKey);

                        launchNoteDetailActivity(listcountryActivity.FragmentToLaunch.RADIOS,countryKey);
                    }
                });
                //Log.d(TAG,"yo"+model);
            }
        };

        mRecycler.setAdapter(mAdapter);
        Log.d(TAG,"onactivityCreated4");
        mRecycler.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getContext(), R.drawable.list_divider), true));


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }



   private void launchNoteDetailActivity(listcountryActivity.FragmentToLaunch ftl,String key){

        //grap the note information associated with whatever note item we clicked on


        //create a new intent that launches our notedETAILaCTIVITY
        Intent intent = new Intent(getActivity(),RadiosDetailActivity.class);

        //Pass along the information of the note we clicked on to our noteDetailActivity

        intent.putExtra(listcountryActivity.COUNTRY_ID_EXTRA,key);

        switch (ftl){
            case RADIOS:
                intent.putExtra(listcountryActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,listcountryActivity.FragmentToLaunch.RADIOS);
                break;
            case PLAYER:
                intent.putExtra(listcountryActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,listcountryActivity.FragmentToLaunch.PLAYER);
                break;
        }
        startActivity(intent);

    }

}


