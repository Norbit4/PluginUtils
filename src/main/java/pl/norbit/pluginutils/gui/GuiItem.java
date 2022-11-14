package pl.norbit.pluginutils.gui;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Builder
@Getter
public class GuiItem {
    @Builder.Default
    private int amount = 1, slot = -1;
    @Builder.Default
    private String name = "";

    private ItemEvent event;
    @Builder.Default
    private Material material = Material.BARRIER;
    private List<String> lore;

    public ItemStack getItemStack(){
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(!lore.isEmpty()) {
            itemMeta.setLore(lore);
        }
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }
}

