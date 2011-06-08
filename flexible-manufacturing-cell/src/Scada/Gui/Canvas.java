package Scada.Gui;

import Automaton.Slaves.Data.Slave1Data;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import Auxiliar.Constants;
import Automaton.Slaves.Data.Slave1Data;
import Automaton.Slaves.Data.Slave2Data;
import Automaton.Slaves.Data.Slave3Data;
import Element.Piece.Piece;

/**
 * Canvas where the factory simulation is displayed.
 *
 * @author
 */
public class Canvas extends JPanel {

    private static final long serialVersionUID = 1L;
    private ImageLoader _imageLoader;
    private Slave1Data _slave1Data;
    private Slave2Data _slave2Data;
    private Slave3Data _slave3Data;
    private boolean _emergencyStopped;

    /**
     * Creates a canvas where the factory simulation is displayed.
     *
     * @param imageLoader
     *            The image loader in charge of retrieving the images to paint
     *            the canvas.
     */
    public Canvas(ImageLoader imageLoader) {
        this._imageLoader = imageLoader;
        Dimension size = new Dimension(Constants.CANVAS_WIDTH,
                Constants.CANVAS_HEIGHT);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        _emergencyStopped = false;
      
       
    }

    public void setSlave1Data(Slave1Data slaveData) {
        _slave1Data = slaveData;
        repaint();
    }

    public void setSlave2Data(Slave2Data slaveData) {
        _slave2Data = slaveData;
        repaint();
    }

    public void setSlave3Data(Slave3Data slaveData) {
        _slave3Data = slaveData;
        repaint();
    }

