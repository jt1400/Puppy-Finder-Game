package ca.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.cmpt276.as3.model.GameOption;

public class MainMenu extends AppCompatActivity {
    private GameOption gameOption;
    public static final String APP_PREFERENCES = "AppPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setUpButtonPlayGame();
        setUpButtonOptions();
        setUpButtonHelp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameOption = GameOption.getInstance();
        gameOption.setNumPuppy(OptionsActivity.getNumPuppies(this));
        gameOption.setNumRow(OptionsActivity.getBoardSizeRow(this));
        gameOption.setNumPuppy(OptionsActivity.getBoardSizeCol(this));
    }

    public static Intent makeIntent(Context context)
    {
        Intent intent = new Intent(context, MainMenu.class);
        return intent;
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
    }
}