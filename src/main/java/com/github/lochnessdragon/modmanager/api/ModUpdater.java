package com.github.lochnessdragon.modmanager.api;

import java.net.URL;

import com.terraformersmc.modmenu.util.mod.Mod;

public interface ModUpdater {

	public Mod getMod();
	
	public ModUpdateInfo getUpdateInfo();
	public URL getFile();
	
}
