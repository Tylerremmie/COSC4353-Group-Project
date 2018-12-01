package cosc4353;

import java.util.Stack;

public class ActionManager {
	
	private Stack<Action> undos = new Stack<Action>();
	private Stack<Action> redos = new Stack<Action>();

	public boolean executeAction(Action a) {
		a.execute();
		undos.push(a);
		redos.clear();
		return true;
	}

	public boolean isUndoAvailable() {
		return !undos.empty();
	}

	public boolean undo() {
		assert(!undos.empty());
		Action action = undos.pop();
		action.undo();
		redos.push(action);
		return true;
	}

	public boolean isRedoAvailable() {
		return !redos.empty();
	}

	public boolean redo() {
		assert(!redos.empty());
		Action action = redos.pop();
		action.execute();
		undos.push(action);
		return true;
	}
	
	public int getsize() {
		return undos.size();
	}
}