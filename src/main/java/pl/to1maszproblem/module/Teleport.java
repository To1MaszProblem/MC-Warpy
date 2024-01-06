package pl.to1maszproblem.module;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter @Setter
public class Teleport {
    private Location from;

    private int time;

    private Location to;

    public Teleport(Location from, int time, Location to) {
        this.from = from;
        this.time = time;
        this.to = to;
    }
}
