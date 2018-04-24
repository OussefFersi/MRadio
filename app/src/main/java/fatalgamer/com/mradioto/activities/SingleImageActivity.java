/*
 * Copyright 2014 Flavio Faria
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *//*
package fatalgamer.com.mradioto;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;

public class SingleImageActivity extends KenBurnsActivity {

    private KenBurnsView mImg;

    private TextView logoText;
    private Typeface type;
    private Animation an2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image);
        mImg = (KenBurnsView) findViewById(R.id.img);
        final View logo = findViewById(R.id.img2);
        logoText= (TextView) findViewById(R.id.logo_Text);
        type = Typeface.createFromAsset(getAssets(),"IDroid.otf");

        //logoText.setTypeface(type);
         an2= AnimationUtils.loadAnimation(this,R.anim.text_logo_anim);
        Animation an1= AnimationUtils.loadAnimation(this,R.anim.bounce);
        logo.startAnimation(an1);
        an1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                logoText.setTypeface(type);
                logoText.setVisibility(View.VISIBLE);
                logoText.startAnimation(an2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    @Override
    protected void onPlayClick() {
        mImg.resume();
    }


    @Override
    protected void onPauseClick() {
        mImg.pause();
    }

   /* public void moveLogo(){
        View logoImage = findViewById(R.id.img2);

        FrameLayout.LayoutParams positionRules = new FrameLayout.LayoutParams(FrameLayout.CENTER_IN_PARENT,FrameLayout.TRUE);


        logoImage.setLayoutParams(positionRules);

    }*//*
}
*/