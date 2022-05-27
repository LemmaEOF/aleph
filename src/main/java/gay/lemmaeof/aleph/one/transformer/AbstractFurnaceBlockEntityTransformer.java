package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.block.entity.AbstractFurnaceBlockEntity")
public class AbstractFurnaceBlockEntityTransformer extends MiniTransformer {
	@Patch.Method("createFuelTimeMap()Ljava/util/Map;")
	public void hookFuelTimes(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
			ALOAD(0),
			INVOKESTATIC("gay/lemmaeof/aleph/one/AutoRegistry", "registerItemFuels", "(Ljava/util/Map;)V")
		);
	}
}
