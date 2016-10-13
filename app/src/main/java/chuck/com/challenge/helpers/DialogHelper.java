package chuck.com.challenge.helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;

import chuck.com.challenge.R;

/**
 * Created by Laurence on 17/09/2016.

 * Helper class that Returns an Dialog object that can then be shown on the screen.
 */
public class DialogHelper {


    public static Dialog getErrorDialog(Context context, String errorMessage) {

        return getDialogWithOkButton(context,
                SpannableString.valueOf(ResourceHelper
                        .getString(R.string.generic_title_dialog_error)),
                errorMessage);

    }

    public static Dialog getDialogWithOkButton(Context context,
                                               SpannableString title, String message) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(
                ResourceHelper.getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return alertDialogBuilder.create();

    }

}
