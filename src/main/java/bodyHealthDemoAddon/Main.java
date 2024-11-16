package bodyHealthDemoAddon;

import bodyhealth.api.addons.AddonDebug;
import bodyhealth.api.addons.AddonFileManager;
import bodyhealth.api.addons.AddonInfo;
import bodyhealth.api.addons.BodyHealthAddon;

@AddonInfo(
        name = "DemoAddon",
        description = "Showcases the use of BodyHealths addon system",
        version = "1.0.0",
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

        fileManager.generateFileFromFileName("config.yml"); // Ensures that the primary config file is present

        // You could update your addons config files here. I recommend using this:
        // https://github.com/tchristofferson/Config-Updater?tab=readme-ov-file#config-updater

        registerCommand("demo", new Command()); // register subcommand ("/bodyhealth demo")
        registerListener(new Listener()); // Register our listener
    }

    @Override
    public void onAddonDisable() {
        unregisterListeners();
        unregisterCommands();
    }

    @Override
    public void onBodyHealthReload() {
        // This is called whenever BodyHealth is reloaded
        // You could update files here, reload a potential internal configuration, etc.
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
