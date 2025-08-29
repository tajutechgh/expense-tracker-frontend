package com.tajutechgh.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.User;
import com.tajutechgh.view.DashboardView;
import com.tajutechgh.view.LoginView;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;

public class SqlUtil {

    public static User getUserByEmail(String email){

        HttpURLConnection conn = null;

        try {

            conn = ApiUtil.fetchApi("/api/v1/users/get?email=" + email , ApiUtil.RequestMethod.GET, null);

            if (conn.getResponseCode() != 200){

                System.out.println("Error (getUserByEmail()" + conn.getResponseCode());

                return null;

            }

            String userDataJason = ApiUtil.readApiResponse(conn);

            JsonObject jsonObject = JsonParser.parseString(userDataJason).getAsJsonObject();

            //extract the json data
            Integer id  = jsonObject.get("id").getAsInt();
            String name = jsonObject.get("name").getAsString();
            String emailAddress = jsonObject.get("email").getAsString();
            String password = jsonObject.get("password").getAsString();
            LocalDateTime createdAt = new Gson().fromJson(jsonObject.get("created_at"), LocalDateTime.class);

            return new User(id,  name,  emailAddress,  password,  createdAt);

        }catch (IOException exception){

            exception.printStackTrace();

        }finally {

            if (conn != null){

                conn.disconnect();
            }
        }

        return null;
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

            conn = ApiUtil.fetchApi("/api/v1/users/register", ApiUtil.RequestMethod.POST, userData);

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

    public static void createCategory(JsonObject userData){

        HttpURLConnection conn = null;

        try{

            conn = ApiUtil.fetchApi("/api/v1/transaction-category/create", ApiUtil.RequestMethod.POST, userData);

            if (conn.getResponseCode() == 201){

                Utilities.showAlertDialog(Alert.AlertType.INFORMATION, "Transaction category added Successfully !!!");

            }else {

                Utilities.showAlertDialog(Alert.AlertType.ERROR, "Failed To Create Transaction Category !!!");

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
