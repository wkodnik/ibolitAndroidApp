/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author sasa
 */
public class Tile implements buttonlist.BLItem<Tile> {

    private int x, y, rdn = new java.util.Random().nextInt(4);
    private BufferedImage image = null, mimage = null;
    private Map map = null;
    private static final String typeNames[] = {"Water", "Dirt", "Rock"};
    private static final java.awt.image.BufferedImage[] DM = new BufferedImage[16], WA = new BufferedImage[16], WB = new BufferedImage[16], WBCL = new BufferedImage[16], WBCP = new BufferedImage[16], WBCR = new BufferedImage[16], WCA = new BufferedImage[16], WCD = new BufferedImage[16], WCEBL = new BufferedImage[16], WCEBR = new BufferedImage[16], WCETL = new BufferedImage[16], WCETR = new BufferedImage[16], WCI = new BufferedImage[16], WCL = new BufferedImage[16], WCR = new BufferedImage[16], WCT = new BufferedImage[16], WBL = new BufferedImage[16], WBLP = new BufferedImage[16], WBR = new BufferedImage[16], WBRP = new BufferedImage[16], WCB = new BufferedImage[16], WCBL = new BufferedImage[16], WCBR = new BufferedImage[16], WCTL = new BufferedImage[16], WCTR = new BufferedImage[16], WEB = new BufferedImage[16], WEL = new BufferedImage[16], WER = new BufferedImage[16], WET = new BufferedImage[16], WH = new BufferedImage[16], WL = new BufferedImage[16], WLCB = new BufferedImage[16], WLCT = new BufferedImage[16], WLCP = new BufferedImage[16], WM = new BufferedImage[16], WR = new BufferedImage[16], WRCB = new BufferedImage[16], WRCP = new BufferedImage[16], WRCT = new BufferedImage[16], WT = new BufferedImage[16], WTCL = new BufferedImage[16], WTCP = new BufferedImage[16], WTCR = new BufferedImage[16], WTL = new BufferedImage[16], WTLP = new BufferedImage[16], WTR = new BufferedImage[16], WTRP = new BufferedImage[16], WV = new BufferedImage[16];
    private static final String[] waterNames = {"WA", "WB", "WBCL", "WBCP", "WBCR", "WBL", "WBLP", "WBR", "WBRP", "WCA", "WCB", "WCBL", "WCBR", "WCD", "WCEBL", "WCEBR", "WCETL", "WCETR", "WCI", "WCL", "WCR", "WCT", "WCTL", "WCTR", "WEB", "WEL", "WER", "WET", "WH", "WL", "WLCB", "WLCT", "WLCP", "WM", "WR", "WRCB", "WRCP", "WRCT", "WT", "WTCL", "WTCP", "WTCR", "WTL", "WTLP", "WTR", "WTRP", "WV"};
    private static final BufferedImage[][] DirtImgs = {DM}, WaterImgs = {WA, WB, WBCL, WBCP, WBCR, WBL, WBLP, WBR, WBRP, WCA, WCB, WCBL, WCBR, WCD, WCEBL, WCEBR, WCETL, WCETR, WCI, WCL, WCR, WCT, WCTL, WCTR, WEB, WEL, WER, WET, WH, WL, WLCB, WLCT, WLCP, WM, WR, WRCB, WRCP, WRCT, WT, WTCL, WTCP, WTCR, WTL, WTLP, WTR, WTRP, WV};

    public void setMap(Map map) {
        this.map = map;
        if (map != null && map.getDisplay() != null) {
            map.getDisplay().update(this);
        }
    }

    public BufferedImage getMimage() {
        return mimage;
    }

    public void setMimage(BufferedImage mimage) {
        this.mimage = mimage;
    }

