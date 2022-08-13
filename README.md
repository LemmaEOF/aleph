<img src="aleph.svg" width="180px" align="right"/>

# Aleph
*\~the API lib that should have never been\~*

Aleph is an "invisible" library mod for [NilLoader](https://git.sleeping.town/unascribed/NilLoader) targeting primarily
modern Minecraft versions.

Aleph is composed of two major sections - Aleph:Null and Aleph:One. These are included in the same jar for now, but may
be separated out in the future ~~if I figure out how to use Gradle multiprojects~~. Aleph:Null contains features that
require **no** interaction with Aleph code, and Aleph:One contains features that require **minimal** interaction with
Aleph code, limited to Aleph-specified entrypoints and annotations.

Current Aleph:Null features:
- **Resource pack loading** - Any nilmod (either as a jar or directory) will have its `resources/assets` and
`resources/data` source folders loaded as resource or data packs, respectively.
  - A `pack.mcmeta` is not required - NilLoader will generate one from the mod's `nilmod.css`
- **Mod menu** - The Minecraft Realms button is replaced with a Nilmods button.
- **Client brand** - The client brand is updated to "nil", or "&lt;other brand&gt;,nil" if another loader is present.

Current Aleph:One features:
- **Auto-registration** - Simply place objects in public/static/final fields in a `Runnable` class and add them as a
    specific entrypoint, and Aleph will automagically register them for you! The registry identifier can be set with the 
    annotation `@RegisteredAs`, and after all contents for an entrypoint are registered, the `Runnable` for the 
    entrypoint will be fired. All registerable fields are registered in alphabetical order. For sanity's sake, each hook
    will only be loaded if a nilmod calls for it to be used. If you *really* want to make all hooks load no matter what, 
    you can add `-Daleph.entrypoint.all=true` to your JVM args. This currently supports:
  - Blocks, via the `blocks` entrypoint
    - Block render layers annotated by `@RenderLayer`
    - Block color providers implemented on the block
    - Block color providers annotated by `@ConstantColor`
    - Block color providers annotated by `@ColorProvider`
  - Block entities, via the `block-entities` entrypoint
    - Block entity renderers annotated by `@Renderer`
  - Enchantments, via the `enchantments` entrypoint
  - Entities, via the `entities` entrypoint
    - Entity renderers annotated by `@Renderer`
  - Fluids, via the `fluids` entrypoint
    - Fluid render layers annotated by `@RenderLayer`
  - Game events, via the `game-events` entrypoint
  - Items, via the `items` entrypoint
    - Item color providers implemented on the item
    - Item color providers annotated with `@ConstantColor`
    - Item color providers annotated with `@ColorProvider`
    - Fuel times annotated with `@Fuel`
    - Compost chances annotated with `@Compost`
  - Potions, via the `potions` entrypoint
  - Recipe serializers, via the `recipe-serializers` entrypoint
  - Recipe types, via the `recipe-types` entrypoint
  - Screen handlers, via the `screen-handlers` entrypoint
    - Handled screens annotated by `@Screen`
  - Sound events, via the `sound-events` entrypoint
  - Stats, via the `stats` entrypoint
    - Stat formatters annotated by `@FormattedAs`
  - Status effects, via the `status-effects` entrypoint

## Using Aleph
As Aleph:Null doesn't need to be called from other nilmods, it can just be dropped in the `nilmods` or `mods` directory
in your Minecraft instance.

The annotations used for Aleph:One can be depended on in a development environment through the Gradle configuration
below or manually included within your project's source.

```groovy
repositories {
  maven {
    url 'https://repo.sleeping.town'
    content {
      //if you already have `repo.sleeping.town` added (which you probably do), add this line!
      includeGroup 'gay.lemmaeof'
    }
  }
}

dependencies {
  implementation 'gay.lemmaeof:aleph:<VERSION>'
}
```

## Why To Use Aleph
...that's a good question.

NilLoader isn't exactly meant for content mods, and was created primarily for patching old Minecraft versions with
difficult tooling. I'm looking at making content mods with it purely to mess around with a new toy. It could
*theoretically* be used to write mods that work across multiple loaders, but I suggest using
[MultiLoader](https://github.com/jaredlll08/MultiLoader-Template) instead if you want to do that. This is *just for fun*,
and should not be treated as a serious development option for modern Minecraft versions.

As this isn't meant to be serious, it is licensed under the
[Fuck Around and Find Out License](https://code.boringcactus.com/fafol/).
