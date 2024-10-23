package blackjack;

import java.awt.*;
/**
 *
 * @author sanderengelthilo
 */
public class OverlappingCardLayout implements LayoutManager {
    private final int overlap;

    public OverlappingCardLayout() {
        this.overlap = 30; // Set overlap percentage
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {}

    @Override
    public void removeLayoutComponent(Component comp) {}

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int width = 0;
        int height = 0;

        for (Component comp : parent.getComponents()) {
            Dimension d = comp.getPreferredSize();
            width += (d.width - overlap); // Overlap width for each card
            height = Math.max(height, d.height); // Max height
        }

        return new Dimension(width, height);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        int x = 0; // Start from the leftmost position

        for (Component comp : parent.getComponents()) {
            Dimension d = comp.getPreferredSize();
            comp.setBounds(x, 0, d.width, d.height);
            x += (d.width - overlap); // Move x by the width minus overlap
        }
    }
}

