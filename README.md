# punch
`punch` is a mod for [Minecraft](https://www.minecraft.net/): Java Edition that
changes the rendering of the crosshair in first-person view. Compatible game
versions are `1.20.2` through `1.20.4`. This mod targets the fabric loader and
support for other platforms is not planned.

## Features

### Custom Attack Indicator Sizes
Unmodified, the client renders the crosshair at specific hard-coded dimensions.
Specifically the attack cooldown texture (here called "attack indicator" or
simply "indicator") is 16 pixels wide. This mod allows that width
be changed. Additionally, custom width can change when the crosshair is aimed
at an entity. By default, this feature is enabled with the following custom
sizes:

| when                      | size (width x height)        |
|:--------------------------|:-----------------------------|
| normally                  | 16 $\times$ 4 (Vanilla)      |
| when looking at an entity | 32 $\times$ 8 (twice as big) |

These properties can be configured in the configuration file
[as described below](#configuratoin).

### Full Attack Indicator
Unmodified, the client renders the "full" attack indicator texture (normally
a sword with a cross below it) only  when the player's attack speed is below
a certain number. The mod removes this restriction. This feature can be
enabled or disabled in the configuration file (see below) using the key
`"show_indicator_with_fast_weapon""`.

## Configuration
Features can be configured using a configuration file `.minecraft/config/punch.json`
written in [JSON](https://en.wikipedia.org/wiki/JSON) format. For an example see
[the default configuration](./examples/punch.json). Detailed description of the
configuration follows.

### Configuration Keys
- **"enabled":** ***boolean, default `true`***  
    Enables or disables all features. When `false`, the mod does not change 
    vanilla behaviour.

- **"show_indicator_with_fast_weapon":** ***boolean, default `true`***  
    Enables rendering the "full" indicator texture  regardless of attack speed
    of the player's weapon. This texture is only shown when the attack cooldown
    is fully recovered.

- **"custom_indicator_sizes":** ***object, optional***  
    Configures custom attack indicator sizes. Following fields are within.  
    - **"enabled":** ***boolean, default `true`***  
        Enables or disables custom indicator sizes.
    - **"target_partial":** ***object, optional***  
        Contains dimensions of the 'charging' attack indicator when targeting
        an entity.
        - **"width":** ***positive integer, default `32`***  
        - **"height":** ***positive integer, default `8`***
    
    - **"target_full":** ***object, optional***  
        Contains dimensions of the 'full' attack indicator when targeting
        an entity.
        - **"width":** ***positive integer, default `32`***
        - **"height":** ***positive integer, default `32`***
    
    - **"normal_partial":** ***object, optional***  
        Contains dimensions of the attack indicator when *not* targeting
        an entity.
        - **"width":** ***positive integer, default `16`***
        - **"height":** ***positive integer, default `4`***

## Building
Check [Releases](https://github.com/safeliquids/punch/releases) for pre-built
jars. Otherwise this mod can be built using [Gradle Wrapper](https://gradle.org/).

**Linux:**
``` console
> chmod +x ./gradlew
> ./gradlew build
``` 

**Windos:**
``` console
> .\gradlew.bat build
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
