import java.util.logging.Logger;

public class ModManager {
	public static final String MOD_ID = "modmanager";
	
	public static final Logger LOGGER = Logger.getLogger(MOD_ID);
	
	public static void init() {
		LOGGER.info("Mod Manager start up.");
	}
}
