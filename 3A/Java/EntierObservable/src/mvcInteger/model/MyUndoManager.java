package mvcInteger.model;

import java.util.Observable;

import javax.swing.undo.StateEdit;
import javax.swing.undo.UndoManager;

public class MyUndoManager extends Observable {
	UndoManager undoManager;

	public MyUndoManager() {
		this.undoManager = new UndoManager();
	}

	void addEdit(StateEdit edit) {
		this.undoManager.addEdit(edit);
		this.setChanged();
		this.notifyObservers();
	}

	public void undo() {
		if (this.canUndo()) {
			this.undoManager.undo();
			this.setChanged();
			this.notifyObservers();
		}
	}

	public boolean canUndo() {
		return this.undoManager.canUndo();
	}

	public void redo() {
		if (this.canRedo()) {
			this.undoManager.redo();
			this.setChanged();
			this.notifyObservers();
		}
	}

	public boolean canRedo() {
		return this.undoManager.canRedo();
	}
}
