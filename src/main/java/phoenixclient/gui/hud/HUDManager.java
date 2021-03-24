package phoenixclient.gui.hud;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import phoenixclient.event.EventManager;
import phoenixclient.event.EventTarget;
import phoenixclient.event.events.RenderEvent;

import java.util.Collection;
import java.util.Set;

public class HUDManager {
    private HUDManager() {}

    private static HUDManager instance = null;

    public static HUDManager getInstance() {
        if (instance != null) {return instance;}

        instance = new HUDManager();
        EventManager.register(instance);

        return instance;
    }

    private Set<IRenderer> registeredRenders = Sets.newHashSet();
    private Minecraft mc = Minecraft.getMinecraft();

    public void register(IRenderer... renderers) {
        for (IRenderer render : renderers) {
            this.registeredRenders.add(render);
        }
    }

    public void unregister(IRenderer... renderers) {
        for (IRenderer render : renderers) {
            this.registeredRenders.remove(render);
        }
    }

    public Collection<IRenderer> getRegisteredRenders() {
        return Sets.newHashSet(registeredRenders);
    }

    public void openConfigScreen() {
        mc.displayGuiScreen(new HUDConfigScreen(this));
    }

    @EventTarget
    public void onRender(RenderEvent e) {
        if (mc.currentScreen == null || mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof GuiChat) {
            for(IRenderer renderer : registeredRenders) {
                callRenderer(renderer);
            }
        }
    }

    private void callRenderer(IRenderer renderer) {
        if (!renderer.isEnabled()) {
            return;
        }

        ScreenPosition pos = renderer.load();

        if (pos == null) {
            pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }

        renderer.render(pos);
    }
}