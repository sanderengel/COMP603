package blackjack;

import java.awt.*;

public class OverlappingCardLayout implements LayoutManager {
    private final int overlap;

    public OverlappingCardLayout(int overlap) {
        this.overlap = overlap; // Set overlap in pixels
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
            width += (d.width - overlap); // Accumulate width with overlap
            height = Math.max(height, d.height); // Keep track of the tallest card
        }

        return new Dimension(width + overlap, height); // Add overlap to the final width to account for the first card
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        int x = 0; // Start at the leftmost position

        // Iterate through the components (cards) in normal order (left to right)
        for (Component comp : parent.getComponents()) {
            Dimension d = comp.getPreferredSize();
            // Set the bounds for the component, overlapping with the previous one
            comp.setBounds(x, 0, d.width, d.height);
            // Move x to the right, but only by (width - overlap), so the cards overlap
            x += (d.width - overlap);
			
			// Bring the most recently added card to the top by setting its Z-order to 0
			parent.setComponentZOrder(comp, 0);
        }
    }

	
//	@Override
//	public void layoutContainer(Container parent) {
//		int totalComponents = parent.getComponentCount();
//		int x = 0; // Start at the leftmost position
//
//		// Iterate through the components (cards) in reverse order (newest card first)
//		for (int i = totalComponents - 1; i >= 0; i--) {
//			Component comp = parent.getComponent(i);
//			Dimension d = comp.getPreferredSize();
//			// Set the bounds for the component, starting from the rightmost and stacking backwards
//			comp.setBounds(x, 0, d.width, d.height);
//			// Move x to the right, but only by (width - overlap), so the cards overlap
//			x += (d.width - overlap);
//		}
//	}
}
