package com.unascribed.niltest;

import nilloader.api.ClassTransformer;
import nilloader.api.NilLogger;

public class NilTest implements Runnable {

	public static final NilLogger log = NilLogger.get("NilTest");
	
	@Override
	public void run() {
//		ModRemapper.setTargetMapping("default");
		ClassTransformer.register(new MinecraftTransformer());
	}
	
}
