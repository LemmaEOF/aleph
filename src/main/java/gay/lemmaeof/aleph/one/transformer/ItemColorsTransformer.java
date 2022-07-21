package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.color.item.ItemColors")
public class ItemColorsTransformer extends MiniTransformer {
	@Patch.Method("create(Lnet/minecraft/client/color/block/BlockColors;)Lnet/minecraft/client/color/item/ItemColors;")
	public void hookItemColorProviders(PatchContext ctx) {
		ctx.search(
				ALOAD(1),
				ARETURN()
		).jumpBefore();
		ctx.add(
				ALOAD(1),
				INVOKESTATIC("gay/lemmaeof/aleph/one/ClientAutoRegistry", "registerItemColorProviders", "(Lnet/minecraft/client/color/item/ItemColors;)V")
		);
	}
}
