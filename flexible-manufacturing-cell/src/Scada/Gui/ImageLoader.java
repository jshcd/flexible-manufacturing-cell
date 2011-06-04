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
    public Image _sensorOff = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/sensorOff.png"));    
    public Image _sensorGreen = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/sensorGreenLight.png"));
    public Image _sensorRed = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/sensorRedLight.png"));
    public Image _gear = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/gear.png"));
    public Image _axis = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/axis.png"));
    public Image _fullPiece = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/fullPiece.png"));
    public Image _fullPieceOk = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/fullPieceOk.png"));
    public Image _fullPieceNotOk = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/fullPieceNotOk.png"));
    public Image _assembler = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/assembler.png"));
    public Image _torchDisabled = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/torchDisabled.png"));    
    public Image _torchEnabled = Toolkit.getDefaultToolkit().getImage(
            ImageLoader.class.getResource(IMAGES_FOLDER + "/torchEnabled.png"));

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
            _mediaTracker.addImage(_sensorOff, 0);
            _mediaTracker.addImage(_sensorRed, 0);
            _mediaTracker.addImage(_sensorGreen, 0);
            _mediaTracker.addImage(_gear, 0);
            _mediaTracker.addImage(_axis, 0);
            _mediaTracker.addImage(_fullPiece, 0);
            _mediaTracker.addImage(_fullPieceOk, 0);
            _mediaTracker.addImage(_fullPieceNotOk, 0);
            _mediaTracker.addImage(_on, 0);
            _mediaTracker.addImage(_off, 0);
            _mediaTracker.addImage(_torchEnabled, 0);
            _mediaTracker.addImage(_torchDisabled, 0);
            _mediaTracker.addImage(_assembler, 0);
            _mediaTracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
