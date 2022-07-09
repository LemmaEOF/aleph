package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.color.item.ItemColors")
public class ItemColorsTransformer extends MiniTransformer {
	@Patch.Method("create()Lnet/minecraft/client/color/item/ItemColors;")
	public void hookItemColorProviders(PatchContext ctx) {
		ctx.search(
				ALOAD(0),
				RETURN()
		).jumpBefore();
		ctx.add(
				ALOAD(0),
				INVOKESTATIC("gay/lemmaeof/aleph/one/ClientAutoRegistry", "registerItemColorProviders", "(Lnet/minecraft/color/item/ItemColors;)V")
		);
	}
}
