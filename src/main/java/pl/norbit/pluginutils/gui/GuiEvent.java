package pl.norbit.pluginutils.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public interface GuiEvent {

    void onClose(InventoryCloseEvent e);

    void onClick(InventoryClickEvent e, GuiItem[] items);

    void onOpen(InventoryOpenEvent e);
}
