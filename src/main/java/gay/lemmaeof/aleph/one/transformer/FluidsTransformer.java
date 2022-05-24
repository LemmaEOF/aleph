package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.fluid.Fluids")
public class FluidsTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookFluids(PatchContext ctx) {
		ctx.search(
				GETSTATIC("net/minecraft/util/Registry", "FLUID", "Lnet/minecraft/util/registry/DefaultedRegistry;"),
				INVOKEVIRTUAL("net/minecraft/util/registry/DefaultedRegistry", "iterator", "()Ljava/util/Iterator;")
		).jumpBefore();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/AutoRegistry", "registerFluids", "()V")
		);
		ctx.addFireEntrypoint("fluids");
	}
}
