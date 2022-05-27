package gay.lemmaeof.aleph.nll.transformer;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.ClientBrandRetriever")
public class ClientBrandRetrieverTransformer extends MiniTransformer {

	@Patch.Method("getClientModName()Ljava/lang/String;")
	public void patchGetClientModName(PatchContext ctx) {
		ctx.search(LDC("vanilla")).jumpAfter();
		ctx.add(
			POP(),
			LDC("nil")
		);
	}
	
}
