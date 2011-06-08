package Scada.Gui;

import Scada.DataBase.ReportData;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 * Class the represents the window in which the reports are shown.
 * @author Echoplex
 */
public class Report extends JDialog {

    /* GUI COMPONENTS */
    private JButton _buttonClose;
    private JPanel _panel1, _panel2, _panel3;
    private String _currentRightPieces, _currentWrongPieces, _totalRightPieces,
            _totalWrongPieces, _totalNormalStops, _totalEmergencyStops, _totalRestarts;
    private JTextField _currentRightPiecesTxt, _currentWrongPiecesTxt, _totalRightPiecesTxt,
            _totalWrongPiecesTxt, _totalNormalStopsTxt, _totalEmergencyStopsTxt, _totalRestartsTxt;

    /* LISTENERS */
    private ActionListener _btnActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == _buttonClose) {
                setVisible(false);
            }
        }
    };

    /**
     * Constructor of the class
     */
    public Report() {
        setTitle("Report");
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

        //Report 1
        _panel1 = new JPanel();
        _panel1.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Current Execution");
        _panel1.setBorder(titled);

        //Report 2
        _panel2 = new JPanel();
        _panel2.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Overall Execution");
        _panel2.setBorder(titled);

        //Report 3
        _panel3 = new JPanel();
        _panel3.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Stops and Restarts");
        _panel3.setBorder(titled);

        _buttonClose = new JButton("Close");
        _buttonClose.addActionListener(_btnActionListener);
    }

    /**
     * Distributes the components in the panel
     */
    private void layoutComponents() {

        MigLayout contentPaneLayout = new MigLayout("wrap 2",
                "[fill][grow]", "");
        Container contentPane = getContentPane();
        contentPane.setLayout(contentPaneLayout);

        add(_panel1);
        add(_panel2);
        add(_panel3, "wrap 15px");

        MigLayout reportLayout = new MigLayout("wrap 3",
                "[left][fill, grow, 40lp:40lp:]", "");

        _panel1.setLayout(reportLayout);
        _panel1.add(new JLabel("Right pieces: "), "span 2");
        _currentRightPiecesTxt = new JTextField(_currentRightPieces);
        _currentRightPiecesTxt.setEditable(false);
        _panel1.add(_currentRightPiecesTxt, "span 2, wrap");
        _panel1.add(new JLabel("Wrong pieces: "), "span 2");
        _currentWrongPiecesTxt = new JTextField(_currentWrongPieces);
        _currentWrongPiecesTxt.setEditable(false);
        _panel1.add(_currentWrongPiecesTxt, "span 2, wrap");

        reportLayout = new MigLayout("wrap 3",
                "[left][fill, grow, 40lp:40lp:]", "");
        _panel2.setLayout(reportLayout);
        _panel2.add(new JLabel("Right pieces: "), "");
        _totalRightPiecesTxt = new JTextField(_totalRightPieces);
        _totalRightPiecesTxt.setEditable(false);
        _panel2.add(_totalRightPiecesTxt, "span 2");
        _panel2.add(new JLabel("Wrong pieces: "), "");
        _totalWrongPiecesTxt = new JTextField(_totalWrongPieces);
        _totalWrongPiecesTxt.setEditable(false);
        _panel2.add(_totalWrongPiecesTxt, "span 2");

        reportLayout = new MigLayout("wrap 3",
                "[left][fill, grow, 40lp:40lp:]", "");
        _panel3.setLayout(reportLayout);
        _panel3.add(new JLabel("Number of normal stops: "), "");
        _totalNormalStopsTxt = new JTextField(_totalNormalStops);
        _totalNormalStopsTxt.setEditable(false);
        _panel3.add(_totalNormalStopsTxt, "span 2");
        _panel3.add(new JLabel("Number of emergency stops: "), "");
        _totalEmergencyStopsTxt = new JTextField(_totalEmergencyStops);
        _totalEmergencyStopsTxt.setEditable(false);
        _panel3.add(_totalEmergencyStopsTxt, "span 2");
        _panel3.add(new JLabel("Number of restarts: "), "");
        _totalRestartsTxt = new JTextField(_totalRestarts);
        _totalRestartsTxt.setEditable(false);
        _panel3.add(_totalRestartsTxt, "span 2");


        add(_buttonClose, "cell 0 4, right, width pref!");


    }

    /**
     * Shows the specified report data.
     *
     * @param reportData
     *            The report data to show.
     */
    public void getValues(ReportData reportData) {
        _currentRightPiecesTxt.setText(String.valueOf(reportData._rightPiecesCurrentExec));
        _currentWrongPiecesTxt.setText(String.valueOf(reportData._wrongPiecesCurrentExec));
        _totalRightPiecesTxt.setText(String.valueOf(reportData._rightPiecesAllExec));
        _totalWrongPiecesTxt.setText(String.valueOf(reportData._wrongPiecesAllExec));
        _totalNormalStopsTxt.setText(String.valueOf(reportData._nNormalStops));
        _totalEmergencyStopsTxt.setText(String.valueOf(reportData._nEmergencyStops));
        _totalRestartsTxt.setText(String.valueOf(reportData._nRestarts));
    }
}
