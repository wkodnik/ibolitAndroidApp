/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import GUI.Palette;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author Sasa
 */
public class Team implements buttonlist.BLItem<Team> {

    private static final Team
            NeutralHostile = new Team("H", "NeutralHostile", java.awt.Color.LIGHT_GRAY),
            NeutralPassive = new Team("P", "Neutral", java.awt.Color.DARK_GRAY),
            PlayerA = new Team("A","Player A",java.awt.Color.RED),
            PlayerB = new Team("B","Player B",java.awt.Color.BLUE);

    //private java.awt.image.BufferedImage[] unitImgs = new java.awt.image.BufferedImage[Unit.Type.values().length];
    private java.util.Hashtable<Unit.Type,java.awt.image.BufferedImage> unitImgs =
            new java.util.Hashtable<Unit.Type,java.awt.image.BufferedImage>();
    public static Team getPlayerA() {
        return PlayerA;
    }

    public static Team getPlayerB() {
        return PlayerB;
    }

    public static Team getNeutralHostile() {
        return NeutralHostile;
    }

    public static Team getNeutralPassive() {
        return NeutralPassive;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Team other = (Team) obj;
        if ((this.teamLetter == null) ? (other.teamLetter != null) : !this.teamLetter.equals(other.teamLetter))
            return false;
        if ((this.teamName == null) ? (other.teamName != null) : !this.teamName.equals(other.teamName))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.teamLetter != null ? this.teamLetter.hashCode() : 0);
        hash = 41 * hash + (this.teamName != null ? this.teamName.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return this.teamName;
    }
    private void initImgs(){
        for (Unit.Type type : Unit.Type.values()) {
            try {
                BufferedImage img = javax.imageio.ImageIO.read(new java.io.File("Images/Units/" + teamLetter + "/" + type.toString() + ".png"));
                unitImgs.put(type, img);
            } catch (IOException ex) {
                System.out.println("Cant load " + teamLetter + "/" + type.toString());
            }
        }

    }
    public Team(String teamLetter, String teamName, java.awt.Color teamColor) {
        this.teamLetter = teamLetter;
        this.teamName = teamName;
        this.teamColor = teamColor;
        initImgs();
    }
    private final String teamLetter,  teamName;
    private final java.awt.Color teamColor;

    public java.awt.image.BufferedImage getUnitImg(Unit.Type type){
        return unitImgs.get(type);
    }


    public String getTeamLetter() {
        return teamLetter;
    }

    @Override
    public BufferedImage getImage() {
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(32, 32, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D gps = img.createGraphics();
        gps.setColor(teamColor);
        gps.fillRect(0, 0, 32, 32);
        return img;
    }

    public static void addTeam(Team team){
        mapedit.Constants.getCurrentMap().addTeam(team);
        for (Palette palet : mapedit.Constants.palettes) {
            palet.setTeamList();
        }
    }

    @Override
    public Team getItem() {
        return this;
    }
}
