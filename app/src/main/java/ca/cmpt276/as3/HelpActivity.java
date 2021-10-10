package ca.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        fillOutHelpContent();
    }

    private void fillOutHelpContent()
    {
        TextView tvGameIns = findViewById(R.id.textViewGameInstructionContent);
        String gameIns = "To find a hidden puppy, you can click on any of the cell on your game screen. " +
                "If the cell happens to contain a hidden puppy, then congrats, you have just found one more puppy." +
                "If the cell does not contain a hidden puppy, then it will show you the number of hidden puppies left on that row and column. "+
                "You can also click on a puppy to trigger the scan on its row and column for hidden puppies.";
        tvGameIns.setText(gameIns);

        TextView tvAuthor = findViewById(R.id.textViewAuthorContent);
        String author = "Written by Jocelyn Tandrea and Ivy Tran for Introduction to Software Engineering. "+
                "Course website: https://opencoursehub.cs.sfu.ca/bfraser/grav-cms/cmpt276/home ";
        tvAuthor.setText(author);

        TextView tvResources = findViewById(R.id.textViewResourcesContent);
        String resource = "Image of Puppies and Books by Iryna Kazlova Airspa " +
                "(https://www.dreamstime.com/cute-little-puppies-shelf-books-light-background-pembroke-welsh-corgi-closeup-photo-image140177115 )";
        tvResources.setText(resource);
    }

    public static Intent makeIntent(Context context)
    {
        Intent intent = new Intent(context, HelpActivity.class);
        return intent;
    }
}