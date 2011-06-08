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
 * @author Echoplex
 */
public class Log extends JPanel {

    private static final long serialVersionUID = 1L;
    /* GUI COMPONENTS */
    private JTextArea _txtLog;
    private JScrollPane _scrLogs;
    private Handler _logHandler;

    /**
     * Constructs a panel where log messages can be displayed.
     */
    public Log() {
        createComponents();
        layoutComponents();
    }

    /**
     * Initializes the components of the panel
     */
    private void createComponents() {
        _txtLog = new JTextArea();
        _txtLog.setWrapStyleWord(true);
        _txtLog.setLineWrap(true);
        _txtLog.setEditable(false);
        _txtLog.setBackground(Color.WHITE);
        _scrLogs = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        _logHandler = new JTextAreaHandler(_txtLog);
    }

    /**
     * Distributes the components in the panel
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBorder(new TitledBorder(Constants.TITLE_LOG));
        _scrLogs.setViewportView(_txtLog);
        add(_scrLogs, BorderLayout.CENTER);
    }

    /**
     * Gets the handler that a <code>Logger</code> can use to send log messages.
     *
     * @return Returns the handler that a logger object can use to send log
     *         messages.
     */
    public Handler getLogHandler() {
        return _logHandler;
    }
}
