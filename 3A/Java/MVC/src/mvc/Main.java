package mvc;

public class Main {

	public static void main(String[] args) {

		Model model = new Model();
		MyMouseMotionListener mouseListener = new MyMouseMotionListener(model);
		Complex_view complex_view = new Complex_view(mouseListener);
		View view = new View();
		ModelObserver modelObs = new ModelObserver(complex_view, view);
		model.addObserver(modelObs);
	}
}
