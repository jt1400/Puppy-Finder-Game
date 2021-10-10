package ca.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setupSKipButton();
        setupPuppyVideo();
    }

    private void setupPuppyVideo() {
        VideoView videoView = findViewById(R.id.puppyVideo);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.puppy_video2);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launchMainMenu();
                    }
                }, 4000);
            }
        });
    }

    private void setupSKipButton() {
        Button button = findViewById(R.id.btnSkip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainMenu();
            }
        });
    }

    private void launchMainMenu()
    {
        Intent intent = MainMenu.makeIntent(WelcomeActivity.this);
        startActivity(intent);
        finish();
    }

}