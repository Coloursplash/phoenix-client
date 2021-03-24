package phoenixclient.mod.mods;

import phoenixclient.gui.hud.ScreenPosition;
import phoenixclient.mod.ModDraggable;

import java.awt.*;

public class ModXYZ extends ModDraggable {
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
        return fontRenderer.getStringWidth(getXYZString());
    }

    @Override
    public int getHeight() {
        return fontRenderer.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {
        fontRenderer.drawString(getXYZString(), pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, Color.WHITE.getRGB());
    }

    private String getXYZString() {
        return String.format("XYZ: %.2f / %.2f/ %.2f", mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
    }
}
