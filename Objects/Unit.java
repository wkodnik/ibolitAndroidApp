/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import java.io.IOException;

/**
 *
 * @author sasa
 */
public class Unit implements buttonlist.BLItem<Unit> {

    private final Type type;
    private final Team team;
    private java.awt.image.BufferedImage image;

    public static void initImgs() {
        for (Type type : Type.values()) {
            try {
                type.img = javax.imageio.ImageIO.read(new java.io.File("Images/Units/A/" + type.toString() + ".png"));
            } catch (IOException ex) {
                System.out.println("Can't find image for " + type.toString());
            }
        }
    }

    @Override
    public Unit getItem() {
        return this;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    public static enum Type {

        //ARCHON("A"), TURRET("U"), SOLDIER("L"), CHAINER("Z"), WOUT("W"), AURA("Q"), TELEPORTER("T"), COMM("C");
        HQ("A"), TOWER("B");
        
        private java.awt.image.BufferedImage img;

        Type(String rep) {
            this.rep = rep;
        }

        public static Type getTypeByName(String name) {
            for (Type type : Type.values()) {
                if (type.toString().equals(name))
                    return type;
            }
            return null;
        }

        public java.awt.image.BufferedImage getImg() {
            return img;
        }

        public String getRep() {
            return rep;
        }
        private String rep;
    }

    public Unit(Type type, Team team) {
        this.type = type;
        this.team = team;
        this.image = type.img;
    }

    public Team getTeam() {
        return team;
    }

    public String getRep() {
        return type.rep;
    }

    public Type getType() {
        return type;
    }

    public Unit copy() {
        Unit u = new Unit(type, team);
        return u;
    }

    public Unit(String rep, Team team) {
        this.team = team;
        for (Type itype : Type.values()) {
            if (itype.rep.equals(rep)) {
                this.type = itype;
                this.image = type.img;
                return;
            }
        }
        throw new java.lang.IllegalArgumentException();
    }

    @Override
    public java.awt.image.BufferedImage getImage() {
        return team.getUnitImg(type);
    }

    public void refreshImage() {
        //TODO REFRESH IMAGE
    }
}
