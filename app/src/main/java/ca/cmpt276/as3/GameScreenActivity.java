package ca.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import ca.cmpt276.as3.model.Game;
import ca.cmpt276.as3.model.GameOption;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;

public class GameScreenActivity extends AppCompatActivity {
    GameOption gameOption;
    Game game;
    Button buttons[][];

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
                    String countAsString = buttons[row][i].getText().toString();
                    int countAsInt = Integer.parseInt(countAsString);
                    countAsInt--;
                    buttons[row][i].setText(String.format(Locale.getDefault(), "%d", countAsInt));
                }
            }

            for(int i=0; i < gameOption.getNumRow(); i++)
            {
                if(game.isTileScanned(i, col))
                {
                    String countAsString = buttons[i][col].getText().toString();
                    int countAsInt = Integer.parseInt(countAsString);
                    countAsInt--;
                    buttons[i][col].setText(String.format(Locale.getDefault(), "%d", countAsInt));
                }
            }

            if(game.getNumPuppiesFound() == gameOption.getNumPuppy())
            {
                FragmentManager manager = getSupportFragmentManager();
                WinDialogFragment dialog = new WinDialogFragment();
                dialog.show(manager,"WinDialog");
            }
        }
        else
        {
            if(!game.isTileScanned(row, col))
            {
                //add vibration
                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                //vibrate for 500 ms
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    v.vibrate(VibrationEffect.createOneShot(400, 75));
                }
                else {
                    v.vibrate(500);
                }

                int scanResult = game.scanTile(row, col);
                buttons[row][col].setText(String.format(Locale.getDefault(), "%d", scanResult));
            }
        }

        updateGameStatus();
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