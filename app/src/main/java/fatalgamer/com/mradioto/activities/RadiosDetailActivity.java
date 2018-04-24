package fatalgamer.com.mradioto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import fatalgamer.com.mradioto.R;
import fatalgamer.com.mradioto.fragments.RadioListFragment;

public class RadiosDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radios_detail);
        createAndAddFragment();
    }

    private void createAndAddFragment(){

        //grab intent and fragment to launch from our main activity list list fragment
        Intent intent = getIntent();
        listcountryActivity.FragmentToLaunch fragmentToLaunch = (listcountryActivity.FragmentToLaunch) intent.getSerializableExtra(listcountryActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA);

        //grabbing our fragment manager and our fragment transaction so that we can add our edit or view dynamicaly
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //choose the correct fragment to load
        switch (fragmentToLaunch){
            case RADIOS:
                RadioListFragment radioListFragment = new RadioListFragment();

                setTitle("list radio");
                fragmentTransaction.add(R.id.radio_container,radioListFragment,"NOTE_EDIT_FRAGMENT");

                break;
            case RADIODETAIL:
                /*NoteViewFragment noteViewFragment = new NoteViewFragment();

                setTitle(R.string.viewFragmentTitle);
                fragmentTransaction.add(R.id.note_container,noteViewFragment,"NOTE_VIEW_FRAGMENT");
                */
                break;
            case PLAYER:/*
                NoteEditFragment noteCreateFragment = new NoteEditFragment();
                setTitle("Create Note");
                Bundle bundle = new Bundle();
                bundle.putBoolean(NEW_NOTE_EXTRA,true);
                noteCreateFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.note_container,noteCreateFragment,"NOTE_CREATE_FRAGMENT");*/
                break;

        }

        fragmentTransaction.commit();

    }
}
