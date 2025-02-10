package net.eaglerdevs.modsArgon.stitcher;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.renderer.texture.Stitcher;

public class HolderSlot extends SpriteSlot {
    private final Stitcher.Holder holder;

    public HolderSlot(Stitcher.Holder holder) {
        this.holder = holder;
        width = holder.getWidth();
        height = holder.getHeight();
    }

    public Stitcher.Holder getHolder() {
        return holder;
    }

    @Override
    public List<Stitcher.Slot> getSlots(Rect2D parent) {
        Stitcher.Slot slot = new Stitcher.Slot(x + parent.x, y + parent.y, width, height);
        slot.insertHolder(holder);
        return Collections.singletonList(slot);
    }
}
