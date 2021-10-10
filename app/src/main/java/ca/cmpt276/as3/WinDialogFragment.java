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
import ca.cmpt276.as3.R;
import androidx.appcompat.app.AppCompatDialogFragment;


public class WinDialogFragment extends AppCompatDialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.win_message_dialog, null);

        DialogInterface.OnClickListener listener = (dialogInterface, i) -> {
            if(i == DialogInterface.BUTTON_NEUTRAL){
                getActivity().finish();
            }
        };

        TextView title = new TextView(getContext());

        title.setText("YOU WIN!");
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
//        return super.onCreateDialog(savedInstanceState);
    }
}
