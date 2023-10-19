/**
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package Objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import xmlska.CommentNode;
import xmlska.Node;

/*
 *
 * @author sasa
 */
public class Map {

    private java.util.HashSet<Team> mapTeams = new java.util.HashSet<Team>();
    private int rounds, points, seed = new java.util.Random().nextInt();
    private boolean changed = false;

    public void change() {
        Map oldMap = null;
        if (!mapedit.Constants.lockMapChange) oldMap = this.copy();
        if (!changed) {
            changed = true;
            mapedit.Constants.mapEdit.setTitle(mapedit.Constants.mapEdit.getTitle() + "*");
            System.out.println("changing title");
        }
        if (nextMap != null) {
            mapedit.Constants.removeMap(nextMap);
            nextMap = null;
        }
        if (!mapedit.Constants.lockMapChange) {
            if (this.previousMap != null) {
                oldMap.previousMap = this.previousMap;
                this.previousMap.nextMap = oldMap;
            }
            this.previousMap = oldMap;
            oldMap.nextMap = this;
            mapedit.Constants.mapEdit.setUndoRedoAbility(true, false);
        }
    }

    public void undo() {
        if (this.previousMap != null) {
            mapedit.Constants.removeMap(this);
            this.getDisplay().clearImage();
            if (!this.previousMap.mapRefreshed)
                this.previousMap.refreshAllImages();
            this.previousMap.getDisplay().fullRedraw();
            mapedit.Constants.mapEdit.setUndoRedoAbility(this.previousMap.previousMap != null, true);
        }
    }

    public void redo() {
        if (this.nextMap != null) {
            mapedit.Constants.removeMap(this);
            this.getDisplay().clearImage();
            if (!this.nextMap.mapRefreshed)
                this.nextMap.refreshAllImages();
            this.nextMap.getDisplay().fullRedraw();
            mapedit.Constants.mapEdit.setUndoRedoAbility(true, this.nextMap.nextMap != null);
        }
    }

    public void change(boolean force) {
        if (force)
            changed = false;
        change();
    }

    public boolean isChanged() {
        return changed;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        if (points != this.points) {
            change();
            this.points = points;
        }
    }

    public int getSeed() {
        return seed;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        if (rounds != this.rounds) {
            change();
            this.rounds = rounds;
        }
    }

