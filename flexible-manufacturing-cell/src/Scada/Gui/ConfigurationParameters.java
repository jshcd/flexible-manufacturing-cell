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
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * Dialog that allows the user to view and/or modify the configuration
 * parameters.
 * @author Echoplex
 */
public class ConfigurationParameters extends JDialog {

    private static final long serialVersionUID = 1L;

    /* GUI COMPONENTS */
    private JButton _buttonCancel, _buttonAccept;
    private JPanel _slave1Parameters, _slave2Parameters, _slave3Parameters, _masterParameters, _robot1Parameters, _robot2Parameters;
    private String _axisBeltCapacity, _axisBeltSpeed, _axisBeltLength,
            _gearBeltCapacity, _gearBeltSpeed, _gearBeltLength, _assemblyActivationTime,
            _weldingBeltSpeed, _weldingBeltLength, _weldingActivationTime,
            _OKBeltSpeed, _OKBeltLength, _notOKBeltLength, _qualityActivationTime, _successRate,
            _robot1Axis, _robot1Gear, _robot1Assembly, _robot2Welding, _robot2Checked, _robot2Assembly;
    private JTextField _axisBeltCapacityTxt, _axisBeltSpeedTxt, _axisBeltLengthTxt,
            _gearBeltCapacityTxt, _gearBeltSpeedTxt, _gearBeltLengthTxt, _assemblyActivationTimeTxt,
            _weldingBeltSpeedTxt, _weldingBeltLengthTxt, _weldingActivationTimeTxt,
            _OKBeltSpeedTxt, _OKBeltLengthTxt, _notOKBeltLengthTxt, _qualityActivationTimeTxt, _successRateTxt,
            _clockCycleTimeTxt, _robot1AxisTxt, _robot1GearTxt, _robot1AssemblyTxt,
            _robot2WeldingTxt, _robot2CheckedTxt, _robot2AssemblyTxt;
    private String _clockCycleTime;
    private MasterConfigurationData _masterConfiguration;
    private Master _masterAutomaton;
    private boolean _showResources;

