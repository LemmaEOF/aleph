package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.render.RenderLayers")
public class RenderLayersTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookRenderLayers(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				GETSTATIC("net/minecraft/client/render/RenderLayers", "BLOCKS", "()Lnet/java/util/Map;"),
				INVOKESTATIC("gay/lemmaeof/aleph/ClientAutoRegistry", "registerBlockRenderLayers", "(Lnet/java/util/Map;)V"),
				GETSTATIC("net/minecraft/client/render/RenderLayers", "FLUIDS", "()Lnet/java/util/Map;"),
				INVOKESTATIC("gay/lemmaeof/aleph/ClientAutoRegistry", "registerFluidRenderLayers", "(Lnet/java/util/Map;)V")
		);
	}
}
