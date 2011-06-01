package Scada.Gui;

import Auxiliar.Constants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.logging.Handler;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * Panel where log messages can be displayed.
 * 
 * @author
 */
public class Log extends JPanel {
	private static final long serialVersionUID = 1L;

	
	/* GUI COMPONENTS */
	private JTextArea txtLog;
	private JScrollPane scrLogs;
	private Handler logHandler;

	/**
	 * Constructs a panel where log messages can be displayed.
	 */
	public Log() {
		createComponents();
		layoutComponents();
	}

	private void createComponents() {
		txtLog = new JTextArea();
		txtLog.setWrapStyleWord(true);
		txtLog.setLineWrap(true);
		txtLog.setEditable(false);
		txtLog.setBackground(Color.WHITE);
		scrLogs = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		logHandler = new JTextAreaHandler(txtLog);
	}

	private void layoutComponents() {
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(Constants.TITLE_LOG));
		scrLogs.setViewportView(txtLog);
		add(scrLogs, BorderLayout.CENTER);
	}

	/**
	 * Gets the handler that a <code>Logger</code> can use to send log messages.
	 * 
	 * @return Returns the handler that a logger object can use to send log
	 *         messages.
	 */
	public Handler getLogHandler() {
		return logHandler;
	}
}
