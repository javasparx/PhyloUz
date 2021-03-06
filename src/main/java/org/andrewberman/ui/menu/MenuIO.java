/*******************************************************************************
 * Copyright (c) 2007, 2008 Gregory Jordan
 *
 * This file is part of PhyloWidget.
 *
 * PhyloWidget is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 *
 * PhyloWidget is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * PhyloWidget. If not, see <http://www.gnu.org/licenses/>.
 */
package org.andrewberman.ui.menu;

import processing.core.PApplet;
import processing.xml.XMLElement;

import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Stack;

public class MenuIO {
    PApplet app;
    Object[] actionObjects;

    ClassLoader cl;

    public MenuIO() {
        String menuPackage = Menu.class.getPackage().getName();
        menuPackages.add(menuPackage);
    }

    /**
     * @param p
     * @param filename
     * @param actionHolder (optional) the object which will contain the action methods
     *                     for this menu set.
     * @return
     */
    public ArrayList<MenuItem> loadFromXML(Reader in, PApplet p, Object... actionHolders) {
        ArrayList<MenuItem> menus = new ArrayList<MenuItem>();
        app = p;
        cl = p.getClass().getClassLoader();
        actionObjects = actionHolders;
        //		InputStream in = p.openStream(filename);
        /*
         * Search depth-first through the XML tree, adding the highest-level
		 * menu elements we can find.
		 */
        Stack s = new Stack();
        try {
            XMLElement x = new XMLElement(in);
            s.push(x);
            while (!s.isEmpty()) {
                XMLElement curEl = (XMLElement) s.pop();
                if (curEl.getName().equalsIgnoreCase("menu")) {
                    // If curEl is a menu, parse it and add it to the ArrayList.
                    menus.add(processElement(null, curEl));
                } else {
                    // If not, keep going through the XML tree and search for
                    // more <menu> elements.
                    Enumeration en = curEl.enumerateChildren();
                    while (en.hasMoreElements()) {
                        s.push(en.nextElement());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menus;
    }

    public MenuItem processElement(MenuItem parent, XMLElement el) {
        //		long t = System.currentTimeMillis();
        MenuItem newItem = null;
        String elName = el.getName();
        String itemName = el.getStringAttribute("name");
        el.removeAttribute("name");
        if (el.hasAttribute("type")) {
			/*
			 * If this element has the "type" attribute, then we use that to
			 * create a new Menu or MenuItem from scratch.
			 */
            String type = el.getStringAttribute("type");
            el.removeAttribute("type");
            newItem = createMenu(type);
            // Set this Menu's name.
            if (itemName != null)
                newItem.setName(itemName);
            else
                newItem.setName("");
        }

		/*
		 * If this is any other element (I expect it to be <item>), then
		 * let's make sure it has a parent Menu or MenuItem:
		 */
        if (parent == null && !elName.equalsIgnoreCase("menu"))
            throw new RuntimeException("[MenuIO] XML menu parsing error on " + elName
                    + " element: <item> requires a parent <menu> or <item>!");

        if (elName.equalsIgnoreCase("item") || elName.equalsIgnoreCase("menu")) {
			/*
			 * If all is well, then we use the parent item's add() method to
			 * create this new Item element.
			 */
            if (newItem != null && parent != null)
                newItem = parent.add(newItem);
            else if (parent != null)
                newItem = parent.add(itemName);
        } else if (elName.equalsIgnoreCase("methodcall")) {
            String mName = el.getStringAttribute("method");
            String p = el.getStringAttribute("param");
            el.removeAttribute("method");
            el.removeAttribute("param");
            try {
                Method m = parent.getClass().getMethod(mName, new Class[]{String.class});
                m.invoke(parent, new Object[]{p});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

		/*
		 * At this point, we have a good "newItem" MenuItem. Now we need to
		 * populate its attributes using a bean-like Reflection scheme. Every
		 */
        Enumeration attrs = el.enumerateAttributeNames();
        while (attrs.hasMoreElements()) {
            String attr = (String) attrs.nextElement();
            setAttribute(newItem, attr, el.getStringAttribute(attr));
        }

        //		long curT = System.currentTimeMillis();
        //		long dt = curT - t;
        //		System.out.println((dt / 1000f) + "   " + itemName);

		/*
		 * Now, keep the recursion going: go through the current XMLElement's
		 * children and call menuElement() on each one.
		 */
        XMLElement[] els = el.getChildren();
        for (int i = 0; i < els.length; i++) {
            XMLElement child = els[i];
            processElement(newItem, child);
        }
        return newItem;
    }

    protected ArrayList<String> menuPackages = new ArrayList<String>();

    //	protected static final String menuPackage = Menu.class.getPackage().getName();
    //	protected static final String toolPackage = Tool.class.getPackage().getName();

    /**
     * Uses Reflection to create a Menu of the given class type.
     *
     * @param classType The desired Menu class to create, either as a simple class
     *                  name (if the class resides within the base Menu package) or as
     *                  the fully-qualified Class name (i.e.
     *                  org.something.SomethingElse).
     * @return
     */
    protected MenuItem createMenu(String classType) {
		/*
		 * We need to give the complete package name of the desired Class, so we
		 * need to assume that the desired class resides within the base Menu
		 * package.
		 */
        Class c = null;
        if (classType.indexOf('.') != -1) {
            try {
                c = cl.loadClass(classType);
            } catch (ClassNotFoundException e) {
                //				e.printStackTrace();
            }
        }

        if (c == null) {
            for (String menuPackage : menuPackages) {
                //				System.out.println("H");
                String fullClass = menuPackage + "." + classType;
                try {
                    c = cl.loadClass(fullClass);
                    //				c = Class.forName(fullClass);
                    //				System.out.println(fullClass);
                    //				c = getClass().getClassLoader().loadClass(fullClass);
                    //				c = Class.forName(fullClass, false,app.getClass().getClas);
                    //				app.getClass().getClassLoader().loadClass(fullClass);
                    break;
                } catch (Exception e) {
                    //				e.printStackTrace();
                    continue;
                }
            }
        }
        //
        //		/*
        //		 * If using the predefined package names didn't work, try loading as if we were given the full class name.
        //		 */
        //		if (c == null)
        //		{
        //			try
        //			{
        //				c = Class.forName(classType);
        //			} catch (java.lang.ClassNotFoundException e2)
        //			{
        //				e2.printStackTrace();
        //			}
        //		}

        Constructor construct;
        try {
            construct = c.getConstructor(new Class[]{PApplet.class});
            Object newMenu = construct.newInstance(new Object[]{app});
            return (MenuItem) newMenu;
        } catch (Exception e) {
            //			e.printStackTrace();
            try {
                construct = c.getConstructor(new Class[]{});
                Object newMenu = construct.newInstance(new Object[]{});
                return (MenuItem) newMenu;
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    /**
     * Use reflection to call the setXXX function for the given attribute and
     * value.
     * <p/>
     * All setXXX methods should just take a String argument, except for the
     * defined exceptions.
     *
     * @param item
     * @param attr
     * @param value
     */
    protected void setAttribute(MenuItem item, String attr, String value) {
        String attrL = attr.toLowerCase();
        String upperFirst = "set" + upperFirst(attr);

		/*
		 * Special case: setAction(Object o, String s)
		 * setProperty(Object o, String s)
		 * setMethodCall(Object o, String s)
		 * setCondition(Object o, String s)
		 */
        if (attrL.matches("(action|property|methodcall|condition)")) {
            setWithObjectRef(item, attr, value);
            return;
        }

        Class c = item.getClass();

        try {
			/*
			 * Start with the bread-and-butter, setXXX(String s) method call.
			 */
            Method m = c.getMethod(upperFirst, String.class);
            m.invoke(item, value);
        } catch (Exception e) {
            try {
				/*
				 * Ok, that didn't work... let's parse to a float.
				 */
                Method m = c.getMethod(upperFirst, Float.TYPE);
                m.invoke(item, Float.parseFloat(value));
            } catch (Exception e2) {
                try {
					/*
					 * Ok, let's parse to a boolean.
					 */
                    Method m = c.getMethod(upperFirst, Boolean.TYPE);
                    m.invoke(item, Boolean.parseBoolean(value));
                } catch (Exception e3) {
					/*
					 * Dammit, nothing worked. Let's let loose on the console.
					 */
                    //					e.printStackTrace();
                    //					e2.printStackTrace();
                    //					e3.printStackTrace();
                }
            }
        }
    }

    protected void setWithObjectRef(MenuItem item, String attr, String value) {
        String methodS = "set" + upperFirst(attr);
        Class c = item.getClass();
        try {
            Method m = c.getMethod(methodS, Object.class, String.class);
            for (Object ao : actionObjects) {
                try {
//					System.out.println(item + "  " + ao + "  " + value);
                    m.invoke(item, ao, value);
                    break;
                } catch (Exception e) {
//					e.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
            //			e.printStackTrace();
        }
    }

    public static String upperFirst(String s) {
        String upper = s.substring(0, 1).toUpperCase();
        String orig = s.substring(1, s.length());
        return upper + orig;
    }
}
