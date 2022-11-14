package pl.norbit.pluginutils.gui;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@AllArgsConstructor
public class McGui {
    private Inventory inventory;

    public void openInv(Player p){
        p.openInventory(inventory);
    }
}
