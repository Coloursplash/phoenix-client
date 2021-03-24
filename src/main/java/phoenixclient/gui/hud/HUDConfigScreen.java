package phoenixclient.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class HUDConfigScreen extends GuiScreen {
    private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<IRenderer, ScreenPosition>();

    private Optional<IRenderer> selectedRenderer = Optional.empty();

    private int prevX, prevY;

    public HUDConfigScreen(HUDManager hudManager) {
        Collection<IRenderer> registeredRenderers = hudManager.getRegisteredRenders();

        for (IRenderer renderer : registeredRenderers) {
            if (!renderer.isEnabled()) {
                continue;
            }

            ScreenPosition pos = renderer.load();

            if (pos == null) {
                pos = ScreenPosition.fromRelativePosition(0.5, 0.5);
            }

            adjustBounds(renderer, pos);
            this.renderers.put(renderer, pos);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();

        final float zBackup = this.zLevel;
        this.zLevel = 200;
        this.drawHollowRect(0, 0, this.width - 1, this.height - 1, Color.RED.getRGB());

        for (IRenderer renderer : renderers.keySet()) {
            ScreenPosition pos = renderers.get(renderer);
            renderer.renderDummy(pos);

            this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), renderer.getWidth(), renderer.getHeight(), Color.CYAN.getRGB());
        }

        this.zLevel = zBackup;
    }

    /**
     * Draws a hollow color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */
    public void drawHollowRect(int x, int y, int width, int height, int color)
    {
        this.drawHorizontalLine(x, x + width, y, color);
        this.drawHorizontalLine(x, x + width, y + height, color);

        this.drawVerticalLine(x, y + height, y, color);
        this.drawVerticalLine(x + width, y + height, y, color);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            renderers.entrySet().forEach((entry) -> {
                entry.getKey().save(entry.getValue());
            });
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (selectedRenderer.isPresent()) {
            moveSelectedRenderBy(mouseX - prevX, mouseY - prevY);
        }

        this.prevX = mouseX;
        this.prevY = mouseY;
    }

    private void moveSelectedRenderBy(int offsetX, int offsetY) {
        IRenderer renderer = selectedRenderer.get();
        ScreenPosition pos = renderers.get(renderer);

        pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY);

        adjustBounds(renderer, pos);
    }

    @Override
    public void onGuiClosed() {for (IRenderer renderer : renderers.keySet()) {renderer.save(renderers.get(renderer));}}

    @Override
    public boolean doesGuiPauseGame() {return true;}

    private void adjustBounds(IRenderer renderer, ScreenPosition pos) {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

        int screenWidth = res.getScaledWidth();
        int screenHeight = res.getScaledHeight();

        int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
        int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));

        pos.setAbsolute(absoluteX, absoluteY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.prevX = mouseX;
        this.prevY = mouseY;
        
        loadMouseOver(mouseX, mouseY);
    }

    private void loadMouseOver(int x, int y) {
        this.selectedRenderer = renderers.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
    }

    private class MouseOverFinder implements Predicate<IRenderer> {
        private int mouseX, mouseY;

        public MouseOverFinder(int x, int y) {
            this.mouseX = x;
            this.mouseY = y;
        }

        @Override
        public boolean test(IRenderer renderer) {
            ScreenPosition pos = renderers.get(renderer);

            int absoluteX = pos.getAbsoluteX();
            int absoluteY = pos.getAbsoluteY();

            if (mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth()) {
                if (mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight()) {
                    return true;
                }
            }

            return false;
        }
    }
}
