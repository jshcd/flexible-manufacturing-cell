package Scada.Gui;

import Automaton.Master.Master;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import net.miginfocom.swing.MigLayout;
import Scada.DataBase.MasterConfigurationData;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * Dialog that allows the user to view and/or modify the configuration
 * parameters.
 *
 * @author
 */
public class ConfigurationParameters extends JDialog {

    private static final long serialVersionUID = 1L;

    /* GUI COMPONENTS */
    private JButton _btnCancel, _btnAccept;
    private JPanel _slave1Parameters, _slave2Parameters, _slave3Parameters, _masterParameters;
    private String _axisBeltCapacity, _axisBeltSpeed, _axisBeltLength,
            _gearBeltCapacity, _gearBeltSpeed, _gearBeltLength, _assemblyActivationTime,
            _weldingBeltSpeed, _weldingBeltLength, _weldingActivationTime,
            _OKBeltSpeed, _OKBeltLength, _notOKBeltLength, _qualityActivationTime;
    private JTextField _axisBeltCapacityTxt, _axisBeltSpeedTxt, _axisBeltLengthTxt,
            _gearBeltCapacityTxt, _gearBeltSpeedTxt, _gearBeltLengthTxt, _assemblyActivationTimeTxt,
            _weldingBeltSpeedTxt, _weldingBeltLengthTxt, _weldingActivationTimeTxt,
            _OKBeltSpeedTxt, _OKBeltLengthTxt, _notOKBeltLengthTxt, _qualityActivationTimeTxt,
            _clockCycleTimeTxt;
    private String _clockCycleTime;
    private MasterConfigurationData _masterConfiguration;
    private Master _masterAutomaton;
    private boolean _showResources;

