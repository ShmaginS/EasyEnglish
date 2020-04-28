package com.shmagins.superbrain;

public class ImageState {
    public int resource;
    public boolean isOpened;
    public boolean isSelected;

    public ImageState(int resource, boolean isOpened) {
        this.resource = resource;
        this.isOpened = isOpened;
        isSelected = false;
    }
}
