package Config;

import org.aeonbits.owner.Config;


@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"file:./src/main/java/Config/config.properties"})


public interface ReadPropertyFile extends Config {

	String env();

	@Key("${env}.url")
	String url2();

	String url();

	@Key("${env}.user_id")
    int user_id();

	@Key("${environment}.ie_database_db")
	String ie_database_db();

	@Key("${environment}.ie_database_dw")
	String ie_database_dw();

	String login_email();

    String login_password();

	String fp_email();

    String fp_password();

    
    

}