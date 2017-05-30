
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VueGenerale {

	public JFrame frame;
	public JPanel panelCenter;
	public JMenu menu;
	public JPanel panelLeft;
	public JButton buttonPlus;
	public JButton buttonMinus;
	public JTextField textField;
	public JLabel label;
	
	public VueGenerale(){
		this.frame = new JFrame();
		this.frame.setTitle(I18n.resourceBundle.getString(I18n.TITLE));
		
		this.panelCenter = new JPanel();
		
		this.panelLeft = new JPanel();
		this.buttonMinus = new JButton();
		this.buttonPlus = new JButton();
		this.label = new JLabel(I18n.resourceBundle.getString(I18n.POSITION));
		this.textField = new JTextField();
		
		this.panelLeft.setLayout(new BoxLayout(this.panelLeft, BoxLayout.Y_AXIS));
		this.panelLeft.setBorder(BorderFactory.createEtchedBorder());
		this.textField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.add(this.buttonPlus);
		panel.add(this.buttonMinus);
		
		JPanel panel2 = new JPanel();
		panel2.add(this.label);
		panel2.add(this.textField);
		
		this.panelLeft.add(panel);
		this.panelLeft.add(panel2);
		
		this.panelCenter.add(this.panelLeft);
		
		this.frame.add(this.panelCenter);
		
		JMenuBar menuBar = new JMenuBar();
		this.menu = new JMenu(I18n.resourceBundle.getString(I18n.MENU));
		menuBar.add(this.menu);
		
		this.frame.setJMenuBar(menuBar);
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
	}
	
	public void addVue(Vue vue){
		this.panelCenter.add(vue.panelContainer);
	}
	
}
