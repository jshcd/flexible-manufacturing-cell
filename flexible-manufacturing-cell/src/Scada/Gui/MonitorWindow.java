package Scada.Gui;

import Automaton.Master.Master;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

/**
 * Monitor where the whole system state is displayed: factory simulation,
 * automata connectivity, log messages...
 *
 * @author
 */
public class MonitorWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    /* GUI COMPONENTS */
    private Canvas canvas;
    private Connections connectionStatus;
    private Log log;
    private JButton buttonConfiguration,
            buttonReport,
            buttonStart,
            buttonStop,
            buttonEmergencyStop;
    private ImageLoader imageLoader;
    //private ConfigurationParametersDialog configurationParametersDialog;
    private Report report;
    private Master masterAutomaton;
    /* LISTENERS */
    private ActionListener btnActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == buttonStart) {
                if (masterAutomaton != null) {
                    JOptionPane.showMessageDialog(
                            MonitorWindow.this,
                            "The system cannot be started until all the slave automata have been connected.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);
                    buttonEmergencyStop.setEnabled(true);
                    canvas.setEmergencyStopped(false);
                    masterAutomaton.startSystem();
                }
            } else if (e.getSource() == buttonEmergencyStop) {
                buttonStart.setEnabled(true);
                buttonStop.setEnabled(false);
                buttonEmergencyStop.setEnabled(false);
                canvas.setEmergencyStopped(true);
                masterAutomaton.emergencyStop();
                ;
            } else if (e.getSource() == buttonStop) {
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(false);
                buttonEmergencyStop.setEnabled(false);
                masterAutomaton.stopSystem();
            } else if (e.getSource() == buttonReport) {
                //report.getValues...
                report.setVisible(true);
            }
        }
    };

    /**
     * Constructs a monitor window and starts the master automaton.
     *
     * @param port
     *            The port on which the master automaton is listening to slave
     *            automata connections.
     */
    public MonitorWindow(int port) {
        imageLoader = new ImageLoader(this);
        report = new Report();
        setTitle(Constants.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createComponents();
        layoutComponents();

        masterAutomaton = new Master();

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void createComponents() {
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        canvas = new Canvas(imageLoader);

        buttonConfiguration = new JButton(new ImageIcon(imageLoader.configurationButton));
        buttonConfiguration.setToolTipText(Constants.CONFIGURATION_TOOL_TIP);
        buttonConfiguration.setCursor(handCursor);
        buttonConfiguration.addActionListener(btnActionListener);
        buttonReport = new JButton(new ImageIcon(imageLoader.reportButton));
        buttonReport.setToolTipText(Constants.REPORT_TOOL_TIP);
        buttonReport.setCursor(handCursor);
        buttonReport.addActionListener(btnActionListener);
        buttonStart = new JButton(new ImageIcon(imageLoader.startButton));
        buttonStart.setToolTipText(Constants.START_TOOL_TIP);
        buttonStart.setCursor(handCursor);
        buttonStart.addActionListener(btnActionListener);
        buttonStop = new JButton(new ImageIcon(imageLoader.stopButton));
        buttonStop.setToolTipText(Constants.STOP_TOOL_TIP);
        buttonStop.setCursor(handCursor);
        buttonStop.setEnabled(false);
        buttonStop.addActionListener(btnActionListener);
        buttonEmergencyStop = new JButton(new ImageIcon(imageLoader.emergencyStopButton));
        buttonEmergencyStop.setToolTipText(Constants.EMERGENCY_STOP_TOOL_TIP);
        buttonEmergencyStop.setCursor(handCursor);
        buttonEmergencyStop.setEnabled(false);
        buttonEmergencyStop.addActionListener(btnActionListener);
        log = new Log();
        connectionStatus = new Connections(imageLoader);

    }

    private void layoutComponents() {
        MigLayout contentPaneLayout = new MigLayout("wrap 3",
                "[fill][fill][fill]", "[fill, grow][fill]");
        Container contentPane = getContentPane();
        contentPane.setLayout(contentPaneLayout);
        canvas.setBorder(new LineBorder(Color.GRAY));
        MigLayout pnlButtonsLayout = new MigLayout();
        JPanel pnlButtons = new JPanel(pnlButtonsLayout);
        pnlButtons.setBorder(new TitledBorder(Constants.CONTROL_PANEL_TITLE));
        pnlButtons.add(buttonConfiguration, "");
        pnlButtons.add(buttonStart, "");
        pnlButtons.add(buttonEmergencyStop, "span 1 2, wrap");
        pnlButtons.add(buttonReport, "");
        pnlButtons.add(buttonStop, "wrap");
        contentPane.add(canvas, "span 3, wrap, center");
        contentPane.add(pnlButtons, "");

        contentPane.add(connectionStatus, "");
        contentPane.add(log, "");
    }

    /**
     * Gets the canvas.
     *
     * @return Returns the canvas.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    public Connections getConnectionStatus() {
        return connectionStatus;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public Log getLog() {
        return log;
    }

    public Report getReport() {
        return report;
    }

    /**
     * Enables the start button and disables the stop buttons.
     */
    public void enableStart() {
        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);
        buttonEmergencyStop.setEnabled(false);
    }

    /**
     * Disables the start button and the stop buttons.
     */
    public void disableAll() {
        buttonStart.setEnabled(false);
        buttonStop.setEnabled(false);
        buttonEmergencyStop.setEnabled(false);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }

        int port;
        try {
            if (args.length == 0) {
                port = Constants.DEFAULT_MASTER_PORT;
            } else if (args.length == 1) {
                port = Integer.parseInt(args[0]);
            } else {
                System.err.println("Usage error: java -jar masterSCADA.jar [<port>]\n\n"
                        + "where <port> is a non-priviledged available port in the system [1014, 65535]. "
                        + "If no port is specified, the default port (42680) is used.");
                return;
            }

            if (port < 1024 || port > 65535) {
                System.err.println("Usage error: java -jar masterSCADA.jar [<port>]\n\n"
                        + "where <port> is a non-priviledged available port in the system [1014, 65535]. "
                        + "If no port is specified, the default port (42680) is used.");
                return;
            }

            try {
                MonitorWindow w = new MonitorWindow(port);
                w.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Usage error: java -jar masterSCADA.jar [<port>]\n\n"
                    + "where <port> is a non-priviledged available port in the system [1014, 65535]. "
                    + "If no port is specified, the default port (42680) is used.");

        }
    }
}
