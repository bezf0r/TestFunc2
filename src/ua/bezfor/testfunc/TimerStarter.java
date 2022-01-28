package ua.bezfor.testfunc;

import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftCow;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPig;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ua.bezfor.testfunc.Main.plugin;

public class timerStarter implements CommandExecutor {
    static List<CraftPig> craftPigList = new ArrayList<>();
    static List<Entity> spawnReplaceEntity = new ArrayList<>();

    public void replaceEntityId(Player player){
        //Спрятать зомби для всех кроме сендера
        for(CraftCow craftCow : craftCowList){
            PacketPlayOutEntityDestroy entityCowDestroy = new PacketPlayOutEntityDestroy(craftCow.getEntityId());

        List<Player> sortedOnlinePl = Bukkit.getOnlinePlayers().stream().filter(players -> players != player).collect(Collectors.toList());
        for(Player players : sortedOnlinePl)
            ((CraftPlayer) players).getHandle().playerConnection.sendPacket(entityCowDestroy);}

        //Спрятать скелетов для сендера
        for (Entity entity : entityList)
            spawnReplaceEntity.add(entity.getLocation().getWorld().
                    spawnEntity(entity.getLocation(), EntityType.PIG));

        int amount = spawnReplaceEntity.size();
        while ((amount--) != 0) {
            craftPigList.add((CraftPig) spawnReplaceEntity.get(amount));
        }
        for(CraftPig craftPig : craftPigList) {
            PacketPlayOutEntityDestroy entityPigDestroy = new PacketPlayOutEntityDestroy(craftPig.getEntityId());

            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(entityPigDestroy);
        }

    }
    static Player player;
    static List<CraftCow> craftCowList = new ArrayList<>();
    static List<Entity> entityList = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            player = (Player) sender;
            entityList = player.getNearbyEntities(30.0, 30.0, 30.0).stream().
                    filter(entity -> entity.getType().equals(EntityType.COW)).collect(Collectors.toList());

            int amount = entityList.size();
            while((amount--) != 0 ){
                craftCowList.add((CraftCow) entityList.get(amount));
            }
            replaceEntityId(player);
        timer();
    }else {return false;}
        return true;
    }
    static int turnsAmount = 46;
    static int loc = turnsAmount;
    public static void timer() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> Bukkit.getOnlinePlayers().forEach(p -> {
                List<Point> locations = CircleLocations.getLocations(player.getLocation().getX(),
                        player.getLocation().getZ(), 8, turnsAmount);
                if ((loc--) == 0) {
                    loc = turnsAmount;
                } else {
                    for(CraftCow craftCow : craftCowList)
                        craftCow.getHandle().getNavigation().a(locations.get(loc).getX(), player.getLocation().getY(),
                                locations.get(loc).getY(), 1.5F);
                    for(CraftPig craftPig : craftPigList)
                    craftPig.getHandle().getNavigation().a(locations.get(loc).getX(),
                            player.getLocation().getY(), locations.get(loc).getY(), 1.5F);
                }
        }), 0, 20).getTaskId();
    }
}
