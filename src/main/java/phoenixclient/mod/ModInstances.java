package phoenixclient.mod;

import phoenixclient.gui.hud.HUDManager;
import phoenixclient.mod.mods.ModArmorStatus;
import phoenixclient.mod.mods.ModFPS;
import phoenixclient.mod.mods.ModKeystrokes;
import phoenixclient.mod.mods.ModXYZ;

public class ModInstances {
    private static ModArmorStatus modArmorStatus;
    private static ModFPS modFPS;
    private static ModXYZ modXYZ;
    private static ModKeystrokes modKeystrokes;

    public static void register(HUDManager hudManager) {
        modArmorStatus = new ModArmorStatus();
        hudManager.register(modArmorStatus);

        modFPS = new ModFPS();
        hudManager.register(modFPS);

        modXYZ = new ModXYZ();
        hudManager.register(modXYZ);

        modKeystrokes = new ModKeystrokes();
        hudManager.register(modKeystrokes);
    }
}