    public static void initImgs() {
        for (int i = 0; i < 16; i++) {
            int l = 0, r = 0, c = 0;
            boolean on = true;
            try {
                DM[i] = ImageIO.read(new File("Images/Terrain/Dirt/DM" + (i + 1) + ".bmp"));
                if (on) {
                    r++;
                } else {
                    c = 0;
                    l = r = i;
                    on = true;
                }
            } catch (IOException ex) {
                if (i == 0) {
                    ex.printStackTrace();
                    break;
                }
                DM[i] = DM[l + c];
                c = (c + 1) % (r - l + 1);
                on = false;
                System.out.println("cant load DM" + (i + 1) + ".bmp");
            }
        }
        for (int i = 0; i < WaterImgs.length; i++) {
            BufferedImage[] imglst = WaterImgs[i];
            for (int j = 0; j < 16; j++) {
                int l = 0, r = 0, c = 0;
                boolean on = true;
                try {
                    imglst[j] = ImageIO.read(new File("Images/Terrain/Water/" + waterNames[i] + (j + 1) + ".bmp"));
                    if (on) {
                        r++;
                    } else {
                        c = 0;
                        l = r = j;
                        on = true;
                    }
                } catch (java.io.IOException e) {
                    if (j == 0) {
                        e.printStackTrace();
                        break;
                    }
                    imglst[j] = imglst[l + c];
                    c = (c + 1) % (r - l + 1);
                    on = false;
                    System.out.println("cant load " + waterNames[i] + (j + 1) + ".bmp");
                }
            }
        }
    }

    public enum Type {

        WATER(0), GROUND(1);
        private String name;
        private java.awt.image.BufferedImage icoImg, cImg;

        public BufferedImage getCImg() {
            return cImg;
        }

        private Type(int typex) {
            name = typeNames[typex];
            try {
                icoImg = ImageIO.read(new java.io.File("Images/Tiles/" + name + "/ico.gif"));
                cImg = javax.imageio.ImageIO.read(new java.io.File("Images/Tiles/" + name + "/c.gif"));
            } catch (java.io.IOException ex) {
                System.out.println("No Picture exists for tile " + name);
            }
        }

        @Override
        public String toString() {
            return name;
        }

        public java.awt.image.BufferedImage getIcoImg() {
            return icoImg;
        }

        public String getName() {
            return name;
        }
    }

    public void randomizeIndex() {
        rdn = new java.util.Random().nextInt(16);
    }

    public int getRandomIndex() {
        return rdn;
    }

    public int getX() {
        return x;
    }

    protected void setType(Type type) {
        this.type = type;
        if (map != null) {
            if (tU() != null) 
                tU().refreshImg();
            if (tD() != null)
                tD().refreshImg();
            if (tL() != null)
                tL().refreshImg();
            if (tR() != null)
                tR().refreshImg();
            if (tUL() != null)
                tUL().refreshImg();
            if (tUR() != null)
                tUR().refreshImg();
            if (tDL() != null)
                tDL().refreshImg();
            if (tDR() != null)
                tDR().refreshImg();
            randomizeIndex();
            refreshImg();
            map.getDisplay().update(this);
        }
    }

