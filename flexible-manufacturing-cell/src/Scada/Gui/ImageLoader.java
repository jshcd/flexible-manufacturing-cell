package Scada.Gui;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

/**
 * Loads all the images required for the application.
 * 
 * @author
 */
public class ImageLoader {

    private static final String IMAGES_FOLDER = "Images";
    private MediaTracker mediaTracker;

    /* CANVAS IMAGES */
    public Image background = Toolkit.getDefaultToolkit().getImage(
    ImageLoader.class.getResource(IMAGES_FOLDER + "/background3.png"));
    
    /* APPLICATION IMAGES */
    public Image reportButton = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER+"/report.png"));
    public Image configurationButton = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER+"/configuration.png"));
    public Image startButton = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER+"/on.png"));
    public Image stopButton = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER+"/off.png"));
    public Image emergencyStopButton = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER+"/emergencyStop.png"));
    public Image on = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER+"/panelOn.png"));
    public Image off = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER+"/panelOff.png"));
    
    /**
     * Constructs an image loader associated to a particular component.
     * 
     * @param component
     *            The component associated to the image loader.
     */
    public ImageLoader(Component component) {
        try {
            mediaTracker = new MediaTracker(component);
            mediaTracker.addImage(background,0);
            mediaTracker.addImage(reportButton,0);
            mediaTracker.addImage(configurationButton, 0);
            mediaTracker.addImage(startButton, 0);
            mediaTracker.addImage(reportButton, 0);
            mediaTracker.addImage(stopButton, 0);
            mediaTracker.addImage(emergencyStopButton, 0);
            mediaTracker.addImage(on, 0);
            mediaTracker.addImage(off, 0);
            mediaTracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         catch (Exception e) {
             System.out.println("aki");
             e.printStackTrace();
        }
    }
}
