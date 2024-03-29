package gay.lemmaeof.aleph.nll.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.resource.pack.ResourcePackManager")
public class ResourcePackManagerTransformer extends MiniTransformer {
	@Patch.Method("<init>(Lnet/minecraft/resource/pack/ResourcePackProfile$Factory;[Lnet/minecraft/resource/pack/ResourcePackProvider;)V")
	public void patchPackProviderAddition(PatchContext ctx) {
		ctx.jumpToStart();
		ctx.add(
			ALOAD(2),
			INVOKESTATIC("gay/lemmaeof/aleph/nll/AlephPackProvider", "appendNilPacks", "([Lnet/minecraft/resource/pack/ResourcePackProvider;)[Lnet/minecraft/resource/pack/ResourcePackProvider;"),
			ASTORE(2)
		);
	}
}