    protected void setUnit(Unit unit) {
        this.unit = unit;
        if (map != null)
            map.getDisplay().update(this);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private Tile tU() {
        return map.getTile(x, y + 1);
    }

    private Tile tD() {
        return map.getTile(x, y - 1);
    }

    private Tile tL() {
        return map.getTile(x - 1, y);
    }

    private Tile tR() {
        return map.getTile(x + 1, y);
    }

    private Tile tUL() {
        return map.getTile(x - 1, y + 1);
    }

    private Tile tUR() {
        return map.getTile(x + 1, y + 1);
    }

    private Tile tDL() {
        return map.getTile(x - 1, y - 1);
    }

    private Tile tDR() {
        return map.getTile(x + 1, y - 1);
    }

    @Override
    public String toString() {
        try {
            return type.getName();
        } catch (NullPointerException e) {
            return unit.getType().toString();
        }
    }

    public String getTooltipText() {
        return "x='" + x + "', y='" + y + "', r='" + rockHeight + "'";
    }
    protected int rockHeight;
    private Type type;
    private Unit unit;

    protected void setHeight(int height) {

        this.rockHeight = height;

        if (map != null) {
            map.getDisplay().update(this);
            if (x < map.getWidth() - 1)
                map.getDisplay().update(map.getTile(x + 1, y));
            if (y < map.getHeight() - 1)
                map.getDisplay().update(map.getTile(x, y + 1));
            if (x < map.getWidth() - 1 && y < map.getHeight() - 1)
                map.getDisplay().update(map.getTile(x + 1, y + 1));
        }
    }

    public Tile(int rockHeight, Type type, Unit unit) {
        this.rockHeight = rockHeight;
        this.type = type;
        this.unit = unit;
    }

    private Tile(int rockHeight, Type type, Unit unit, int rand) {
        this.rockHeight = rockHeight;
        this.type = type;
        this.unit = unit;
        this.rdn = rand;
    }

    public java.awt.image.BufferedImage getBaseImg() {
        return type.icoImg;
    }

    public int getHeight() {
        return this.rockHeight;
    }

    public Type getType() {
        return type;
    }

    public Unit getUnit() {
        return unit;
    }

    public java.awt.image.BufferedImage getTerrainImg() {
        if (image != null) {
            return image;
        }
        return type.getCImg();
    }

    public Tile copy() {
        return new Tile(rockHeight, type, unit, rdn);
    }

    @Override
    public java.awt.image.BufferedImage getImage() {
        try {
            return type.icoImg;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Tile getItem() {
        return this;
    }

    public void refreshImg() {
        if (type == Type.GROUND) {
            image = DM[rdn];
        }
        if (type == Type.WATER) {
            if (tU() == null) {
                if (tL() == null) {
                    if (tR().type == type.GROUND && tD().type == type.GROUND)
                        image = WTR[rdn];
                    else if (tR().type == type.WATER && tD().type == type.GROUND)
                        image = WT[rdn];
                    else if (tR().type == type.GROUND && tD().type == type.WATER)
                        image = WR[rdn];
                    else if (tR().type == type.WATER && tD().type == type.WATER) {
                        if (tDR().type == type.WATER)
                            image = WM[rdn];
                        else if (tDR().type == type.GROUND)
                            image = WCTR[rdn];
                    }
                } else if (tR() == null) {
                    if (tL().type == type.GROUND && tD().type == type.GROUND)
                        image = WTL[rdn];
                    else if (tL().type == type.WATER && tD().type == type.GROUND)
                        image = WT[rdn];
                    else if (tL().type == type.GROUND && tD().type == type.WATER)
                        image = WL[rdn];
                    else if (tL().type == type.WATER && tD().type == type.WATER) {
                        if (tDL().type == type.WATER)
                            image = WM[rdn];
                        else if (tDL().type == type.GROUND)
                            image = WCTL[rdn];
                    }
                } else {
                    if (tR().type == type.GROUND && tD().type == type.GROUND && tL().type == type.GROUND)
                        image = WEB[rdn];
                    else if (tR().type == type.WATER && tD().type == type.GROUND && tL().type == type.GROUND)
                        image = WTL[rdn];
                    else if (tR().type == type.GROUND && tD().type == type.WATER && tL().type == type.GROUND)
                        image = WV[rdn];
                    else if (tR().type == type.GROUND && tD().type == type.GROUND && tL().type == type.WATER)
                        image = WTR[rdn];
                    else if (tR().type == type.WATER && tD().type == type.GROUND && tL().type == type.WATER)
                        image = WT[rdn];
                    else if (tR().type == type.GROUND && tD().type == type.WATER && tL().type == type.WATER) {
                        if (tDL().type == type.GROUND)
                            image = WRCT[rdn];
                        else if (tDL().type == type.WATER)
                            image = WR[rdn];
                    } else if (tR().type == type.WATER && tD().type == type.WATER && tL().type == type.GROUND) {
                        if (tDR().type == type.GROUND)
                            image = WLCT[rdn];
                        else if (tDR().type == type.WATER)
                            image = WL[rdn];
                    } else if (tR().type == type.WATER && tD().type == type.WATER && tL().type == type.WATER) {
                        if (tDR().type == type.GROUND && tDL().type == type.GROUND)
                            image = WCT[rdn];
                        else if (tDR().type == type.WATER && tDL().type == type.GROUND)
                            image = WCTL[rdn];
                        else if (tDR().type == type.GROUND && tDL().type == type.WATER)
                            image = WCTR[rdn];
                        else if (tDR().type == type.WATER && tDL().type == type.WATER)
                            image = WM[rdn];
                    }
                }
            } else if (tR() == null) {
                if (tU() == null) {
                    if (tL().type == type.GROUND && tD().type == type.GROUND)
                        image = WTL[rdn];
                    else if (tL().type == type.WATER && tD().type == type.GROUND)
                        image = WT[rdn];
                    else if (tL().type == type.GROUND && tD().type == type.WATER)
                        image = WL[rdn];
                    else if (tL().type == type.WATER && tD().type == type.WATER) {
                        if (tDL().type == type.WATER)
                            image = WM[rdn];
                        else if (tDL().type == type.GROUND)
                            image = WCTL[rdn];
                    }
                } else if (tD() == null) {
                    if (tL().type == type.GROUND && tU().type == type.GROUND)
                        image = WBL[rdn];
                    else if (tL().type == type.WATER && tU().type == type.GROUND)
                        image = WB[rdn];
                    else if (tL().type == type.GROUND && tU().type == type.WATER)
                        image = WL[rdn];
                    else if (tL().type == type.WATER && tU().type == type.WATER) {
                        if (tUL().type == type.WATER)
                            image = WM[rdn];
                        else if (tUL().type == type.GROUND)
                            image = WCBL[rdn];
                    }
                } else {
                    if (tU().type == type.GROUND && tL().type == type.GROUND && tD().type == type.GROUND)
                        image = WER[rdn];
                    else if (tU().type == type.WATER && tL().type == type.GROUND && tD().type == type.GROUND)
                        image = WTL[rdn];
                    else if (tU().type == type.GROUND && tL().type == type.WATER && tD().type == type.GROUND)
                        image = WH[rdn];
                    else if (tU().type == type.GROUND && tL().type == type.GROUND && tD().type == type.WATER)
                        image = WBL[rdn];
                    else if (tU().type == type.WATER && tL().type == type.GROUND && tD().type == type.WATER)
                        image = WL[rdn];
                    else if (tU().type == type.GROUND && tL().type == type.WATER && tD().type == type.WATER) {
                        if (tDL().type == type.GROUND)
                            image = WBCL[rdn];
                        else if (tDL().type == type.WATER)
                            image = WB[rdn];
                    } else if (tU().type == type.WATER && tL().type == type.WATER && tD().type == type.GROUND) {
                        if (tUL().type == type.GROUND)
                            image = WTCL[rdn];
                        else if (tUL().type == type.WATER)
                            image = WT[rdn];
                    } else if (tU().type == type.WATER && tL().type == type.WATER && tD().type == type.WATER) {
                        if (tDL().type == type.WATER && tUL().type == type.WATER)
                            image = WM[rdn];
                        else if (tDL().type == type.GROUND && tUL().type == type.WATER)
                            image = WCTL[rdn];
                        else if (tDL().type == type.WATER && tUL().type == type.GROUND)
                            image = WCBL[rdn];
                        else if (tDL().type == type.GROUND && tUL().type == type.GROUND)
                            image = WCL[rdn];
                    }
                }
            } else if (tD() == null) {
                if (tL() == null) {
                    if (tR().type == type.GROUND && tU().type == type.GROUND)
                        image = WBR[rdn];
                    else if (tR().type == type.WATER && tU().type == type.GROUND)
                        image = WB[rdn];
                    else if (tR().type == type.GROUND && tU().type == type.WATER)
                        image = WR[rdn];
                    else if (tR().type == type.WATER && tU().type == type.WATER) {
                        if (tUR().type == type.WATER)
                            image = WM[rdn];
                        else if (tUR().type == type.GROUND)
                            image = WCBR[rdn];
                    }
                } else if (tR() == null) {
                    if (tL().type == type.GROUND && tU().type == type.GROUND)
                        image = WBL[rdn];
                    else if (tL().type == type.WATER && tU().type == type.GROUND)
                        image = WB[rdn];
                    else if (tL().type == type.GROUND && tU().type == type.WATER)
                        image = WL[rdn];
                    else if (tL().type == type.WATER && tU().type == type.WATER) {
                        if (tUL().type == type.WATER)
                            image = WM[rdn];
                        else if (tUL().type == type.GROUND)
                            image = WCBL[rdn];
                    }
                } else {
                    if (tR().type == type.GROUND && tU().type == type.GROUND && tL().type == type.GROUND)
                        image = WET[rdn];
                    else if (tR().type == type.WATER && tU().type == type.GROUND && tL().type == type.GROUND)
                        image = WBL[rdn];
                    else if (tR().type == type.GROUND && tU().type == type.WATER && tL().type == type.GROUND)
                        image = WV[rdn];
                    else if (tR().type == type.GROUND && tU().type == type.GROUND && tL().type == type.WATER)
                        image = WBR[rdn];
                    else if (tR().type == type.WATER && tU().type == type.GROUND && tL().type == type.WATER)
                        image = WB[rdn];
                    else if (tR().type == type.GROUND && tU().type == type.WATER && tL().type == type.WATER) {
                        if (tUL().type == type.GROUND)
                            image = WRCB[rdn];
                        else if (tUL().type == type.WATER)
                            image = WR[rdn];
                    } else if (tR().type == type.WATER && tU().type == type.WATER && tL().type == type.GROUND) {
                        if (tUR().type == type.GROUND)
                            image = WLCB[rdn];
                        else if (tUR().type == type.WATER)
                            image = WL[rdn];
                    } else if (tR().type == type.WATER && tU().type == type.WATER && tL().type == type.WATER) {
                        if (tUR().type == type.GROUND && tUL().type == type.GROUND)
                            image = WCB[rdn];
                        else if (tUR().type == type.WATER && tUL().type == type.GROUND)
                            image = WCBL[rdn];
                        else if (tUR().type == type.GROUND && tUL().type == type.WATER)
                            image = WCBR[rdn];
                        else if (tUR().type == type.WATER && tUL().type == type.WATER)
                            image = WM[rdn];
                    }
                }
            } else if (tL() == null) {
                if (tU() == null) {
                    if (tR().type == type.GROUND && tD().type == type.GROUND)
                        image = WTR[rdn];
                    else if (tR().type == type.WATER && tD().type == type.GROUND)
                        image = WT[rdn];
                    else if (tR().type == type.GROUND && tD().type == type.WATER)
                        image = WR[rdn];
                    else if (tR().type == type.WATER && tD().type == type.WATER) {
                        if (tDR().type == type.WATER)
                            image = WM[rdn];
                        else if (tDR().type == type.GROUND)
                            image = WCTR[rdn];
                    }
                } else if (tD() == null) {
                    if (tR().type == type.GROUND && tU().type == type.GROUND)
                        image = WBR[rdn];
                    else if (tR().type == type.WATER && tU().type == type.GROUND)
                        image = WB[rdn];
                    else if (tR().type == type.GROUND && tU().type == type.WATER)
                        image = WR[rdn];
                    else if (tR().type == type.WATER && tU().type == type.WATER) {
                        if (tUR().type == type.WATER)
                            image = WM[rdn];
                        else if (tUR().type == type.GROUND)
                            image = WCBR[rdn];
                    }
                } else {
                    if (tU().type == type.GROUND && tR().type == type.GROUND && tD().type == type.GROUND)
                        image = WEL[rdn];
                    else if (tU().type == type.WATER && tR().type == type.GROUND && tD().type == type.GROUND)
                        image = WTR[rdn];
                    else if (tU().type == type.GROUND && tR().type == type.WATER && tD().type == type.GROUND)
                        image = WH[rdn];
                    else if (tU().type == type.GROUND && tR().type == type.GROUND && tD().type == type.WATER)
                        image = WBR[rdn];
                    else if (tU().type == type.WATER && tR().type == type.GROUND && tD().type == type.WATER)
                        image = WR[rdn];
                    else if (tU().type == type.GROUND && tR().type == type.WATER && tD().type == type.WATER) {
                        if (tDR().type == type.GROUND)
                            image = WBCR[rdn];
                        else if (tDR().type == type.WATER)
                            image = WB[rdn];
                    } else if (tU().type == type.WATER && tR().type == type.WATER && tD().type == type.GROUND) {
                        if (tUR().type == type.GROUND)
                            image = WTCR[rdn];
                        else if (tUR().type == type.WATER)
                            image = WT[rdn];
                    } else if (tU().type == type.WATER && tR().type == type.WATER && tD().type == type.WATER) {
                        if (tDR().type == type.WATER && tUR().type == type.WATER)
                            image = WM[rdn];
                        else if (tDR().type == type.GROUND && tUR().type == type.WATER)
                            image = WCTR[rdn];
                        else if (tDR().type == type.WATER && tUR().type == type.GROUND)
                            image = WCBR[rdn];
                        else if (tDR().type == type.GROUND && tUR().type == type.GROUND)
                            image = WCR[rdn];
                    }
                }
            } else if (tU().type == type.GROUND && tR().type == type.GROUND && tD().type == type.GROUND && tL().type == type.GROUND) {
                image = WA[rdn];
            } else if (tU().type == type.WATER && tR().type == type.GROUND && tD().type == type.GROUND && tL().type == type.GROUND) {
                image = WEB[rdn];
            } else if (tU().type == type.GROUND && tR().type == type.WATER && tD().type == type.GROUND && tL().type == type.GROUND) {
                image = WER[rdn];
            } else if (tU().type == type.GROUND && tR().type == type.GROUND && tD().type == type.WATER && tL().type == type.GROUND) {
                image = WET[rdn];
            } else if (tU().type == type.GROUND && tR().type == type.GROUND && tD().type == type.GROUND && tL().type == type.WATER) {
                image = WEL[rdn];
            } else if (tU().type == type.WATER && tR().type == type.GROUND && tD().type == type.WATER && tL().type == type.GROUND) {
                image = WV[rdn];
            } else if (tU().type == type.GROUND && tR().type == type.WATER && tD().type == type.GROUND && tL().type == type.WATER) {
                image = WH[rdn];
            } else if (tU().type == type.GROUND && tR().type == type.GROUND && tD().type == type.WATER && tL().type == type.WATER) {
                if (tDL().type == type.WATER) {
                    image = WBR[rdn];
                } else {
                    image = WBRP[rdn];
                }
            } else if (tU().type == type.GROUND && tR().type == type.WATER && tD().type == type.WATER && tL().type == type.GROUND) {
                if (tDR().type == type.WATER) {
                    image = WBL[rdn];
                } else {
                    image = WBLP[rdn];
                }
            } else if (tU().type == type.WATER && tR().type == type.WATER && tD().type == type.GROUND && tL().type == type.GROUND) {
                if (tUR().type == type.WATER) {
                    image = WTL[rdn];
                } else {
                    image = WTLP[rdn];
                }
            } else if (tU().type == type.WATER && tR().type == type.GROUND && tD().type == type.GROUND && tL().type == type.WATER) {
                if (tUL().type == type.WATER) {
                    image = WTR[rdn];
                } else {
                    image = WTRP[rdn];
                }
            } else if (tU().type == type.GROUND && tR().type == type.WATER && tD().type == type.WATER && tL().type == type.WATER) {
                if (tDR().type == type.WATER && tDL().type == type.WATER) {
                    image = WB[rdn];
                } else if (tDR().type == type.GROUND && tDL().type == type.WATER) {
                    image = WBCR[rdn];
                } else if (tDR().type == type.WATER && tDL().type == type.GROUND) {
                    image = WBCL[rdn];
                } else if (tDR().type == type.GROUND && tDL().type == type.GROUND) {
                    image = WBCP[rdn];
                }
            } else if (tU().type == type.WATER && tR().type == type.GROUND && tD().type == type.WATER && tL().type == type.WATER) {
                if (tUL().type == type.WATER && tDL().type == type.WATER) {
                    image = WR[rdn];
                } else if (tUL().type == type.GROUND && tDL().type == type.WATER) {
                    image = WRCB[rdn];
                } else if (tUL().type == type.WATER && tDL().type == type.GROUND) {
                    image = WRCT[rdn];
                } else if (tUL().type == type.GROUND && tDL().type == type.GROUND) {
                    image = WRCP[rdn];
                }
            } else if (tU().type == type.WATER && tR().type == type.WATER && tD().type == type.GROUND && tL().type == type.WATER) {
                if (tUR().type == type.WATER && tUL().type == type.WATER) {
                    image = WT[rdn];
                } else if (tUR().type == type.GROUND && tUL().type == type.WATER) {
                    image = WTCR[rdn];
                } else if (tUR().type == type.WATER && tUL().type == type.GROUND) {
                    image = WTCL[rdn];
                } else if (tUR().type == type.GROUND && tUL().type == type.GROUND) {
                    image = WTCP[rdn];
                }
            } else if (tU().type == type.WATER && tR().type == type.WATER && tD().type == type.WATER && tL().type == type.GROUND) {
                if (tDR().type == type.WATER && tUR().type == type.WATER) {
                    image = WL[rdn];
                } else if (tDR().type == type.GROUND && tUR().type == type.WATER) {
                    image = WLCT[rdn];
                } else if (tDR().type == type.WATER && tUR().type == type.GROUND) {
                    image = WLCB[rdn];
                } else if (tDR().type == type.GROUND && tUR().type == type.GROUND) {
                    image = WLCP[rdn];
                }
            } else if (tU().type == type.WATER && tR().type == type.WATER && tD().type == type.WATER && tL().type == type.WATER) {
                if (tUR().type == type.WATER && tUL().type == type.WATER && tDR().type == type.WATER && tDL().type == type.WATER)
                    image = WM[rdn];
                else if (tUR().type == type.GROUND && tUL().type == type.WATER && tDR().type == type.WATER && tDL().type == type.WATER)
                    image = WCBR[rdn];
                else if (tUR().type == type.WATER && tUL().type == type.GROUND && tDR().type == type.WATER && tDL().type == type.WATER)
                    image = WCBL[rdn];
                else if (tUR().type == type.WATER && tUL().type == type.WATER && tDR().type == type.GROUND && tDL().type == type.WATER)
                    image = WCTR[rdn];
                else if (tUR().type == type.WATER && tUL().type == type.WATER && tDR().type == type.WATER && tDL().type == type.GROUND)
                    image = WCTL[rdn];
                else if (tUR().type == type.GROUND && tUL().type == type.WATER && tDR().type == type.WATER && tDL().type == type.GROUND)
                    image = WCD[rdn];
                else if (tUR().type == type.WATER && tUL().type == type.GROUND && tDR().type == type.GROUND && tDL().type == type.WATER)
                    image = WCI[rdn];
                else if (tUR().type == type.GROUND && tUL().type == type.GROUND && tDR().type == type.WATER && tDL().type == type.WATER)
                    image = WCB[rdn];
                else if (tUR().type == type.WATER && tUL().type == type.GROUND && tDR().type == type.WATER && tDL().type == type.GROUND)
                    image = WCL[rdn];
                else if (tUR().type == type.WATER && tUL().type == type.WATER && tDR().type == type.GROUND && tDL().type == type.GROUND)
                    image = WCT[rdn];
                else if (tUR().type == type.GROUND && tUL().type == type.WATER && tDR().type == type.GROUND && tDL().type == type.WATER)
                    image = WCR[rdn];
                else if (tUR().type == type.GROUND && tUL().type == type.GROUND && tDR().type == type.GROUND && tDL().type == type.WATER)
                    image = WCETL[rdn];
                else if (tUR().type == type.GROUND && tUL().type == type.GROUND && tDR().type == type.WATER && tDL().type == type.GROUND)
                    image = WCETR[rdn];
                else if (tUR().type == type.GROUND && tUL().type == type.WATER && tDR().type == type.GROUND && tDL().type == type.GROUND)
                    image = WCEBL[rdn];
                else if (tUR().type == type.WATER && tUL().type == type.GROUND && tDR().type == type.GROUND && tDL().type == type.GROUND)
                    image = WCEBR[rdn];
                else if (tUR().type == type.GROUND && tUL().type == type.GROUND && tDR().type == type.GROUND && tDL().type == type.GROUND)
                    image = WCA[rdn];
            }
        }
        map.getDisplay().update(this);
    }
}
