package pl.norbit.pluginutils.commands;

import lombok.Builder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import pl.norbit.pluginutils.chat.ChatUtil;
import pl.norbit.pluginutils.permissions.PermissionUtil;
import pl.norbit.pluginutils.register.PluginUtilsRegistry;

@Builder
public class CommandUtil {

    public enum TimeUnits{
        SECONDS,
        MINUTES
    }

    public static void setPermMessage(String message){
        CommandUtil.PERM_MESSAGE = message;
    }

    public static void setCooldownMessage(String message){
        CommandUtil.COOLDOWN_MESSAGE = message;
    }

    private static JavaPlugin javaPlugin;
    private static BukkitTask timerTask;
    protected static String PERM_MESSAGE = "&cYou dont have permission!";
    protected static String COOLDOWN_MESSAGE = "&cCooldown! {COOLDOWN}!";
    private String commandName;
    private CommandExecutor commandExecutor;
    private String[] permissions;
    @Builder.Default
    private int delay = 0;
    @Builder.Default
    private TimeUnits timeUnits = TimeUnits.SECONDS;

    public void register(){

        if(timeUnits == TimeUnits.MINUTES){
            delay = delay * 60;
        }

        CommandUtil.javaPlugin = PluginUtilsRegistry.getRegisterJavaPlugin();

        if(javaPlugin == null) throw new Error("JavaPlugin is not registered!");

        if(timerTask == null && delay > 0) timerTask = CommandTimer.startTimer(javaPlugin);

        if(commandExecutor == null) throw new Error("CommandExecutor is null!");

        if(commandName == null ) throw new Error("Command name is null!");

        CommandExecutor Permission = new Permission(commandExecutor, permissions, delay);
        javaPlugin.getCommand(commandName).setExecutor(Permission);
    }

    public static class Permission implements CommandExecutor{
        private final CommandExecutor defaultCommandExecutor;
        private final int delay;
        private final String[] permissions;

        public Permission(CommandExecutor commandExecutor, String[] permissions, int delay) {
            this.defaultCommandExecutor = commandExecutor;
            this.permissions = permissions;
            this.delay = delay;
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            Player p = (Player) sender;

            if (permissions != null) {

                PermissionUtil permissionUtil = new PermissionUtil(p);

                if (!permissionUtil.hasPermission(permissions)) {
                    p.sendMessage(ChatUtil.format(CommandUtil.PERM_MESSAGE));
                    return false;
                }
            }

            if(!cmdIsReady(p, label)){
                String message = CommandUtil.COOLDOWN_MESSAGE
                        .replace("{COOLDOWN}", CommandTimer.getCmdCooldown(p, label));

                p.sendMessage(ChatUtil.format(message));
                return false;
            }

            CommandTimer.addCommand(p,label, delay);

            defaultCommandExecutor.onCommand(sender, command, label, args);

            return false;
        }

        private boolean cmdIsReady(Player p, String commandName){

            return !CommandTimer.commandExist(p, commandName);
        }
    }


}
