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
/* -------------
 * EdgeReversedGraph.java
 * -------------
 * (C) Copyright 2006-2007, by John V. Sichi and Contributors.
 *
 * Original Author:  John V. Sichi
 * Contributor(s):   -
 *
 * $Id: EdgeReversedGraph.java 586 2008-01-27 23:30:50Z perfecthash $
 *
 * Changes
 * -------
 * 16-Sept-2006 : Initial revision (JVS);
 *
 */
package org.jgrapht.graph;

import org.jgrapht.DirectedGraph;

import java.util.Set;


/**
 * Provides an edge-reversed view g' of a directed graph g. The vertex sets for
 * the two graphs are the same, but g' contains an edge (v2, v1) iff g contains
 * an edge (v1, v2). g' is backed by g, so changes to g are reflected in g', and
 * vice versa.
 * <p/>
 * <p>This class allows you to use a directed graph algorithm in reverse. For
 * example, suppose you have a directed graph representing a tree, with edges
 * from parent to child, and you want to find all of the parents of a node. To
 * do this, simply create an edge-reversed graph and pass that as input to
 * {@link org.jgrapht.traverse.DepthFirstIterator}.
 *
 * @author John V. Sichi
 * @see org.jgrapht.graph.AsUndirectedGraph
 */
public class EdgeReversedGraph<V, E>
        extends GraphDelegator<V, E>
        implements DirectedGraph<V, E> {
    //~ Static fields/initializers ---------------------------------------------

    /**
     */
    private static final long serialVersionUID = 9091361782455418631L;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new EdgeReversedGraph.
     *
     * @param g the base (backing) graph on which the edge-reversed view will be
     *          based.
     */
    public EdgeReversedGraph(DirectedGraph<V, E> g) {
        super(g);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * @see org.jgrapht.Graph#getEdge(Object, Object)
     */
    public E getEdge(V sourceVertex, V targetVertex) {
        return super.getEdge(targetVertex, sourceVertex);
    }

    /**
     * @see org.jgrapht.Graph#getAllEdges(Object, Object)
     */
    public Set<E> getAllEdges(V sourceVertex, V targetVertex) {
        return super.getAllEdges(targetVertex, sourceVertex);
    }

    /**
     * @see org.jgrapht.Graph#addEdge(Object, Object)
     */
    public E addEdge(V sourceVertex, V targetVertex) {
        return super.addEdge(targetVertex, sourceVertex);
    }

    /**
     * @see org.jgrapht.Graph#addEdge(Object, Object, Object)
     */
    public boolean addEdge(V sourceVertex, V targetVertex, E e) {
        return super.addEdge(targetVertex, sourceVertex, e);
    }

    /**
     * @see org.jgrapht.DirectedGraph#inDegreeOf(Object)
     */
    public int inDegreeOf(V vertex) {
        return super.outDegreeOf(vertex);
    }

    /**
     * @see org.jgrapht.DirectedGraph#outDegreeOf(Object)
     */
    public int outDegreeOf(V vertex) {
        return super.inDegreeOf(vertex);
    }

    /**
     * @see org.jgrapht.DirectedGraph#incomingEdgesOf(Object)
     */
    public Set<E> incomingEdgesOf(V vertex) {
        return super.outgoingEdgesOf(vertex);
    }

    /**
     * @see org.jgrapht.DirectedGraph#outgoingEdgesOf(Object)
     */
    public Set<E> outgoingEdgesOf(V vertex) {
        return super.incomingEdgesOf(vertex);
    }

    /**
     * @see org.jgrapht.Graph#removeEdge(Object, Object)
     */
    public E removeEdge(V sourceVertex, V targetVertex) {
        return super.removeEdge(targetVertex, sourceVertex);
    }

    /**
     * @see org.jgrapht.Graph#getEdgeSource(Object)
     */
    public V getEdgeSource(E e) {
        return super.getEdgeTarget(e);
    }

    /**
     * @see org.jgrapht.Graph#getEdgeTarget(Object)
     */
    public V getEdgeTarget(E e) {
        return super.getEdgeSource(e);
    }

    /**
     * @see Object#toString()
     */
    public String toString() {
        return toStringFromSets(
                vertexSet(),
                edgeSet(),
                true);
    }
}

// End EdgeReversedGraph.java