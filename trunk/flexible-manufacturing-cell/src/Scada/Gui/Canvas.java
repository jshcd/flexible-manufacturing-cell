package Scada.Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import Auxiliar.Constants;
import Automaton.Slaves.Slave1;
import Automaton.Slaves.Slave2;
import Automaton.Slaves.Slave3;
import Element.Piece.Piece;

/**
 * Canvas where the factory simulation is displayed.
 *
 * @author
 */
public class Canvas extends JPanel {

    private static final long serialVersionUID = 1L;
    private ImageLoader _imageLoader;
    /*
    private AssemblyStation _assembly;
    private QualityControlStation _quality;
    private WeldingStation _welding;
     */
    private Slave1 _slave1;
    private Slave2 _slave2;
    private Slave3 _slave3;
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
    
    public void setSlave1Data(Slave1 slave){_slave1 = slave;}    
    public void setSlave2Data(Slave2 slave){_slave2 = slave;}
    public void setSlave3Data(Slave3 slave){_slave3 = slave;}

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
        //paintMasterAutomaton(g);
        paintSlave1(g);
        paintSlave2(g);
        paintSlave3(g);
    }
    
    public void paintSlave1(Graphics g){
        if(_slave1 != null){
            /* Paint Gears */
            for(Piece piece : _slave1.getGearBelt().getPieces()){
                if(piece.getType() == Element.Piece.Piece.PieceType.gear){
                    g.drawImage(_imageLoader._gear, piece.getPos().x, piece.getPos().y, null);
                }
            }
            
            /* Paint Axis */
            for(Piece piece : _slave1.getAxisBelt().getPieces()){
                if(piece.getType() == Element.Piece.Piece.PieceType.axis){
                    g.drawImage(_imageLoader._axis, piece.getPos().x, piece.getPos().y, null);
                }
            }
            
            /* Paint Assembled Pieces */
            for(Piece piece : _slave1.getWeldingBelt().getPieces()){
                if(piece.getType() == Element.Piece.Piece.PieceType.assembly){
                    g.drawImage(_imageLoader._fullPiece, piece.getPos().x, piece.getPos().y, null);
                }
            }
            
            /* Paint Sensor1 */
            if(_slave1.getSensor1().isActivated()){
                g.drawImage(_imageLoader._sensorGreen, 
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.x, 
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.y, 
                        null);
            }else{
                g.drawImage(_imageLoader._sensorRed, 
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.x, 
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.y, 
                        null);
            }
            
            /* Paint Sensor2 */
            if(_slave1.getSensor2().isActivated()){
                g.drawImage(_imageLoader._sensorGreen, 
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x, 
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x, 
                        null);
            }else{
                g.drawImage(_imageLoader._sensorRed, 
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x, 
                        Constants.GEAR_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x, 
                        null);
            }
            
            /* Paint Sensor3 */
            if(_slave1.getSensor3().isActivated()){
                g.drawImage(_imageLoader._sensorGreen, 
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.x, 
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.y, 
                        null);
            }else{
                g.drawImage(_imageLoader._sensorRed, 
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.x, 
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_LEFT_POSITION.y, 
                        null);
            }
            
             /* Paint Sensor4 */
            if(_slave1.getSensor4().isActivated()){
                g.drawImage(_imageLoader._sensorGreen, 
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x, 
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y, 
                        null);
            }else{
                g.drawImage(_imageLoader._sensorRed, 
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x, 
                        Constants.AXIS_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y, 
                        null);
            }
            
            
            /* Paint Sensor5 */
            if(_slave1.getSensor5().isActivated()){
                g.drawImage(_imageLoader._sensorGreen, 
                        Constants.ASSEMBLY_STATION_SENSOR_LIGHT_POSITION.x, 
                        Constants.ASSEMBLY_STATION_SENSOR_LIGHT_POSITION.y, 
                        null);
            }else{
                g.drawImage(_imageLoader._sensorRed, 
                        Constants.ASSEMBLY_STATION_SENSOR_LIGHT_POSITION.x, 
                        Constants.ASSEMBLY_STATION_SENSOR_LIGHT_POSITION.y, 
                        null);
            }
            
            /* Assembler Stamper */
            if(_slave1.getAssemblyStation().isMoving()){
                g.drawImage(_imageLoader._assembler, 
                        Constants.WELDING_STAMPER_POSITION_ENABLED.x, 
                        Constants.WELDING_STAMPER_POSITION_ENABLED.y, null);
            }else{                
                g.drawImage(_imageLoader._assembler, 
                        Constants.WELDING_STAMPER_POSITION_DISABLED.x, 
                        Constants.WELDING_STAMPER_POSITION_DISABLED.y, null);
                
                /* Check the pieces that are placed in the station to draw them */
                 for(Piece piece : _slave1.getAssemblyStation().getPieces()){
                    if(piece.getType() == Element.Piece.Piece.PieceType.gear){
                        g.drawImage(_imageLoader._gear,
                                Constants.WELDING_GEAR_POSITION.x, 
                                Constants.WELDING_GEAR_POSITION.y, null);
                    }else if(piece.getType() == Element.Piece.Piece.PieceType.axis){                        
                        g.drawImage(_imageLoader._axis,
                                Constants.WELDING_AXIS_POSITION.x, 
                                Constants.WELDING_AXIS_POSITION.y, null);
                    }else if(piece.getType() == Element.Piece.Piece.PieceType.assembly){                        
                        g.drawImage(_imageLoader._fullPiece,
                                Constants.WELDING_WELDED_PIECE_POSITION.x, 
                                Constants.WELDING_WELDED_PIECE_POSITION.y, null);
                    }
                }
            }
        }
    }
    
    public void paintSlave2(Graphics g){
        if(_slave2 != null){
            
            /* Paint Sensor6 */
            if(_slave2.getSensor6().isActivated()){
                g.drawImage(_imageLoader._sensorGreen, 
                        Constants.WELDING_STATION_SENSOR_LIGHT_POSITION.x, 
                        Constants.WELDING_STATION_SENSOR_LIGHT_POSITION.y, 
                        null);
            }else{
                g.drawImage(_imageLoader._sensorRed, 
                        Constants.WELDING_STATION_SENSOR_LIGHT_POSITION.x, 
                        Constants.WELDING_STATION_SENSOR_LIGHT_POSITION.y, 
                        null);
            }
            
            /* Paint Sensor7 */
            if(_slave2.getSensor7().isActivated()){
                g.drawImage(_imageLoader._sensorGreen, 
                        Constants.QUALITY_CONTROL_STATION_LIGHT_SENSOR_POSITION.x, 
                        Constants.QUALITY_CONTROL_STATION_LIGHT_SENSOR_POSITION.y, 
                        null);
            }else{
                g.drawImage(_imageLoader._sensorRed, 
                        Constants.QUALITY_CONTROL_STATION_LIGHT_SENSOR_POSITION.x, 
                        Constants.QUALITY_CONTROL_STATION_LIGHT_SENSOR_POSITION.y, 
                        null);
            }
            
            if(_slave2.getWeldingStation().isMoving()){
                g.drawImage(_imageLoader._torchEnabled, 
                        Constants.TORCH_POSITION_ENABLED.x, 
                        Constants.TORCH_POSITION_ENABLED.y, null);
            }else{                
                g.drawImage(_imageLoader._torchDisabled, 
                        Constants.TORCH_POSITION_DISABLED.x, 
                        Constants.TORCH_POSITION_DISABLED.y, null);
            }            
        }
    }
    
    public void paintSlave3(Graphics g){
        if(_slave3 != null){
            /* Paint Accepted Pieces */
            for(Piece piece : _slave3.getAcceptedBelt().getPieces()){
                g.drawImage(_imageLoader._fullPieceOk, piece.getPos().x, piece.getPos().y, null);
            }
            
            /* Paint Rejected Pieces */
            for(Piece piece : _slave3.getRejectedBelt().getPieces()){
                g.drawImage(_imageLoader._fullPieceNotOk, piece.getPos().x, piece.getPos().y, null);
            }
            
            /* Paint Sensor8 */
            if(_slave3.getSensor8().isActivated()){
                g.drawImage(_imageLoader._sensorGreen, 
                        Constants.OK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.x, 
                        Constants.OK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.y, 
                        null);
            }else{
                g.drawImage(_imageLoader._sensorRed, 
                        Constants.OK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.x, 
                        Constants.OK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.y, 
                        null);
            }
            
            /* Paint Sensor9 */
            if(_slave3.getSensor9().isActivated()){
                g.drawImage(_imageLoader._sensorGreen, 
                        Constants.OK_CONVEYOR_SENSOR_RIGHT_LIGHT_POSITION.x, 
                        Constants.OK_CONVEYOR_SENSOR_RIGHT_LIGHT_POSITION.y, 
                        null);
            }else{
                g.drawImage(_imageLoader._sensorRed, 
                        Constants.OK_CONVEYOR_SENSOR_RIGHT_LIGHT_POSITION.x, 
                        Constants.OK_CONVEYOR_SENSOR_RIGHT_LIGHT_POSITION.y, 
                        null);
            }
            
            /* Paint Sensor10 */
            if(_slave3.getSensor10().isActivated()){
                g.drawImage(_imageLoader._sensorGreen, 
                        Constants.NOTOK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.x, 
                        Constants.NOTOK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.y, 
                        null);
            }else{
                g.drawImage(_imageLoader._sensorRed, 
                        Constants.NOTOK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.x, 
                        Constants.NOTOK_CONVEYOR_SENSOR_LEFT_LIGHT_POSITION.y, 
                        null);
            }
            
            /* Paint Sensor11 */
            if(_slave3.getSensor11().isActivated()){
                g.drawImage(_imageLoader._sensorGreen, 
                        Constants.NOTOK_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x, 
                        Constants.NOTOK_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y, 
                        null);
            }else{
                g.drawImage(_imageLoader._sensorRed, 
                        Constants.NOTOK_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.x, 
                        Constants.NOTOK_CONVEYOR_SENSOR_LIGHT_RIGHT_POSITION.y, 
                        null);
            }
        }
    }
}
