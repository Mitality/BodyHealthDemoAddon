package bodyHealthDemoAddon;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static String language_no_further_args;
    public static String language_message_prefix;
    public static String language_explode_success;
    public static String language_no_message;
    public static String language_no_target;
    public static String language_invalid_target;
    public static String language_message_sent;
    public static String language_invalid_subcommand;

    public static void load(FileConfiguration config) {

        language_no_further_args = config.getString("language.no-further-args", "language_no_further_args");
        language_message_prefix = config.getString("language.message-prefix", "language_message_prefix");
        language_explode_success = config.getString("language.explode-success", "language_explode_success");
        language_no_message = config.getString("language.no-message", "language_no_message");
        language_no_target = config.getString("language.no-target", "language_no_target");
        language_invalid_target = config.getString("language.invalid-target", "language_invalid_target");
        language_message_sent = config.getString("language.message-sent", "language_message_sent");
        language_invalid_subcommand = config.getString("language.invalid-subcommand", "language_invalid_subcommand");

    }

}