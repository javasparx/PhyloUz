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
/* -----------------------------
 * TraversalListenerAdapter.java
 * -----------------------------
 * (C) Copyright 2003-2007, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh
 * Contributor(s):   Christian Hammer
 *
 * $Id: TraversalListenerAdapter.java 568 2007-09-30 00:12:18Z perfecthash $
 *
 * Changes
 * -------
 * 06-Aug-2003 : Initial revision (BN);
 * 11-Aug-2003 : Adaptation to new event model (BN);
 * 11-Mar-2004 : Made generic (CH);
 *
 */
package org.jgrapht.event;

/**
 * An empty do-nothing implementation of the {@link org.jgrapht.event.TraversalListener} interface
 * used for subclasses.
 *
 * @author Barak Naveh
 * @since Aug 6, 2003
 */
public class TraversalListenerAdapter<V, E>
        implements TraversalListener<V, E> {
    //~ Methods ----------------------------------------------------------------

    /**
     * @see org.jgrapht.event.TraversalListener#connectedComponentFinished(org.jgrapht.event.ConnectedComponentTraversalEvent)
     */
    public void connectedComponentFinished(
            ConnectedComponentTraversalEvent e) {
    }

    /**
     * @see org.jgrapht.event.TraversalListener#connectedComponentStarted(org.jgrapht.event.ConnectedComponentTraversalEvent)
     */
    public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
    }

    /**
     * @see org.jgrapht.event.TraversalListener#edgeTraversed(org.jgrapht.event.EdgeTraversalEvent)
     */
    public void edgeTraversed(EdgeTraversalEvent<V, E> e) {
    }

    /**
     * @see org.jgrapht.event.TraversalListener#vertexTraversed(org.jgrapht.event.VertexTraversalEvent)
     */
    public void vertexTraversed(VertexTraversalEvent<V> e) {
    }

    /**
     * @see org.jgrapht.event.TraversalListener#vertexFinished(org.jgrapht.event.VertexTraversalEvent)
     */
    public void vertexFinished(VertexTraversalEvent<V> e) {
    }
}

// End TraversalListenerAdapter.java
