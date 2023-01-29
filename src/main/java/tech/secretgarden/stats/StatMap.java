package tech.secretgarden.stats;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class StatMap {

    public static HashMap<String, Integer> getStatMap(Player player) {

        // init statMap to insert into playerMap
        HashMap<String, Integer> statMap = new HashMap<>();

        long time = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        int hours = (int) (((time / 20) / 60) / 60);

        // input all stats to Hashmap
        // general
        statMap.put(ColNames.BREED, player.getStatistic(Statistic.ANIMALS_BRED));
        statMap.put(ColNames.HOURS, hours);
        statMap.put(ColNames.DEATH_COUNT, player.getStatistic(Statistic.DEATHS));
        statMap.put(ColNames.DAMAGE_DEALT, player.getStatistic(Statistic.DAMAGE_DEALT));
        statMap.put(ColNames.DAMAGE_TAKEN, player.getStatistic(Statistic.DAMAGE_TAKEN));
        statMap.put(ColNames.FISH, player.getStatistic(Statistic.FISH_CAUGHT));

        //get values in meters
        statMap.put(ColNames.FALL, player.getStatistic(Statistic.FALL_ONE_CM) / 100);
        statMap.put(ColNames.FLY, player.getStatistic(Statistic.FLY_ONE_CM) / 100);
        statMap.put(ColNames.WALK, player.getStatistic(Statistic.WALK_ONE_CM) / 100);

        statMap.put(ColNames.ENCHANTS, player.getStatistic(Statistic.ITEM_ENCHANTED));
        statMap.put(ColNames.JUMP, player.getStatistic(Statistic.JUMP));
        statMap.put(ColNames.RAID, player.getStatistic(Statistic.RAID_WIN));
        statMap.put(ColNames.TRADES, player.getStatistic(Statistic.TRADED_WITH_VILLAGER));

        // get value in minutes
        statMap.put(ColNames.DEATH, (player.getStatistic(Statistic.TIME_SINCE_DEATH) / 20) / 60);
        statMap.put(ColNames.REST, (player.getStatistic(Statistic.TIME_SINCE_REST) / 20) / 60);

        // kill
        statMap.put(ColNames.PLAYER, player.getStatistic(Statistic.PLAYER_KILLS));
        statMap.put(ColNames.DRAGON, player.getStatistic(Statistic.KILL_ENTITY, EntityType.ENDER_DRAGON));
        statMap.put(ColNames.WITHER, player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER));
        statMap.put(ColNames.WITHER_SKELETON, player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER_SKELETON));
        statMap.put(ColNames.WITCH, player.getStatistic(Statistic.KILL_ENTITY, EntityType.WITCH));
        statMap.put(ColNames.VILLAGER, player.getStatistic(Statistic.KILL_ENTITY, EntityType.VILLAGER));
        statMap.put(ColNames.ZOMBIE_VILLAGER, player.getStatistic(Statistic.KILL_ENTITY, EntityType.ZOMBIE_VILLAGER));
        statMap.put(ColNames.WANDERING, player.getStatistic(Statistic.KILL_ENTITY, EntityType.WANDERING_TRADER));
        statMap.put(ColNames.GHAST, player.getStatistic(Statistic.KILL_ENTITY, EntityType.GHAST));

        // mine
        statMap.put(ColNames.DIAMOND, player.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE) + player.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_DIAMOND_ORE));
        statMap.put(ColNames.COPPER, player.getStatistic(Statistic.MINE_BLOCK, Material.COPPER_ORE) + player.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_COPPER_ORE));
        statMap.put(ColNames.EMERALD, player.getStatistic(Statistic.MINE_BLOCK, Material.EMERALD_ORE) + player.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_EMERALD_ORE));
        statMap.put(ColNames.GOLD, player.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE) + player.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_GOLD_ORE));
        statMap.put(ColNames.IRON, player.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE) + player.getStatistic(Statistic.MINE_BLOCK, Material.DEEPSLATE_IRON_ORE));

        return statMap;
    }
}
