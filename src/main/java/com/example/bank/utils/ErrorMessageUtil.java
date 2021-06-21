package com.example.bank.utils;

import java.util.ResourceBundle;

public class ErrorMessageUtil {
    private final ResourceBundle resourceBundle;

    private static ErrorMessageUtil errorMessageUtil;

    private ErrorMessageUtil() {
        resourceBundle = ResourceBundle.getBundle("error_messages");
    }

    public static synchronized ErrorMessageUtil getInstance(){
        if (errorMessageUtil == null)
            errorMessageUtil = new ErrorMessageUtil();

        return errorMessageUtil;
    }

    public String getMessageByKey(String key){
        return resourceBundle.getString(key);
    }

}
