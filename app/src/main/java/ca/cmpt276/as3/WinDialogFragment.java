package ca.cmpt276.as3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * WinDialogFragment class supports the creation of a dialog once user wins the game by inflating an win_dialog.xml.
 * This class shows a dialog with the design of the xml file, which contains confetti icons to congratulate the user.
 * This class also adjusts the congratulations message accordingly when user wins the game versus when users wins the game & sets a new game record.
 */
public class WinDialogFragment extends AppCompatDialogFragment{
    String winMessage;
    public WinDialogFragment(String winMes)
    {
        this.winMessage = winMes;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.win_dialog, null);
        TextView tvWinMes = v.findViewById(R.id.winMessage);
        tvWinMes.setText(this.winMessage);

        DialogInterface.OnClickListener listener = (dialogInterface, i) -> {
            if(i == DialogInterface.BUTTON_NEUTRAL){
                getActivity().finish();
            }
        };

        TextView title = new TextView(getContext());
        title.setText(R.string.you_win);
        title.setGravity(Gravity.CENTER);
        title.setPadding(10, 10, 10, 10);
        title.setTextSize(20);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCustomTitle(title)
                .setView(v)
                .setNeutralButton(android.R.string.ok, listener)
                .create();
        AlertDialog dialog = builder.create();
        dialog.show();

        Button okButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams okButtonLL = (LinearLayout.LayoutParams) okButton.getLayoutParams();
        okButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
        okButton.setLayoutParams(okButtonLL);
        return dialog;
    }
}
