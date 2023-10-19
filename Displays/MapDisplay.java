/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Displays;

import Objects.Tile;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author 
 */
public class MapDisplay {

    private Objects.Map map;
    private java.awt.Rectangle bounds;
    private java.awt.image.BufferedImage image, selectionMask, whiteMask, blackMask;
    private int xScale, yScale;
    private java.util.HashSet<Objects.Tile> changies = new java.util.HashSet<Objects.Tile>();
    private boolean updateAll = false;

    public void update(Objects.Tile tile) {
        changies.add(tile);
    }

    public void clearImage() {
        image = null;
        for (int i = 0; i < map.getTiles().length; i++) {
            for (int j = 0; j < map.getTiles()[i].length; j++) {
                Tile t = map.getTiles()[i][j];
                t.setMimage(null);
            }

        }
    }

    public void updateAll() {
        changies.clear();
        for (int i = 0; i < map.getTiles().length; i++)
            for (int j = 0; j < map.getTiles()[i].length; j++)
                changies.add(map.getTiles()[i][j]);
    }

    public java.awt.image.BufferedImage getImage() {
        return image;
    }

    public Objects.Map getMap() {
        return map;
    }

    public int getXScale() {
        return xScale;
    }

    public int getYScale() {
        return yScale;
    }

    public MapDisplay(Objects.Map map) {
        this.map = map;
        map.setDisplay(this);
        xScale = yScale = 64;
        selectionMask = mapedit.Constants.selectionMask;
        blackMask = mapedit.Constants.blackMask;
        whiteMask = mapedit.Constants.whiteMask;
    }

    public java.awt.Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(java.awt.Rectangle bounds) {
        this.bounds = bounds;
    }

    //Will be extended
    public void fullRedraw() {
        if (map != mapedit.Constants.getCurrentMap()) {
            mapedit.Constants.bringToFront(map);
        }
        java.awt.Rectangle picBounds = mapedit.Constants.mapEdit.getMapBounds();
        java.awt.image.BufferedImage cimg = new java.awt.image.BufferedImage(picBounds.width, picBounds.height, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g = cimg.createGraphics();
        for (int y = picBounds.y / yScale; y <= (picBounds.y + picBounds.height) / yScale; y++) {
            for (int x = picBounds.x / xScale; x <= (picBounds.x + picBounds.width) / xScale; x++) {
                try {
                    java.awt.image.BufferedImage img = maskHeight(x, y);
                    if (!mapedit.Constants.mapEdit.isSelected(x, y)) {
                        g.drawImage(img, x * xScale - picBounds.x, y * yScale - picBounds.y, null);
                    } else {
                        g.drawImage(maskSelection(img), x * xScale - picBounds.x, y * yScale - picBounds.y, null);
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                } catch (NullPointerException e) {
                    if (x < mapedit.Constants.getCurrentMap().getWidth() && y < mapedit.Constants.getCurrentMap().getHeight())
                        e.printStackTrace();
                }
            }
        }
        this.image = cimg;
        mapedit.Constants.mapEdit.repaint();
    }

    public void setScale(int s) {
        xScale = yScale = s;
        if (mapedit.Constants.getCurrentMap() == map) {
            mapedit.Constants.mapEdit.setPicWidth(s * map.getWidth());
            mapedit.Constants.mapEdit.setPicHeight(s * map.getHeight());
            fullRedraw();
        }
    }

    private java.awt.image.BufferedImage maskHeight(int x, int y) {
        Objects.Tile t = map.getTile(x, y);
        if (!changies.contains(t) && t.getMimage() != null)
            return t.getMimage();
        java.awt.image.BufferedImage img = t.getTerrainImg();
        Objects.Tile tile = map.getTile(x, y);
        double height = tile.getHeight();
        java.awt.image.BufferedImage mask = new java.awt.image.BufferedImage(xScale, yScale, java.awt.image.BufferedImage.TRANSLUCENT);
        java.awt.image.BufferedImage sMask = (height >= 0) ? blackMask : whiteMask;
        java.awt.Graphics2D g = mask.createGraphics();
        g.drawImage(img.getScaledInstance(xScale, yScale, java.awt.Image.SCALE_DEFAULT), 0, 0, null);
        g.setFont(new Font("Monospaced", Font.PLAIN, xScale * 14 / 64 + 4));
        g.setColor(Color.GREEN);
        g.drawString("" + (int)(height * 5), 4, xScale * 14 / 64 + 4);
        g.setColor(new java.awt.Color(0, 255, 0));
        if (x > 0 && map.getTile(x, y).getHeight() != map.getTile(x - 1, y).getHeight())
            for (int i = 0; i < 3; i++)
                g.drawLine(i, 0, i, yScale);
        if (y > 0 && map.getTile(x, y).getHeight() != map.getTile(x, y - 1).getHeight())
            for (int i = 0; i < 3; i++)
                g.drawLine(0, i, xScale, i);
        if (y > 0 && x > 0
                && map.getTile(x - 1, y).getHeight() != map.getTile(x - 1, y - 1).getHeight()
                && map.getTile(x, y - 1).getHeight() != map.getTile(x - 1, y - 1).getHeight()) {
            for (int i = 0; i < 3; i++)
                g.drawLine(0, i, 2, i);
        }
        g.setColor(new java.awt.Color(255, 255, 255));
        
        if (x > 0 && mapedit.Constants.grid)
            g.drawLine(0, 0, 0, yScale);
        if (y > 0 && mapedit.Constants.grid)
            g.drawLine(0, 0, xScale, 0);
        if (y > 0 && x > 0 && mapedit.Constants.grid) {
            g.drawLine(0, 0, 0, 0);
        }
        if (map.getTile(x, y).getUnit() != null) {
            g.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_ATOP, 1));
            g.drawImage(map.getTile(x, y).getUnit().getImage().getScaledInstance(xScale, yScale, java.awt.Image.SCALE_DEFAULT), 0, 0, null);
        }
        //g.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, (float) (0.75 * Math.abs(height - 16) / 16)));
        
        g.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, (float) (0.75 * Math.abs(height) / 20)));
        g.drawImage(sMask, 0, 0, null);
        t.setMimage(mask);
        changies.remove(t);
        return mask;
    }

    private java.awt.image.BufferedImage maskSelection(java.awt.image.BufferedImage image) {
        java.awt.image.BufferedImage mask = new java.awt.image.BufferedImage(xScale, yScale, java.awt.image.BufferedImage.TRANSLUCENT);
        java.awt.Graphics2D g = mask.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, (float) 0.3));
        g.drawImage(selectionMask, 0, 0, null);
        return mask;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
