package ca.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setupTitleAnimation();
        setupSKipButton();
        setupPuppyVideo();

    }

    private void setupTitleAnimation() {
        TextView title1 = findViewById(R.id.tvWelcomeTitle);
        TextView title2 = findViewById(R.id.tvWelcomeTitle2);

        Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        bounce.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launchMainMenu();
                    }
                }, 4000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        title1.startAnimation(bounce);
        title2.startAnimation(bounce);

    }

    private void setupPuppyVideo() {
        VideoView videoView = findViewById(R.id.puppyVideo);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.puppy_video2);
        videoView.setVideoURI(uri);
        videoView.start();

//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        launchMainMenu();
//                    }
//                }, 4000);
//            }
//        });
    }

    private void setupSKipButton() {
        Button button = findViewById(R.id.btnSkip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainMenu();
            }
        });
        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        button.startAnimation(aniFade);
    }

    private void launchMainMenu()
    {
        Intent intent = MainMenu.makeIntent(WelcomeActivity.this);
        startActivity(intent);
        finish();
    }

}