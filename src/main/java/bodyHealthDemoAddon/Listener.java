package bodyHealthDemoAddon;

import bodyhealth.api.events.BodyPartHealthChangeEvent;
import bodyhealth.api.events.BodyPartStateChangeEvent;
import org.bukkit.event.EventHandler;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onBodyPartHealthChange(BodyPartHealthChangeEvent event) {
        Main.debug().log("Health of " + event.getPlayer().getName() + "'s " + event.getBodyPart().name() + " changed from " + event.getOldHealth() + " to " + event.getNewHealth());
    }

    @EventHandler
    public void onBodyPartStateChange(BodyPartStateChangeEvent event) {
        Main.debug().log("State of " + event.getPlayer().getName() + "'s " + event.getBodyPart().name() + " changed from " + event.getOldState() + " to " + event.getNewState());
    }

}
