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
    private JLabel _assembly, _welding, _quality, _master;
    private JLabel _assemblyStatus, _weldingStatus, _qualityStatus, _masterStatus;
    private ImageLoader _imageLoader;
    private boolean _slave1Connected, _slave2Connected, _slave3Connected;

    /**
     * Constructs a connections panel.
     *
     * @param imageLoader
     *            The image loader in charge of retrieving the images to display
     *            the connections status.
     */
    public Connections(ImageLoader imageLoader) {
        this._imageLoader = imageLoader;
        createComponents();
        layoutComponents();
    }

    private void createComponents() {
        _welding = new JLabel(Constants.WELDING_AUTOMATON_LABEL);
        _assembly = new JLabel(Constants.ASSEMBLY_AUTOMATON_LABEL);
        _quality = new JLabel(Constants.QUALITY_AUTOMATON_LABEL);
        _master = new JLabel(Constants.MASTER_SCADA_AUTOMATON_LABEL);

        _weldingStatus = new JLabel(new ImageIcon(_imageLoader._off));
        _assemblyStatus = new JLabel(new ImageIcon(_imageLoader._off));
        _qualityStatus = new JLabel(new ImageIcon(
                _imageLoader._off));
        _masterStatus = new JLabel(new ImageIcon(_imageLoader._on));
        _slave1Connected = false;
        _slave2Connected = false;
        _slave3Connected = false;
    }

    private void layoutComponents() {
        MigLayout layout = new MigLayout("wrap 2, ins 0", "[left]15lp[right]",
                "[fill, grow][fill, grow][fill, grow][fill, grow]");
        setLayout(layout);
        setBorder(new TitledBorder(Constants.TITLE_CONNECTIONS));
        add(_assembly, "growy");
        add(_assemblyStatus, "");
        add(_welding, "growy");
        add(_weldingStatus, "");
        add(_quality, "growy");
        add(_qualityStatus, "");
        add(_master, "growy");
        add(_masterStatus, "");
    }

    public void setConnectionStatus(int automatonID, boolean connected) {

        if (connected && automatonID == Constants.SLAVE1_ID) {
            _assemblyStatus.setIcon(new ImageIcon(_imageLoader._on));
            _slave1Connected = true;

        }

        if (connected && automatonID == Constants.SLAVE2_ID) {
            _weldingStatus.setIcon(new ImageIcon(_imageLoader._on));
            _slave2Connected = true;

        }

        if (connected && automatonID == Constants.SLAVE3_ID) {
            _qualityStatus.setIcon(new ImageIcon(_imageLoader._on));
            _slave3Connected = true;
        }
    }

    public boolean getConnectionStatus(int automatonID) {
       boolean state=false;
        if (automatonID == Constants.SLAVE1_ID) {
            state=  _slave1Connected;
        } else if (automatonID == Constants.SLAVE2_ID) {
            state = _slave1Connected;
        } else if (automatonID == Constants.SLAVE3_ID) {
            state = _slave1Connected;
        }
       return state;
    }
}
