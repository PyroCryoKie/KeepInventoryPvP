import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin implements Listener {

    private HashMap<UUID, Boolean> playersKilledByPlayer = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer != null) {
            // Player was killed by another player
            playersKilledByPlayer.put(victim.getUniqueId(), true);
            keepInventory(victim, true);
            event.getDrops().clear(); // Optional: Clears items from dropping on the ground.
        } else {
            // Player died by something other than a player
            playersKilledByPlayer.put(victim.getUniqueId(), false);
            keepInventory(victim, false);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Boolean keepInventory = playersKilledByPlayer.getOrDefault(player.getUniqueId(), false);
        if (!keepInventory) {
            // Clear the player's inventory if keepInventory is false
            player.getInventory().clear();
        }
    }

    private void keepInventory(Player player, boolean keep) {
        // Add your logic here to store player's inventory state if necessary
    }
}