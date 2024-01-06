package pl.to1maszproblem.module.sterialize;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import pl.to1maszproblem.module.Warp;
public class WarpSterializer implements ObjectSerializer<Warp> {
    @Override
    public boolean supports(@NonNull Class<? super Warp> type) {
        return Warp.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Warp object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("item", object.getItem());
        data.add("slot", object.getSlot());
        data.add("location", object.getLocation());
    }

    @Override
    public Warp deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        ItemStack item = data.get("item", ItemStack.class);
        Integer slot = data.get("slot", Integer.class);
        Location location = data.get("location", Location.class);
        return new Warp(name, item, location, slot);
    }
}
