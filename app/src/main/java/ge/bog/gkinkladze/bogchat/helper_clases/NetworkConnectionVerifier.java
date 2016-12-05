package ge.bog.gkinkladze.bogchat.helper_clases;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Giorgi on 5/15/2015.
 * Checks if network connection exists.
 */
public class NetworkConnectionVerifier {

    /**
     * takes context as its argument and checks if network connection exists
     * @param context
     * @return true if connection exist, false otherwise
     */
    public static boolean CheckNetworkConnection(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
