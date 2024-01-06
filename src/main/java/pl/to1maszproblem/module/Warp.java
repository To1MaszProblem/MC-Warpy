package pl.to1maszproblem.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Warp implements Serializable {
    private String name;

    private ItemStack item;

    private Location location;

    private int slot;

}