    /* LISTENERS */
    private ActionListener btnActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == _btnAccept) {
                String errors = checkData();
                if (errors.length() > 0) {
                    JOptionPane.showMessageDialog(
                            ConfigurationParameters.this, errors,
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    _masterAutomaton.getDbmanager().updateParameters(_masterConfiguration);
                    setVisible(false);
                }
            } else if (e.getSource() == _btnCancel) {
                setVisible(false);
            }
        }
    };

    /**
     * Constructs a configuration parameters dialog.
     *
     * @param masterAutomaton
     *            The master automaton.
     */
    public ConfigurationParameters(Master masterAutomaton) {
        this._masterAutomaton = masterAutomaton;
        setTitle("Configuration Parameters");
        createComponents();
        layoutComponents();

        pack();
        setLocationRelativeTo(null);
    }

    private void createComponents() {
        _masterConfiguration = new MasterConfigurationData();

        Border paneEdge = BorderFactory.createEmptyBorder(0, 10, 10, 10);
        TitledBorder titled;

        //Slave 1 parameters
        _slave1Parameters = new JPanel();
        _slave1Parameters.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Assembly station parameters");
        _slave1Parameters.setBorder(titled);

        //Slave 2 parameters
        _slave2Parameters = new JPanel();
        _slave2Parameters.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Welding station parameters");
        _slave2Parameters.setBorder(titled);

        //Slave 3 parameters
        _slave3Parameters = new JPanel();
        _slave3Parameters.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Quality station parameters");
        _slave3Parameters.setBorder(titled);

        //Master parameters
        _masterParameters = new JPanel();
        _masterParameters.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Master parameters");
        _masterParameters.setBorder(titled);

        _btnAccept = new JButton("Accept");
        _btnAccept.addActionListener(btnActionListener);
        _btnCancel = new JButton("Cancel");
        _btnCancel.addActionListener(btnActionListener);



    }

    private void layoutComponents() {
        MigLayout contentPaneLayout = new MigLayout("wrap 2",
                "[fill, grow][fill, grow]",
                "[fill, grow][fill, grow][fill, grow][center]");
        Container contentPane = getContentPane();
        contentPane.setLayout(contentPaneLayout);

        add(_slave1Parameters);
        add(_slave1Parameters);
        add(_slave1Parameters);
        add(_masterParameters);

        MigLayout pnlCommonLayout = new MigLayout("wrap 3",
                "[left][fill, grow, 40lp:40lp:][]", "");
        //Slave 1
        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Gear belt length: "));
        _gearBeltLengthTxt = new JTextField(_gearBeltLength);
        _slave1Parameters.add(_gearBeltLengthTxt);
        _slave1Parameters.add(new JLabel("cm"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Gear belt speed: "));
        _gearBeltSpeedTxt = new JTextField(_gearBeltSpeed);
        _slave1Parameters.add(_gearBeltSpeedTxt);
        _slave1Parameters.add(new JLabel("cm/minute"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Gear belt capacity: "));
        _gearBeltCapacityTxt = new JTextField(_gearBeltCapacity);
        _slave1Parameters.add(_gearBeltCapacityTxt);
        _slave1Parameters.add(new JLabel("gears"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Axis belt length: "));
        _axisBeltLengthTxt = new JTextField(_axisBeltLength);
        _slave1Parameters.add(_axisBeltLengthTxt);
        _slave1Parameters.add(new JLabel("cm"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Axis belt speed: "));
        _axisBeltSpeedTxt = new JTextField(_axisBeltSpeed);
        _slave1Parameters.add(_axisBeltSpeedTxt);
        _slave1Parameters.add(new JLabel("cm/minute"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Axis belt capacity: "));
        _axisBeltCapacityTxt = new JTextField(_axisBeltCapacity);
        _slave1Parameters.add(_axisBeltCapacityTxt);
        _slave1Parameters.add(new JLabel("axis"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Activation time: "));
        _assemblyActivationTimeTxt = new JTextField(_assemblyActivationTime);
        _slave1Parameters.add(_assemblyActivationTimeTxt);
        _slave1Parameters.add(new JLabel("sec"));

        //slave 2
        _slave2Parameters.setLayout(pnlCommonLayout);
        _slave2Parameters.add(new JLabel("Belt length: "));
        _weldingBeltLengthTxt = new JTextField(_weldingBeltLength);
        _slave2Parameters.add(_weldingBeltLengthTxt);
        _slave2Parameters.add(new JLabel("cm"));

        _slave2Parameters.setLayout(pnlCommonLayout);
        _slave2Parameters.add(new JLabel("Belt speed: "));
        _weldingBeltSpeedTxt = new JTextField(_weldingBeltSpeed);
        _slave2Parameters.add(_weldingBeltSpeedTxt);
        _slave2Parameters.add(new JLabel("cm/minute"));

        _slave2Parameters.setLayout(pnlCommonLayout);
        _slave2Parameters.add(new JLabel("Activation time: "));
        _weldingActivationTimeTxt = new JTextField(_weldingActivationTime);
        _slave2Parameters.add(_weldingActivationTimeTxt);
        _slave2Parameters.add(new JLabel("axis"));

        //slave 3
        _slave3Parameters.setLayout(pnlCommonLayout);
        _slave3Parameters.add(new JLabel("OK Belt length: "));
        _OKBeltLengthTxt = new JTextField(_OKBeltLength);
        _slave3Parameters.add(_OKBeltLengthTxt);
        _slave3Parameters.add(new JLabel("cm"));

        _slave3Parameters.setLayout(pnlCommonLayout);
        _slave3Parameters.add(new JLabel("OK Belt speed: "));
        _OKBeltSpeedTxt = new JTextField(_OKBeltSpeed);
        _slave3Parameters.add(_OKBeltSpeedTxt);
        _slave3Parameters.add(new JLabel("cm/minute"));

        _slave3Parameters.setLayout(pnlCommonLayout);
        _slave3Parameters.add(new JLabel("Not OK belt length: "));
        _notOKBeltLengthTxt = new JTextField(_notOKBeltLength);
        _slave3Parameters.add(_notOKBeltLengthTxt);
        _slave3Parameters.add(new JLabel("cm"));

        _slave3Parameters.setLayout(pnlCommonLayout);
        _slave3Parameters.add(new JLabel("Activation time: "));
        _qualityActivationTimeTxt = new JTextField(_qualityActivationTime);
        _slave3Parameters.add(_qualityActivationTimeTxt);
        _slave3Parameters.add(new JLabel("axis"));

        //Master
        _masterParameters.setLayout(pnlCommonLayout);
        _masterParameters.add(new JLabel("Clock cycle: "));
        _clockCycleTimeTxt = new JTextField(_clockCycleTime);
        _masterParameters.add(_clockCycleTimeTxt);
        _masterParameters.add(new JLabel("ms"));


    }

    /**
     * Shows the specified configuration parameters.
     *
     * @param configurationParameters
     *            The configuration parameters to show.
     * @param showResources
     *            Whether the resource quantities are to be displayed or not.
     */
    public void getValues(MasterConfigurationData configurationParameters,
            boolean showResources) {
        this._masterConfiguration = configurationParameters;
        this._showResources = showResources;

        _axisBeltSpeed = String.valueOf(_masterConfiguration._slave1ConfigurationData._axisBeltConfiguration.getSpeed());
        _axisBeltCapacity = String.valueOf(_masterConfiguration._slave1ConfigurationData._axisBeltConfiguration.getCapacity());
        _axisBeltLength = String.valueOf(_masterConfiguration._slave1ConfigurationData._axisBeltConfiguration.getLength());
        _gearBeltSpeed = String.valueOf(_masterConfiguration._slave1ConfigurationData._gearBeltConfiguration.getSpeed());
        _gearBeltCapacity = String.valueOf(_masterConfiguration._slave1ConfigurationData._gearBeltConfiguration.getCapacity());
        _gearBeltLength = String.valueOf(_masterConfiguration._slave1ConfigurationData._gearBeltConfiguration.getLength());
        _assemblyActivationTime = String.valueOf(_masterConfiguration._slave2ConfigurationData._activationTime);
        _weldingBeltSpeed = String.valueOf(_masterConfiguration._slave2ConfigurationData._weldingBeltConfiguration.getSpeed());
        _weldingBeltLength = String.valueOf(_masterConfiguration._slave2ConfigurationData._weldingBeltConfiguration.getLength());
        _weldingActivationTime = String.valueOf(_masterConfiguration._slave2ConfigurationData._activationTime);
        _OKBeltSpeed = String.valueOf(_masterConfiguration._slave3ConfigurationData._qualityControlBelt.getSpeed());
        _OKBeltLength = String.valueOf(_masterConfiguration._slave3ConfigurationData._qualityControlBelt.getLength());
        _notOKBeltLength = String.valueOf(_masterConfiguration._slave3ConfigurationData._notAcceptedBelt.getLength());
        _qualityActivationTime = String.valueOf(_masterConfiguration._slave3ConfigurationData._activationTime);
        _clockCycleTime = String.valueOf(_masterConfiguration._clockCycleTime);

        if (!showResources) {
            _axisBeltSpeedTxt.setEditable(false);
            _axisBeltCapacityTxt.setEditable(false);
            _axisBeltLengthTxt.setEditable(false);
            _gearBeltSpeedTxt.setEditable(false);
            _gearBeltCapacityTxt.setEditable(false);
            _gearBeltLengthTxt.setEditable(false);
            _assemblyActivationTimeTxt.setEditable(false);
            _weldingBeltSpeedTxt.setEditable(false);
            _weldingBeltLengthTxt.setEditable(false);
            _weldingActivationTimeTxt.setEditable(false);
            _OKBeltSpeedTxt.setEditable(false);
            _OKBeltLengthTxt.setEditable(false);
            _notOKBeltLengthTxt.setEditable(false);
            _qualityActivationTimeTxt.setEditable(false);
             _clockCycleTimeTxt.setEditable(false);
        } else {
            _axisBeltSpeedTxt.setEditable(true);
            _axisBeltCapacityTxt.setEditable(true);
            _axisBeltLengthTxt.setEditable(true);
            _gearBeltSpeedTxt.setEditable(true);
            _gearBeltCapacityTxt.setEditable(true);
            _gearBeltLengthTxt.setEditable(true);
             _assemblyActivationTimeTxt.setEditable(true);
            _weldingBeltSpeedTxt.setEditable(true);
            _weldingBeltLengthTxt.setEditable(true);
            _weldingActivationTimeTxt.setEditable(true);
            _OKBeltSpeedTxt.setEditable(true);
            _OKBeltLengthTxt.setEditable(true);
            _notOKBeltLengthTxt.setEditable(true);
            _qualityActivationTimeTxt.setEditable(true);
             _clockCycleTimeTxt.setEditable(true);
        }
    }

    private String checkData() {
       
        return "a";
    }

    /**
     * @see javax.swing.JDialog#createRootPane()
     */
    @Override
    protected JRootPane createRootPane() {
        // Listener that handles the closing operation
        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        };

        // A keyboard action is registered in the root pane of the dialog
        JRootPane rootPane = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        rootPane.registerKeyboardAction(actionListener, stroke,
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootPane;
    }
}
