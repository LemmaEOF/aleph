package com.unascribed.niltest;

import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityCow;

public class NilTestClient implements Runnable {

	@Override
	public void run() {
		NilTest.log.info("Hello, Nil World! {}", Arrays.toString(EntityCow.pickUpLootProability));
		NilTest.log.info("Minecraft: {}", Minecraft.getMinecraft());
		NilTest.log.info("Profiler: {}", Minecraft.getMinecraft().mcProfiler);
	}

}