    /* LISTENERS */
    private ActionListener btnActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == _buttonAccept) {
                String errors = validateData();
                if (errors.length() > 0) {
                    JOptionPane.showMessageDialog(
                            ConfigurationParameters.this, errors,
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    _masterAutomaton.getDbmanager().updateParameters(_masterConfiguration);
                    setVisible(false);
                }
            } else if (e.getSource() == _buttonCancel) {
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
        _masterAutomaton = masterAutomaton;
        setTitle("Configuration Parameters");
        createComponents();
        layoutComponents();
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Initializes the components of the panel
     */
    private void createComponents() {

        Border paneEdge = BorderFactory.createEmptyBorder(0, 10, 10, 10);
        TitledBorder titled;

        //Slave 1 parameters
        _slave1Parameters = new JPanel();
        _slave1Parameters.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Assembly station ");
        _slave1Parameters.setBorder(titled);

        //Slave 2 parameters
        _slave2Parameters = new JPanel();
        _slave2Parameters.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Welding station ");
        _slave2Parameters.setBorder(titled);

        //Slave 3 parameters
        _slave3Parameters = new JPanel();
        _slave3Parameters.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Quality station ");
        _slave3Parameters.setBorder(titled);

        //Master parameters
        _masterParameters = new JPanel();
        _masterParameters.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Master ");
        _masterParameters.setBorder(titled);

        _robot1Parameters = new JPanel();
        _robot1Parameters.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Robot 1 ");
        _robot1Parameters.setBorder(titled);

        _robot2Parameters = new JPanel();
        _robot2Parameters.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Robot 2 ");
        _robot2Parameters.setBorder(titled);

        _buttonAccept = new JButton("Accept");
        _buttonAccept.addActionListener(btnActionListener);
        _buttonCancel = new JButton("Cancel");
        _buttonCancel.addActionListener(btnActionListener);

    }

    /**
     * Distributes the components in the panel
     */
    private void layoutComponents() {
        MigLayout contentPaneLayout = new MigLayout("wrap 2",
                "[fill, grow][fill, grow]",
                "[fill, grow][fill, grow][fill, grow][center]");
        Container contentPane = getContentPane();
        contentPane.setLayout(contentPaneLayout);

        add(_slave1Parameters);
        add(_robot1Parameters);
        add(_slave2Parameters);
        add(_robot2Parameters);
        add(_slave3Parameters);
        add(_masterParameters);

        MigLayout pnlCommonLayout = new MigLayout("wrap 3",
                "[left][fill, grow, 40lp:40lp:][]", "");
        //Slave 1
        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Gear belt length: "));
        _gearBeltLengthTxt = new JTextField(_gearBeltLength);
        _slave1Parameters.add(_gearBeltLengthTxt);
        _slave1Parameters.add(new JLabel("m"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Gear belt speed: "));
        _gearBeltSpeedTxt = new JTextField(_gearBeltSpeed);
        _slave1Parameters.add(_gearBeltSpeedTxt);
        _slave1Parameters.add(new JLabel("m/minute"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Gear belt capacity: "));
        _gearBeltCapacityTxt = new JTextField(_gearBeltCapacity);
        _slave1Parameters.add(_gearBeltCapacityTxt);
        _slave1Parameters.add(new JLabel("gears"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Axis belt length: "));
        _axisBeltLengthTxt = new JTextField(_axisBeltLength);
        _slave1Parameters.add(_axisBeltLengthTxt);
        _slave1Parameters.add(new JLabel("m"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Axis belt speed: "));
        _axisBeltSpeedTxt = new JTextField(_axisBeltSpeed);
        _slave1Parameters.add(_axisBeltSpeedTxt);
        _slave1Parameters.add(new JLabel("m/minute"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Axis belt capacity: "));
        _axisBeltCapacityTxt = new JTextField(_axisBeltCapacity);
        _slave1Parameters.add(_axisBeltCapacityTxt);
        _slave1Parameters.add(new JLabel("axis"));

        _slave1Parameters.setLayout(pnlCommonLayout);
        _slave1Parameters.add(new JLabel("Activation time: "));
        _assemblyActivationTimeTxt = new JTextField(_assemblyActivationTime);
        _slave1Parameters.add(_assemblyActivationTimeTxt);
        _slave1Parameters.add(new JLabel("ms"));

        //slave 2
        pnlCommonLayout = new MigLayout("wrap 3",
                "[left][fill, grow, 40lp:40lp:][]", "");
        _slave2Parameters.setLayout(pnlCommonLayout);
        _slave2Parameters.add(new JLabel("Belt length: "));
        _weldingBeltLengthTxt = new JTextField(_weldingBeltLength);
        _slave2Parameters.add(_weldingBeltLengthTxt);
        _slave2Parameters.add(new JLabel("m"));

        _slave2Parameters.setLayout(pnlCommonLayout);
        _slave2Parameters.add(new JLabel("Belt speed: "));
        _weldingBeltSpeedTxt = new JTextField(_weldingBeltSpeed);
        _slave2Parameters.add(_weldingBeltSpeedTxt);
        _slave2Parameters.add(new JLabel("m/minute"));

        _slave2Parameters.setLayout(pnlCommonLayout);
        _slave2Parameters.add(new JLabel("Activation time: "));
        _weldingActivationTimeTxt = new JTextField(_weldingActivationTime);
        _slave2Parameters.add(_weldingActivationTimeTxt);
        _slave2Parameters.add(new JLabel("ms"));

        //robot 1
        pnlCommonLayout = new MigLayout("wrap 3",
                "[left][fill, grow, 40lp:40lp:][]", "");
        _robot1Parameters.setLayout(pnlCommonLayout);
        _robot1Parameters.add(new JLabel("Axis picking/transport time: "));
        _robot1AxisTxt = new JTextField(_robot1Axis);
        _robot1Parameters.add(_robot1AxisTxt);
        _robot1Parameters.add(new JLabel("ms"));

        _robot1Parameters.add(new JLabel("Gear picking/transport time: "));
        _robot1GearTxt = new JTextField(_robot1Gear);
        _robot1Parameters.add(_robot1GearTxt);
        _robot1Parameters.add(new JLabel("ms"));

        _robot1Parameters.setLayout(pnlCommonLayout);
        _robot1Parameters.add(new JLabel("Assembly picking/transport time: "));
        _robot1AssemblyTxt = new JTextField(_robot1Assembly);
        _robot1Parameters.add(_robot1AssemblyTxt);
        _robot1Parameters.add(new JLabel("ms"));

        //robot 2
        pnlCommonLayout = new MigLayout("wrap 3",
                "[left][fill, grow, 40lp:40lp:][]", "");
        _robot2Parameters.setLayout(pnlCommonLayout);
        _robot2Parameters.add(new JLabel("Welded Assembly picking/transport time: "));
        _robot2WeldingTxt = new JTextField(_robot2Welding);
        _robot2Parameters.add(_robot2WeldingTxt);
        _robot2Parameters.add(new JLabel("ms"));

        _robot2Parameters.add(new JLabel("Checked Assembly picking/transport time: "));
        _robot2CheckedTxt = new JTextField(_robot2Checked);
        _robot2Parameters.add(_robot2CheckedTxt);
        _robot2Parameters.add(new JLabel("ms"));

        _robot2Parameters.setLayout(pnlCommonLayout);
        _robot2Parameters.add(new JLabel("Assembly picking/transport time: "));
        _robot2AssemblyTxt = new JTextField(_robot2Assembly);
        _robot2Parameters.add(_robot2AssemblyTxt);
        _robot2Parameters.add(new JLabel("ms"));

        //slave 3
        pnlCommonLayout = new MigLayout("wrap 3",
                "[left][fill, grow, 40lp:40lp:][]", "");
        _slave3Parameters.setLayout(pnlCommonLayout);
        _slave3Parameters.add(new JLabel("OK Belt length: "));
        _OKBeltLengthTxt = new JTextField(_OKBeltLength);
        _slave3Parameters.add(_OKBeltLengthTxt);
        _slave3Parameters.add(new JLabel("m"));

        _slave3Parameters.setLayout(pnlCommonLayout);
        _slave3Parameters.add(new JLabel("OK Belt speed: "));
        _OKBeltSpeedTxt = new JTextField(_OKBeltSpeed);
        _slave3Parameters.add(_OKBeltSpeedTxt);
        _slave3Parameters.add(new JLabel("m/minute"));

        _slave3Parameters.setLayout(pnlCommonLayout);
        _slave3Parameters.add(new JLabel("Not OK belt length: "));
        _notOKBeltLengthTxt = new JTextField(_notOKBeltLength);
        _slave3Parameters.add(_notOKBeltLengthTxt);
        _slave3Parameters.add(new JLabel("m"));

        _slave3Parameters.setLayout(pnlCommonLayout);
        _slave3Parameters.add(new JLabel("Activation time: "));
        _qualityActivationTimeTxt = new JTextField(_qualityActivationTime);
        _slave3Parameters.add(_qualityActivationTimeTxt, "left");
        _slave3Parameters.add(new JLabel("ms"));

        _slave3Parameters.setLayout(pnlCommonLayout);
        _slave3Parameters.add(new JLabel("Success Rate: "));
        _successRateTxt = new JTextField(_successRate);
        _slave3Parameters.add(_successRateTxt);
        _slave3Parameters.add(new JLabel("%"));

        //Master
        pnlCommonLayout = new MigLayout("wrap 3",
                "[60:80:80][20:60:60][]", "");
        _masterParameters.setLayout(pnlCommonLayout);
        _masterParameters.add(new JLabel("Clock cycle: "));
        _clockCycleTimeTxt = new JTextField(_clockCycleTime);
        _clockCycleTimeTxt.setPreferredSize(new Dimension(60, 15));
        _clockCycleTimeTxt.setAlignmentX(this.RIGHT_ALIGNMENT);
        _masterParameters.add(_clockCycleTimeTxt);
        _masterParameters.add(new JLabel("ms"));

        MigLayout pnlButtonsLayout = new MigLayout("", "[]20[]", "");
        JPanel pnlButtons = new JPanel(pnlButtonsLayout);
        pnlButtons.add(_buttonAccept);
        pnlButtons.add(_buttonCancel);

        add(pnlButtons, "span 2, center, width pref!");

    }

    /**
     * Shows the specified configuration parameters.
     *
     * @param configurationParameters
     *            The configuration parameters to show.
     * @param showResources
     *            Whether the resource quantities can be updated or not.
     */
    public void getValues(MasterConfigurationData configurationParameters,
            boolean showResources) {
        this._masterConfiguration = configurationParameters;
        this._showResources = showResources;

        _axisBeltSpeedTxt.setText(String.valueOf(_masterConfiguration._slave1ConfigurationData._axisBeltConfiguration.getSpeed()));
        _axisBeltCapacityTxt.setText(String.valueOf(_masterConfiguration._slave1ConfigurationData._axisBeltConfiguration.getCapacity()));
        _axisBeltLengthTxt.setText(String.valueOf(_masterConfiguration._slave1ConfigurationData._axisBeltConfiguration.getLength()));
        _gearBeltSpeedTxt.setText(String.valueOf(_masterConfiguration._slave1ConfigurationData._gearBeltConfiguration.getSpeed()));
        _gearBeltCapacityTxt.setText(String.valueOf(_masterConfiguration._slave1ConfigurationData._gearBeltConfiguration.getCapacity()));
        _gearBeltLengthTxt.setText(String.valueOf(_masterConfiguration._slave1ConfigurationData._gearBeltConfiguration.getLength()));
        _assemblyActivationTimeTxt.setText(String.valueOf(_masterConfiguration._slave1ConfigurationData._assemblyActivationTime));
        _weldingBeltSpeedTxt.setText(String.valueOf(_masterConfiguration._slave1ConfigurationData._weldingBeltConfiguration.getSpeed()));
        _weldingBeltLengthTxt.setText(String.valueOf(_masterConfiguration._slave1ConfigurationData._weldingBeltConfiguration.getLength()));
        _weldingActivationTimeTxt.setText(String.valueOf(_masterConfiguration._slave2ConfigurationData._weldingActivationTime));
        _OKBeltSpeedTxt.setText(String.valueOf(_masterConfiguration._slave3ConfigurationData._acceptedBelt.getSpeed()));
        _OKBeltLengthTxt.setText(String.valueOf(_masterConfiguration._slave3ConfigurationData._acceptedBelt.getLength()));
        _notOKBeltLengthTxt.setText(String.valueOf(_masterConfiguration._slave3ConfigurationData._notAcceptedBelt.getLength()));
        _qualityActivationTimeTxt.setText(String.valueOf(_masterConfiguration._slave2ConfigurationData._qualityControlActivationTime));
        _successRateTxt.setText(String.valueOf(_masterConfiguration._successRate));
        _clockCycleTimeTxt.setText(String.valueOf(_masterConfiguration._clockCycleTime));
        _robot1AssemblyTxt.setText(String.valueOf(_masterConfiguration._robot1ConfigurationData.getPickAndPlaceAssemblyTime()));
        _robot1AxisTxt.setText(String.valueOf(_masterConfiguration._robot1ConfigurationData.getPickAndPlaceAxisTime()));
        _robot1GearTxt.setText(String.valueOf(_masterConfiguration._robot1ConfigurationData.getPickAndPlaceGearTime()));
        _robot2AssemblyTxt.setText(String.valueOf(_masterConfiguration._robot2ConfigurationData.getPickAndTransportAssemblyTime()));
        _robot2CheckedTxt.setText(String.valueOf(_masterConfiguration._robot2ConfigurationData.getPickAndTransportCheckedAssemblyTime()));
        _robot2WeldingTxt.setText(String.valueOf(_masterConfiguration._robot2ConfigurationData.getPickAndTransportWeldedAssemblyTime()));

        if (showResources) {
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
            _successRateTxt.setEditable(false);
            _clockCycleTimeTxt.setEditable(false);
            _robot1AssemblyTxt.setEditable(false);
            _robot1AxisTxt.setEditable(false);
            _robot1GearTxt.setEditable(false);
            _robot2AssemblyTxt.setEditable(false);
            _robot2CheckedTxt.setEditable(false);
            _robot2WeldingTxt.setEditable(false);
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
            _successRateTxt.setEditable(true);
            _clockCycleTimeTxt.setEditable(true);
            _robot1AssemblyTxt.setEditable(true);
            _robot1AxisTxt.setEditable(true);
            _robot1GearTxt.setEditable(true);
            _robot2AssemblyTxt.setEditable(true);
            _robot2CheckedTxt.setEditable(true);
            _robot2WeldingTxt.setEditable(true);
        }

    }

    /**
     * validates data entered in the configuration parameters
     * @return Validation errors
     */
    private String validateData() {
        StringBuilder errors = new StringBuilder();
        int value;

        try {
            value = Integer.parseInt(_axisBeltSpeedTxt.getText());
            if (value < 1) {
                errors.append("'Axis belt speed' must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave1ConfigurationData._axisBeltConfiguration.setSpeed(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Axis belt speed' must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_axisBeltCapacityTxt.getText());
            if (value < 1) {
                errors.append("'Axis belt capacity' must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave1ConfigurationData._axisBeltConfiguration.setCapacity(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Axis belt capacity' must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_axisBeltLengthTxt.getText());
            if (value < 1) {
                errors.append("'Axis belt length' must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave1ConfigurationData._axisBeltConfiguration.setLength(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Axis belt length' must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_gearBeltSpeedTxt.getText());
            if (value < 1) {
                errors.append("'Gear belt speed' must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave1ConfigurationData._gearBeltConfiguration.setSpeed(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Gear belt speed' must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_gearBeltCapacityTxt.getText());
            if (value < 1) {
                errors.append("'Gear belt capacity' must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave1ConfigurationData._gearBeltConfiguration.setCapacity(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Gear belt capacity' must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_gearBeltLengthTxt.getText());
            if (value < 1) {
                errors.append("'Gear belt length' must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave1ConfigurationData._gearBeltConfiguration.setLength(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Gear belt length' must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_assemblyActivationTimeTxt.getText());
            if (value < 1) {
                errors.append("'Activation time'in Assembly station must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave1ConfigurationData._assemblyActivationTime = value;
            }
        } catch (NumberFormatException e) {
            errors.append("'Activation time'in Assembly station must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_weldingBeltSpeedTxt.getText());
            if (value < 1) {
                errors.append("'Belt speed' in Welding station must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave1ConfigurationData._weldingBeltConfiguration.setSpeed(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Belt speed' in Welding station must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_weldingBeltLengthTxt.getText());
            if (value < 1) {
                errors.append("'Belt length' in Welding station must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave1ConfigurationData._weldingBeltConfiguration.setLength(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Belt length' in Welding station must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_OKBeltSpeedTxt.getText());
            if (value < 1) {
                errors.append("'Ok belt speed' in Quality station must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave3ConfigurationData._acceptedBelt.setSpeed(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Ok belt speed' in Quality station must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_OKBeltLengthTxt.getText());
            if (value < 1) {
                errors.append("'Ok belt length' in Quality station must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave3ConfigurationData._acceptedBelt.setLength(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Ok belt length' in Quality station must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_notOKBeltLengthTxt.getText());
            if (value < 1) {
                errors.append("'Not OK belt length' in Quality station must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave3ConfigurationData._notAcceptedBelt.setLength(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Not OK belt length' in Quality station must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_assemblyActivationTimeTxt.getText());
            if (value < 1) {
                errors.append("'Activation time' in Assembly station must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave1ConfigurationData._assemblyActivationTime = value;
            }
        } catch (NumberFormatException e) {
            errors.append("'Activation time' in Assembly station must be an integer greater or equal than 1.\n");
        }
        try {
            value = Integer.parseInt(_weldingActivationTimeTxt.getText());
            if (value < 1) {
                errors.append("'Activation time' in Welding station must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave2ConfigurationData._weldingActivationTime = value;
            }
        } catch (NumberFormatException e) {
            errors.append("'Activation time' in Welding station must be an integer greater or equal than 1.\n");
        }
        try {
            value = Integer.parseInt(_qualityActivationTimeTxt.getText());
            if (value < 1) {
                errors.append("'Activation time' in Quality station must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._slave2ConfigurationData._qualityControlActivationTime = value;
            }
        } catch (NumberFormatException e) {
            errors.append("'Activation time' in Quality station must be an integer greater or equal than 1.\n");
        }
        try {
            value = Integer.parseInt(_robot1AxisTxt.getText());
            if (value < 1) {
                errors.append("'Axis picking/transport time'  must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._robot1ConfigurationData.setPickAndPlaceAxisTime(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Axis picking/transport time'  must be an integer greater or equal than 1.\n");
        }
        try {
            value = Integer.parseInt(_robot1GearTxt.getText());
            if (value < 1) {
                errors.append("'Gear picking/transport time'  must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._robot1ConfigurationData.setPickAndPlaceGearTime(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Gear picking/transport time'  must be an integer greater or equal than 1.\n");
        }
        try {
            value = Integer.parseInt(_robot1AssemblyTxt.getText());
            if (value < 1) {
                errors.append("'Assembly picking/transport time' in Robot 1 must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._robot1ConfigurationData.setPickAndPlaceAssemblyTime(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Assembly picking/transport time' in Robot 1 must be an integer greater or equal than 1.\n");
        }
        try {
            value = Integer.parseInt(_robot2AssemblyTxt.getText());
            if (value < 1) {
                errors.append("'Assembly picking/transport time' in Robot 2 must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._robot2ConfigurationData.setPickAndTransportAssemblyTime(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Assembly picking/transport time' in Robot 2 must be an integer greater or equal than 1.\n");
        }
        try {
            value = Integer.parseInt(_robot2CheckedTxt.getText());
            if (value < 1) {
                errors.append("'Checked Assembly picking/transport time'  must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._robot2ConfigurationData.setPickAndTransportCheckedAssemblyTime(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Checked Assembly picking/transport time'  must be an integer greater or equal than 1.\n");
        }
        try {
            value = Integer.parseInt(_robot2WeldingTxt.getText());
            if (value < 1) {
                errors.append("'Welded Assembly picking/transport time'  must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._robot2ConfigurationData.setPickAndTransportWeldedAssemblyTime(value);
            }
        } catch (NumberFormatException e) {
            errors.append("'Welded Assembly picking/transport time'  must be an integer greater or equal than 1.\n");
        }
        try {
            value = Integer.parseInt(_clockCycleTimeTxt.getText());
            if (value < 1) {
                errors.append("'Clock cycle'  must be an integer greater or equal than 1.\n");
            } else {
                _masterConfiguration._clockCycleTime = value;
            }
        } catch (NumberFormatException e) {
            errors.append("'Clock cycle' in Master must be an integer greater or equal than 1.\n");
        }

        try {
            value = Integer.parseInt(_successRateTxt.getText());
            if (value < 0 || value > 100) {
                errors.append("'Success Rate' must be greater than 0 and less than 100.\n");
            } else {
                _masterConfiguration._successRate = value;
            }
        } catch (NumberFormatException e) {
            errors.append("'Success Rate' must be a number greater than 0 and less than 100.\n");
        }

        return errors.toString();

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
