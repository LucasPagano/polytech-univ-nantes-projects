
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Vue {
	public JPanel panelContainer;
	public JPanel panelMove;
	public JLabel labelPosition;
	public JTextField textField;
	public JButton buttonInit;
	
	public Vue(){
		this.panelContainer = new JPanel();
		this.panelMove = new JPanel();
		this.labelPosition = new JLabel(I18n.resourceBundle.getString(I18n.POSITION));
		this.textField = new JTextField();
		this.buttonInit = new JButton();
		
		this.panelContainer.setLayout(new BoxLayout(this.panelContainer, BoxLayout.Y_AXIS));
		this.panelContainer.setBorder(BorderFactory.createEtchedBorder());
		
		this.textField.setColumns(10);
		
		this.panelMove.setPreferredSize(new Dimension(100, 100));
		this.panelMove.setBackground(Color.gray);
		
		this.panelContainer.add(panelMove);
		JPanel panel = new JPanel();
		panel.add(labelPosition);
		panel.add(textField);
		this.panelContainer.add(panel);
		
		this.panelContainer.add(buttonInit);
	}
}
