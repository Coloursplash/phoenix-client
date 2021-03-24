package phoenixclient;

import net.minecraft.client.Minecraft;
import phoenixclient.event.EventManager;
import phoenixclient.event.EventTarget;
import phoenixclient.event.events.ClientTickEvent;
import phoenixclient.gui.hud.HUDManager;
import phoenixclient.mod.ModInstances;

public class Client {
    private static final float version = 0.1f;

    private static final Client INSTANCE = new Client();
    public static final Client getInstance() {return INSTANCE;}

    private DiscordRP discordRP = new DiscordRP();

    private HUDManager hudManager;

    /** Runs on client initialization */
    public void init() {
        discordRP.start();
        EventManager.register(this);
    }

    public void start() {
        hudManager = HUDManager.getInstance();
        ModInstances.register(hudManager);
    }

    public void shutdown() {
        discordRP.shutdown();
    }

    public static float getVersion() {return version;}

    public DiscordRP getDiscordRP() {return discordRP;}

    @EventTarget
    public void onTick(ClientTickEvent e) {
        if (Minecraft.getMinecraft().gameSettings.keyBindModMenu.isPressed()) {
            hudManager.openConfigScreen();
        }
    }
}
