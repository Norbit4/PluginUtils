package pl.norbit.pluginutils.gui;

public enum GuiSlots {
    _9(9),
    _18(18),
    _27(27),
    _36(36),
    _45(45),
    _54(54);

    private final int size;

    GuiSlots(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
