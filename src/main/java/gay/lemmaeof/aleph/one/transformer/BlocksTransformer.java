package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.block.Blocks")
public class BlocksTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookBlocks(PatchContext ctx) {
		ctx.search(
				GETSTATIC("net/minecraft/util/Registry", "BLOCK", "Lnet/minecraft/util/registry/DefaultedRegistry;"),
				INVOKEVIRTUAL("net/minecraft/util/registry/DefaultedRegistry", "iterator", "()Ljava/util/Iterator;")
		).jumpBefore();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/AutoRegistry", "registerBlocks", "()V")
		);
		ctx.addFireEntrypoint("blocks");
	}
}
