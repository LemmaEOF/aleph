package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.gui.screen.ingame.HandledScreens")
public class HandledScreensTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookHandledScreens(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/ClientAutoRegistry", "registerHandledScreens", "()V")
		);
	}
}
