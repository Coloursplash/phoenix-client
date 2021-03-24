package phoenixclient.mod.mods;

import phoenixclient.gui.hud.ScreenPosition;
import phoenixclient.mod.ModDraggable;

public class ModCPS extends ModDraggable {
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
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void render(ScreenPosition pos) {

    }
}
