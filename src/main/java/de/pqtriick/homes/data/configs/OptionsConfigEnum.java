package de.pqtriick.homes.data.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OptionsConfigEnum {

    OPTIONS_INFO("options.info", "All Available Particles: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html"),
    OPTIONS_PARTICLE_ENABLED("options.particle.enabled", "true"),
    OPTIONS_PARTICLE("options.particle.particle", "FLAME"),
    OPTIONS_PARTICLE_DELAY("options.particle.spawnDelay", "10"),
    OPTIONS_NAVIPARTICLE_SPACING("options.navigation.spacing", "0.5"),
    OPTIONS_NAVIPARTICLE_LENGTH("options.navigation.length", "5"),
    OPTIONS_NAVIPARTICLE("options.navigation.particle", "SOUL_FIRE_FLAME"),
    OPTIONS_HOME_MAXHOMES("options.homes.maxhomes", "99"),
    OPTIONS_HOMES_BLOCK("options.homes.block", "CYAN_BED");

    final String path;
    final String value;
}
