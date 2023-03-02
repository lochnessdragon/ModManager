import net.fabricmc.api.ModInitializer;

public class ModManagerFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		ModManagerFabricLike.init();
	}
}
