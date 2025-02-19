package org.qwerris.filmsreviews.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JspHelper {
    public static String getJspPath(String path) {
        return "WEB-INF/jsp/" + path + ".jsp";
    }
}
