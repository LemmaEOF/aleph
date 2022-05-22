package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.item.Items")
public class ItemsTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookItems(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/AutoRegistry", "registerItems", "()V")
		);
		ctx.addFireEntrypoint("items");
	}
}
