package ca.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;
import ca.cmpt276.as3.model.GameOption;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class GameScreenActivity extends AppCompatActivity {
    GameOption gameOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        gameOption = GameOption.getInstance();
    }

    public static Intent makeIntent(Context context)
    {
        Intent intent = new Intent(context, GameScreenActivity.class);
        return intent;
    }
}