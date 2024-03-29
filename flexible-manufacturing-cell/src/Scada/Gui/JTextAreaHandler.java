package Scada.Gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.swing.JTextArea;

/**
 * Allows a <code>Logger</code> to send log messages to a <code>JTextArea</code>
 * @author Echoplex
 */
public class JTextAreaHandler extends Handler {

    private JTextArea _txtLog;
    private SimpleDateFormat _dateFormatter;

    /**
     * Constructs a logging handler for a particular <code>JTextArea</code>.
     *
     * @param txtLog
     *            The <code>JTextArea</code>.
     */
    public JTextAreaHandler(JTextArea txtLog) {
        this._txtLog = txtLog;
        _dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    }

    /**
     * @see java.util.logging.Handler#close()
     */
    @Override
    public void close() throws SecurityException {
    }

    /**
     * @see java.util.logging.Handler#flush()
     */
    @Override
    public void flush() {
    }

    /**
     * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
     */
    @Override
    public void publish(LogRecord record) {
        Date date = new Date(record.getMillis());
        String level;
        if (record.getLevel() == Level.INFO) {
            level = "INFO";
        } else {
            level = "ERROR";
        }
        _txtLog.append(_dateFormatter.format(date) + " [" + level + "] => "
                + record.getMessage() + "\n");
        _txtLog.setCaretPosition(_txtLog.getText().length());
    }
}
