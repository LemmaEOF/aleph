package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.block.entity.BlockEntityType")
public class BlockEntityTypeTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookBlockEntityTypes(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/one/AutoRegistry", "registerBlockEntities", "()V")
		);
		ctx.addFireEntrypoint("blockentitites");
	}
}
