package cosc4353;

import java.util.Stack;

public class ActionManager {
	
	private Stack<Action> undos = new Stack<Action>();
	private Stack<Action> redos = new Stack<Action>();

	public void executeAction(Action a) {
		a.execute();
		undos.push(a);
		redos.clear();
	}

	public boolean isUndoAvailable() {
		return !undos.empty();
	}

	public void undo() {
		assert(!undos.empty());
		Action action = undos.pop();
		action.undo();
		redos.push(action);
	}

	public boolean isRedoAvailable() {
		return !redos.empty();
	}
	
	public int getsize() {
		return undos.size();
	}
	
	public void redo() {
		System.out.println("CARD PRICE");
	}
}