package ca.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.as3.model.Game;
import ca.cmpt276.as3.model.GameOption;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Locale;

public class GameScreenActivity extends AppCompatActivity {
    GameOption gameOption;
    Game game;
    Button buttons[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        gameOption = GameOption.getInstance();
        game = new Game(gameOption);
        buttons = new Button[gameOption.getNumRow()][gameOption.getNumCol()];

        populateTiles();
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
            //scale image to button (only works with JellyBean)
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.puppiesandbooks);
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
                finish();
            }
        }
        else
        {
            if(!game.isTileScanned(row, col))
            {
                int scanResult = game.scanTile(row, col);
                buttons[row][col].setText(String.format(Locale.getDefault(), "%d", scanResult));
            }
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
        Intent intent = new Intent(context, GameScreenActivity.class);
        return intent;
    }
}