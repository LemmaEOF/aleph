package com.unascribed.niltest;

import nilloader.api.lib.mini.MiniTransformer;
import nilloader.api.lib.mini.PatchContext;
import nilloader.api.lib.mini.annotation.Patch;

@Patch.Class("net.minecraft.client.Minecraft")
public class MinecraftTransformer extends MiniTransformer {

	@Patch.Method("startGame()V")
	public void patchStartGame(PatchContext ctx) {
		ctx.jumpToStart();
		ctx.addFireEntrypoint("client");
	}

}
