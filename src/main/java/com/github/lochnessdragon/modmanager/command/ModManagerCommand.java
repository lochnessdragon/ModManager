package com.github.lochnessdragon.modmanager.command;

import java.util.Map;

import com.github.lochnessdragon.modmanager.ModManager;
import com.github.lochnessdragon.modmanager.api.ModUpdater;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class ModManagerCommand {
	public static int check(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ModManager modManager = ModManager.INSTANCE;
		modManager.updateModList();
		
		Map<String, ModUpdater> outOfDate = modManager.checkUpdates();
		
		if(!outOfDate.isEmpty()) {
			MutableText text = new LiteralText("Found " + outOfDate.size() + " mod(s) to be updated: \n").formatted(Formatting.AQUA);
			
			for(ModUpdater updater : outOfDate.values()) {
				MutableText line = new LiteralText("  - " + updater.getUpdateInfo().id + " to version: " + updater.getUpdateInfo().newVersion.getFriendlyString() + "\n").formatted(Formatting.WHITE);
				
				text.append(line);
			}
			
			context.getSource().sendFeedback(text, true);
		}
		
		return 1;
	}
	
	public static int update(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		ModManager modManager = ModManager.INSTANCE;
		check(context);
		
		context.getSource().sendFeedback(new LiteralText("Updating mods. (WILL REQUIRE A RESTART)").formatted(Formatting.RED), true);
		modManager.update();
		
		return 1;
	}
}
