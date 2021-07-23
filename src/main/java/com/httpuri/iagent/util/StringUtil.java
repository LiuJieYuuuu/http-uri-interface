package com.httpuri.iagent.util;

/**
 * <b>String common type handler</b>
 */
public class StringUtil {

    /**
     * <b>get string between startWiths and endWiths by content</b>
     * @param content
     * @param startWiths
     * @param endWiths
     * @return
     */
    public static String getStringByTags(String content, String startWiths, String endWiths){
        if(content.contains(startWiths) && content.contains(endWiths)){
            int startIndex = content.indexOf(startWiths) + startWiths.length();
            int endIndex = content.indexOf(endWiths);
            return content.substring(startIndex,endIndex);
        }
        return null;
    }

}
