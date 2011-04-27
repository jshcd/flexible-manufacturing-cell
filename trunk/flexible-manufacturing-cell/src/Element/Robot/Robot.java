package Element.Robot;

import Element.Piece.Piece;

public interface Robot {

	public void runCommand(int command);

	public RobotMailBox getMailBox();

	public void setMailBox(RobotMailBox mailBox);

	public void notifyAutomaton();

	public Piece getLoadedPiece();

	public void setLoadedPiece(Piece loadedPiece);
}