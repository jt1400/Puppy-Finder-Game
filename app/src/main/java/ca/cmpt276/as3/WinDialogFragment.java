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
 * WinDialogFragment class supports the creation of the dialog to inform user on winning the game.
 * This class inflates the win_dialog.xml file to make a nice congratulating dialog featuring confetti icons.
 * This class also adjust the congratulations message accordingly to when user wins the game without breaking best score record
 * versus when user wins the game & breaks best score record.
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


        TextView title = new TextView(getContext());
        title.setText(R.string.you_win);
        title.setGravity(Gravity.CENTER);
        title.setPadding(10, 10, 10, 10);
        title.setTextSize(20);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.setCustomTitle(title)
                .setView(v)
                .setNeutralButton(android.R.string.ok, null)
                .create();
        dialog.show();

        Button okButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams okButtonLL = (LinearLayout.LayoutParams) okButton.getLayoutParams();
        okButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
        okButton.setLayoutParams(okButtonLL);
        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        getActivity().finish();
    }
}
