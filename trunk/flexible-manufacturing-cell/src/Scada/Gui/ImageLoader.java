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
    private MediaTracker _mediaTracker;

    /* CANVAS IMAGES */
    public Image _background = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/background.png"));
    /* APPLICATION IMAGES */
    public Image _reportButton = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/report.png"));
    public Image _configurationButton = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/configuration.png"));
    public Image _startButton = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/on.png"));
    public Image _stopButton = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/off.png"));
    public Image _emergencyStopButton = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/emergencyStop.png"));
    public Image _on = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/panelOn.png"));
    public Image _off = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/panelOff.png"));

    /**
     * Constructs an image loader associated to a particular component.
     * 
     * @param component
     *            The component associated to the image loader.
     */
    public ImageLoader(Component component) {
        try {
            _mediaTracker = new MediaTracker(component);
            _mediaTracker.addImage(_background, 0);
            _mediaTracker.addImage(_reportButton, 0);
            _mediaTracker.addImage(_configurationButton, 0);
            _mediaTracker.addImage(_startButton, 0);
            _mediaTracker.addImage(_reportButton, 0);
            _mediaTracker.addImage(_stopButton, 0);
            _mediaTracker.addImage(_emergencyStopButton, 0);
            _mediaTracker.addImage(_on, 0);
            _mediaTracker.addImage(_off, 0);
            _mediaTracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("aki");
            e.printStackTrace();
        }
    }
}
