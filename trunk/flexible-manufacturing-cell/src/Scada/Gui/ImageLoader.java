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
	private static final String IMAGES_FOLDER = "images";

	private MediaTracker mediaTracker;

	/* CANVAS IMAGES */
	public Image background = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "background.png"));
	public Image plainPie = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "pie_blank.png"));
	public Image chocolatePie = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "pie_choco.png"));
	public Image caramelPie = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "pie_caramel.png"));
	public Image sensorOn = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "sensor_green_light.png"));
	public Image sensorOff = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "sensor_red_light.png"));

	public Image plasticFilmRoll = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "film_roll.png"));

	public Image stamper = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "stamper.png"));
	public Image cutter = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "cutter.png"));
	public Image emptyBlister = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "empty_blister.png"));
	public Image stampedBlister = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "stamped_blister.png"));
	public Image cutBlister = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "cut_blister.png"));

	public Image controlLightOn = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "control_light_green.png"));
	public Image controlLightOff = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "control_light_red.png"));
	public Image packageFull = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "full_blister.png"));
	public Image sealer = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "sealer_up.png"));

	public Image blister1 = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "blister-1.png"));
	public Image blister2 = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "blister-2.png"));
	public Image blister3 = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "blister-3.png"));
	public Image blister4 = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "full_blister.png"));

	public Image blisterRight = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "full_blister_ok.png"));
	public Image blisterWrong = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "full_blister_bad.png"));
	public Image blisterSealedRight = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "full_blister_sealed_ok.png"));

	public Image qualityControl = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "quality_control.png"));
	public Image stopAutomaton = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "stop_automaton.png"));

	/* APPLICATION IMAGES */
	public Image configurationButton = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "configuration_button.png"));
	public Image reportButton = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "report_button.png"));
	public Image startButton = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "start_button.png"));
	public Image stopButton = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "stop_button.png"));
	public Image emergencyButton = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "emergency_button.png"));
	public Image on = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "on.png"));
	public Image off = Toolkit.getDefaultToolkit().getImage(
			ImageLoader.class.getResource(IMAGES_FOLDER + "/"
					+ "off.png"));

	/**
	 * Constructs an image loader associated to a particular component.
	 * 
	 * @param component
	 *            The component associated to the image loader.
	 */
	public ImageLoader(Component component) {
		try {
			mediaTracker = new MediaTracker(component);
			mediaTracker.addImage(configurationButton, 0);
			mediaTracker.addImage(reportButton, 0);
			mediaTracker.addImage(startButton, 0);
			mediaTracker.addImage(stopButton, 0);
			mediaTracker.addImage(emergencyButton, 0);
			mediaTracker.addImage(on, 0);
			mediaTracker.addImage(off, 0);

			mediaTracker.addImage(background, 0);
			mediaTracker.addImage(plainPie, 0);
			mediaTracker.addImage(chocolatePie, 0);
			mediaTracker.addImage(caramelPie, 0);
			mediaTracker.addImage(sensorOn, 0);
			mediaTracker.addImage(sensorOff, 0);

			mediaTracker.addImage(plasticFilmRoll, 0);
			mediaTracker.addImage(stamper, 0);
			mediaTracker.addImage(cutter, 0);
			mediaTracker.addImage(emptyBlister, 0);
			mediaTracker.addImage(stampedBlister, 0);
			mediaTracker.addImage(cutBlister, 0);

			mediaTracker.addImage(controlLightOn, 0);
			mediaTracker.addImage(controlLightOff, 0);
			mediaTracker.addImage(blisterRight, 0);
			mediaTracker.addImage(blisterWrong, 0);
			mediaTracker.addImage(blisterSealedRight, 0);
			mediaTracker.addImage(qualityControl, 0);
			mediaTracker.addImage(stopAutomaton, 0);

			mediaTracker.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
