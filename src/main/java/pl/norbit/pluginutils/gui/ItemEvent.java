package pl.norbit.pluginutils.gui;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface ItemEvent {

    void onClick(InventoryClickEvent e, GuiItem item);
}
