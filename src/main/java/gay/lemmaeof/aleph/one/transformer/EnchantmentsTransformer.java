package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.enchantment.Enchantments")
public class EnchantmentsTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookEnchantments(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/AutoRegistry", "registerEnchantments", "()V")
		);
		ctx.addFireEntrypoint("enchantments");
	}
}
