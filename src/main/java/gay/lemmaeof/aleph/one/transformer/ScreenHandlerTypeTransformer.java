package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.screen.ScreenHandlerType")
public class ScreenHandlerTypeTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookScreenHandlers(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/AutoRegistry", "registerScreenHandlers", "()V")
		);
		ctx.addFireEntrypoint("screen-handlers");
	}
}
