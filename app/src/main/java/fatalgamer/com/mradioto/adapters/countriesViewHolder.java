package fatalgamer.com.mradioto.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fatalgamer.com.mradioto.R;
import fatalgamer.com.mradioto.entities.Country;

/**
 * Created by FATALGAMER on 11/29/2016.
 */
//implements View.OnClickListener
public class countriesViewHolder extends RecyclerView.ViewHolder {

    public TextView nameC;
    public ImageView flag;

    public View mView;
    private String key;
    public countriesViewHolder(View itemView){

        super(itemView);
        //itemView.setOnClickListener(this);
        mView=itemView;
        nameC = (TextView)itemView.findViewById(R.id.nameCountry);
        flag=(ImageView)itemView.findViewById(R.id.countryFlag);

    }

    public void bindToCountry(Country country, Context context, String key){

        this.key=key;
        nameC.setText(country.name);
        Picasso.with(context).load(country.flag).into(flag);
    }
/*
    @Override
    public void onClick(View view) {
        Log.d("item", "onClick " + getPosition()+"key = "+key);
    }*/
}
