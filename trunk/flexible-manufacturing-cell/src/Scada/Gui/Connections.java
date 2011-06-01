package Scada.Gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import Auxiliar.Constants;
import net.miginfocom.swing.MigLayout;

/**
 * Displays the automata connectivity status.
 * 
 * @author 
 */
public class Connections extends JPanel {
	private static final long serialVersionUID = 1L;

	
	/* GUI COMPONENTS */
	private JLabel assembly, welding, quality, master;
	private JLabel assemblyStatus, weldingStatus, qualityStatus, masterStatus;

	private ImageLoader imageLoader;

	/**
	 * Constructs a connections panel.
	 * 
	 * @param imageLoader
	 *            The image loader in charge of retrieving the images to display
	 *            the connections status.
	 */
	public Connections(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
		createComponents();
		layoutComponents();
	}

	private void createComponents() {
		welding = new JLabel(Constants.WELDING_AUTOMATON_LABEL);
		assembly = new JLabel(Constants.ASSEMBLY_AUTOMATON_LABEL);
		quality = new JLabel(Constants.QUALITY_AUTOMATON_LABEL);
		master = new JLabel(Constants.MASTER_SCADA_AUTOMATON_LABEL);

		weldingStatus = new JLabel(new ImageIcon(imageLoader.off));
		assemblyStatus = new JLabel(new ImageIcon(imageLoader.off));
		qualityStatus = new JLabel(new ImageIcon(
				imageLoader.off));
		masterStatus = new JLabel(new ImageIcon(imageLoader.on));
	}

	private void layoutComponents() {
		MigLayout layout = new MigLayout("wrap 2, ins 0", "[left]15lp[right]",
				"[fill, grow][fill, grow][fill, grow][fill, grow]");
		setLayout(layout);
		setBorder(new TitledBorder(Constants.TITLE_CONNECTIONS));
		add(assembly, "growy");
		add(assemblyStatus, "");
		add(welding, "growy");
		add(weldingStatus, "");
		add(quality, "growy");
		add(qualityStatus, "");
		add(master, "growy");
		add(masterStatus, "");
	}

	public void setConnectionStatus(int automatonID, boolean connected) {

            //if assembly station connected{
			if (connected) {
				assemblyStatus.setIcon(new ImageIcon(imageLoader.on));
			} else {
				assemblyStatus.setIcon(new ImageIcon(imageLoader.off));
			}
		//} else if welding station connected {
			if (connected) {
				weldingStatus.setIcon(new ImageIcon(imageLoader.on));
			} else {
				weldingStatus.setIcon(new ImageIcon(
						imageLoader.off));
			}
		//} else if welding station connected {
			if (connected) {
				qualityStatus.setIcon(new ImageIcon(imageLoader.on));
			} else {
				qualityStatus.setIcon(new ImageIcon(imageLoader.off));
			}
		}
	
}
