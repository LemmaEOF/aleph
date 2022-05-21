package gay.lemmaeof.aleph;

import gay.lemmaeof.aleph.transformer.BlocksTransformer;
import gay.lemmaeof.aleph.transformer.ResourcePackManagerTransformer;
import nilloader.api.ClassTransformer;
import nilloader.api.NilLogger;

public class Aleph implements Runnable {

	public static final NilLogger log = NilLogger.get("Aleph");
	
	@Override
	public void run() {
//		ModRemapper.setTargetMapping("default");
		ClassTransformer.register(new BlocksTransformer());
		ClassTransformer.register(new ResourcePackManagerTransformer());
	}
	
}
