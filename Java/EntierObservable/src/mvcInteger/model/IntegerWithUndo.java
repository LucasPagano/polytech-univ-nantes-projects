package mvcInteger.model;

import java.util.Hashtable;

import javax.swing.undo.StateEdit;
import javax.swing.undo.StateEditable;

public class IntegerWithUndo extends MyInteger implements StateEditable{

	MyUndoManager undoManager;

	public IntegerWithUndo() {
		this.undoManager = new MyUndoManager();
	}

	@Override
	public void setVal(int val) throws MyRangeException {
		StateEdit stateEdit = new StateEdit(this);
		super.setVal(val);
		stateEdit.end();
		this.undoManager.addEdit(stateEdit);
	}
	
	@Override
	public void storeState(Hashtable<Object, Object> state) {
		state.put("intValue", this.getVal());
	}

	@Override
	public void restoreState(Hashtable<?, ?> state) {
		Integer val;
		if ((val = (int)state.get("intValue")) != null){
			try {
				super.setVal(val.intValue());
			} catch (MyRangeException e) {
				e.getMessage();
			}
		}
	}

	public MyUndoManager getUndoManager() {
		return undoManager;
	}
}