    public void save() {
        xmlska.Node map = toXML();
        try {
            xmlska.Writer.write(map, SaveFile);
            changed = false;
            mapedit.Constants.bringToFront(this);
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveTmp() {
        xmlska.Node map = toXML();
        try {
            String tmpdir = "tmp" + java.io.File.separator;
            if (SaveFile == null) tmpdir += "Untitled.xml";
            else tmpdir += SaveFile.getName();
            java.io.File tmp = new java.io.File(tmpdir);
            xmlska.Writer.write(map, tmp);
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }

    public Object[] toArray() {
        return mapTeams.toArray();
    }

    public boolean addAll(java.util.Collection<? extends Team> c) {
        return mapTeams.addAll(c);
    }

    public boolean remove(Team o) {
        return mapTeams.remove(o);
    }

    public boolean contains(Team o) {
        return mapTeams.contains(o);
    }

    public boolean addTeam(Team e) {
        boolean b = mapTeams.add(e);
        GUI.Palette.refreshTeams();
        return b;
    }

    public HashSet<Team> getMapTeams() {
        return mapTeams;
    }

    public Team getTeamByName(String name) {
        for (Team team : mapTeams)
            if (team.getTeamName().equals(name))
                return team;
        return null;
    }

    public Map(int x, int y) {
        tiles = new Tile[y][x];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = mapedit.Constants.baseTile.copy();
                tiles[i][j].setY(i);
                tiles[i][j].setX(j);
                tiles[i][j].setMap(this);
            }
        }
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j].randomizeIndex();
            }
        }
        refreshAllImages();
        //mapedit.Constants.bringToFront(this);
        nextMap = previousMap = null;
    }
    private boolean mapRefreshed = false;

    private void refreshAllImages() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j].refreshImg();
            }
        }
        mapRefreshed = true;
    }

    public void setDisplay(Displays.MapDisplay display) {
        this.display = display;
    }

    public int getWidth() {
        return tiles[0].length;
    }

    public int getHeight() {
        return tiles.length;
    }

    public Displays.MapDisplay getDisplay() {
        return display;
    }

    public Tile getTile(int x, int y) {
        try {
            return tiles[y][x];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public void setTile(int x, int y, Tile tile, boolean aggregateChange) {
        if (aggregateChange) {
            tiles[y][x] = tile;
            tiles[y][x].setX(x);
            tiles[y][x].setY(y);
            tiles[y][x].setMap(this);
            display.update(tiles[y][x]);
        } else {
            change();
            tiles[y][x] = tile;
            tiles[y][x].setX(x);
            tiles[y][x].setY(y);
            tiles[y][x].setMap(this);
            tiles[y][x].refreshImg();
            display.update(tiles[y][x]);

        }
    }

    public void setTiles(int x, int y, Tile[][] tilemap) {
        change();
        for (int i = 0; i < tilemap.length; i++) {
            for (int j = 0; j < tilemap[i].length; j++) {
                if (y + i >= 0 && y + i < tiles.length && x + j >= 0 && x + j < tiles[y + i].length) {
                    setTile(x + j, y + i, tilemap[i][j].copy(), true);
                }
            }
        }
        for (int i = -1; i <= tilemap.length; i++) {
            for (int j = -1; j <= tilemap[0].length; j++) {
                if (y + i >= 0 && y + i < tiles.length && x + j >= 0 && x + j < tiles[y + i].length) {
                    tiles[y + i][x + j].refreshImg();
                    display.update(tiles[y + i][x + j]);
                }
            }
        }
    }

    public static Map fromXML(xmlska.Node head) {
        if (!head.getName().equals("map"))
            throw new java.lang.IllegalArgumentException("Not A Map");
        Map m = new Map(Integer.parseInt(head.getAttributeValue("width")),
            Integer.parseInt(head.getAttributeValue("height")));
        m.addTeam(Team.getPlayerA());
        m.addTeam(Team.getPlayerB());
        m.addTeam(Team.getNeutralPassive());
        mapedit.Constants.bringToFront(m);
        m.rounds = Integer.parseInt(head.getChild("game").getAttributeValue("rounds"));
        m.seed = Integer.parseInt(head.getChild("game").getAttributeValue("seed"));
        String dirt = null, water = null;
        java.util.Hashtable<String, Unit> univals = new java.util.Hashtable<String, Unit>();
        for (Node node : head.getChild("symbols").getChildren("symbol")) {
            if (node.getAttributeValue("type").equals("TERRAIN")) {
                if (node.getAttributeValue("terrain").equals("NORMAL"))
                    dirt = node.getAttributeValue("character");
                else if (node.getAttributeValue("terrain").equals("VOID"))
                    water = node.getAttributeValue("character");
            } else {
                if (!node.hasAttribute("team") || node.getAttributeValue("team").equals("NEUTRAL")) {
                } else if (node.getAttributeValue("team").equals("A")) {
                    univals.put(node.getAttributeValue("character"),
                        new Unit(Unit.Type.getTypeByName(node.getAttributeValue("type")), Team.getPlayerA()));
                } else if (node.getAttributeValue("team").equals("B")) {
                    univals.put(node.getAttributeValue("character"),
                        new Unit(Unit.Type.getTypeByName(node.getAttributeValue("type")), Team.getPlayerB()));
                }
            }
        }
        xmlska.CDATANode data = (xmlska.CDATANode) head.getChild("data").getSubNodes().get(0);
        String d = data.getData();
        int i = 0, r = 0, c = 0;
        String[] dat = d.trim().split("\\s+");
        System.out.println(Arrays.asList(dat));
        
        while (i < dat.length && i < m.getWidth() * m.getHeight()) {
            r = i % m.getWidth();
            c = i / m.getWidth();
            
            String di = dat[i];
            System.out.println(di);
            if (di.charAt(0) == dirt.charAt(0))
                m.getTile(r, c).setType(Tile.Type.GROUND);
            else if (di.charAt(0) == water.charAt(0))
                m.getTile(r, c).setType(Tile.Type.WATER);
            else
                m.getTile(r, c).setUnit(univals.get("" + di.charAt(0)).copy());
            m.getTile(r, c).setHeight(Integer.parseInt(di.substring(1)) / 5);
            i++;
        }
        m.changed = false;
        m.previousMap = m.nextMap = null;
        m.mapRefreshed = true;
        return m;
    }

    public Tile[][] getTiles() {
        return tiles;
    }
    private final Tile[][] tiles;
    private Displays.MapDisplay display = new Displays.MapDisplay(this);

    private Node toXML() {
        String d;
        Node head = new Node();
        head.setHead(true);
        head.setName("map");
        head.addAttribute("width", "" + getWidth());
        head.addAttribute("height", "" + getHeight());
        CommentNode cn = new CommentNode();
        cn.linkUp(head);
        cn.setData("Generated by Mapedit. A map editor created for Battlecode by aibolit (Aleks Tamarkin)");
        Node game = new Node();
        game.linkUp(head);
        game.setName("game");
        game.addAttribute("seed", "" + seed);
        game.addAttribute("rounds", "" + rounds);
        Node symbols = new Node();
        symbols.linkUp(head);
        symbols.setName("symbols");
        Node terr = new Node();
        terr.linkUp(symbols);
        terr.setName("symbol");
        terr.addAttribute("character", "n");
        terr.addAttribute("type", "TERRAIN");
        terr.addAttribute("terrain", "NORMAL");
        Node watr = new Node();
        watr.linkUp(symbols);
        watr.setName("symbol");
        watr.addAttribute("character", "v");
        watr.addAttribute("type", "TERRAIN");
        watr.addAttribute("terrain", "VOID");
        for (Unit.Type unit : Unit.Type.values()) {
            Node an = new Node();
            an.linkUp(symbols);
            an.setName("symbol");
            an.addAttribute("character", unit.getRep().toUpperCase());
            an.addAttribute("type", unit.toString());
            an.addAttribute("team", "A");
            Node bn = new Node();
            bn.linkUp(symbols);
            bn.setName("symbol");
            bn.addAttribute("character", unit.getRep().toLowerCase());
            bn.addAttribute("type", unit.toString());
            bn.addAttribute("team", "B");

        }
        Node data = new Node();
        data.linkUp(head);
        data.setName("data");
        xmlska.CDATANode dataval = new xmlska.CDATANode();
        dataval.linkUp(data);
        d = "\n";
        for (int i = 0; i < tiles.length; i++) {
            d += "\t\t";
            for (int j = 0; j < tiles[i].length; j++) {
                Unit u = tiles[i][j].getUnit();
                if (u != null) {
                    if (u.getTeam() == Team.getPlayerA())
                        d += u.getRep().toUpperCase();
                    else if (u.getTeam() == Team.getPlayerB())
                        d += u.getRep().toLowerCase();
                    else
                        d += "*";
                } else {
                    if (tiles[i][j].getType() == Tile.Type.GROUND)
                        d += "n";
                    else
                        d += "v";
                }
                d += (tiles[i][j].getHeight() * 5) + (j == tiles[i].length - 1 ? "" : " ");
            }
            d += "\n";
        }
        dataval.setData(d);

        return head;
    }

    public enum Mirrors {

        UTOD, DTOU, LTOR, RTOL;
    }

    public void Mirror(Mirrors m) {
        change();
        boolean flip = mapedit.Constants.mapEdit.getFlip();

        if (m == Mirrors.UTOD) {
            for (int j = 0; j < tiles.length / 2; j++) {
                for (int i = 0; i < tiles[j].length; i++) {
                    int ii = (flip) ? tiles[j].length - i - 1 : i;
                    setTile(i, tiles.length - 1 - j, tiles[j][ii].copy(), true);
                }
            }
        } else if (m == Mirrors.DTOU) {
            for (int j = 0; j < tiles.length / 2; j++) {
                for (int i = 0; i < tiles[j].length; i++) {
                    int ii = (flip) ? tiles[j].length - i - 1 : i;
                    setTile(i, j, tiles[tiles.length - j - 1][ii].copy(), true);
                }
            }
        } else if (m == Mirrors.LTOR) {
            for (int j = 0; j < tiles.length; j++) {
                for (int i = 0; i < tiles[j].length / 2; i++) {
                    int jj = (flip) ? tiles.length - j - 1 : j;
                    setTile(tiles[j].length - i - 1, j, tiles[jj][i].copy(), true);
                }
            }
        } else if (m == Mirrors.RTOL) {
            for (int j = 0; j < tiles.length; j++) {
                for (int i = 0; i < tiles[j].length / 2; i++) {
                    int jj = (flip) ? tiles.length - j - 1 : j;
                    setTile(i, j, tiles[jj][tiles[jj].length - i - 1].copy(), true);
                }
            }
        }

        this.refreshAllImages();

    }
    private java.io.File SaveFile = null;

    public java.io.File getSaveFile() {
        return SaveFile;
    }

    public String getTitle() {
        return (SaveFile != null) ? SaveFile.getName() : "Untitled.xml";
    }

    public void setSaveFile(java.io.File SaveFile) {
        this.SaveFile = SaveFile;
        if (this == mapedit.Constants.getCurrentMap())
            mapedit.Constants.mapEdit.setTitle(SaveFile.getName());
    }

    @Override
    public String toString() {
        String mv = super.toString();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
            }
        }

        return mv;
    }

    public void setTileType(int x, int y, Tile.Type type) {
        Tile tc = getTile(x, y).copy();
        setTile(x, y, tc, false);
        tc.setType(type);
    }

    public void setTileHeight(int x, int y, int height) {
        Tile tc = getTile(x, y).copy();
        setTile(x, y, tc, false);
        tc.setHeight(height);
    }

    public void setTileUnit(int x, int y, Unit unit) {
        Tile tc = getTile(x, y).copy();
        setTile(x, y, tc, false);
        tc.setUnit(unit);
    }

    public Map copy() {
        Map copy = new Map(this.getWidth(), this.getHeight());
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Tile t = tiles[i][j];
                copy.setTile(j, i, t.copy(), true);
            }
        }
        copy.points = points;
        copy.display = new Displays.MapDisplay(copy);
        copy.display.setScale(display.getXScale());
        copy.changed = false;
        copy.SaveFile = SaveFile;
        copy.rounds = rounds;
        copy.seed = seed;
        copy.mapRefreshed = false;
        copy.changed = this.changed;
        return copy;
    }
    private Map nextMap = null, previousMap = null;
}
