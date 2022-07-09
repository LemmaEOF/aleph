package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.entity.effect.StatusEffects")
public class StatusEffectsTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookStatusEffects(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/one/AutoRegistry", "registerStatusEffects", "()V")
		);
		ctx.addFireEntrypoint("status-effects");
	}
}
