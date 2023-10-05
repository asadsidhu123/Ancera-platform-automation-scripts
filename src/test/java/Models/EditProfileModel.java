package Models;

import Config.ReadPropertyFile;
import Misc.Constants;
import org.aeonbits.owner.ConfigFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class EditProfileModel {
    public String ufname;
    public String ulname;
    public String ucountry;
    public String testCaseName;
    public static ReadPropertyFile config = ConfigFactory.create(ReadPropertyFile.class);

    public EditProfileModel(String _ufname, String _ulname, String _ucountry, String _testCaseName){
        this.ufname = _ufname;
        this.ulname = _ulname;
        this.ucountry = _ucountry;
        this.testCaseName = _testCaseName;
    }
    public static ArrayList<EditProfileModel> lsteditprofile = new ArrayList<>(
            Arrays.asList(
                    new EditProfileModel("Junaid 2","Alam 2","United Kingdom", "Verify that User is able to successfully edit their profile"),
                    new EditProfileModel("Junaid", "Alam", "United States", "erify that User is able to successfully edit their profile back to original settings")));

}
