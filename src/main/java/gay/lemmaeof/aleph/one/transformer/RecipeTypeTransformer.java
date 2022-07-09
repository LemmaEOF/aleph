package gay.lemmaeof.aleph.one.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.recipe.RecipeType")
public class RecipeTypeTransformer extends MiniTransformer {
	@Patch.Method("<clinit>()V")
	public void hookRecipeTypes(PatchContext ctx) {
		ctx.jumpToLastReturn();
		ctx.add(
				INVOKESTATIC("gay/lemmaeof/aleph/one/AutoRegistry", "registerRecipeTypes", "()V")
		);
		ctx.addFireEntrypoint("recipe-types");
	}
}
