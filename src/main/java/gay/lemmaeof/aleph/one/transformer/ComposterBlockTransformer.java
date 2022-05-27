package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.block.ComposterBlock")
public class ComposterBlockTransformer extends MiniTransformer {
	@Patch.Method("registerDefaultCompostableItems()V")
	public void hookCompost(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				GETSTATIC("net/minecraft/block/ComposterBlock", "ITEM_TO_LEVEL_INCREASE_CHANCE", "()Lit/unimi/dsi/fastutil/objects/Object2FloatMap;"),
				INVOKESTATIC("gay/lemmaeof/aleph/AutoRegistry", "registerItemComposts", "(Lit/unimi/dsi/fastutil/objects/Object2FloatMap;)V")
		);
	}
}
