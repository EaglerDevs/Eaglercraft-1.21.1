package net.eaglerdevs.modsArgon.stitcher.packing2d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.eaglerdevs.modsArgon.stitcher.Rect2D;

public abstract class Packer<T extends Rect2D> {
    final int stripWidth;
    final List<T> rectangles;

    Packer(int stripWidth, List<T> rectangles) {
        this.stripWidth = stripWidth;
        this.rectangles = rectangles;
    }

    public static <U extends Rect2D> List<U> pack(List<U> rectangles, Algorithm algorithm, int stripWidth) {
        Packer<U> packer;
        switch (algorithm) {
            case FIRST_FIT_DECREASING_HEIGHT:
                packer = new PackerFFDH<>(stripWidth, rectangles);
                return packer.pack();
            case BEST_FIT_DECREASING_HEIGHT:
                packer = new PackerBFDH<>(stripWidth, rectangles);
                break;
            default:
                return new ArrayList<>();
        }
        return packer.pack();
    }

    public abstract List<T> pack();

    void sortByNonIncreasingHeight(List<T> rectangles) {
        rectangles.sort(Comparator.<T>comparingInt((rect) -> rect.height).reversed());
    }
}
