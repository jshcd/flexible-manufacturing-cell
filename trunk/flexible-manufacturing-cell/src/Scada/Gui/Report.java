package Scada.Gui;

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

    private JButton btnClose;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private String currentRightPieces = "20";
    private String currentWrongPieces = "100";
    private String totalRightPieces;
    private String totalWrongPieces;
    private String totalNormalStops;
    private String totalEmergencyStops;
    private String totalRestarts;

    /* LISTENERS */
    private ActionListener btnActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnClose) {
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

        getValues();


        //Report 1
        panel1 = new JPanel();
        panel1.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Current Execution");
        panel1.setBorder(titled);

        //Report 2
        panel2 = new JPanel();
        panel2.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Overall Execution");
        panel2.setBorder(titled);

        //Report 3
        panel3 = new JPanel();
        panel3.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Stops and Restarts");
        panel3.setBorder(titled);
       
        btnClose = new JButton("Close");
        btnClose.addActionListener(btnActionListener);
    }

    private void layoutComponents() {

        MigLayout contentPaneLayout = new MigLayout("wrap 2",
                "[fill][grow]", "");
        Container contentPane = getContentPane();
        contentPane.setLayout(contentPaneLayout);

        add(panel1);
        add(panel2);
        add(panel3);

        MigLayout reportLayout = new MigLayout("wrap 2",
                "[left][fill, grow, 40lp:40lp:]", "");

        panel1.setLayout(reportLayout);
        panel1.add(new JLabel("Right pieces: "), "");
        JTextField value1 = new JTextField(currentRightPieces);
        value1.setEditable(false);
        panel1.add(value1, "span 2, wrap");
        panel1.add(new JLabel("Wrong pieces: "));
        JTextField value2 = new JTextField(currentWrongPieces);
        value2.setEditable(false);
        panel1.add(value2, "span 2, wrap");

        reportLayout = new MigLayout("wrap 2",
                "[left][fill, grow, 40lp:40lp:]", "");
        panel2.setLayout(reportLayout);
        panel2.add(new JLabel("Right pieces: "), "");
        JTextField value3 = new JTextField(totalRightPieces);
        value3.setEditable(false);
        panel2.add(value3, "span 2, wrap");
        panel2.add(new JLabel("Wrong pieces: "), "");
        JTextField value4 = new JTextField(totalWrongPieces);
        value4.setEditable(false);
        panel2.add(value4, "span 2, wrap");

        reportLayout = new MigLayout("wrap 2",
                "[left][fill, grow, 40lp:40lp:]", "");
        panel3.setLayout(reportLayout);
        panel3.add(new JLabel("Number of normal stops: "), "");
        JTextField value5 = new JTextField(totalNormalStops);
        value5.setEditable(false);
        panel3.add(value5, "span 2, wrap");
        panel3.add(new JLabel("Number of emergency stops: "), "");
        JTextField value6 = new JTextField(totalEmergencyStops);
        value6.setEditable(false);
        panel3.add(value6, "span 2, wrap");
        panel3.add(new JLabel("Number of restarts: "), "");
        JTextField value7 = new JTextField(totalRestarts);
        value7.setEditable(false);
        panel3.add(value7, "span 2, wrap");

        add(btnClose, "cell 1 3, center, width pref!");


    }

    private void getValues() {
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

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("BorderDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Report newContentPane = new Report();
        // newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
