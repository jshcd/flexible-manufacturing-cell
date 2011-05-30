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
	private Canvas_1 canvas;
	//private ConnectionsPanel connectionStatusPanel;
	//private LoggingPanel logPanel;
	private JButton btnConfiguration, btnReport, btnStart, btnStop,
			btnEmergencyStop;
	private ImageLoader imageLoader;
	//private ConfigurationParametersDialog configurationParametersDialog;
	//private ReportDialog reportDialog;

	/* LISTENERS */
	private ActionListener btnActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

                    if (e.getSource() == btnStart) {
				if (masterAutomaton != null) {
					JOptionPane
							.showMessageDialog(
									MonitorWindow.this,
									"The system cannot be started until all the slave automata have been connected.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} else {
					btnStart.setEnabled(false);
					btnStop.setEnabled(true);
					btnEmergencyStop.setEnabled(true);
					canvas.setEmergencyStopped(false);
					masterAutomaton.startSystem();
				}
			} else if (e.getSource() == btnEmergencyStop) {
				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
				btnEmergencyStop.setEnabled(false);
				canvas.setEmergencyStopped(true);
				masterAutomaton.emergencyStop();;
			} else if (e.getSource() == btnStop) {
				btnStart.setEnabled(false);
				btnStop.setEnabled(false);
				btnEmergencyStop.setEnabled(false);
				masterAutomaton.stopSystem();
			}
		}
	};

	private Master masterAutomaton;

	/**
	 * Constructs a monitor window and starts the master automaton.
	 *
	 * @param port
	 *            The port on which the master automaton is listening to slave
	 *            automata connections.
	 */
	public MonitorWindow(int port) {

			imageLoader = new ImageLoader(this);
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
		canvas = new Canvas_1(imageLoader);
		btnConfiguration = new JButton(new ImageIcon(imageLoader.prueba));
		btnConfiguration.setToolTipText(Constants.CONFIGURATION_TOOL_TIP);
		btnConfiguration.setCursor(handCursor);
		btnConfiguration.addActionListener(btnActionListener);
		/*btnReport = new JButton(new ImageIcon(imageLoader.prueba));
		btnReport.setToolTipText(Constants.REPORT_TOOL_TIP);
		btnReport.setCursor(handCursor);
		btnReport.addActionListener(btnActionListener);*/
		btnStart = new JButton(new ImageIcon(imageLoader.prueba));
		btnStart.setToolTipText(Constants.START_TOOL_TIP);
		btnStart.setCursor(handCursor);
		btnStart.addActionListener(btnActionListener);
		btnStop = new JButton(new ImageIcon(imageLoader.prueba));
		btnStop.setToolTipText(Constants.STOP_TOOL_TIP);
		btnStop.setCursor(handCursor);
		btnStop.setEnabled(false);
		btnStop.addActionListener(btnActionListener);
		btnEmergencyStop = new JButton(new ImageIcon(imageLoader.prueba));
		btnEmergencyStop.setToolTipText(Constants.EMERGENCY_STOP_TOOL_TIP);
		btnEmergencyStop.setCursor(handCursor);
		btnEmergencyStop.setEnabled(false);
		btnEmergencyStop.addActionListener(btnActionListener);

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
		pnlButtons.add(btnConfiguration, "");
		pnlButtons.add(btnStart, "");
		pnlButtons.add(btnEmergencyStop, "span 1 2, wrap");
		pnlButtons.add(btnReport, "");
		pnlButtons.add(btnStop, "wrap");
		contentPane.add(canvas, "span 3, wrap, center");
		contentPane.add(pnlButtons, "");

	}

	/**
	 * Gets the canvas.
	 *
	 * @return Returns the canvas.
	 */
	public Canvas_1 getCanvas() {
		return canvas;
	}




	/**
	 * Enables the start button and disables the stop buttons.
	 */
	public void enableStart() {
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
		btnEmergencyStop.setEnabled(false);
	}

	/**
	 * Disables the start button and the stop buttons.
	 */
	public void disableAll() {
		btnStart.setEnabled(false);
		btnStop.setEnabled(false);
		btnEmergencyStop.setEnabled(false);
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
				System.err
						.println("Usage error: java -jar masterSCADA.jar [<port>]\n\n"
								+ "where <port> is a non-priviledged available port in the system [1014, 65535]. "
								+ "If no port is specified, the default port (42680) is used.");
				return;
			}

			if (port < 1024 || port > 65535) {
				System.err
						.println("Usage error: java -jar masterSCADA.jar [<port>]\n\n"
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
			System.err
					.println("Usage error: java -jar masterSCADA.jar [<port>]\n\n"
							+ "where <port> is a non-priviledged available port in the system [1014, 65535]. "
							+ "If no port is specified, the default port (42680) is used.");

		}
	}
}
