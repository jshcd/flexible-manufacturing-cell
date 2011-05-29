package Element.Robot;

import Element.Piece.Piece;

public interface Robot {

    public void runCommand(int command);

    public Piece getLoadedPiece();

    public void setLoadedPiece(Piece loadedPiece);

    public void startServer();
}