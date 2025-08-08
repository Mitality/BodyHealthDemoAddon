package bodyHealthDemoAddon;

import bodyhealth.api.addons.AddonDebug;
import bodyhealth.api.addons.AddonFileManager;
import bodyhealth.api.addons.AddonInfo;
import bodyhealth.api.addons.BodyHealthAddon;

import java.io.File;

@AddonInfo(
    name = "DemoAddon",
    description = "Showcases the use of BodyHealths addon system",
    version = "1.1.0",
    author = "Mitality"
)
public class Main extends BodyHealthAddon {

    private static AddonFileManager fileManager;
    private static AddonDebug debug;
    private static Main instance;

    @Override
    public void onAddonPreEnable() {
        // Called when the addon is loaded together with potential other addons before enabling them
        // Just use onAddonEnable() unless you know what you are doing and need to do something here
    }

    @Override
    public void onAddonEnable() {

        fileManager = getAddonFileManager();
        debug = getAddonDebug();
        instance = this;

        updateAndLoadConfig();

        registerCommand("demo", new Command()); // register subcommand ("/bodyhealth demo")
        registerListener(new Listener()); // Register our listener
    }

    @Override
    public void onAddonDisable() {

        // This is not needed, but you can do it for style points
        unregisterListeners();
        unregisterCommands();

    }

    @Override
    public void onBodyHealthReload() {
        updateAndLoadConfig();
    }

    // We make this its own method here, because we want to do this onAddonEnable AND onBodyHealthReload
    private static void updateAndLoadConfig() {
        fileManager.saveResource("config.yml", false);
        File configFile = fileManager.getFile("config.yml");
        fileManager.updateYamlFile("config.yml", configFile);
        Config.load(fileManager.getYamlConfiguration("config.yml"));
    }

    public static Main getInstance() {
        return instance;
    }

    public static AddonFileManager getFileManager() {
        return fileManager;
    }

    public static AddonDebug debug() {
        return debug;
    }

}
