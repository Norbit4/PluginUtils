package pl.norbit.pluginutils.task;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import pl.norbit.pluginutils.register.PluginUtilsRegistry;

import java.util.ArrayList;
import java.util.List;

@Builder
public class TaskUtil {

    static {
        bukkitTaskList = new ArrayList<>();
    }

    private static List<BukkitTask> bukkitTaskList;

    public static void stopAllTasks(){

        bukkitTaskList.forEach(bukkitTask1 -> {
            if(bukkitTask1 != null){
                bukkitTask1.cancel();
            }
        });
        bukkitTaskList.clear();
    }

    @Getter
    private BukkitTask bukkitTask;
    private static JavaPlugin javaPlugin = PluginUtilsRegistry.getRegisterJavaPlugin();

    @Builder.Default
    private boolean asynchronous = false;
    private Runnable runnable;
    @Builder.Default
    private TaskType taskType = TaskType.TIMER;
    @Builder.Default
    private TaskUnit periodUnit = TaskUnit.MILLISECONDS, delayUnit = TaskUnit.MILLISECONDS;

    @Builder.Default
    private int
            delay = 0,
            period = 20;

    public BukkitTask start(){
        if (javaPlugin == null) throw new Error("JavaPlugin is not registered!");

        int formatDelay = getFormat(delayUnit, delay);
        int formatPeriod = getFormat(periodUnit, period);

        BukkitScheduler scheduler = javaPlugin.getServer().getScheduler();

        if(taskType == TaskType.TIMER) {
            if(asynchronous) {

                bukkitTask = scheduler.runTaskTimerAsynchronously(javaPlugin, runnable, formatDelay, formatPeriod);
            }else{

                bukkitTask = scheduler.runTaskTimer(javaPlugin, runnable, formatDelay, formatPeriod);
            }
        }else if(taskType == TaskType.LATER) {
            if(asynchronous) {

                bukkitTask = scheduler.runTaskLaterAsynchronously(javaPlugin, runnable, formatDelay);
            }else{

                bukkitTask = scheduler.runTaskLater(javaPlugin, runnable, formatDelay);
            }
        }
        bukkitTaskList.add(bukkitTask);

        return bukkitTask;
    }

    public void stop(){
        bukkitTaskList.remove(bukkitTask);
        bukkitTask.cancel();
    }

    private int getFormat(TaskUnit periodUnit, int time) {
        int formatTime;
        switch (periodUnit){
            case MINUTES: formatTime = 60 * 20 * time;
                break;
            case SECONDS: formatTime = 20 * time;
                break;
            default: formatTime = time;
        }
        return formatTime;
    }
}
