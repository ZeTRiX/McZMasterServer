/*
 * McZLauncher (ZeTRiX's Minecraft Launcher)
 * Copyright (C) 2012 Evgen Yanov <http://www.zetlog.ru>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program (In the "_License" folder). If not, see <http://www.gnu.org/licenses/>.
*/

package ru.zetrix;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author ZeTRiX
 */
public class Util {        
        public static BufferedImage getRes(String resname) {
            try {
                BufferedImage img = ImageIO.read(ru.zetrix.MServ.class.getResource("/ru/zetrix/res/" + resname));
                System.out.println("Loading resourse: " + resname);
                return img;
            } catch (Exception e) {
                System.out.println("Error loading resource: " + resname);
                System.out.println("Text of Error: " + e);
            } return new BufferedImage(1, 1, 2);
        }
}
