package net.minecraft.client.gui;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

import net.minecraft.client.resources.I18n;

// TODO: make this work on touchscreens

public class GuiClientSettings extends GuiScreen {
    private final GuiScreen parentScreen;
    private String title;
    private GameSettings options;

    public GuiClientSettings(GuiScreen parentScreenIn, GameSettings settings) {
        this.parentScreen = parentScreenIn;
        this.options = settings;
    }

    /**
     * +
     * Adds the buttons (and other controls) to the screen in
     * question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        int i = 0;
        this.title = "Client Specific Settings";
        this.buttonList.add(new GuiButton(GameSettings.Options.HIDE_PASSWORD.returnEnumOrdinal(),
                this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20,
                this.options.getKeyBinding(GameSettings.Options.HIDE_PASSWORD)));
        ++i;
        this.buttonList.add(new GuiButton(GameSettings.Options.ENABLE_SOUND.returnEnumOrdinal(),
                this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20,
                this.options.getKeyBinding(GameSettings.Options.ENABLE_SOUND)));
        ++i;
        this.buttonList.add(new GuiButton(GameSettings.Options.DISABLE_ALPHA.returnEnumOrdinal(),
                this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20,
                this.options.getKeyBinding(GameSettings.Options.DISABLE_ALPHA)));
        ++i;
        ++i;
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1),
                I18n.format("gui.done", new Object[0])));
    }

    protected void actionPerformed(GuiButton parGuiButton) {
        if (parGuiButton.enabled) {
            if (parGuiButton.id == 200) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(this.parentScreen);
            }
            if (parGuiButton.id == GameSettings.Options.HIDE_PASSWORD.returnEnumOrdinal()) {
                this.options.setOptionValue(GameSettings.Options.HIDE_PASSWORD, 1);
                parGuiButton.displayString = this.options.getKeyBinding(GameSettings.Options.HIDE_PASSWORD);
            }

            if (parGuiButton.id == GameSettings.Options.ENABLE_SOUND.returnEnumOrdinal()) {
                this.options.setOptionValue(GameSettings.Options.ENABLE_SOUND, 1);
                parGuiButton.displayString = this.options.getKeyBinding(GameSettings.Options.ENABLE_SOUND);
            }

            if (parGuiButton.id == GameSettings.Options.DISABLE_ALPHA.returnEnumOrdinal()) {
                this.options.setOptionValue(GameSettings.Options.DISABLE_ALPHA, 1);
                parGuiButton.displayString = this.options.getKeyBinding(GameSettings.Options.DISABLE_ALPHA);
            }
        }
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
        super.drawScreen(i, j, f);
    }

}
