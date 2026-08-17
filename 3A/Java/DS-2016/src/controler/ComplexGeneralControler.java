package controler;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import model.ComplexModel;
import model.SimpleModel;
import view.ComplexView;
import view.SimpleView;

public class ComplexGeneralControler {

	ComplexModel model;
	ArrayList<SimpleView> viewList;
	ComplexView complexView;

	public ComplexGeneralControler(ComplexModel model) {
		this.model = model;
		this.viewList = new ArrayList<SimpleView>();

		for (SimpleModel simpleModel : this.model.modelList) {
			GeneralControler gen = new GeneralControler(simpleModel);
			this.viewList.add(gen.view);
		}

		this.complexView = new ComplexView(this.viewList);
		PlusAction plus  = new PlusAction();
		MinusAction minus = new MinusAction();
		this.complexView.getPlus().setAction(plus);
		this.complexView.getMenuPlus().setAction(plus);
		this.complexView.getMinus().setAction(minus);
		this.complexView.getMenuMinus().setAction(minus);

		ComplexControler complexControler = new ComplexControler(this.model,
				this.complexView);

		for (SimpleModel simpleModel : this.model.modelList) {
			simpleModel.addObserver(complexControler);
		}

	}

	private class PlusAction extends AbstractAction {

		public PlusAction() {
			this.putValue(NAME, "plus");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (SimpleModel simpleModel : model.modelList) {
				simpleModel.plus();
			}
		}

	}

	private class MinusAction extends AbstractAction {

		public MinusAction() {
			this.putValue(NAME, "minus");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			for (SimpleModel simpleModel : model.modelList) {
				simpleModel.moins();
			}
		}

	}

}
