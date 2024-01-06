package pl.to1maszproblem.utils;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.stream.Collectors;

public class ItemBuilder {
    private final ItemStack item;

    private final ItemMeta meta;

    public ItemMeta getMeta() {
        return this.meta;
    }

    private List<String> lore = new ArrayList<>();

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public ItemBuilder(Material material, int amount, short data) {
        this.item = new ItemStack(material, amount, data);
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(material, 1, (short)0);
    }

    public ItemBuilder setName(String name) {
        if (name == null)
            return this;
        this.meta.setDisplayName(TextUtil.fixColor(name));
        return this;
    }

    public ItemBuilder setOwner(String owner) {
        if (owner == null)
            return this;
        if (!(this.meta instanceof SkullMeta))
            return this;
        ((SkullMeta)this.meta).setOwner(owner);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder addAttribute(Attribute attribute, double amount) {
        this.meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        this.meta.addAttributeModifier(attribute, new AttributeModifier(
                String.valueOf(UUID.randomUUID()), amount, AttributeModifier.Operation.ADD_NUMBER));
        return this;
    }

    public ItemBuilder setGlowing(boolean t) {
        if (t) {
            this.meta.addEnchant(Enchantment.DURABILITY, 1, true);
            this.meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        } else {
            if (this.meta.getEnchants().containsKey(Enchantment.DURABILITY))
                this.meta.removeEnchant(Enchantment.DURABILITY);
            this.meta.removeItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        }
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        this.lore.addAll((Collection<? extends String>) Arrays.<String>stream(lore).map(TextUtil::fixColor).collect(Collectors.toList()));
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemBuilder addLore(List<String> lore) {
        this.lore.addAll((Collection<? extends String>)lore.stream().map(TextUtil::fixColor).collect(Collectors.toList()));
        return this;
    }

    public ItemStack build() {
        this.meta.setLore(this.lore);
        this.item.setItemMeta(this.meta);
        return this.item;
    }

    public ItemBuilder clone() {
        return (new ItemBuilder(this.item.getType(), this.item.getAmount(), this.item.getDurability())).setName(this.meta.getDisplayName()).addLore(this.lore);
    }
}
