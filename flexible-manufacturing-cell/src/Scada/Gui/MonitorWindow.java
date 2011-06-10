package Scada.Gui;

import Automaton.Master.Master;
import Automaton.Slaves.Data.Slave1Data;
import Automaton.Slaves.Data.Slave2Data;
import Automaton.Slaves.Data.Slave3Data;
import Auxiliar.AutomatonState;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import Auxiliar.Constants;
import Auxiliar.MailboxData;
import Element.Piece.Piece;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * Monitor where the whole system state is displayed: factory simulation,
 * automata connectivity, log messages...
 *
 * @author Echoplex
 */
public class MonitorWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    /* GUI COMPONENTS */
    private Canvas _canvas;
    private Connections _connectionStatus;
    private Log _log;
    private JButton _buttonConfiguration,
            _buttonReport,
            _buttonStart,
            _buttonStop,
            _buttonEmergencyStop;
    private ImageLoader _imageLoader;
    private ConfigurationParameters _configurationParameters;
    private Report _report;
    private Master _masterAutomaton;
    /* LISTENERS */
    private ActionListener _btnActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == _buttonStart) {
                if (_masterAutomaton == null) {

                    JOptionPane.showMessageDialog(
                            MonitorWindow.this,
                            "The system cannot be started. Check if the master have received the right parameters.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    _buttonStart.setEnabled(true);
                    _buttonStop.setEnabled(true);
                    _buttonEmergencyStop.setEnabled(true);
                    _canvas.setEmergencyStopped(false);
                    _masterAutomaton.startSystem();
                }
            } else if (e.getSource() == _buttonEmergencyStop) {
                _buttonStart.setEnabled(true);
                _buttonStop.setEnabled(false);
                _buttonEmergencyStop.setEnabled(false);
                _canvas.setEmergencyStopped(true);
                _masterAutomaton.emergencyStop();

                ;
            } else if (e.getSource() == _buttonStop) {
                _buttonStop.setEnabled(false);
                _buttonEmergencyStop.setEnabled(false);
                _masterAutomaton.stopSystem();
                _buttonStart.setEnabled(true);
            } else if (e.getSource() == _buttonReport) {
                _masterAutomaton.getDbmanager().writeReportData(_masterAutomaton.getReportData());
                _report.getValues(_masterAutomaton.getDbmanager().readReportData());
                _report.setVisible(true);
            } else if (e.getSource() == _buttonConfiguration) {
                _configurationParameters.getValues(_masterAutomaton.getDbmanager().readParameters(), _buttonStart.isEnabled());
                _configurationParameters.setVisible(true);
            }
        }
    };

    /**
     * Constructs a monitor window and starts the master automaton.
     *
     */
    public MonitorWindow() {
        _imageLoader = new ImageLoader(this);
        _report = new Report();
        Dimension size = new Dimension(Constants.GUI_WIDTH, Constants.GUI_HEIGHT);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setResizable(false);
        setTitle(Constants.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //  setIconImage(_imageLoader._frameIcon);
        createComponents();
        layoutComponents();
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        _masterAutomaton = new Master(this);
        _configurationParameters = new ConfigurationParameters(_masterAutomaton);
        _masterAutomaton.initialize();
        _masterAutomaton.startRobot();
    }

    /**
     * Initializes the components of the GUI
     */
    private void createComponents() {

        Image icon = Toolkit.getDefaultToolkit().getImage("logo.png");
        setIconImage(icon);
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        _canvas = new Canvas(_imageLoader);
        _buttonConfiguration = new JButton(new ImageIcon(_imageLoader._configurationButton));
        _buttonConfiguration.setToolTipText(Constants.CONFIGURATION_TOOL_TIP);
        _buttonConfiguration.setCursor(handCursor);
        _buttonConfiguration.addActionListener(_btnActionListener);
        _buttonReport = new JButton(new ImageIcon(_imageLoader._reportButton));
        _buttonReport.setToolTipText(Constants.REPORT_TOOL_TIP);
        _buttonReport.setCursor(handCursor);
        _buttonReport.addActionListener(_btnActionListener);
        _buttonStart = new JButton(new ImageIcon(_imageLoader._startButton));
        _buttonStart.setToolTipText(Constants.START_TOOL_TIP);
        _buttonStart.setCursor(handCursor);
        _buttonStart.addActionListener(_btnActionListener);
        _buttonStop = new JButton(new ImageIcon(_imageLoader._stopButton));
        _buttonStop.setToolTipText(Constants.STOP_TOOL_TIP);
        _buttonStop.setCursor(handCursor);
        _buttonStop.setEnabled(false);
        _buttonStop.addActionListener(_btnActionListener);
        _buttonEmergencyStop = new JButton(new ImageIcon(_imageLoader._emergencyStopButton));
        _buttonEmergencyStop.setToolTipText(Constants.EMERGENCY_STOP_TOOL_TIP);
        _buttonEmergencyStop.setCursor(handCursor);
        _buttonEmergencyStop.setEnabled(false);
        _buttonEmergencyStop.addActionListener(_btnActionListener);
        _log = new Log();
        _connectionStatus = new Connections(_imageLoader);

    }

    /**
     * Distributes the components in the panel
     */
    private void layoutComponents() {
        MigLayout contentPaneLayout = new MigLayout("wrap 3",
                "[fill][fill][fill]", "[fill, grow][fill]");
        Container contentPane = getContentPane();
        contentPane.setLayout(contentPaneLayout);
        _canvas.setBorder(new LineBorder(Color.GRAY));
        MigLayout pnlButtonsLayout = new MigLayout();
        JPanel pnlButtons = new JPanel(pnlButtonsLayout);
        pnlButtons.setBorder(new TitledBorder(Constants.CONTROL_PANEL_TITLE));
        pnlButtons.add(_buttonConfiguration, "");
        pnlButtons.add(_buttonStart, "");
        pnlButtons.add(_buttonEmergencyStop, "span 1 2, wrap");
        pnlButtons.add(_buttonReport, "");
        pnlButtons.add(_buttonStop, "wrap");
        contentPane.add(_canvas, "span 3, wrap, center");
        contentPane.add(pnlButtons, "");
        contentPane.add(_connectionStatus, "");
        contentPane.add(_log, "");
    }

    /**
     * Gets the canvas.
     *
     * @return Returns the canvas.
     */
    public Canvas getCanvas() {
        return _canvas;
    }

    /**
     * Gets the connection status
     * @return the Connections
     */
    public Connections getConnectionStatus() {
        return _connectionStatus;
    }

    /**
     * Gets the GUI images
     * @return the ImageLoader
     */
    public ImageLoader getImageLoader() {
        return _imageLoader;
    }

    /**
     * Gets the log panel
     * @return the log panel
     */
    public Log getLog() {
        return _log;
    }

    /**
     * Gets the report panel
     * @return the report panel
     */
    public Report getReport() {
        return _report;
    }

    /**
     * Enables the start button and disables the stop buttons.
     */
    public void enableStart() {
        _buttonStart.setEnabled(true);
        _buttonStop.setEnabled(false);
        _buttonEmergencyStop.setEnabled(false);
    }

    /**
     * Disables the start button and the stop buttons.
     */
    public void disableAll() {
        _buttonStart.setEnabled(false);
        _buttonStop.setEnabled(false);
        _buttonEmergencyStop.setEnabled(false);
    }

    /**
     * Sets the state of the slave
     * @param slaveId Slave identifier
     * @param status If it is connected or not
     */
    public void setConnectionStatus(int slaveId, boolean status) {
        _connectionStatus.setConnectionStatus(slaveId, status);
    }

    /**
     * Sets the slave status in the canvas
     * @param slaveId Slave identifier
     * @param data Information to be updated
     */
    public void setCanvasStatus(int slaveId, MailboxData data) {
        switch (slaveId) {
            case Constants.SLAVE1_ID:
                _canvas.setSlave1Data((Slave1Data) data);
                break;
            case Constants.SLAVE2_ID:
                _canvas.setSlave2Data((Slave2Data) data);
                break;
            case Constants.SLAVE3_ID:
                _canvas.setSlave3Data((Slave3Data) data);
                break;
        }
    }

    /**
     * Update the robot2 status
     * @param automatonState  State of the automaton who control the robot
     * @param piece The piece that the robot takes
     */
    public void updateRobot2(AutomatonState automatonState, Piece piece) {
        _canvas.updateRobot2(automatonState, piece);
    }

    /**
     * Main class
     * @param args
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }

        try {
            MonitorWindow w = new MonitorWindow();
            w.setVisible(true);


        } catch (Exception e) {
            e.printStackTrace();
            return;
        }


    }
}
