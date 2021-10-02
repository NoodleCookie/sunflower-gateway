package sunflower.util;

import java.util.ArrayList;
import java.util.List;

public class WhiteUrl {

    private final static List<String> whiteUrl;

    static {
        whiteUrl = new ArrayList<>();
        whiteUrl.add("/login");
        whiteUrl.add("/register");
    }

    public static boolean routeCheck(String url) {
        for (String whiteWord : whiteUrl) {
            if (url.contains(whiteWord)) {
                return true;
            }
        }
        return false;
    }
}
