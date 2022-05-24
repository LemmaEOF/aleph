package gay.lemmaeof.aleph;

import gay.lemmaeof.aleph.nll.transformer.ResourcePackManagerTransformer;
import gay.lemmaeof.aleph.one.transformer.*;
import nilloader.api.ClassTransformer;
import nilloader.api.NilLogger;

public class Aleph implements Runnable {

	public static final NilLogger log = NilLogger.get("Aleph");

	@Override
	public void run() {
		//Aleph:Null transformers
		ClassTransformer.register(new ResourcePackManagerTransformer());

		//Aleph:One transformers
		ClassTransformer.register(new BlockColorsTransformer());
		ClassTransformer.register(new BlockEntityRendererFactoriesTransformer());
		ClassTransformer.register(new BlockEntityTypeTransformer());
		ClassTransformer.register(new BlocksTransformer());
		ClassTransformer.register(new EnchantmentsTransformer());
		ClassTransformer.register(new EntityRenderersTransformer());
		ClassTransformer.register(new EntityTypeTransformer());
		ClassTransformer.register(new FluidsTransformer());
		ClassTransformer.register(new ItemColorsTransformer());
		ClassTransformer.register(new ItemsTransformer());
		ClassTransformer.register(new PotionsTransformer());
		ClassTransformer.register(new RenderLayersTransformer());
		ClassTransformer.register(new SoundEventsTransformer());
		ClassTransformer.register(new StatusEffectsTransformer());
	}
	
}
