package me.jakub.randomtp.listeners;

import me.jakub.randomtp.Randomtp;
import me.jakub.randomtp.utils.TeleportUtils;
import me.jakub.randomtp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIEvent implements Listener {

    static Randomtp plugin;

    public GUIEvent(Randomtp plugin) {
        this.plugin = plugin;
    }

    TeleportUtils teleportUtils = new TeleportUtils(plugin);

    @EventHandler
    public void guiEvent(InventoryClickEvent e) {

        if (e.getCurrentItem() == null) return; //Prevent nullpointerexception when clicking empty slots
        if (e.getView().getTitle().equalsIgnoreCase(Utils.getConfirmGUITitle()) && Utils.isConfirmGUIEnabled()) {
            Player player = (Player) e.getView().getPlayer();
            e.setCancelled(true);//Cancel event if the player is in confirm GUI

            if (e.getCurrentItem().getType() == Utils.getConfirmItem()) {
                //Player confirmed
                player.closeInventory();
                player.updateInventory();
                teleportUtils.rtpPlayer(player, null, false, !Randomtp.vaultHooked, true, null, false, true);
            } else if (e.getCurrentItem().getType() == Utils.getCancelItem()) {
                //Player cancelled
                player.closeInventory();
                player.updateInventory();
                return;
            }
        }
    }
}
