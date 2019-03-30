package home.hyywk.top.superwebsiterabbitmq.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    private static  Gson gson;

    static {
        if ( gson == null ) {
            synchronized ( Gson.class ) {
                if ( gson == null ) {
                    gson = new GsonBuilder().setPrettyPrinting().create();
                }
            }
        }
    }

    public static String fromObjectToString( Object object) {
        return gson.toJson( object );
    }

    public static <T> T fromStringToObject( String str, Class T) {
        return (T)gson.fromJson(str, T );
    }

}
