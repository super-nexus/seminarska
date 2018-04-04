package andrija.asp;

/**
 * Created by Andrija on 2/19/18.
 */

public class AppConstants {

    private static final String setupPath = "/setupApp2";
    private static final String lightSwitchPath = "/switchLight";
    private static final String returnLightsForAreaPath = "/returnLightsForArea";
    private static final String ip = "192.168.1.101:3000";
    private static final String protocol = "http://";
    private static final String TAG = "aspTAG";


    public static String getSetupPath() {
        return setupPath;
    }

    public static String getLightSwitchPath() {
        return lightSwitchPath;
    }

    public static String getIp() {
        return ip;
    }

    public static String getProtocol() {
        return protocol;
    }

    public static String getTAG() {
        return TAG;
    }

    public static String getReturnLightsForAreaPath() {
        return returnLightsForAreaPath;
    }
}
