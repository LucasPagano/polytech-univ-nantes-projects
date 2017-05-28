package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class SimpleView extends JPanel {

	private static final long serialVersionUID = 2362570545376178470L;
	JPanel positionPanel;
	JTextField field;
	JButton init;
	JLabel position;

	public SimpleView() {
		this.positionPanel = new JPanel();
		this.init = new JButton("init");
		this.position = new JLabel("position");
		this.field = new JTextField("(0, 0)");
		
		this.field.setHorizontalAlignment(JTextField.CENTER);
		this.field.setPreferredSize(new Dimension(150,20));
		this.positionPanel.setPreferredSize(new Dimension(100,100));
		this.positionPanel.setBackground(Color.darkGray);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		this.add(this.positionPanel);
		
		JPanel positionField = new JPanel();
		positionField.setLayout(new BoxLayout(positionField, BoxLayout.X_AXIS));
		positionField.add(this.position);
		positionField.add(this.field);
		this.add(positionField);

		this.add(this.init);

		this.setVisible(true);
	}

	public JPanel getPositionPanel() {
		return positionPanel;
	}

	public JTextField getField() {
		return field;
	}

	public JButton getInit() {
		return init;
	}

	public JLabel getPosition() {
		return position;
	}

	public Container getPanel() {
		return this;
	}
	
}
