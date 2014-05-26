/* ==========================================
 * JGraphT : a free Java graph-theory library
 * ==========================================
 *
 * Project Info:  http://jgrapht.sourceforge.net/
 * Project Creator:  Barak Naveh (http://sourceforge.net/users/barak_naveh)
 *
 * (C) Copyright 2003-2007, by Barak Naveh and Contributors.
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/* -----------------------
 * DepthFirstIterator.java
 * -----------------------
 * (C) Copyright 2003-2007, by Liviu Rau and Contributors.
 *
 * Original Author:  Liviu Rau
 * Contributor(s):   Barak Naveh
 *                   Christian Hammer
 *
 * $Id: DepthFirstIterator.java 568 2007-09-30 00:12:18Z perfecthash $
 *
 * Changes
 * -------
 * 29-Jul-2003 : Initial revision (LR);
 * 31-Jul-2003 : Fixed traversal across connected components (BN);
 * 06-Aug-2003 : Extracted common logic to TraverseUtils.XXFirstIterator (BN);
 * 31-Jan-2004 : Reparented and changed interface to parent class (BN);
 * 04-May-2004 : Made generic (CH)
 *
 */
package org.jgrapht.traverse;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.List;


/**
 * A depth-first iterator for a directed and an undirected graph. For this
 * iterator to work correctly the graph must not be modified during iteration.
 * Currently there are no means to ensure that, nor to fail-fast. The results of
 * such modifications are undefined.
 *
 * @author Liviu Rau
 * @author Barak Naveh
 * @since Jul 29, 2003
 */
public class DepthFirstIterator<V, E>
        extends CrossComponentIterator<V, E, CrossComponentIterator.VisitColor> {
    //~ Instance fields --------------------------------------------------------

    /**
     * LIFO stack of vertices which have been encountered but not yet visited
     * (WHITE). This stack also contains <em>sentinel</em> entries representing
     * vertices which have been visited but are still GRAY. A sentinel entry is
     * a sequence (v, null), whereas a non-sentinel entry is just (v).
     */
    private List<V> stack = new ArrayList<V>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new depth-first iterator for the specified graph.
     *
     * @param g the graph to be iterated.
     */
    public DepthFirstIterator(Graph<V, E> g) {
        this(g, null);
    }

    /**
     * Creates a new depth-first iterator for the specified graph. Iteration
     * will start at the specified start vertex and will be limited to the
     * connected component that includes that vertex. If the specified start
     * vertex is <code>null</code>, iteration will start at an arbitrary vertex
     * and will not be limited, that is, will be able to traverse all the graph.
     *
     * @param g           the graph to be iterated.
     * @param startVertex the vertex iteration to be started.
     */
    public DepthFirstIterator(Graph<V, E> g, V startVertex) {
        super(g, startVertex);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * @see org.jgrapht.traverse.CrossComponentIterator#isConnectedComponentExhausted()
     */
    protected boolean isConnectedComponentExhausted() {
        for (; ; ) {
            if (stack.isEmpty()) {
                return true;
            }
            if (peekStack() != null) {
                // Found a non-sentinel.
                return false;
            }

            // Found a sentinel:  pop it, record the finish time,
            // and then loop to check the rest of the stack.

            // Pop null we peeked at above.
            popStack();

            // This will pop corresponding vertex to be recorded as finished.
            recordFinish();
        }
    }

    /**
     * @see org.jgrapht.traverse.CrossComponentIterator#encounterVertex(Object, Object)
     */
    protected void encounterVertex(V vertex, E edge) {
        putSeenData(vertex, VisitColor.WHITE);
        stack.add(vertex);
    }

    /**
     * @see org.jgrapht.traverse.CrossComponentIterator#encounterVertexAgain(Object, Object)
     */
    protected void encounterVertexAgain(V vertex, E edge) {
        VisitColor color = getSeenData(vertex);
        if (color != VisitColor.WHITE) {
            // We've already visited this vertex; no need to mess with the
            // stack (either it's BLACK and not there at all, or it's GRAY
            // and therefore just a sentinel).
            return;
        }
        int i = stack.indexOf(vertex);

        // Since we've encountered it before, and it's still WHITE, it
        // *must* be on the stack.
        assert (i > -1);
        stack.remove(i);
        stack.add(vertex);
    }

    /**
     * @see org.jgrapht.traverse.CrossComponentIterator#provideNextVertex()
     */
    protected V provideNextVertex() {
        V v;
        for (; ; ) {
            v = popStack();
            if (v == null) {
                // This is a finish-time sentinel we previously pushed.
                recordFinish();
                // Now carry on with another pop until we find a non-sentinel
            } else {
                // Got a real vertex to start working on
                break;
            }
        }

        // Push a sentinel for v onto the stack so that we'll know
        // when we're done with it.
        stack.add(v);
        stack.add(null);
        putSeenData(v, VisitColor.GRAY);
        return v;
    }

    private V popStack() {
        return stack.remove(stack.size() - 1);
    }

    private V peekStack() {
        return stack.get(stack.size() - 1);
    }

    private void recordFinish() {
        V v = popStack();
        putSeenData(v, VisitColor.BLACK);
        finishVertex(v);
    }
}

// End DepthFirstIterator.java
