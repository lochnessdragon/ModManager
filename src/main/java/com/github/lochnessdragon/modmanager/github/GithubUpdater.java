package com.github.lochnessdragon.modmanager.github;

import java.net.URL;

import com.github.lochnessdragon.modmanager.api.AbstractModUpdater;
import com.github.lochnessdragon.modmanager.api.ModUpdateInfo;
import com.terraformersmc.modmenu.util.mod.Mod;

public class GithubUpdater extends AbstractModUpdater {

	Mod mod;
	
	public GithubUpdater(Mod mod) {
		this.mod = mod;
	}
	
	@Override
	public Mod getMod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModUpdateInfo getUpdateInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getFile() {
		// TODO Auto-generated method stub
		return null;
	}

}
