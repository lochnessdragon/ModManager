package com.github.lochnessdragon.modmanager.modrinth;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.github.lochnessdragon.modmanager.ModManager;
import com.github.lochnessdragon.modmanager.api.AbstractModUpdater;
import com.github.lochnessdragon.modmanager.api.ModUpdateInfo;
import com.github.lochnessdragon.modmanager.rest.RestClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.terraformersmc.modmenu.util.mod.Mod;

import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.VersionParsingException;

public class ModrinthUpdater extends AbstractModUpdater {

	public static String MODRINTH_ENDPOINT = "https://api.modrinth.com/";
	Mod mod;
	ModUpdateInfo info;

	public ModrinthUpdater(Mod mod) {
		this.mod = mod;
		updateInfo();
	}

	@Override
	public ModUpdateInfo getUpdateInfo() {
		if(info == null) {
			updateInfo();
		}
		return info;
	}
	
	public void updateInfo() {
		String id = mod.getId();
		String version = mod.getVersion();

		List<String> versions = getAllVersions(id);

		try {
			SemanticVersion latestVersion = SemanticVersion.parse(mod.getVersion());
			for (String potentialVersion : versions) {
				latestVersion = checkVersion(latestVersion, potentialVersion);
			}

			if (latestVersion.compareTo(SemanticVersion.parse(mod.getVersion())) > 0) {
				ModManager.LOGGER.info("Found a new version for mod: {}, version: {}", id,
						latestVersion.getFriendlyString());
				this.info = new ModUpdateInfo(SemanticVersion.parse(version), latestVersion, id, "update_url");
				return;
			}
		} catch (NumberFormatException | VersionParsingException e) {
		}	catch (Exception e) {
			e.printStackTrace();
		}
		this.info = null;
	}

	private SemanticVersion checkVersion(SemanticVersion latest, String potential) {
		RestClient client = new RestClient(MODRINTH_ENDPOINT);

		JsonObject versionInfo = client.request("api/v1/version/" + potential);

		String versionNumber = versionInfo.get("version_number").getAsString();
		SemanticVersion checkVersion = null;
		try {
			checkVersion = SemanticVersion.parse(versionNumber);
		} catch (VersionParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (checkVersion != null) {
			if (checkVersion.compareTo(latest) > 0) {
				return checkVersion;
			}
		}

		return latest;
	}

	private List<String> getAllVersions(String id) {
		RestClient client = new RestClient(MODRINTH_ENDPOINT);

		JsonObject info = client.request("api/v1/mod/" + id);
		if (info.has("versions")) {
			JsonArray jsonVersions = info.getAsJsonArray("versions");

			List<String> versions = new ArrayList<String>();
			for (int i = 0; i < jsonVersions.size(); i++) {
				versions.add(jsonVersions.get(i).getAsString());
			}

			return versions;
		} else {
			return new ArrayList<String>();
		}
	}

	public void updateMod() {
		if (info == null) {
			updateInfo();
		}
	}

	@Override
	public Mod getMod() {
		// TODO Auto-generated method stub
		return this.mod;
	}

	@Override
	public URL getFile() {
		updateMod();
		return null;
	}

}
