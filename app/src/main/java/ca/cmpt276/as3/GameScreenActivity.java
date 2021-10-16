package ca.cmpt276.as3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.Arrays;
import java.util.Locale;

import ca.cmpt276.as3.model.Game;
import ca.cmpt276.as3.model.GameOption;

public class GameScreenActivity extends AppCompatActivity {
    GameOption gameOption;
    Game game;
    Button[][] buttons;
    public static final String APP_PREFERENCES = "AppPreferences";
    public static final String HIGH_SCORES = "high scores";
    public static final String TIMES_GAME_PLAYED = "times game played";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_game_screen);

        gameOption = GameOption.getInstance();
        game = new Game(gameOption);
        buttons = new Button[gameOption.getNumRow()][gameOption.getNumCol()];

        populateTiles();
        updateGameStatus();
        displayGameHistory();
    }

    @Override
    protected void onStop() {
        saveGameRecords();
        super.onStop();
    }

    private void saveGameRecords()
    {
        String highScoresJson = gameOption.convertHighScoresToJson();
        String timesPlayedJson = gameOption.convertTimesGamePlayedToJson();

        SharedPreferences refs = this.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = refs.edit();
        editor.putString(HIGH_SCORES, highScoresJson);
        editor.putString(TIMES_GAME_PLAYED, timesPlayedJson);
        editor.apply();
    }

    public static String getHighScoresFromSharedPreferences(Context context)
    {
        SharedPreferences refs = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        return refs.getString(HIGH_SCORES, null);
    }

    public static String getTimesGamePlayedFromSharedPreferences(Context context)
    {
        SharedPreferences refs = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        return refs.getString(TIMES_GAME_PLAYED, null);
    }

    private void displayGameHistory() {
        TextView tvtimes_game_played = findViewById(R.id.textViewTimesPlayed);
        tvtimes_game_played.setText("Number of games played: " + gameOption.getTimesPlayed());

        TextView tv_high_score= findViewById(R.id.textViewHighScore);
        int highScore =  gameOption.getHighScore();
        if(highScore == 91) {
            tv_high_score.setText(R.string.no_best_score_recorded);
        }
        else {
            tv_high_score.setText("Best score: " + highScore);
        }
    }

    private void populateTiles() {
        TableLayout table = findViewById(R.id.tablesForTiles);
        for(int row=0; row < gameOption.getNumRow(); row++)
        {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);

            for(int col=0; col < gameOption.getNumCol(); col++)
            {
                final int ROW = row;
                final int COL = col;
                Button tile = new Button(this);
                tile.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                tile.getBackground().setAlpha(200);
                tile.setPadding(0, 0, 0, 0);
                tile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tileButtonClicked(ROW, COL);
                    }
                });
                tableRow.addView(tile);
                buttons[row][col] = tile;
            }
        }
    }

    private void tileButtonClicked(int row, int col) {
        Button button = buttons[row][col];

        lockButtonSize();

        if(game.checkForHiddenPuppy(row, col))
        {
            //play barking sound
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.short_bark);
            mp.start();

            //add vibration
            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            long[] pattern = {150,100, 200, 200, 150};

            //vibrate for 500 ms
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                v.vibrate(VibrationEffect.createWaveform(pattern, -1));
            }
            else {
                v.vibrate(pattern, -1);
            }

            //scale image to button (only works with JellyBean)
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seekpng_com_cute_puppy_png_853146);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            //once we show the hidden puppy, we need to update the counts
            for(int i=0; i < gameOption.getNumCol(); i++)
            {
                if(game.isTileScanned(row, i))
                {
                    game.decrementScanValueAtTile(row, i);
                    buttons[row][i].setText(String.format(Locale.getDefault(), "%d", game.getScanValueAtTile(row, i)));
                }
            }

            for(int i=0; i < gameOption.getNumRow(); i++)
            {
                if(game.isTileScanned(i, col))
                {
                    game.decrementScanValueAtTile(i, col);
                    buttons[i][col].setText(String.format(Locale.getDefault(), "%d", game.getScanValueAtTile(i, col)));
                }
            }

            if(game.getNumPuppiesFound() == gameOption.getNumPuppy())
            {
                String winMes = getString(R.string.congrats_you_found_all_my_puppies);
                if(gameOption.isNewHighScore(game.getNumOfScans()))
                {
                    winMes = getString(R.string.congrats_you_have_set_new_best_score);
                }
                FragmentManager manager = getSupportFragmentManager();
                WinDialogFragment dialog = new WinDialogFragment(winMes);
                dialog.show(manager, "WinDialog");

                gameOption.setHighScore(game.getNumOfScans());
            }
        }
        else
        {
            if(!game.isTileScanned(row, col)) {
                final MediaPlayer mpWrongAnswer = MediaPlayer.create(this, R.raw.wrong_answer_sound_effect);
                mpWrongAnswer.start();

                //add vibration
                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                //vibrate for 500 ms
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(400, 75));
                } else {
                    v.vibrate(400);
                }

                startScanningAnimation(row, col);

                int[] distance = {row, gameOption.getNumRow() - row - 1, col, gameOption.getNumCol() - col - 1};
                int max = Arrays.stream(distance).max().getAsInt();

                game.scanTile(row, col);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttons[row][col].setText("" + game.getScanValueAtTile(row, col));
                    }
                }, (max+1) * 150L + 150L);
            }
        }
        updateGameStatus();
    }

    private void startScanningAnimation(int row, int col) {
        boolean top = true;
        boolean right = true;
        boolean bottom = true;
        boolean left = true;
        int i = 0;

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        while (top || right || bottom || left) {
            final int k = i;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(col - k >= 0) {
                        if (!game.checkIfPuppyRevealed(row, col - k)) {
                            buttons[row][col - k].startAnimation(shake);
                        }
                    }
                    if(row + k <= gameOption.getNumRow() - 1) {
                        if (!game.checkIfPuppyRevealed(row + k, col)) {
                            buttons[row + k][col].startAnimation(shake);
                        }
                    }
                    if (col + k <= gameOption.getNumCol() - 1) {
                        if (!game.checkIfPuppyRevealed(row, col + k)) {
                            buttons[row][col + k].startAnimation(shake);
                        }
                    }
                    if (row - k >= 0) {
                        if (!game.checkIfPuppyRevealed(row - k, col)) {
                            buttons[row - k][col].startAnimation(shake);
                        }
                    }
                }
            }, 150L * (k+1));

            if (col - i < 0) {
                top = false;
            }

            if (row + i > gameOption.getNumRow() - 1) {
                right = false;
            }

            if (col + i > gameOption.getNumCol() - 1) {
                bottom = false;
            }

            if (row - i < 0) {
                left = false;
            }
            i++;

        }
    }

    private void lockButtonSize()
    {
        //lock button size
        for(int r=0; r < gameOption.getNumRow(); r++)
        {
            for(int c=0; c < gameOption.getNumCol(); c++)
            {
                Button button = buttons[r][c];
                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    public static Intent makeIntent(Context context)
    {
        return new Intent(context, GameScreenActivity.class);
    }

    private void updateGameStatus()
    {
        TextView tvTotalPuppies = findViewById(R.id.textViewTotalPuppies);
        String puppy_status = "Found " + game.getNumPuppiesFound() + " out of " + gameOption.getNumPuppy() + " puppies.";
        tvTotalPuppies.setText(puppy_status);

        TextView tvNumScans = findViewById(R.id.textViewNumOfScans);
        String num_scans = getString(R.string.number_of_scans_used);
        num_scans = num_scans.concat(String.format(Locale.getDefault()," %d", game.getNumOfScans()));
        tvNumScans.setText(num_scans);
    }
}