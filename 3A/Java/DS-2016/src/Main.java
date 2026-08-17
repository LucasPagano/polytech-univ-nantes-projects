import model.ComplexModel;
import model.SimpleModel;
import controler.ComplexGeneralControler;


public class Main {
 public static void main(String[] args) {
	SimpleModel model1 = new SimpleModel();
	SimpleModel model2 = new SimpleModel();
	SimpleModel model3 = new SimpleModel();
	SimpleModel model4 = new SimpleModel();
	
	ComplexModel complexModel = new ComplexModel(model1, model2, model3, model4);
	new ComplexGeneralControler(complexModel);
}
}
