# punch
`punch` is a mod for [Minecraft](https://www.minecraft.net/): Java Edition that
changes the rendering of the crosshair in first-person view. Compatible game
versions are `1.20` through `1.20.4`. This mod targets the fabric loader and
support for other platforms is not planned.

## Features

### Custom Attack Indicator Sizes
Unmodified, the client renders the crosshair at specific hard-coded dimensions.
Specifically the attack cooldown texture (here called "attack indicator" or
simply "indicator") is 16 pixels wide. This mod allows that width
be changed. Additionally, custom width can change when the crosshair is aimed
at an entity. By default, this feature is enabled with the following custom
sizes:

| when                      | size (width x height) |
|:--------------------------|:----------------------|
| normally                  | 16 x 4 (Vanilla)      |
| when looking at an entity | 32 x 8 (twice as big) |

These properties can be configured in the configuration file (see below) using
the keys `"indicator_width"`, `"indicator_height"`, `"targetied_indicator_width"`
and `"targeted_indicator_height"`. The whole feature can be enabled or disabled
using `"do_custom_indicator_sizes"`.

### Full Attack Indicator
Unmodified, the client renders the "full" attack indicator texture (normally
a sword with a cross below it) only  when the player's attack speed is below
a certain number. The mod removes this restriction. This feature can be
enabled or disabled in the configuration file (see below) using the key
`"show_indicator_with_fast_weapon""`.

## Configuration
Features can be configured using a configuration file `.minecraft/config/punch.json`.
This file is in [JSON](https://en.wikipedia.org/wiki/JSON) format and contains
a single object with several keys. Keys are described below and an  example
file  showing the default config can be  found [here](./examples/punch.json).

### Configuration Keys
- **"enabled":** ***boolean, default `true`***
  
  Enables or disables all features. When `false`, the mod does not change 
  vanilla behaviour.

- **"show_indicator_with_fast_weapon":** ***boolean, default `true`***
  
  Enables rendering the "full" indicator texture  regardless of attack speed of
  the player's weapon. This texture is only shown when the attack cooldown is
  fully recovered.

- **"do_custom_indicator_sizes":** ***boolean, default`true`***
  
  Enables custom attack indicator sizes.                          

- **"indicator_width":** ***positive whole number, default 16***
  
  Normal width of the attack indicator                                      

- **"indicator_height":** ***positive whole number, default 4***
  
  Normal height of the attack indicator                                     

- **"targeted_indicator_width":** ***positive whole number, default 32***
  
  Width of the indicator when looking at an entity     

- **"targeted_indicator_height":** ***positive whole number, default 8***
  
  Height of the indicator when looking at an entity    

## Building
Check [Releases](https://github.com/safeliquids/punch/releases) for pre-built
jars. Otherwise this mod can be build using [Gradle](https://gradle.org/)
``` console
> gradle build
``` 

## Acknowledgements
This project extends a template created using the
[Template Mod Generator](https://fabricmc.net/develop/template/).

The [Fabric Docs](https://docs.fabricmc.net/develop/),
[Fabric Wiki](https://wiki.fabricmc.net/)
and [Mixin Javadoc](https://jenkins.liteloader.com/view/Other/job/Mixin/javadoc/index.html)
as well as the [Mixin Introduction](https://github.com/SpongePowered/Mixin/wiki#introduction-series)
and [Mixin Extras wiki](https://github.com/LlamaLad7/MixinExtras/wiki)
were great resources during development.
