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
        data.add("location", object.getLocation());
        data.add("slot", object.getSlot());
    }

    @Override
    public Warp deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        ItemStack item = data.get("item", ItemStack.class);
        Location location = data.get("location", Location.class);
        Integer slot = data.get("slot", Integer.class);
        return new Warp(name, item, location, slot);
    }
}
