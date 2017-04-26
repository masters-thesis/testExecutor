package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KURZRO on 05.04.2017.
 */
public class Log {

    private static List<String> messages = new ArrayList<>();

    public static void resetLog(){
        messages.clear();
    }

    public static String appendMessage(String message){
        messages.add( message );
        return message;
    }

    public static void print() {
        messages.stream().forEach( message -> System.out.println(message) );
    }

    public static List<String> getLogs(){
        return messages;
    }
}
