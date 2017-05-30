
import java.util.ArrayList;
import java.util.List;


public class Test {
	public static void main(String[] args) {
		
		List<Model> models = new ArrayList<>();
		for(int i = 0; i< 4; i++){
			models.add(new Model());
		}
		
		
		new ControlerGenerale(models);
		
	}
}
