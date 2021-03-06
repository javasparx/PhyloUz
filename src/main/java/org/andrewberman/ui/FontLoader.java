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
package org.andrewberman.ui;

import processing.core.PApplet;
import processing.core.PFont;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
    private PApplet p;

    public PFont vera;
    public PFont veraNonNative;
    public Font font;

    public FontLoader(PApplet p) {
//		p.hint(PApplet.ENABLE_NATIVE_FONTS);
        this.p = p;
        vera = p.loadFont("BitstreamVeraSans-Roman-36.vlw");
        InputStream in = p.createInput("vera.ttf");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, in);
            in.close();
            vera.setFont(font);
        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
