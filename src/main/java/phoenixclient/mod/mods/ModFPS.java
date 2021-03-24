package phoenixclient.mod.mods;

import phoenixclient.gui.hud.ScreenPosition;
import phoenixclient.mod.ModDraggable;

import java.awt.*;

public class ModFPS extends ModDraggable {
    private ScreenPosition pos;

    @Override
    public void save(ScreenPosition pos) {
        this.pos = pos;
    }

    @Override
    public ScreenPosition load() {
        return pos;
    }

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public int getHeight() {
        return fontRenderer.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {
        fontRenderer.drawString("FPS: " + mc.getDebugFps(), pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, Color.WHITE.getRGB());
    }
}