    /**
     * Tells the canvas whether the system is at an emergency stop or not.
     *
     * @param emergencyStopped
     *            Whether the system is at an emergency stop or not.
     */
    public void setEmergencyStopped(boolean emergencyStopped) {
        this._emergencyStopped = emergencyStopped;
        repaint();
    }



    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g) {
        // Erases the panel
        g.drawImage(_imageLoader._background, 0, 0, null);

        // Paints the different automata
        paintSlave1(g);
        paintSlave2(g);
        paintSlave3(g);
    }
    
    public void paintSlave1(Graphics g) {
        if (_slave1Data != null) {
            /* Paint Gears */
           for (Piece piece : _slave1Data.getGearBeltPieces()) {
                if (piece.getType() == Element.Piece.Piece.PieceType.gear) {
                    g.drawImage(_imageLoader._gear, piece.getPos().x, piece.getPos().y, null);
                }
            }


            /* Paint Axis */
            for (Piece piece : _slave1Data.getAxisBeltPieces()) {
                if (piece.getType() == Element.Piece.Piece.PieceType.axis) {
                    g.drawImage(_imageLoader._axis, piece.getPos().x, piece.getPos().y, null);
                }
            }

            /* Paint Assembled Pieces */
            for (Piece piece : _slave1Data.getWeldingBeltPieces()) {
                if (piece.getType() == Element.Piece.Piece.PieceType.assembly) {
                    g.drawImage(_imageLoader._fullPiece, piece.getPos().x, piece.getPos().y, null);
                }
            }

            /* Paint Sensor1 */
            if (_slave1Data.isSensor1Status()) {
                g.drawImage(_imageLoader._sensorGreen,
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x,
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y,
                        null);
            } else {
                g.drawImage(_imageLoader._sensorRed,
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x,
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y,
                        null);
            }

            /* Paint Sensor2 */
            if (_slave1Data.isSensor2Status()) {
                g.drawImage(_imageLoader._sensorGreen,
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x,
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y,
                        null);
            } else {
                g.drawImage(_imageLoader._sensorRed,
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x,
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y,
                        null);
            }

            /* Paint Sensor3 */
            if (_slave1Data.isSensor3Status()) {
                g.drawImage(_imageLoader._sensorGreen,
                        Constants.ASSEMBLY_STATION_SENSOR_LIGHT_POSITION.x,
                        Constants.ASSEMBLY_STATION_SENSOR_LIGHT_POSITION.y,
                        null);
            } else {
                g.drawImage(_imageLoader._sensorRed,
                        Constants.ASSEMBLY_STATION_SENSOR_LIGHT_POSITION.x,
                        Constants.ASSEMBLY_STATION_SENSOR_LIGHT_POSITION.y,
                        null);
            }

            /* Paint Sensor4 */
            if (_slave1Data.isSensor4Status()) {
                g.drawImage(_imageLoader._sensorGreen,
                        Constants.WELDING_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.x,
                        Constants.WELDING_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.y,
                        null);
            } else {
                g.drawImage(_imageLoader._sensorRed,
                        Constants.WELDING_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.x,
                        Constants.WELDING_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.y,
                        null);
            }


            /* Paint Sensor5 */
            if (_slave1Data.isSensor5Status()) {
                g.drawImage(_imageLoader._sensorGreen,
                        Constants.WELDING_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x,
                        Constants.WELDING_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y,
                        null);
            } else {
                g.drawImage(_imageLoader._sensorRed,
                        Constants.WELDING_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x,
                        Constants.WELDING_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y,
                        null);
            }

            /* Assembler Stamper */
            if (_slave1Data.isAssemblyStationRunning()) {
                g.drawImage(_imageLoader._assembler,
                        Constants.WELDING_STAMPER_POSITION_ENABLED.x,
                        Constants.WELDING_STAMPER_POSITION_ENABLED.y, null);
            } else {
                g.drawImage(_imageLoader._assembler,
                        Constants.WELDING_STAMPER_POSITION_DISABLED.x,
                        Constants.WELDING_STAMPER_POSITION_DISABLED.y, null);

                /* Check the pieces that are placed in the station to draw them */
                for (Piece piece : _slave1Data.getAssemblyStationPieces()) {
                    if (piece.getType() == Element.Piece.Piece.PieceType.gear) {
                        g.drawImage(_imageLoader._gear,
                                Constants.WELDING_GEAR_POSITION.x,
                                Constants.WELDING_GEAR_POSITION.y, null);
                    } else if (piece.getType() == Element.Piece.Piece.PieceType.axis) {
                        g.drawImage(_imageLoader._axis,
                                Constants.WELDING_AXIS_POSITION.x,
                                Constants.WELDING_AXIS_POSITION.y, null);
                    } else if (piece.getType() == Element.Piece.Piece.PieceType.assembly) {
                        g.drawImage(_imageLoader._fullPiece,
                                Constants.WELDING_WELDED_PIECE_POSITION.x,
                                Constants.WELDING_WELDED_PIECE_POSITION.y, null);
                    }
                }
            }
        }
    }

    public void paintSlave2(Graphics g) {
        if (_slave2Data != null) {

            /* Paint Sensor6 */
            if (_slave2Data.isSensor6Status()) {
                g.drawImage(_imageLoader._sensorGreen,
                        Constants.WELDING_STATION_SENSOR_LIGHT_POSITION.x,
                        Constants.WELDING_STATION_SENSOR_LIGHT_POSITION.y,
                        null);
            } else {
                g.drawImage(_imageLoader._sensorRed,
                        Constants.WELDING_STATION_SENSOR_LIGHT_POSITION.x,
                        Constants.WELDING_STATION_SENSOR_LIGHT_POSITION.y,
                        null);
            }

            /* Paint Sensor7 */
            if (_slave2Data.isSensor7Status()) {
                g.drawImage(_imageLoader._sensorGreen,
                        Constants.QUALITY_CONTROL_STATION_LIGHT_SENSOR_POSITION.x,
                        Constants.QUALITY_CONTROL_STATION_LIGHT_SENSOR_POSITION.y,
                        null);
            } else {
                g.drawImage(_imageLoader._sensorRed,
                        Constants.QUALITY_CONTROL_STATION_LIGHT_SENSOR_POSITION.x,
                        Constants.QUALITY_CONTROL_STATION_LIGHT_SENSOR_POSITION.y,
                        null);
            }

            /* Paint torch */
            if (_slave2Data.isWeldingStationRunning()) {
                g.drawImage(_imageLoader._torchEnabled,
                        Constants.TORCH_POSITION_ENABLED.x,
                        Constants.TORCH_POSITION_ENABLED.y, null);
            } else {
                g.drawImage(_imageLoader._torchDisabled,
                        Constants.TORCH_POSITION_DISABLED.x,
                        Constants.TORCH_POSITION_DISABLED.y, null);
            }
            
            /* Paint glass */
            if(_slave2Data.isQualityStationRunning()){                
                g.drawImage(_imageLoader._glassEnabled,
                        Constants.GLASS_POSITION_ENABLED.x,
                        Constants.GLASS_POSITION_ENABLED.y, null);
            }else{                
                g.drawImage(_imageLoader._glassDisabled,
                        Constants.GLASS_POSITION_ENABLED.x,
                        Constants.GLASS_POSITION_ENABLED.y, null);                
            }
            
            /* Paint Torched Pieces */
            if(!_slave2Data.getWeldingStationPieces().isEmpty()) {
                g.drawImage(_imageLoader._fullPiece, Constants.WELDING_STATION_CENTER_POSITION.x, 
                        Constants.WELDING_STATION_CENTER_POSITION.y, null);
            }
            
            /* Paint Quality Pieces */
            if(!_slave2Data.getQualityStationPieces().isEmpty()) {
                g.drawImage(_imageLoader._fullPiece, Constants.QUALITY_STATION_CENTER_POSITION.x, 
                        Constants.QUALITY_STATION_CENTER_POSITION.y, null);
            }
        }
    }

    public void paintSlave3(Graphics g) {
        if (_slave3Data != null) {
            /* Paint Accepted Pieces */
            
         //  System.out.println("ok : "+_slave3Data.getAcceptedBeltPieces().size());
          for (Piece piece : _slave3Data.getAcceptedBeltPieces()) {
                g.drawImage(_imageLoader._fullPieceOk, piece.getPos().x, piece.getPos().y, null);
            }

            /* Paint Rejected Pieces */
            for (Piece piece : _slave3Data.getRejectedBeltPieces()) {
                g.drawImage(_imageLoader._fullPieceNotOk, piece.getPos().x, piece.getPos().y, null);
               
            }

            /* Paint Sensor8 */
            if (_slave3Data.isSensor8Status()) {
                g.drawImage(_imageLoader._sensorGreen,
                        Constants.NOTOK_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x,
                        Constants.NOTOK_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y,
                        null);
            } else {
                g.drawImage(_imageLoader._sensorRed,
                        Constants.NOTOK_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x,
                        Constants.NOTOK_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y,
                        null);
            }

            /* Paint Sensor9 */
            if (_slave3Data.isSensor9Status()) {
                g.drawImage(_imageLoader._sensorGreen,
                        Constants.OK_CONVEYOR_SENSOR_RIGHT_LIGHT_POSITION.x,
                        Constants.OK_CONVEYOR_SENSOR_RIGHT_LIGHT_POSITION.y,
                        null);
            } else {
                g.drawImage(_imageLoader._sensorRed,
                        Constants.OK_CONVEYOR_SENSOR_RIGHT_LIGHT_POSITION.x,
                        Constants.OK_CONVEYOR_SENSOR_RIGHT_LIGHT_POSITION.y,
                        null);
            }

            /* Paint Sensor10 */
            if (_slave3Data.isSensor10Status()) {
                g.drawImage(_imageLoader._sensorGreen,
                        Constants.NOTOK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.x,
                        Constants.NOTOK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.y,
                        null);
            } else {
                g.drawImage(_imageLoader._sensorRed,
                        Constants.NOTOK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.x,
                        Constants.NOTOK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.y,
                        null);
            }

            /* Paint Sensor11 */
            if (_slave3Data.isSensor11Status()) {
                g.drawImage(_imageLoader._sensorGreen,
                        Constants.OK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.x,
                        Constants.OK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.y,
                        null);
            } else {
                g.drawImage(_imageLoader._sensorRed,
                        Constants.OK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.x,
                        Constants.OK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.y,
                        null);
            }
        }
    }
}
