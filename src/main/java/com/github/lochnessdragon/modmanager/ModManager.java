package com.github.lochnessdragon.modmanager;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.lochnessdragon.modmanager.api.ModUpdateInfo;
import com.github.lochnessdragon.modmanager.api.ModUpdater;
import com.github.lochnessdragon.modmanager.modrinth.ModrinthUpdater;
import com.github.lochnessdragon.modmanager.registry.ModCommands;
import com.terraformersmc.modmenu.util.mod.Mod;
import com.terraformersmc.modmenu.util.mod.fabric.FabricMod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

public class ModManager implements ModInitializer {

	public static Logger LOGGER = LogManager.getLogger();
	public static ModManager INSTANCE;

	private Map<String, Mod> mods = new HashMap<>();

	private Map<String, ModUpdater> outOfDateMods = new HashMap<>();

	public ModManager() {
		INSTANCE = this;
	}
	
	@Override
	public void onInitialize() {
		LOGGER.info("Mod Manager Initalizing");

		updateModList();

		checkUpdates();
		
		ModCommands.register();
	}
	
	public Map<String, Mod> updateModList() {
		mods.clear();
		for (ModContainer modContainer : FabricLoader.INSTANCE.getAllMods()) {
			mods.put(modContainer.getMetadata().getId(), new FabricMod(modContainer));
		}
		
		return mods;
	}
	
	public Map<String, Mod> getMods() {
		return mods;
	}
	
	public Map<String, ModUpdater> getOutOfDateMods() {
		return outOfDateMods;
	}

	public Map<String, ModUpdater> checkUpdates() {
		outOfDateMods.clear();
		
		for (Mod mod : mods.values()) {
			// Check if mods are out of date
			String id = mod.getId();
			String version = mod.getVersion();
			
			//LOGGER.info("Mod ID: {} Version: {}", id, version);

			ModrinthUpdater updater = new ModrinthUpdater(mod);
			ModUpdateInfo info = updater.getUpdateInfo();
			
			if(info != null) {
				outOfDateMods.put(id, updater);
			}
		}
		
		return outOfDateMods;
	}
	
	public void update() {
		
	}
}
