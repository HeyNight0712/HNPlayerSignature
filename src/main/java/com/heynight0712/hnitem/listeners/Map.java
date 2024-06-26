package com.heynight0712.hnitem.listeners;

import com.heynight0712.hnitem.core.LanguageManager;
import com.heynight0712.hnitem.data.KeyManager;
import com.heynight0712.hnitem.utils.data.ItemData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.CartographyInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Map implements Listener {
    @EventHandler
    public void onMap(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        // 檢查 製圖台
        if (event.getClickedInventory() instanceof CartographyInventory) {
            ItemData itemData = new ItemData(event.getInventory().getItem(0));
            if (itemData.getItemStack() == null || itemData.getItemStack().getItemMeta() == null) return;
            PersistentDataContainer container = itemData.getPersistentDataContainer();

            // 檢查 是否簽名
            if (container.has(KeyManager.getUUID()) && event.getSlot() == 2) {
                Player player = (Player) event.getWhoClicked();
                String playerUUID = String.valueOf(player.getUniqueId());

                // 檢查 UUID 是否相同
                if (container.get(KeyManager.getUUID(), PersistentDataType.STRING).equals(playerUUID)) return;

                event.setCancelled(true);
                event.getWhoClicked().sendMessage(LanguageManager.getString("Cartography.NotOwner"));
            }
        }
    }
}
