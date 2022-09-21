package pl.norbit.pluginutils.task;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

@Builder
public class TaskBuilder {

    static {
        bukkitTaskList = new ArrayList<>();
    }

    private static List<BukkitTask> bukkitTaskList;

    @Getter
    private BukkitTask bukkitTask;
    private JavaPlugin javaPlugin;
    private Runnable runnable;
    @Builder.Default
    private TaskType taskType = TaskType.TIMER;
    @Builder.Default
    private TaskUnit periodUnit = TaskUnit.MILLISECONDS, delayUnit = TaskUnit.MILLISECONDS;

    @Builder.Default
    private int
            delay = 0,
            period = 20;

    public void start(){
        int formatDelay = getFormat(delayUnit, delay);
        int formatPeriod = getFormat(periodUnit, period);

        System.out.println(formatPeriod);
        if(taskType == TaskType.TIMER) {
            bukkitTask =
                    javaPlugin.getServer().getScheduler().runTaskTimer(javaPlugin, runnable, formatDelay, formatPeriod);

            bukkitTaskList.add(bukkitTask);
        }else if(taskType == TaskType.LATER) {
            bukkitTask =
                    javaPlugin.getServer().getScheduler().runTaskLater(javaPlugin, runnable, formatDelay);

            bukkitTaskList.add(bukkitTask);
        }
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
