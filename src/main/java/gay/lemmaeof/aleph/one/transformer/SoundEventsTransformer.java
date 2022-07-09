package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.sound.SoundEvents")
public class SoundEventsTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookSoundEvents(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/one/AutoRegistry", "registerSoundEvents", "()V")
		);
		ctx.addFireEntrypoint("sound-events");
	}
}
