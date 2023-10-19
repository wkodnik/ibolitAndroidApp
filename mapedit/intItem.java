/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapedit;

/**
 *
 * @author Sasa
 */
public class intItem implements buttonlist.BLItem {

    private int val;
    private java.awt.image.BufferedImage img;
    @Override
    public String toString(){
        return "" + val;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final intItem other = (intItem) obj;
        if (this.val != other.val)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.val;
        return hash;
    }

    public intItem(int val) {
        this.val = val;
        try {
            img = javax.imageio.ImageIO.read(new java.io.File("Images/Icons/Cliff_" + val + ".bmp"));
        } catch (java.io.IOException ex) {
            System.out.println("No Picture exists for Selection");
        }
    }

    @Override
    public java.awt.image.BufferedImage getImage() {
        return img;
    }

    @Override
    public Object getItem() {
        return new Integer(val);
    }
}
