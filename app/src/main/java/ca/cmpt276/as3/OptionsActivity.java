package ca.cmpt276.as3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import ca.cmpt276.as3.model.GameOption;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * OptionsActivity class provides a screen for user to change game settings and configurations.
 * This class allows user to select the size of the game board and the total number of puppies that user wants to find.
 * This class saves these configuration values into GameOption instance and SharedPreferences to be used across the app.
 * This class also has a "Reset Scores" button so user can reset all games played to 0 and all best score records.
 */
public class OptionsActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "AppPreferences";
    public static final String ROW_SIZE = "Row size";
    public static final String COL_SIZE = "Col size";
    public static final String NUMBER_OF_PUPPIES = "Number of puppies";
    GameOption gameOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        setContentView(R.layout.activity_options);

        createRadioButtonsForNumPuppies();
        createRadioButtonsForBoardSize();
        setUpResetButton();
        gameOption = GameOption.getInstance();
    }

    private void createRadioButtonsForBoardSize() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_board_size);
        int[] numCols = getResources().getIntArray(R.array.num_cols);
        int[] numRows = getResources().getIntArray(R.array.num_rows);

        for (int i = 0; i < numCols.length; i++){
            final int numCol = numCols[i];
            final int numRow = numRows[i];

            RadioButton button = new RadioButton(this);
            button.setText(String.format("%s %s", getString(R.string.rows_by, numRow), getString(R.string.columns, numCol)));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveBoardSize(numRow, numCol);
                    gameOption.setNumCol(numCol);
                    gameOption.setNumRow(numRow);
                }
            });

            group.addView(button);

            // Select default button
            if(numCol == getBoardSizeCol(this)){
                button.setChecked(true);
            }
        }
    }

    private void saveBoardSize(int numRow, int numCol) {
        SharedPreferences prefs = this.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ROW_SIZE, numRow);
        editor.putInt(COL_SIZE, numCol);
        editor.apply();
    }
    private void saveNumPuppies(int numPuppies) {
        SharedPreferences prefs = this.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUMBER_OF_PUPPIES, numPuppies);
        editor.apply();
    }

    private void createRadioButtonsForNumPuppies() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_puppies);
        int[] numPuppies = getResources().getIntArray(R.array.num_puppies);

        for (final int numPuppy : numPuppies) {
            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.puppies, numPuppy));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveNumPuppies(numPuppy);
                    gameOption.setNumPuppy(numPuppy);
                }
            });

            group.addView(button);

            // Select default button
            if (numPuppy == getNumPuppies(this)) {
                button.setChecked(true);
            }
        }
    }

    public static int getBoardSizeRow(Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        return prefs.getInt(ROW_SIZE, 4);
    }

    public static int getBoardSizeCol(Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        return prefs.getInt(COL_SIZE, 6);
    }

    public static int getNumPuppies(Context context){
        SharedPreferences prefs = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        return prefs.getInt(NUMBER_OF_PUPPIES, 6);
    }

    public static Intent makeIntent(Context context)
    {
        return new Intent(context, OptionsActivity.class);
    }

    private void setUpResetButton()
    {
        Button buttonReset = findViewById(R.id.buttonResetScores);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OptionsActivity.this);
                builder.setMessage(R.string.are_you_sure_you_want_to_reset_all_game_scores);
                builder.setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gameOption.resetScores();
                        Toast.makeText(OptionsActivity.this, getString(R.string.all_scores_have_been_reset), Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //DO NOTHING
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}