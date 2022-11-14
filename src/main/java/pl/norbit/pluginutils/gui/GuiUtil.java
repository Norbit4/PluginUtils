package pl.norbit.pluginutils.gui;

import lombok.Builder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class GuiUtil implements Listener {

    private GuiEvent events;
    private GuiItem[] items;
    @EventHandler
    public void close(InventoryCloseEvent e){
        if(e.getInventory().getName().equalsIgnoreCase(inventory.getName())){

            if(events == null) return;

            events.onClose(e);
        }
    }
    @EventHandler
    public void open(InventoryOpenEvent e){
        if(e.getInventory().getName().equalsIgnoreCase(inventory.getName())){

            if(events == null) return;

            events.onOpen(e);
        }
    }

    @EventHandler
    public void click(InventoryClickEvent e){
        if(e.getInventory().getName().equalsIgnoreCase(inventory.getName())){

            e.setCancelled(true);

            for (GuiItem item : items) {

                if(item.getEvent() == null) return;

                if (item.getMaterial() != e.getCurrentItem().getType()) return;

                if(!item.getName().equals(e.getCurrentItem().getItemMeta().getDisplayName())) return;

                item.getEvent().onClick(e, item);
            }

            if(events == null) return;

            events.onClick(e, items);
        }
    }

    private Inventory inventory;

    public GuiUtil() {
    }

    @Builder
    private static GuiUtil of(String tittle, int size , GuiEvent guiEvent, GuiItem... items) {
        GuiUtil guiUtil = new GuiUtil();
        Inventory inventory = Bukkit.createInventory(null, size, tittle);
        guiUtil.events = guiEvent;
        guiUtil.items = items;

        for (GuiItem item : items) {
            int slot = item.getSlot();

            if(slot == -1){
                inventory.addItem(item.getItemStack());
            }else{
                inventory.setItem(slot, item.getItemStack());
            }
        }

        guiUtil.inventory = inventory;

        return guiUtil;
    }

    public Inventory createInv(){
        return inventory;
    }
}
