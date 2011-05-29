package Scada.Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import Auxiliar.Constants;
import Element.Station.AssemblyStation;
import Element.Station.QualityControlStation;
import Element.Station.WeldingStation;

/**
 * Canvas where the factory simulation is displayed.
 *
 * @author
 */
public class Canvas_1 extends JPanel {
	private static final long serialVersionUID = 1L;

	private ImageLoader imageLoader;

        private AssemblyStation assembly;
        private QualityControlStation quality;
        private WeldingStation welding;


	private boolean emergencyStopped;

	/**
	 * Creates a canvas where the factory simulation is displayed.
	 *
	 * @param imageLoader
	 *            The image loader in charge of retrieving the images to paint
	 *            the canvas.
	 */
	public Canvas_1(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
		Dimension size = new Dimension(Constants.CANVAS_WIDTH,
				Constants.CANVAS_HEIGHT);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);

		emergencyStopped = false;
	}

    public void setAssembly(AssemblyStation assembly) {
        this.assembly = assembly;
    }

    public void setQuality(QualityControlStation quality) {
        this.quality = quality;
    }

    public void setWelding(WeldingStation welding) {
        this.welding = welding;
    }



	/**
	 * Tells the canvas whether the system is at an emergency stop or not.
	 *
	 * @param emergencyStopped
	 *            Whether the system is at an emergency stop or not.
	 */
	public void setEmergencyStopped(boolean emergencyStopped) {
		this.emergencyStopped = emergencyStopped;
		repaint();
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		// Erases the panel
		g.drawImage(imageLoader.prueba, 0, 0, null);

		// Paints the different automata
		//paintMasterAutomaton(g);
		paintAssembly(g);

	}

	private void paintAssembly(Graphics g) {
		if (assembly != null) {
			// Painting the pieces
			for (Element.Piece.Piece pac : assembly.getPieces()) {

					g.drawImage(imageLoader.prueba, 50, 45, null);

			}

			// Painting the quality control
			g.drawImage(imageLoader.prueba,
					Constants.QUALITY_POSITION.x, Constants.QUALITY_POSITION.y,
					null);

            }

	}




}