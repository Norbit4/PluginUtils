package pl.norbit.pluginutils.commands;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class CommandTimer {

    @Getter
    protected static class Command {
        private final String commandName;
        private int time;

        public Command(String commandName, int time) {
            this.commandName = commandName;
            this.time = time;
        }
        
        public int updateTime(){
            return time--;
        }
    }

    private static HashMap<UUID, LinkedList<Command>> commandTimer;

    public static void addCommand(Player p, String commandName, int time){

        if(!commandTimer.containsKey(p.getUniqueId()))
            commandTimer.put(p.getUniqueId(), new LinkedList<>());

        LinkedList<Command> commands = commandTimer.get(p.getUniqueId());

        commands.add(new Command(commandName, time));
    }

    public static boolean commandExist(Player p, String commandName){

        if (!commandTimer.containsKey(p.getUniqueId())) return false;

        LinkedList<Command> commands = commandTimer.get(p.getUniqueId());

        for (Command command : commands) {

            if(command.getCommandName().equalsIgnoreCase(commandName)){
                return true;
            }
        }

        return false;
    }


    public static BukkitTask startTimer(JavaPlugin javaPlugin){
        commandTimer = new HashMap<>();

        return Bukkit.getScheduler().runTaskTimerAsynchronously(javaPlugin, () -> {

            commandTimer.forEach((player, commands) -> {

                for (Command command : commands) {

                    int updatedTime = command.updateTime();

                    if (updatedTime == 0) commands.remove(command);
                }

                if (commands.isEmpty()) {
                    commandTimer.remove(player);
                }
            });

        }, 0L, 20L);
    }
}

