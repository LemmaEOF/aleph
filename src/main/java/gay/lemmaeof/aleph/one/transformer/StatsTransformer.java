package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.stat.Stats")
public class StatsTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookStats(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/one/AutoRegistry", "registerStats", "()V")
		);
		ctx.addFireEntrypoint("stats");
	}
}
