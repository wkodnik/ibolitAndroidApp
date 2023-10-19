/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package misc;

/**
 *
 * @author Sasa
 */
public class Splash extends java.awt.Frame {

    public Splash() throws InterruptedException{
        super("MapEdit");
        this.setSize(300, 300);

        final java.awt.SplashScreen splash = java.awt.SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        } else {
            Thread.sleep(10000);
        }
        this.dispose();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

}
