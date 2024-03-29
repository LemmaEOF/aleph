package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.color.block.BlockColors")
public class BlockColorsTransformer extends MiniTransformer {
	@Patch.Method("create()Lnet/minecraft/client/color/block/BlockColors;")
	public void hookBlockColorProviders(PatchContext ctx) {
		ctx.search(
				ALOAD(0),
				ARETURN()
		).jumpBefore();
		ctx.add(
				ALOAD(0),
				INVOKESTATIC("gay/lemmaeof/aleph/one/ClientAutoRegistry", "registerBlockColorProviders", "(Lnet/minecraft/client/color/block/BlockColors;)V")
		);
	}
}
