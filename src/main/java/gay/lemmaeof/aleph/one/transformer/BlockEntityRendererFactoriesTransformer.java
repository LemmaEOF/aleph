package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.render.block.entity.BlockEntityRendererFactories")
public class BlockEntityRendererFactoriesTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookBlockEntityRenderers(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				GETSTATIC("net/minecraft/client/render/block/entity/BlockEntityRendererFactories", "FACTORIES", "()Ljava/util/Map;"),
				INVOKESTATIC("gay/lemmaeof/aleph/one/ClientAutoRegistry", "registerBlockEntityRenderers", "(Ljava/util/Map;)V")
		);
	}
}
