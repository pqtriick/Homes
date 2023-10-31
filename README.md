# Homes

A plugin for your minecraft server that allows users to create homes

# Features
This plugin allows you to:
* Add up to 45 homes per player
* Delete custom homes
* Teleport to homes
* Navigation via compass to home with additional particles
* Create homes in other dimensions and / or worlds
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

# Pictures

![](https://github.com/pqtriick/Homes/blob/master/2023-10-07_17.06.01.png)
![](https://github.com/pqtriick/Homes/blob/master/2023-10-07_17.21.13.png)
