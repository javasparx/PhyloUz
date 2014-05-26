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
/* -------------------------
 * BreadthFirstIterator.java
 * -------------------------
 * (C) Copyright 2003-2007, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh
 * Contributor(s):   Liviu Rau
 *                   Christian Hammer
 *
 * $Id: BreadthFirstIterator.java 568 2007-09-30 00:12:18Z perfecthash $
 *
 * Changes
 * -------
 * 24-Jul-2003 : Initial revision (BN);
 * 06-Aug-2003 : Extracted common logic to TraverseUtils.XXFirstIterator (BN);
 * 31-Jan-2004 : Reparented and changed interface to parent class (BN);
 *
 */
package org.jgrapht.traverse;

import org.jgrapht.Graph;

import java.util.LinkedList;


/**
 * A breadth-first iterator for a directed and an undirected graph. For this
 * iterator to work correctly the graph must not be modified during iteration.
 * Currently there are no means to ensure that, nor to fail-fast. The results of
 * such modifications are undefined.
 *
 * @author Barak Naveh
 * @since Jul 19, 2003
 */
public class BreadthFirstIterator<V, E>
        extends CrossComponentIterator<V, E, Object> {
    //~ Instance fields --------------------------------------------------------

    /**
     * <b>Note to users:</b> this queue implementation is a bit lame in terms of
     * GC efficiency. If you need it to be improved either let us know or use
     * the source...
     */
    private LinkedList<V> queue = new LinkedList<V>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new breadth-first iterator for the specified graph.
     *
     * @param g the graph to be iterated.
     */
    public BreadthFirstIterator(Graph<V, E> g) {
        this(g, null);
    }

    /**
     * Creates a new breadth-first iterator for the specified graph. Iteration
     * will start at the specified start vertex and will be limited to the
     * connected component that includes that vertex. If the specified start
     * vertex is <code>null</code>, iteration will start at an arbitrary vertex
     * and will not be limited, that is, will be able to traverse all the graph.
     *
     * @param g           the graph to be iterated.
     * @param startVertex the vertex iteration to be started.
     */
    public BreadthFirstIterator(Graph<V, E> g, V startVertex) {
        super(g, startVertex);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * @see org.jgrapht.traverse.CrossComponentIterator#isConnectedComponentExhausted()
     */
    protected boolean isConnectedComponentExhausted() {
        return queue.isEmpty();
    }

    /**
     * @see org.jgrapht.traverse.CrossComponentIterator#encounterVertex(Object, Object)
     */
    protected void encounterVertex(V vertex, E edge) {
        putSeenData(vertex, null);
        queue.add(vertex);
    }

    /**
     * @see org.jgrapht.traverse.CrossComponentIterator#encounterVertexAgain(Object, Object)
     */
    protected void encounterVertexAgain(V vertex, E edge) {
    }

    /**
     * @see org.jgrapht.traverse.CrossComponentIterator#provideNextVertex()
     */
    protected V provideNextVertex() {
        return queue.removeFirst();
    }
}

// End BreadthFirstIterator.java