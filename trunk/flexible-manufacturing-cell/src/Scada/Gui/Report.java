package Scada.Gui;

import Scada.DataBase.MasterConfigurationData;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SpringLayout.Constraints;
import net.miginfocom.swing.MigLayout;

public class Report extends JDialog {

    private JButton _buttonClose;
    private JPanel _panel1,_panel2,_panel3;
    private String _currentRightPieces,_currentWrongPieces,_totalRightPieces,
            _totalWrongPieces,_totalNormalStops,_totalEmergencyStops, _totalRestarts;

    /* LISTENERS */
    private ActionListener  _btnActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == _buttonClose) {
                setVisible(false);
            }
        }
    };

    public Report() {
        setTitle("Report");
        createComponents();
        layoutComponents();
        pack();
        setLocationRelativeTo(null);
    }

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

    private void layoutComponents() {

        MigLayout contentPaneLayout = new MigLayout("wrap 2",
                "[fill][grow]", "");
        Container contentPane = getContentPane();
        contentPane.setLayout(contentPaneLayout);

        add(_panel1);
        add(_panel2);
        add(_panel3,  "wrap 15px");

        MigLayout reportLayout = new MigLayout("wrap 2",
                "[left][fill, grow, 40lp:40lp:]", "");

        _panel1.setLayout(reportLayout);
        _panel1.add(new JLabel("Right pieces: "), "");
        JTextField value1 = new JTextField(_currentRightPieces);
        value1.setEditable(false);
        _panel1.add(value1, "span 2, wrap");
        _panel1.add(new JLabel("Wrong pieces: "));
        JTextField value2 = new JTextField(_currentWrongPieces);
        value2.setEditable(false);
        _panel1.add(value2, "span 2, wrap");

        reportLayout = new MigLayout("wrap 2",
                "[left][fill, grow, 40lp:40lp:]", "");
        _panel2.setLayout(reportLayout);
        _panel2.add(new JLabel("Right pieces: "), "");
        JTextField value3 = new JTextField(_totalRightPieces);
        value3.setEditable(false);
        _panel2.add(value3, "span 2, wrap");
        _panel2.add(new JLabel("Wrong pieces: "), "");
        JTextField value4 = new JTextField(_totalWrongPieces);
        value4.setEditable(false);
        _panel2.add(value4, "span 2, wrap");

        reportLayout = new MigLayout("wrap 2",
                "[left][fill, grow, 40lp:40lp:]", "");
        _panel3.setLayout(reportLayout);
        _panel3.add(new JLabel("Number of normal stops: "), "");
        JTextField value5 = new JTextField(_totalNormalStops);
        value5.setEditable(false);
        _panel3.add(value5, "span 2, wrap");
        _panel3.add(new JLabel("Number of emergency stops: "), "");
        JTextField value6 = new JTextField(_totalEmergencyStops);
        value6.setEditable(false);
        _panel3.add(value6, "span 2, wrap");
        _panel3.add(new JLabel("Number of restarts: "), "");
        JTextField value7 = new JTextField(_totalRestarts);
        value7.setEditable(false);
        _panel3.add(value7, "span 2, wrap");

        
        add(_buttonClose, "cell 0 4, right, width pref!");


    }

    public void getValues (MasterConfigurationData configurationParameters){
        //valores que se muestran en el informe
        /*
        currentRightPieces;
        currentWrongPieces;
        totalRightPieces;
        totalWrongPieces;
        totalNormalStops;
        totalEmergencyStops;
        totalRestarts;
         */
    }


}
