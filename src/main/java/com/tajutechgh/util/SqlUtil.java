package com.tajutechgh.util;

import com.google.gson.JsonObject;
import com.tajutechgh.view.LoginView;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.HttpURLConnection;

public class SqlUtil {

    public static boolean getUserByEmail(String email){

        HttpURLConnection conn = null;

        try {

            conn = ApiUtil.fetchApi("/api/v1/users/get?email=" + email , ApiUtil.RequestMethod.GET, null);

            if (conn.getResponseCode() != 200){

                return false;

            }

        }catch (IOException exception){

            exception.printStackTrace();

        }finally {

            if (conn != null){

                conn.disconnect();
            }
        }

        return true;
    }

    public static void loginUser(String email, String password){

        HttpURLConnection conn = null;

        try {

            conn = ApiUtil.fetchApi("/api/v1/users/login?email=" + email + "&password=" + password, ApiUtil.RequestMethod.POST, null);

            if (conn.getResponseCode() != 200){

                Utilities.showAlertDialog(Alert.AlertType.ERROR, "Failed To Authenticate !!!");

            }else {

                Utilities.showAlertDialog(Alert.AlertType.INFORMATION, "Login Successful !!!");
            }

        }catch (IOException exception){

            exception.printStackTrace();

        }finally {

            if (conn != null){

                conn.disconnect();
            }
        }
    }

    public static void registerUser(JsonObject userData){

        HttpURLConnection conn = null;

        try{

            conn = ApiUtil.fetchApi("/api/v1/users/register",  ApiUtil.RequestMethod.POST, userData);

            if (conn.getResponseCode() == 201){

                Utilities.showAlertDialog(Alert.AlertType.INFORMATION, "Account Registered Successfully !!!");

                new LoginView().show();

            }else {

                Utilities.showAlertDialog(Alert.AlertType.ERROR, "Failed To Register Account !!!");

            }

        }catch (IOException exception){

            exception.printStackTrace();

        }finally {

            if (conn != null){

                conn.disconnect();
            }
        }
    }
}
