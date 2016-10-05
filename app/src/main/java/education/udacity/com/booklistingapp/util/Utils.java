package education.udacity.com.booklistingapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dhiegoabrantes on 29/09/16.
 */

public class Utils {

    public static boolean checkConn(Context ctx) {
        ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null|| !i.isConnected() || !i.isAvailable())
            return false;
        return true;
    }

}
