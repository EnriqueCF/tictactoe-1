package usantatecla.tictactoe.models;

import usantatecla.tictactoe.types.Error;
import usantatecla.tictactoe.types.PlayerType;
import usantatecla.tictactoe.types.Token;

public class Game {
    
    private Board board;

	private Player[] players;

    private Turn turn;
    
    public Game() {
        this.board = new Board();
        this.players = new Player[Turn.PLAYERS];
        this.turn = new Turn(this.players);
    }

    public void createPlayers(int numberOfUsers) {
		for (int i = 0; i < numberOfUsers; i++) {
			this.players[i] = new Player(Token.values()[i], this.board, PlayerType.USER_PLAYER);
		}
		for (int i = numberOfUsers; i < Turn.PLAYERS; i++) {
			this.players[i] = new Player(Token.values()[i], this.board, PlayerType.MACHINE_PLAYER);
		}
    }

    Memento createMemento() {
        Board board = this.board.copy();
        return new Memento(board, this.createCopyOfPlayers(players, board), turn);
    }

    void set(Memento memento) {
        this.board = memento.getBoard().copy();
        this.players = this.createCopyOfPlayers(memento.getPlayers(), this.board);
        this.turn = memento.getTurn().copy(this.players);
    }

    private Player[] createCopyOfPlayers(Player[] players, Board board) {
        Player[] playersCopy = new Player[2];
        for (int i = 0; i < Turn.PLAYERS; i++) {
            playersCopy[i] = new Player(players[i].getToken(), board, players[i].getType());
        }
        return playersCopy;
    }

    public Board getBoard() {
        return this.board;
    }

    public boolean isBoardComplete() {
        return this.board.isCompleted();
    }

    public void putTokenPlayerFromTurn(Coordinate coordinate) {
        this.turn.getPlayer().put(coordinate);
    }

    public void moveTokenPlayerFromTurn(Coordinate[] coordinates) {
        this.turn.getPlayer().move(coordinates);
    }

    public Player getTokenPlayerFromTurn() {
        return this.turn.getPlayer();
    }

    public PlayerType getTypeOfTokenPlayerFromTurn() {
        return this.turn.getPlayer().getType();
    }

    public Error controlErrorsPutCoordinate(Coordinate coordinate) {
        if (!board.isEmpty(coordinate)) {
			return Error.NOT_OWNER;
		}
		return null;
    }

    public Error controlErrorsMoveOriginCoordinate(Coordinate originCoordinate) {
        if (!board.isOccupied(originCoordinate, this.turn.getPlayer().getToken())) {
			return Error.NOT_OWNER;
		}
		return null;
    }

    public Error controlErrorsMoveTargetCoordinate(Coordinate originCoordinate, Coordinate targetCoordinate) {
        if (originCoordinate.equals(targetCoordinate)) {
			return Error.SAME_COORDINATES;
		} else if (!board.isEmpty(targetCoordinate)) {
			return Error.NOT_EMPTY;
		}
		return null;
    }

    public Token getToken(int row, int column) {
        return this.board.getToken(new Coordinate(row, column));
    }

    public void changeTurn() {
        this.turn.change();
    }

    public boolean isTicTacToe() {
        return this.board.isTicTacToe(this.turn.getOtherPlayer().getToken());
    }

    public int getOtherValueFromTurn() {
        return this.turn.getOtherValue();
    }
}