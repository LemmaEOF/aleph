package gay.lemmaeof.aleph.nll.transformer;

import java.util.function.Predicate;

import gay.lemmaeof.aleph.nll.AlephPackProvider;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer.Pack;
import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.gui.screen.pack.PackScreen")
public class PackScreenTransformer extends MiniTransformer {

	@Patch.Method("updatePackList(Lnet/minecraft/client/gui/screen/pack/PackListWidget;Ljava/util/stream/Stream;)V")
	public void patchUpdatePackList(PatchContext ctx) {
		ctx.jumpToStart();
		
		ctx.add(
			ALOAD(2),
			NEW("gay/lemmaeof/aleph/nll/transformer/PackScreenTransformer$Filter"),
			DUP(),
			INVOKESPECIAL("gay/lemmaeof/aleph/nll/transformer/PackScreenTransformer$Filter", "<init>", "()V"),
			INVOKEINTERFACE("java/util/stream/Stream", "filter", "(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;"),
			ASTORE(2)
		);
	}
	
	public static class Filter implements Predicate<Pack> {

		@Override
		public boolean test(Pack p) {
			return p.getSource() != AlephPackProvider.SOURCE;
		}
		
	}
	
}
