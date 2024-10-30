package bodyHealthDemoAddon;

import bodyhealth.api.addons.AddonCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Command implements AddonCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        // Note that args[0] is the subcommand itself (e.g. "demo" in this case)

        YamlConfiguration config = Main.getFileManager().getYamlConfiguration("config.yml");

        if (args.length < 2) {
            // User only executed '/bodyhealth demo' without further arguments
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                config.getString("language.no-further-args", "&ePlease provide more arguments (e.g. \"explode\")")));
            return true;
        }

        if (args[1].equalsIgnoreCase("explode")) {

            if (args.length < 3 && !(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    config.getString("language.no-target", "&cPlease provide a valid target")));
                return true;
            }

            Player target = args.length > 2 ? Bukkit.getPlayer(args[2]) : (Player) sender;

            if (target == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    config.getString("language.invalid-target", "&cTarget player not found")));
                return true;
            }

            Objects.requireNonNull(target.getLocation().getWorld()).createExplosion(target.getLocation(), 5);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                config.getString("language.explode-success", "&cTarget successfully exploded")));
            return true;

        }

        if (args[1].equalsIgnoreCase("message")) {

            // Check if the sender provided a message to send
            if (args.length < 3) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    config.getString("language.no-message", "&ePlease provide a message to send")));
                return true;
            }

            Player target;
            String message;

            if (args.length > 3) {
                target = Bukkit.getPlayer(args[2]);
                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        config.getString("language.invalid-target", "&cTarget player not found")));
                    return true;
                }
                // Combine all words after the player name into the message
                message = String.join(" ", args).substring(args[0].length() + args[1].length() + args[2].length() + 3);
            }

            else { // No target specified, send the message to the sender themselves if they are a player
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        config.getString("language.no-target", "&cPlease provide a valid target")));
                    return true;
                }
                target = (Player) sender;
                // Combine all words after the subcommand into the message
                message = String.join(" ", args).substring(args[0].length() + args[1].length() + 2);
            }

            // Send the message to the target player
            target.sendMessage(ChatColor.translateAlternateColorCodes('&',
                config.getString("language.message-prefix", "&e[Message from &a{sender}&e]: &r").replace("{sender}", sender.getName()) + message));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                config.getString("language.message-sent", "&aMessage sent successfully")));
            return true;
        }

        // User specified an unknown / invalid subcommand
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
            config.getString("language.invalid-subcommand", "&cUnknown subcommand")));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {

        if (args.length == 2) { // this is what comes after '/bodyhealth demo '
            return List.of("explode", "message");
        }

        if (args.length == 3) { // this comes after '/bodyhealth demo explode/message '
            List<String> playerNames = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerNames.add(player.getName());
            }
            return playerNames; // suggest all online players
        }

        return List.of(); // This ensures nothing is suggested
    }

    @Override
    public String permission() {
        return "bodyhealth.demo";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

}
