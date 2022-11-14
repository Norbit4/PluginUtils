package pl.norbit.pluginutils.gui;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.norbit.pluginutils.chat.Formatter;

import java.util.List;

@Builder
@Getter
@ToString
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

        if(lore != null) {
            if (!lore.isEmpty()) {
                for (String s : lore) {
                    Formatter.format(s);
                }
                itemMeta.setLore(lore);
            }
        }
        name = Formatter.format(name);

        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        return itemStack;

    }
}

