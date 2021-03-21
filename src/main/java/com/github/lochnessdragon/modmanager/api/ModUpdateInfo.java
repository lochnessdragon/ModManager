package com.github.lochnessdragon.modmanager.api;

import net.fabricmc.loader.api.SemanticVersion;

public class ModUpdateInfo {
	
	public SemanticVersion version;
	public SemanticVersion newVersion;
	public String id;
	public String updateURL;
	
	public ModUpdateInfo(SemanticVersion oldVersion, SemanticVersion newVersion, String id, String updateURL) {
		super();
		this.version = oldVersion;
		this.newVersion = newVersion;
		this.id = id;
		this.updateURL = updateURL;
	}
}
