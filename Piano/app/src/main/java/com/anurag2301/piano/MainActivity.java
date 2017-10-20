package com.anurag2301.piano;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Context context;

    final MediaPlayer[] mp = new MediaPlayer[17];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        String x = "";
        mp[0] = MediaPlayer.create(this, R.raw.a_1);
        mp[1] = MediaPlayer.create(this, R.raw.a_2);
        mp[2] = MediaPlayer.create(this, R.raw.a_3);
        mp[3] = MediaPlayer.create(this, R.raw.a_4);
        mp[4] = MediaPlayer.create(this, R.raw.a_5);
        mp[5] = MediaPlayer.create(this, R.raw.a_6);
        mp[6] = MediaPlayer.create(this, R.raw.a_7);
        mp[7] = MediaPlayer.create(this, R.raw.a_8);
        mp[8] = MediaPlayer.create(this, R.raw.a_9);
        mp[9] = MediaPlayer.create(this, R.raw.a_10);
        mp[10] = MediaPlayer.create(this, R.raw.a_11);
        mp[11] = MediaPlayer.create(this, R.raw.a_12);
        mp[12] = MediaPlayer.create(this, R.raw.a_13);
        mp[13] = MediaPlayer.create(this, R.raw.a_14);
        mp[14] = MediaPlayer.create(this, R.raw.a_15);
        mp[15] = MediaPlayer.create(this, R.raw.a_16);
        mp[16] = MediaPlayer.create(this, R.raw.a_17);




        /*
        final Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (mp[0].isPlaying()) {
                        mp[0].stop();
                        mp[0].release();
                        mp[0] = MediaPlayer.create(context, R.raw.a_1);
                    } mp[0].start();
                } catch(Exception e) { e.printStackTrace(); }
            }
        });
*/
    }


    public void onClick(View v) {

        try {
            Button b = (Button) findViewById(v.getId());
            int id = b.getText().charAt(0)-'a';

            if (mp[id].isPlaying()) {
                mp[id].stop();
                mp[id].release();
                mp[id] = MediaPlayer.create(context, R.raw.a_1);
            } mp[id].start();
        } catch(Exception e) { e.printStackTrace(); }
    }

}
