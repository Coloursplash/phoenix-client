package phoenixclient.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import phoenixclient.Client;
import phoenixclient.event.EventManager;

public class Mod {
    private boolean isEnabled = true;

    protected final Minecraft mc;
    protected final FontRenderer fontRenderer;
    protected final Client client;

    public Mod() {
        this.mc = Minecraft.getMinecraft();
        this.fontRenderer = this.mc.fontRendererObj;
        this.client = Client.getInstance();

        setEnabled(isEnabled);
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;

        if (isEnabled) {EventManager.register(this);} else {EventManager.unregister(this);}
    }

    public boolean isEnabled() {return this.isEnabled;}
}