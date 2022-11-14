package pl.norbit.pluginutils.gui;

import lombok.Builder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import pl.norbit.pluginutils.register.PluginUtilsRegistry;

import java.util.HashMap;
import java.util.HashSet;

public class GuiUtil implements Listener {

    private static final HashMap<Integer, GuiUtil> guiUtils = new HashMap<>();
    private static final HashSet<Integer> ids = new HashSet<>();

    public static McGui getGui(int id){
        if(guiUtils.containsKey(id)){
            return new McGui(guiUtils.get(id).inventory);
        }
        return null;
    }

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

            if(e.getCurrentItem() == null) return;

            e.setCancelled(true);

            Material type = e.getCurrentItem().getType();

            for (GuiItem item : items) {

                if(item.getEvent() == null) continue;

                if (item.getMaterial() != type) continue;

                if(!item.getName().equals(e.getCurrentItem().getItemMeta().getDisplayName())) continue;

                item.getEvent().onClick(e, item);
                break;

            }

            if(events == null) return;

            events.onClick(e, items);
        }
    }

    private Inventory inventory;

    public GuiUtil() {
    }

    @Builder
    private static GuiUtil of(int id, String tittle, GuiSlots guiSlots , GuiEvent guiEvent, GuiItem... items) {

        GuiUtil guiUtil = new GuiUtil();
        Inventory inventory = Bukkit.createInventory(null, guiSlots.getSize(), tittle);
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

        if(!ids.contains(id)){
            PluginUtilsRegistry
                    .getRegisterJavaPlugin()
                    .getServer()
                    .getPluginManager()
                    .registerEvents(guiUtil, PluginUtilsRegistry.getRegisterJavaPlugin());
            ids.add(id);
        }

        guiUtils.put(id, guiUtil);

        return guiUtil;
    }
}
