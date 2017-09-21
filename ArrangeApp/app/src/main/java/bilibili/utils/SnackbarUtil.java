package bilibili.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by kan212 on 17/9/20.
 */

public class SnackbarUtil {

    public static void showMessage(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }
}
