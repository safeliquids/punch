# punch

`punch` is a mod for [Minecraft](https://www.minecraft.net/): Java Edition that
changes the rendering of the crosshair in first-person view. Compatible game
versions are `1.20` through `1.20.4`. This mod targets the fabric loader and
support for other platforms is not planned.

## Features

This mod's only feature is changing the logic of how the client renders the
crosshair, more specifically the attack cooldown texture. This texture has two
variants - a **progress bar** of the attack cooldown and a **full** texture. The
former renders whenever the player's attack cooldown has not yet recovered after
a swing. The latter renders if the cooldown is fully recovered, the player is
looking at an entity, and the player's attack speed is less than 4. The `punch`
mod removes this last condition. All other mechanisms, including the rendering
of the crosshair and other textures, are undisturbed.

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
