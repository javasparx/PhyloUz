package org.phylowidget.render;

import org.phylowidget.PhyloWidget;
import org.phylowidget.tree.PhyloNode;
import processing.core.PGraphics;

import java.util.List;

/**
 * An unrooted radial layout based on the equal-angle algorithm.
 *
 * @author Greg
 */
public class LayoutUnrooted extends LayoutBase {
    static final float STARTING_ANGLE = (float) -Math.PI / 2;

    @Override
    public void layoutImpl() {
        float angle = STARTING_ANGLE + PhyloWidget.cfg.layoutAngle / 360f * (float) Math.PI * 2f;
        layoutNode((PhyloNode) tree.getRoot(), angle, angle + 2 * Math.PI);
    }

    @Override
    public void drawLine(PGraphics canvas, PhyloNode p, PhyloNode c) {
        canvas.line(c.getX(), c.getY(), p.getX(), p.getY());
    }

    void layoutNode(PhyloNode n, double loAngle, double hiAngle) {
        if (tree.isRoot(n)) // Set the root to (0,0).
        {
            setPosition(n, 0, 0);
        }
        if (tree.isLeaf(n)) // Leaves need no more laying out!
            return;

        float numEnclosed = tree.getNumEnclosedLeaves(n); // Total enclosed leaves for this node.
        double curX = n.getLayoutX();
        double curY = n.getLayoutY();

        List<PhyloNode> children = tree.getChildrenOf(n);
        double curAngle = loAngle;
        for (int i = 0; i < children.size(); i++) {
            PhyloNode child = children.get(i);

            // Get the % of leaves under this child.
            float childEnclosed = tree.getNumEnclosedLeaves(child);
            double childRatio = childEnclosed / numEnclosed;
            double arcSize = childRatio * (hiAngle - loAngle);

            // Place this child in the middle of its given arc.
            double length = child.getBranchLength() * 10;
            double midAngle = curAngle + arcSize / 2;

            double newX = curX + Math.cos(midAngle) * length;
            double newY = curY + Math.sin(midAngle) * length;

            setPosition(child, (float) newX, (float) newY);
            setAngle(child, (float) midAngle);

            layoutNode(child, curAngle, curAngle + arcSize);

            curAngle += arcSize;
        }
    }


}
