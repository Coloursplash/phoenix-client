package phoenixclient.mod;

import phoenixclient.gui.hud.IRenderer;
import phoenixclient.gui.hud.ScreenPosition;

public abstract class ModDraggable extends Mod implements IRenderer {
    protected final int getLineOffset(ScreenPosition pos, int lineNum) {return pos.getAbsoluteX() + getLineOffset(lineNum);}

    private  int getLineOffset(int lineNum) {return (fontRenderer.FONT_HEIGHT + 3) * lineNum;}
}
