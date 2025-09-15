package com.tajutechgh.util;

import com.google.gson.*;
import com.model.Transaction;
import com.model.TransactionCategory;
import com.model.User;
import com.tajutechgh.view.LoginView;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlUtil {

    //get user by email
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

    //login user
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

    //register user
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

    //create transaction category
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

    //list all transaction categories by a user
    public static List<TransactionCategory> getAllTransactionCategoriesByUser(User user){

        List<TransactionCategory> transactionCategories = new ArrayList<>();

        HttpURLConnection conn = null;

        try{

            conn = ApiUtil.fetchApi("/api/v1/transaction-category/user/all/" + user.getId(), ApiUtil.RequestMethod.GET, null);

            if (conn.getResponseCode() != 200){

                System.out.println("Error (getTransactionCategoriesByUser()) " + conn.getResponseCode());

                return null;
            }

            String results = ApiUtil.readApiResponse(conn);

            JsonArray resultJsonArray = new JsonParser().parse(results).getAsJsonArray();

            for (JsonElement jsonElement : resultJsonArray){

                Integer id = jsonElement.getAsJsonObject().get("id").getAsInt();
                String categoryName = jsonElement.getAsJsonObject().get("categoryName").getAsString();
                String categoryColor = jsonElement.getAsJsonObject().get("categoryColor").getAsString();

                transactionCategories.add(new TransactionCategory(id, categoryName, categoryColor));
            }

            return transactionCategories;

        }catch (IOException exception){

            exception.printStackTrace();

        }finally {

            if (conn != null){

                conn.disconnect();
            }
        }

        return null;
    }

    //update transaction controller
    public static void updateTransactionCategory(int categoryId, String categoryName, String categoryColor){

        HttpURLConnection conn = null;

        try{

            conn = ApiUtil.fetchApi(
                    "/api/v1/transaction-category/update/" +  categoryId + "?categoryName=" + UrlEncode.getUrlEncoder(categoryName) + "&categoryColor=" + UrlEncode.getUrlEncoder(categoryColor),
                    ApiUtil.RequestMethod.PUT,
                    null
            );

            if (conn.getResponseCode() == 200){

                Utilities.showAlertDialog(Alert.AlertType.INFORMATION, "Transaction category updated Successfully !!!");

            }else {

                Utilities.showAlertDialog(Alert.AlertType.ERROR, "Failed To UpdateTransaction Category !!!");

            }

        }catch (IOException exception){

            exception.printStackTrace();

        }finally {

            if (conn != null){

                conn.disconnect();
            }
        }
    }

    //delete transaction category
    public static boolean deleteTransactionCategory(int categoryId){

        HttpURLConnection conn = null;

        try{

            conn = ApiUtil.fetchApi(
                    "/api/v1/transaction-category/delete/"  + categoryId,
                    ApiUtil.RequestMethod.DELETE,
                    null
            );

            if (conn.getResponseCode() == 200){

                Utilities.showAlertDialog(Alert.AlertType.INFORMATION, "Transaction category deleted Successfully !!!");

                return true;

            }else {

                Utilities.showAlertDialog(Alert.AlertType.ERROR, "Failed To DeleteTransaction Category !!!");

                return false;
            }

        }catch (IOException exception){

            exception.printStackTrace();

        }finally {

            if (conn != null){

                conn.disconnect();
            }
        }

        return false;
    }

    //create transaction
    public static void createTransaction(JsonObject userData){

        HttpURLConnection conn = null;

        try{

            conn = ApiUtil.fetchApi("/api/v1/transactions/create", ApiUtil.RequestMethod.POST, userData);

            if (conn.getResponseCode() == 201){

                Utilities.showAlertDialog(Alert.AlertType.INFORMATION, "Transaction added Successfully !!!");

            }else {

                Utilities.showAlertDialog(Alert.AlertType.ERROR, "Failed To Create Transaction !!!");

            }

        }catch (IOException exception){

            exception.printStackTrace();

        }finally {

            if (conn != null){

                conn.disconnect();
            }
        }
    }

    //list recent transactions by a user
    public static List<Transaction> getRecentTransactionByUser(int userId, int pageNum, int pageSize){

        List<Transaction> transactions = new ArrayList<>();

        HttpURLConnection conn = null;

        try{

            conn = ApiUtil.fetchApi(
                    "/api/v1/transactions/recent/user/" + userId + "?page=" + pageNum + "&size=" + pageSize,
                    ApiUtil.RequestMethod.GET,
                    null
            );

            if (conn.getResponseCode() != 200){

                System.out.println("Error (getRecentTransactionsByUser()) " + conn.getResponseCode());

                return null;
            }

            String results = ApiUtil.readApiResponse(conn);

            JsonArray resultJsonArray = new JsonParser().parse(results).getAsJsonArray();

            for (JsonElement jsonElement : resultJsonArray){

                Integer id = jsonElement.getAsJsonObject().get("id").getAsInt();
                Integer transactionCategoryId = jsonElement.getAsJsonObject().get("categoryId").getAsInt();
                String transactionName = jsonElement.getAsJsonObject().get("transactionName").getAsString();
                double transactionAmount = jsonElement.getAsJsonObject().get("transactionAmount").getAsDouble();
                LocalDate transactionDate = LocalDate.parse(jsonElement.getAsJsonObject().get("transactionDate").getAsString());
                String transactionType = jsonElement.getAsJsonObject().get("transactionType").getAsString();

                transactions.add(new Transaction(id, transactionCategoryId, transactionName, transactionAmount, transactionDate, transactionType));
            }

            return transactions;

        }catch (IOException exception){

            exception.printStackTrace();

        }finally {

            if (conn != null){

                conn.disconnect();
            }
        }

        return null;
    }

    //delete transaction
    public static boolean deleteTransaction(int transactionId){

        HttpURLConnection conn = null;

        try{

            conn = ApiUtil.fetchApi(
                    "/api/v1/transactions/delete/"  + transactionId,
                    ApiUtil.RequestMethod.DELETE,
                    null
            );

            if (conn.getResponseCode() == 200){

                Utilities.showAlertDialog(Alert.AlertType.INFORMATION, "Transaction deleted Successfully !!!");

                return true;

            }else {

                Utilities.showAlertDialog(Alert.AlertType.ERROR, "Failed To DeleteTransaction !!!");

                return false;
            }

        }catch (IOException exception){

            exception.printStackTrace();

        }finally {

            if (conn != null){

                conn.disconnect();
            }
        }

        return false;
    }
}
