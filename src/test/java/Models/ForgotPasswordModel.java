package Models;

import java.util.ArrayList;
import java.util.Arrays;

public class ForgotPasswordModel {
    public String email;
    public String alertMessage;
    public String testCaseName;
    public String testCasePassMsg;
    public String testCaseFailMsg;

    public ForgotPasswordModel(String email, String alertMessage, String testCaseName, String testCasePassMsg, String testCaseFailMsg){
        this.email = email;
        this.alertMessage = alertMessage;
        this.testCaseName = testCaseName;
        this.testCasePassMsg = testCasePassMsg;
        this.testCaseFailMsg = testCaseFailMsg;
    }
public static ArrayList<ForgotPasswordModel> lstForgotPassword = new ArrayList<>(
        Arrays.asList(
                new ForgotPasswordModel(
                        "asd2260223667@gmail.com",
                        "This email is not registered with Ancera. Please contact your administrator",
                        "Verify that User is not Able to Send Reset Password Link for An Email That Does not Exist in the System.",
                        "User received alert message that 'User is not registered.' successfully",
                        "User did not receive the alert message that 'User is not registered."
                        ),
                new ForgotPasswordModel(
                        "as2260223667@hotmail.com",
                        "The email has been sent!",
                        "Verify that User is able to Send Reset Password Link for A Valid User Email.",
                        "User received alert message that 'Please check your e-mail for instructions.' successfully",
                        "User did not receive the alert message that 'Please check your e-mail for instructions."
                        )));

}
