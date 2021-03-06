/**************************************************************************
 * Copyright (c) 2007, 2008 Gregory Jordan
 *
 * This file is part of PhyloWidget.
 *
 * PhyloWidget is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * PhyloWidget is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PhyloWidget.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.phylowidget.render;

import org.andrewberman.ui.Point;
import org.andrewberman.ui.TextField;
import org.phylowidget.tree.PhyloNode;
import org.phylowidget.tree.RootedTree;
import processing.core.PGraphics;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public interface TreeRenderer {
    public static final int NODE = 0;
    public static final int LABEL = 1;

    public void render(PGraphics canvas, float x, float y, float w, float h, boolean mainRender);

//	public UIRectangle getVisibleRect();

    public void layoutTrigger();

    public void setTree(RootedTree t);

    public void setLayout(LayoutBase layout);

    public RootedTree getTree();

    public void dispose();

    public void setMouseLocation(Point pt);

    public void nodesInRange(ArrayList list, Rectangle2D.Float rect);

//	public Object rangeForNode(Object node);

//	public float getRowHeight();

    public float getNodeRadius();

    public float getTextSize();

    public void positionText(PhyloNode node, TextField text);
}
