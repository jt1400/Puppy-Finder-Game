package ca.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.cmpt276.as3.model.GameOption;

public class MainMenu extends AppCompatActivity {
    private GameOption gameOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main_menu);

        gameOption = GameOption.getInstance();
        loadGameConfiguration();
        loadGameRecords();

        setUpButtonPlayGame();
        setUpButtonOptions();
        setUpButtonHelp();
    }

    private void loadGameConfiguration()
    {
        gameOption.setNumPuppy(OptionsActivity.getNumPuppies(this));
        gameOption.setNumRow(OptionsActivity.getBoardSizeRow(this));
        gameOption.setNumCol(OptionsActivity.getBoardSizeCol(this));
    }

    private void loadGameRecords()
    {
        String highScores = GameScreenActivity.getHighScoresFromSharedPreferences(MainMenu.this);
        gameOption.convertHighScoresFromJson(highScores);
        String timesPlayed = GameScreenActivity.getTimesGamePlayedFromSharedPreferences(MainMenu.this);
        gameOption.convertTimesPlayedFromJson(timesPlayed);
    }

    public static Intent makeIntent(Context context)
    {
        return new Intent(context, MainMenu.class);
    }

    private void setUpButtonPlayGame()
    {
        Button btnPlayGame = findViewById(R.id.buttonPlayGame);
        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start the game activity
                Intent intent = GameScreenActivity.makeIntent(MainMenu.this);
                startActivity(intent);
            }
        });

        animate(btnPlayGame);
    }

    private void animate(Button button) {
        button.setAlpha(0f);
        button.setTranslationY(50);

        button.animate().alpha(1f).translationYBy(-50).setDuration(1000);
    }

    private void setUpButtonOptions()
    {
        Button btnOptions = findViewById(R.id.buttonOptions);
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start the options activity
                Intent intent = OptionsActivity.makeIntent(MainMenu.this);
                startActivity(intent);
            }
        });
        animate(btnOptions);
    }

    private void setUpButtonHelp()
    {
        Button btnHelp = findViewById(R.id.buttonHelp);
        btnHelp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //start the help activity
                Intent intent = HelpActivity.makeIntent(MainMenu.this);
                startActivity(intent);
            }
        });
        animate(btnHelp);
    }
}