package gay.lemmaeof.aleph;

import nilloader.api.ClassTransformer;
import nilloader.api.NilLogger;

public class Aleph implements Runnable {

	public static final NilLogger log = NilLogger.get("Aleph");
	
	@Override
	public void run() {
//		ModRemapper.setTargetMapping("default");
		ClassTransformer.register(new ResourcePackManagerTransformer());
	}
	
}
