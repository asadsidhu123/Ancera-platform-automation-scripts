package Models;

import Config.ReadPropertyFile;
import Misc.Constants;
import org.aeonbits.owner.ConfigFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginModel {
    public String uname;
    public String pwd;
    public String testCaseName;
    public static ReadPropertyFile config = ConfigFactory.create(ReadPropertyFile.class);

    public LoginModel(String _uname, String _pwd, String _testCaseName){
        this.uname = _uname;
        this.pwd = _pwd;
        this.testCaseName = _testCaseName;
    }
public static ArrayList<LoginModel> lstlogin = new ArrayList<>(
        Arrays.asList(
                new LoginModel("asadsidhu123@yahoo.com","12345678","Verify that user is not able to login using invalid username and invalid password"),
                new LoginModel(Constants.login_email,"12345678","Verify that user is not able to login using correct username and invalid password"),
                new LoginModel("asadsidhu123@yahoo.com", Constants.login_password, "Verify that user is not able to login using invalid username and correct password")));
}
