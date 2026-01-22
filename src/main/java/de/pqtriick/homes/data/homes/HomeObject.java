package de.pqtriick.homes.data.homes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.World;

@AllArgsConstructor
@Getter
@Setter
public class HomeObject {

    private String name;
    private double x;
    private double y;
    private double z;
    private World world;

}
