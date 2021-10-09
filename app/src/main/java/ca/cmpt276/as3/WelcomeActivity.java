package ca.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setupSKipButton();
//        ImageView image = findViewById(R.id.ivWelcomeAnimation);
//        image.setVisibility(View.VISIBLE);
    }

    private void setupSKipButton() {
        Button button = findViewById(R.id.btnSkip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });
    }


}