package usantatecla.tictactoe.views;

import usantatecla.tictactoe.controllers.AcceptorController;
import usantatecla.tictactoe.controllers.ControllersVisitor;
import usantatecla.tictactoe.controllers.PlayController;
import usantatecla.tictactoe.controllers.ResultController;
import usantatecla.tictactoe.controllers.StartController;

public class View implements ControllersVisitor {

    private StartView startView;

	private PlayView playView;

	private ResultView resultView;

	public View() {
		this.startView = new StartView();
		this.playView = new PlayView();
		this.resultView = new ResultView();
	}

	public void interact(AcceptorController acceptorController) {
		acceptorController.accept(this);
	}

	
	public void visit(StartController startController) {
		this.startView.interact(startController);
	}

	
	public void visit(PlayController playController) {
		this.playView.interact(playController);
	}

	
	public void visit(ResultController resultController) {
		this.resultView.interact(resultController);
	}
}