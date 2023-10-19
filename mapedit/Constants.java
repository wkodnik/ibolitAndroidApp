/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapedit;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author sasa
 */
public class Constants {

    public static void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        System.setErr(ec.getPs());
        baseTile = new Objects.Tile(0, Objects.Tile.Type.GROUND, null);
        for (int i = 0; i < Objects.Tile.Type.values().length; i++) {
            tileList[i] = new Objects.Tile(0, Objects.Tile.Type.values()[i], null);
        }
        for (int i = 0; i < Objects.Unit.Type.values().length; i++) {
            unitList[i] = new Objects.Unit(Objects.Unit.Type.values()[i], Objects.Team.getNeutralPassive());
        }
        for (int i = -2; i <= 2; i++) {
            elevationList[i + 2] = new intItem(i);
        }
        teams.add(Objects.Team.getNeutralHostile());
        teams.add(Objects.Team.getNeutralPassive());
        int[] sizes = {1, 2, 3, 5, 8};
        for (int i = 0; i < sizes.length; i++) {
            java.awt.image.BufferedImage image = null;
            try {
                image = javax.imageio.ImageIO.read(new java.io.File("Images/Icons/S" + sizes[i] + ".bmp"));
            } catch (java.io.IOException ex) {
                System.out.println("No Picture exists for size " + sizes[i]);
            }
            paletteSizes[i] = new buttonlist.DefaultBLItem<Integer>(image, sizes[i]);
        }
        try {
            selectionMask = javax.imageio.ImageIO.read(new java.io.File("Images/SelectionGreen.gif"));
            blackMask = javax.imageio.ImageIO.read(new java.io.File("Images/BlackMask.gif"));
            whiteMask = javax.imageio.ImageIO.read(new java.io.File("Images/WhiteMask.gif"));
            blueMask = javax.imageio.ImageIO.read(new java.io.File("Images/SelectionBlue.gif"));
            redMask = javax.imageio.ImageIO.read(new java.io.File("Images/RedMask.gif"));
            blackMask = blueMask;
            whiteMask = redMask;
        } catch (java.io.IOException ex) {
            System.out.println("No Picture exists for Selection");
        }
        Objects.Tile.initImgs();
        Objects.Unit.initImgs();
        java.io.File tmpdir = new java.io.File("tmp");
        if (!tmpdir.exists() || !tmpdir.isDirectory()) {
            if (tmpdir.exists())
                tmpdir.delete();
            tmpdir.mkdir();
        }
        new AutosaveThread().start();
        new GUI.Palette().setVisible(true);
        mapEdit.setVisible(true);
    }

    public static Objects.Map getCurrentMap() {
        if (mapWindows.size() > 0)
            return mapWindows.get(0);
        return null;
    }

    public static GUI.Palette getCurrentPalette() {
        return palettes.get(0);
    }

    public static void quit() {
        for (int i = 0; i < mapWindows.size(); i++) {
            Objects.Map map = mapWindows.get(i);
            map.getDisplay().fullRedraw();
            if (map.isChanged())
                new GUI.Closing(mapEdit, true, map).setVisible(true);
        }
        System.exit(0);
    }

    public static void bringToFront(Objects.Map m) {
        if (m == null) return;
        if (mapWindows.size() > 0 && mapWindows.get(0) != null && mapWindows.get(0).getDisplay() != null)
            mapWindows.get(0).getDisplay().clearImage();
        mapWindows.remove(m);
        mapWindows.add(0, m);
        mapEdit.setPicHeight(m.getHeight() * m.getDisplay().getYScale());
        mapEdit.setPicWidth(m.getWidth() * m.getDisplay().getXScale());
        mapEdit.setTitle(m.getTitle() + ((m.isChanged()) ? "*" : ""));
        mapEdit.getWorkMenu().removeAll();
        for (Objects.Map map : mapedit.Constants.mapWindows) {
            GUI.MapMenuItem mi = new GUI.MapMenuItem(map);
            mi.setText(map.getTitle() + ((map.isChanged()) ? "*" : ""));
            mapEdit.getWorkMenu().add(mi);
        }
    }

    public static boolean removeMap(Objects.Map o) {
        return mapWindows.remove(o);
    }

    public static void setTopPalette(GUI.Palette p) {
        palettes.remove(p);
        palettes.add(0, p);
    }

    public static String iToC(int i) {
        if (i < 10)
            return "" + i;
        return "" + ((char) (i - 10 + 65));
    }

    public static int cToI(String c) {
        char ch = c.toUpperCase().charAt(0);
        if ('0' <= ch && ch <= '9')
            return Integer.parseInt("" + ch);
        return ch + 10 - 65;
    }

    public static void main(String args[]) {
        System.out.println(cToI("Y"));
        System.out.println(iToC(-2));
        System.out.println();
        System.exit(0);
    }

    private static class AutosaveThread extends Thread {

        @Override
        public void run() {
            this.setName("Autosave Thread");
            while (true) {
                try {
                    Thread.sleep(autosaveInterval);
                    if (getCurrentMap() != null && getCurrentMap().isChanged()) {
                        getCurrentMap().saveTmp();
                    }
                } catch (InterruptedException e) {
                    System.err.println("Autosave interrupted; THis should not happen");
                    e.printStackTrace();
                }
            }
        }
    }
    //Editable Data
    public static java.awt.image.BufferedImage selectionMask, redMask, blackMask, whiteMask, blueMask;
    public static Objects.Tile baseTile;
    public static boolean saved = true;
    public static java.util.ArrayList<Objects.Map> mapWindows = new java.util.ArrayList<Objects.Map>();
    public static Objects.Tile[][] copy, selection;
    public static boolean pasting = false;
    public static Objects.Tile[][] tilesCopy;
    public static boolean grid = false;
    public static boolean tooltip = false;
    public static boolean lockMapChange = false;
    public static long autosaveInterval = 5000;
    public static boolean doAutosave = true;
    //Final Data
    public final static java.util.ArrayList<Objects.Team> teams = new java.util.ArrayList<Objects.Team>();
    public final static Objects.Tile[] tileList = new Objects.Tile[Objects.Tile.Type.values().length];
    public final static Objects.Unit[] unitList = new Objects.Unit[Objects.Unit.Type.values().length];
    public final static intItem[] elevationList = new intItem[5];
    public final static GUI.MapEdit mapEdit = new GUI.MapEdit();
    public static final GUI.CrashConsole ec = new GUI.CrashConsole(mapEdit, false);
    public final static buttonlist.DefaultBLItem<Integer>[] paletteSizes = new buttonlist.DefaultBLItem[5];
    public final static java.util.ArrayList<GUI.Palette> palettes = new java.util.ArrayList<GUI.Palette>();
}
