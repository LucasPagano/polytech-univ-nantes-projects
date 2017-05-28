package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ComplexView extends JFrame {

	private static final long serialVersionUID = -6924045754215079089L;

	JButton plus;
	JButton minus;
	JLabel position;
	JTextField field;
	JMenuItem menuPlus;
	JMenuItem menuMinus;
	
	public ComplexView(ArrayList<SimpleView> viewList) {
		this.setTitle("PolyMouvement");
		this.plus = new JButton("plus");
		this.minus = new JButton("minus");
		this.position = new JLabel("position");
		this.field = new JTextField("(0, 0)");
		this.menuPlus = new JMenuItem("Plus");
		this.menuMinus = new JMenuItem("Moins");
		
		this.field.setPreferredSize(new Dimension(100,20));
		this.field.setHorizontalAlignment(JTextField.CENTER);
		
		
		Container panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(200,100));
		
		JPanel innerleftPanel = new JPanel();
		innerleftPanel.add(this.plus);
		innerleftPanel.add(this.minus);
		innerleftPanel.add(this.position);
		innerleftPanel.add(this.field);
		innerleftPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		leftPanel.add(innerleftPanel, BorderLayout.CENTER);
		panel.add(leftPanel);

		
		for (SimpleView view : viewList){
			panel.add(view);
			
		}
		
		JMenuBar menuBar = new JMenuBar();
		JMenu actions = new JMenu("Actions");
		actions.add(this.menuPlus);
		actions.add(this.menuMinus);
		
		menuBar.add(actions);
		
		this.setJMenuBar(menuBar);
		this.add(panel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
	}
	
	public JButton getPlus() {
		return plus;
	}

	public JButton getMinus() {
		return minus;
	}

	public JLabel getPosition() {
		return position;
	}

	public JTextField getField() {
		return field;
	}
	
	public JMenuItem getMenuPlus() {
		return menuPlus;
	}

	public JMenuItem getMenuMinus() {
		return menuMinus;
	}

}
