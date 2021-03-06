package org.phylowidget.ui;

import org.andrewberman.ui.Color;
import org.andrewberman.ui.unsorted.MethodAndFieldSetter;
import org.phylowidget.PhyloWidget;
import org.phylowidget.tree.RootedTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class PhyloConfig {

	/*
     * URL API CONFIGURATION
	 * 
	 * If you are running a database that works with phylogenetic information, the easiest way to "integrate"
	 * PhyloWidget with your service is to create "View in PhyloWidget" links using PhyloWidget's simple URL-based
	 * API. This allows you to customize the look, feel, and functionality of PhyloWidget without having to host
	 * the applet on your own site.
	 * 
	 * The API is simple: just choose from one of the configuration parameters listed below, and call the 
	 * PhyloWidget URL with the desired value appended to the end, as you would any normal GET parameters.
	 * The applet will then read in the configuration parameters and modify its settings accordingly. 
	 * 
	 * Some examples:
	 * - Set the tree that PhyloWidget shows upon startup.
	 *		http://www.phylowidget.org/lite/index.html?tree="(a,(b,c));"
	 * - Set the foreground color, using (R,G,B) format
	 *		http://www.phylowidget.org/lite/index.html?foreground="(255,0,0);"
	 * - Start up PhyloWidget with a preset tree and search string.
	 *		http://www.phylowidget.org/lite/index.html?tree="(a,(b,c));"&search="a"
	 * 
	 * The configurable parameters are displayed below, generally in order from most to least useful. Enjoy!
	 */

    public boolean debug = false;

    /*
     * If you specify a valid URL pointing to a properties file, then PhyloWidget will attempt to load the properties remotely.
     * The format for the properties file is the standard .properties style, with a property name followed by an "=", followed
     * by the property value.
     *
     * ### Example.properties ###
     * tree="Homo sapiens"
     * search="sapiens"
     * renderer="Rectangular"
     * nodeSize=5.0
     */
    public String remoteConfig = "";

    /*
     * Set the starting tree.
     */
    public String tree = DEFAULT_TREE;
    /*
     * Set the starting search string.
     */
    public String search = "";
    /*
     * Set the starting layout type.
     *
     * Possible values: Rectangular, Diagonal, Circular, and Unrooted.
     */
    public String layout = "Rectangular";

    /*
     * Choose the preset XML menu files which PhyloWidget will load.
     *
     * You can load up multiple XML menu files by giving a semicolon-delimited list. See the default
     * value for an example of this.
     *
     * You may also simply let this string be the XML data which you want to load. This is useful for
     * demonstration purposes, letting the user edit and change the menu structure in real-time.
     *
     * Core menu definitions:
     *   - "context.xml" 			The context menu which appears when you click a node.
     *   - "dock.xml" 				The dock, which holds the arrow, pan, and zoom tools.
     *   - "toolbar.xml" 			The toolbar, which sits at the top of the screen and contains
     *   							the menu items and search bar.
     *   - "none.xml" 				No menus at all.
     *
     * Some useful variants:
     *   - "toolbar-onlysearch.xml" 	A toolbar with only the search command, nothing else.
     *   - "dock-onlynav.xml" 			A dock with only the pan and zoom settings, no arrow.
     *   - "context-linkout.xml" 		A context menu which lets you link out to other sites.
     *   								See the XML file for more information.
     *   - "dock-hidden.xml"			A hidden dock. Useful for providing the dock's functionality without
     *   								cluttering up the screen. Users can still use the keyboard shortcuts
     *   								to switch between tools.
     *   - "toolbar-hidden.xml"			Same idea as above, but with the toolbar.
     */
    public String menus = "uz.xml;";

    /* Colors: You can modify the foreground and background colors which PhyloWidget uses.
     * The new value should be formatted as below; a triplet of integer RGB values enclosed in parentheses.
     */
    public String backgroundColor = "(255,255,255)";
    public String textColor = "(0,0,0)";
    public String nodeColor = "(0,0,0)";
    public String branchColor = "(0,0,0)";
    public String alignmentColor = "(140,190,50)";

    /*
     * Node shapes -- usable values are:
     *   - "square"
     *   - "circle"
     *   - "triangle"
     *   - "x" (star shape)
     */
    public String nodeShape = "square";

    /*
     * How to handle the angles of rotated nodes. usable values:
     *   "none" -- keep rotated nodes at all angles (VERY SLOW with large trees)
     *   "quantize" -- Quantize the rotation in 45-degree increments (a little faster...)
     *   "level" -- Level all angles to horizontal (much faster!)
     */
    public String angleHandling = "level";

    //The following parameters can be set using any numerical value, e.g. "textRotation = 0.25"
    public float textRotation = 0f; // Text rotation, in degrees.
    public float textScaling = .8f; // Text scaling, where a value of 1.0 is normal size.
    public float imageSize = 0.95f; // Image scaling, where a value of 1.0 is normal size.
    public float lineWidth = 1f; // Line width. 0 is minimum, 1 is a pretty normal size.
    //    10 is as high as you'll want to go.
    public float nodeSize = 2f; // Node size. Same range as line width: 0 to 10 is reasonable.
    public float innerNodeRatio = 1f; // Ratio between the size of the inner (non-leaf) nodes and the outer (leaf) nodes. Default 1.
    public float renderThreshold = 200f; // Maximum number of nodes to render per frame.
    public float minTextSize = 10; // Minimum text size for leaf node labels.
    //	public float branchLengthScaling = 1f; 			// DEPRECATED.
    public float branchScaling = 1f; // Only used with the Cladogram renderer... scales the width.
    public float cigarScaling = 10f; // How wide should 1bp of cigar line be, relative to the row height
    public float layoutAngle = 0; // The starting angle for the layout (only applicable for circular and unrooted layouts)
    public float animationFrames = 30; // The number of frames it should take nodes to animate to a new destination. (30 frames ~ 1 sec)
    public float viewportX = 0; // The x position of the viewport.
    public float viewportY = 0; // Ditto.
    public float viewportZoom = 0.8f; // I'll bet you can guess this one.

    public boolean showScaleBar = false; // Show a scale bar when showing branch lengths.
    public boolean showCladeLabels = false; // Should we show labels of non-leaf nodes?
    //	public boolean stretchToFit = false;			// DEPRECATED.
    public boolean useBranchLengths = false; // Should the renderer display the tree using the branch length information?
    public boolean showAllLabels = false; // Should the renderer show all labels? This OVERRIDES the minTextSize setting,
    //    so that labels are shown no matter how small they must be displayed.
    public boolean hideAllLabels = false; // Set to TRUE to hide all labels from being drawn. OVERRIDES the showAllLabels setting.
    public boolean showAllLeafNodes = false; // Should we always draw the NODES of all LEAF nodes?
    public boolean treatNodesAsLabels = false;
    public boolean prioritizeDistantLabels = false; // This controls how PhyloWidget prioritizes the display of certain nodes above others.
    //    If set to "true", then PhyloWidget will first display the nodes that are *farthest* from
    //    the root, instead of those that are closest (in terms of # of branches to the root).
    public boolean alignLabels = false; // When drawing with branch lengths, whether all labels should be aligned.
    public boolean useDoubleBuffering = true; // To be honest you probably don't want to mess with this one -- the double buffering really helps!
    public boolean antialias = false; // When set to true this slows down the rendering significantly, but looks much better.
    public boolean outputAllInnerNodes = false; // Kind of a strange one: if set to true, PhyloWidget will *always* output
    //    the labels of non-leaf nodes. Sometimes these are just stupid-looking numbers.
    public boolean enforceUniqueLabels = false; // Enforce uniqueness of node labels.
    public boolean outputFullSizeImages = false; // Output images in the tree at full size, instead of thumbnail (may require LOTS of memory!!)
    public boolean useAnimations = true; // Use animated transitions?
    public boolean animateNewTree = false; // Try to animate between the current tree and new tree? (EXPERIMENTAL IF SET TO TRUE)

    public boolean suppressMessages = false;
    public boolean colorHoveredBranch = true;
    public boolean respondToMouseWheel = true;

    public boolean ignoreAnnotations = false; // ANNOTATIONS: Set to true if you want to globally disable PhyloWidget's display and output of NHX annotations.
    public boolean showBootstrapValues = false; // ANNOTATIONS: Should we show NHX-annotated bootstrap values if they exist?
    public boolean colorSpecies = true; // ANNOTATIONS: Should we assign colors to different leaf nodes NHX-annotated with a given species or taxon?
    public boolean colorDuplications = true; // ANNOTATIONS: Same idea as the others, but for the node duplication coloring.
    public boolean colorBootstrap = true; // ANNOTATIONS: ditto for bootstrap values.

	/*
     *
	 * END: URL API Configuration
	 * 
	 * The rest is all just code involved in making these configuration parameters work properly...
	 */

    public PhyloConfig() {
        super();
        //		setRemoteConfig("http://www.phylowidget.org/nhx_test/ensembl.properties");
    }

    /*
     * COLOR CRAP
     */
    private Color backgroundC = Color.parseColor(backgroundColor);

    public void setBackgroundColor(String s) {
        backgroundC = Color.parseColor(s);
        backgroundColor = s;
    }

    public Color getBackgroundColor() {
        return Color.parseColor(backgroundColor);
    }

    private Color textC = Color.parseColor(textColor);

    public void setTextColor(String s) {
        textC = Color.parseColor(s);
        textColor = s;
    }

    public Color getTextColor() {
        if (textC != null)
            return textC;
        else
            return Color.parseColor(textColor);
    }

    private Color nodeC = Color.parseColor(nodeColor);

    public void setNodeColor(String s) {
        nodeC = Color.parseColor(s);
        nodeColor = s;
    }

    public Color getNodeColor() {
        if (nodeC != null)
            return nodeC;
        else
            return Color.parseColor(nodeColor);
    }

    private Color branchC = Color.parseColor(branchColor);

    public void setBranchColor(String s) {
        branchC = Color.parseColor(s);
        branchColor = s;
    }

    public Color getBranchColor() {
        if (branchC != null)
            return branchC;
        else
            return Color.parseColor(branchColor);
    }

    private Color alignmentC = Color.parseColor(alignmentColor);

    public void setAlignmentColor(String s) {
        alignmentC = Color.parseColor(s);
        alignmentColor = s;
    }

    public Color getAlignmentColor() {
        if (alignmentC != null)
            return alignmentC;
        else
            return Color.parseColor(alignmentColor);
    }

	/*
     * END COLOR CRAP
	 */

    public void setRespondToMouseWheel(boolean respond) {
        if (!respond) {
            PhyloWidget.trees.camera.makeUnresponsive();
        } else {
            PhyloWidget.trees.camera.makeResponsive();
        }
    }

    public void setTree(final String s) {
        new Thread() {
            public void run() {
                PhyloWidget.trees.setTree(s);
                tree = s;
            }
        }.start();
    }

    public void setUseBranchLengths(boolean useEm) {
        useBranchLengths = useEm;
        PhyloWidget.ui.layout();
    }

    //	public void setStretchToFit(boolean fitMe)
    //	{
    //		stretchToFit = fitMe;
    //	}

    public void setSearch(String s) {
        this.search = s;
        PhyloWidget.ui.search();
    }

    public void setEnforceUniqueLabels(boolean b) {
        enforceUniqueLabels = b;
        RootedTree t = PhyloWidget.trees.getTree();
        if (t != null)
            t.setEnforceUniqueLabels(b);
    }

    public void setLayout(String s) {
        s = s.toLowerCase();
        if (s.equals("diagonal")) {
            PhyloWidget.trees.diagonalRender();
        } else if (s.equals("circular")) {
            PhyloWidget.trees.circleRender();
        } else if (s.equals("unrooted")) {
            PhyloWidget.trees.unrootedRender();
        } else {
            PhyloWidget.trees.rectangleRender();
        }
    }

    public void setRemoteConfig(String s) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            URL url = new URL(s);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("#"))
                    continue;
                String[] split = line.split("=");
                if (split.length >= 2) {
                    map.put(split[0], split[1]);
                }
            }
            MethodAndFieldSetter.setMethodsAndFields(this, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMenus(String menus) {
        this.menus = menus;
        new Thread("Menu Loader") {
            @Override
            public void run() {
                PhyloWidget.ui.setMenus();
            }
        }.start();
    }

    public void setShowAllLabels(boolean showAllLabels) {
        this.showAllLabels = showAllLabels;
    }

    public void setPrioritizeDistantLabels(boolean prioritizeDistanceLabels) {
        this.prioritizeDistantLabels = prioritizeDistanceLabels;
        PhyloWidget.ui.layout();
    }

    public void destroy() {
        tree = null;
    }

    public void setTextSize(float textSize) {
        this.textScaling = textSize;
        //		PhyloWidget.ui.layout();
    }

    public void setLayoutAngle(float layoutAngle) {
        this.layoutAngle = layoutAngle;
        PhyloWidget.ui.forceLayout();
    }

    public void setViewportX(float newX) {
        PhyloWidget.trees.camera.nudgeTo(-newX, PhyloWidget.trees.camera.getY());
    }

    public void setViewportY(float newY) {
        PhyloWidget.trees.camera.nudgeTo(PhyloWidget.trees.camera.getX(), -newY);
    }

    public void setViewportZoom(float newZoom) {
        PhyloWidget.trees.camera.zoomTo(newZoom);
    }

    public void setBranchScaling(float newBranchScaling) {
        this.branchScaling = newBranchScaling;
        PhyloWidget.ui.forceLayout();
    }

    public void setShowScaleBar(boolean show) {
        if (show) {
            PhyloWidget.trees.showScaleBar();
        } else {
            PhyloWidget.trees.hideScaleBar();
        }
    }

    public final static String DEFAULT_TREE = "PhyloWidget";

}
