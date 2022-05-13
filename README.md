# Aleph
*~the API lib that should have never been~*

Aleph is an "invisible" library mod for [NilLoader](https://git.sleeping.town/unascribed/NilLoader) targeting primarily
modern Minecraft versions.

Current Aleph features:
- **Resource pack loading** - Any nilmod (either as a jar or directory) will have its `resources/assets` and
`resources/data` source folders loaded as resource or data packs, respectively

## Using Aleph
As Aleph doesn't need to be called from other nilmods, it can just be dropped in the `nilmods` or `mods` directory in
your Minecraft instance.

## Why To Use Aleph
...that's a good question.

NilLoader isn't exactly meant for content mods, and was created primarily for patching old Minecraft versions with
difficult tooling. I'm looking at making content mods with it purely to mess around with a new toy. It could
*theoretically* be used to write mods that work across multiple loaders, but I suggest using
[LoaderComplex](https://github.com/ENDERZOMBI102/LoaderComplex) instead if you want to do that. This is *just for fun*,
and should not be treated as a serious development option for modern Minecraft versions.

As this isn't meant to be serious, it is licensed under the
[Fuck Around and Find Out License](https://code.boringcactus.com/fafol/).