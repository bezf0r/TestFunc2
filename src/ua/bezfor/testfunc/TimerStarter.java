package ua.bezfor.testfunc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftCow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ua.bezfor.testfunc.Main.plugin;

public class TimerStarter implements CommandExecutor {
    static List<CraftCow> craftCowList = new ArrayList<>();
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            List<Entity> entityList = player.getNearbyEntities(30.0, 30.0, 30.0);
            int entityAmount = entityList.size();
            if((entityAmount--) != 0){
                craftCowList.add((CraftCow) entityList.get(entityAmount));
                Bukkit.broadcastMessage("e " + entityAmount);
            }
        timer();
    }
        return true;
    }
    static int turnsAmount = 14;
    static int loc = turnsAmount;
    public static void timer() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> Bukkit.getOnlinePlayers().forEach(p -> {
                List<Point> locations = CircleLocations.getLocations(p.getLocation().getX(), p.getLocation().getZ(), 4, turnsAmount);
                if ((loc--) == 0) {
                    loc = 14;
                } else {
                    for(CraftCow craftCow : craftCowList){
                    craftCow.getHandle().getNavigation().a(locations.get(loc).getX(), p.getLocation().getY(),locations.get(loc).getY(), 1.3F);}
                }
        }), 0, 22).getTaskId();
    }
}
