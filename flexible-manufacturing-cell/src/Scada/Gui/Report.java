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
    private String currentWrongPieces = "50";
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
    /*    panel1 = new JPanel();
        panel1.setBorder(paneEdge);
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        titled = BorderFactory.createTitledBorder("Current Execution");
        JPanel comp1 = new JPanel(new GridLayout(2, 2, 10,5), false);
        comp1.add(new JLabel("Right pieces: "));
        JTextField value1 = new JTextField(currentRightPieces);
        value1.setEditable(false);
        comp1.add(value1);

        comp1.add(new JLabel("Wrong pieces: "));
        JTextField value2 = new JTextField(currentWrongPieces);
        value2.setEditable(false);
        comp1.add(value2);

        comp1.setBorder(titled);
        panel1.add(Box.createRigidArea(new Dimension(0, 15)));
        panel1.add(comp1);*/


        //Report 2
        panel2 = new JPanel();
        panel2.setBorder(paneEdge);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        titled = BorderFactory.createTitledBorder("Overall Execution");

        JPanel comp2 = new JPanel(new GridLayout(2, 2,10,5), false);
        comp2.add(new JLabel("Right pieces: "));
        JTextField value3 = new JTextField(totalRightPieces);
        value3.setEditable(false);
        comp2.add(value3);

        comp2.add(new JLabel("Wrong pieces: "));
        JTextField value4 = new JTextField(totalWrongPieces);
        value4.setEditable(false);
        comp2.add(value4);

        comp2.setBorder(titled);
        panel2.add(Box.createRigidArea(new Dimension(0, 15)));
        panel2.add(comp2);


        //Report 3
        panel3 = new JPanel();
        panel3.setBorder(paneEdge);
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        titled = BorderFactory.createTitledBorder("Stops and Restarts");
      
        JPanel comp3 = new JPanel(new GridLayout(3, 2, 10,5), false);
        comp3.add(new JLabel("Number of normal stops: "));
        JTextField value5 = new JTextField(totalNormalStops);
        value5.setEditable(false);
        comp3.add(value5);
        comp3.add(new JLabel("Number of emergency stops: "));
        JTextField value6 = new JTextField(totalEmergencyStops);
        value6.setEditable(false); 
        comp3.add(value6);

        comp3.add(new JLabel("Number of restarts: "));
        JTextField value7 = new JTextField(totalRestarts);
        value7.setEditable(false);
        comp3.add(value7);

        comp3.setBorder(titled);
        panel3.add(Box.createRigidArea(new Dimension(0, 15)));
        panel3.add(comp3);

        btnClose = new JButton("Close");
        btnClose.addActionListener(btnActionListener);
    }

    private void layoutComponents() {
      /*  MigLayout contentPaneLayout = new MigLayout("wrap 1", "[fill, grow]",  "");
        Container contentPane = getContentPane();
        contentPane.setLayout(contentPaneLayout);
      */
         MigLayout contentPaneLayout = new MigLayout("wrap 2",
                "[fill][grow]", "");
        Container contentPane = getContentPane();
        contentPane.setLayout(contentPaneLayout);

         Border paneEdge = BorderFactory.createEmptyBorder(0, 10, 10, 10);
        TitledBorder titled;


        MigLayout reportLayout = new MigLayout();
        JPanel panel1 = new JPanel(reportLayout);

        panel1.setBorder(paneEdge);
        titled = BorderFactory.createTitledBorder("Prueba Execution");
        panel1.setBorder(titled);
         panel1.add(new JLabel("Right pieces: "), "");
        JTextField value1 = new JTextField(currentRightPieces);
        value1.setEditable(false);
        panel1.add(value1, "");

        panel1.add(new JLabel("Wrong pieces: "), "" );
        JTextField value2 = new JTextField(currentWrongPieces);
        value2.setEditable(false);
        panel1.add(value2, "span 1 2, wrap");

      /*  comp1.setBorder(titled);
        panel1.add(Box.createRigidArea(new Dimension(0, 15)));
        panel1.add(comp1);

        pnlButtons.setBorder(new TitledBorder(Constants.CONTROL_PANEL_TITLE));
        pnlButtons.add(buttonConfiguration, "");
        pnlButtons.add(buttonStart, "");
        pnlButtons.add(buttonEmergencyStop, "span 1 2, wrap");
        pnlButtons.add(buttonReport, "");
        pnlButtons.add(buttonStop, "wrap");*/

        contentPane.add(panel1, "");

      //  add(panel1);
        add(panel2);
        add(panel3);

       
        add(btnClose, "center, width pref!");
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
