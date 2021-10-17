package ca.cmpt276.as3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * HelpActivity provides the Help screen to user.
 * This class informs user about game instructions, authors,
 * and lists all the resources (hyperlinks) that were used in this application.
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        setContentView(R.layout.activity_help);

        enableHyperLinks();
    }

    private void enableHyperLinks()
    {
        TextView tvAuthor = findViewById(R.id.textViewAuthorContent);
        tvAuthor.setMovementMethod(LinkMovementMethod.getInstance());

        TextView tvResources = findViewById(R.id.textViewResourcesContent);
        tvResources.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static Intent makeIntent(Context context)
    {
        return new Intent(context, HelpActivity.class);
    }
}