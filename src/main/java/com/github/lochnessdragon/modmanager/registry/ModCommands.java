package com.github.lochnessdragon.modmanager.registry;

import com.github.lochnessdragon.modmanager.command.ModManagerCommand;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommands {

	public static void register() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			registerCommands(dispatcher, dedicated);
		});
	}

	@SuppressWarnings("unchecked")
	private static void registerCommands(CommandDispatcher dispatcher, boolean dedicated) {
		LiteralCommandNode<ServerCommandSource> modManagerNode = CommandManager.literal("modmanager").build();
		
		LiteralCommandNode<ServerCommandSource> checkNode = CommandManager.literal("check").executes(ModManagerCommand::check).build();
		
		LiteralCommandNode<ServerCommandSource> updateNode = CommandManager.literal("update").executes(ModManagerCommand::update).build();
		
		dispatcher.getRoot().addChild(modManagerNode);
		modManagerNode.addChild(checkNode);
		modManagerNode.addChild(updateNode);
	}

}
