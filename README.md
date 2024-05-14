# Homes

A plugin for your minecraft server that allows users to create homes

# Features
This plugin allows you to:
* Add up to 90 homes per player
* Delete custom homes
* Teleport to homes
* Navigation via compass to home with additional particles
* Create homes in other dimensions and / or worlds
* Rename homes with middle click
* Everything GUI based

## Permissions:

* homes.create | Create homes
* homes.use | To use the GUI Features
* homes.teleport | To use the teleport feature
* homes.navigate | To use the navigation feature
* homes.admin | For other information like version control etc.
  
## Commands:

* /addhome [Name] | Add home
* /homes | Open the main GUI with all features
* /checkhomes [Player] | View homes of a player (Even Offlineplayers!) and delete / teleport to it.
* /reloadvalues | Reload values from the options file.
* /reloadmessages | Reload the messages after editing message file

# Config
* Currently only config support for saving data because there is no need for db.
Example:
```
homes:
  Example1:
    X: '174.78694353928665'
    Y: '65.0'
    Z: '-150.7085900761925'
    world: 'world'
```

Homes can also be added via writing into the Config, however it could cause an error.

# Message Config

To edit messages simple edit the messages.yml.
IMPORTANT: You need to keep the placeholders like %homename%
IMPORTANT: Also after changing the config reload the server.

# Options Config
- Allows you to change maximum home size, particles and more
```
options:
  particle:
    enabled: 'true'
    particle: FLAME
    delay: '10'
    info: '//All Available Particles: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Particle.html'
  homes:
    maxsize: '20'
  navigation:
    particle:
      spacing: '0.5'
      length: '5'
      particle: SOUL_FIRE_FLAME
```
# Permissions Config
- Allows you to change permissions. Leave blank after : to make permission not have any effect
```
homes:
  create: homes.create
  use:
  teleport: homes.teleport
  navigate: homes.navigate
  admin: homes.admin
```
For recommendations write in the discussion tab or create a issue on github

# Pictures

![](https://github.com/pqtriick/Homes/blob/master/2023-10-07_17.06.01.png)
![](https://github.com/pqtriick/Homes/blob/master/Unbenannt.PNG)
