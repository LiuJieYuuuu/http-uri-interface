package com.httpuri.iagent.util;

public class StringUtil {

    public static String getStringByTags(String content, String startWiths, String endWiths){
        if(content.contains(startWiths) && content.contains(endWiths)){
            int startIndex = content.indexOf(startWiths) + startWiths.length();
            int endIndex = content.indexOf(endWiths);
            return content.substring(startIndex,endIndex);
        }
        return null;
    }

}
