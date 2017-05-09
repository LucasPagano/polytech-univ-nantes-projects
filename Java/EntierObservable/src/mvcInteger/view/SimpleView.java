package mvcInteger.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class SimpleView extends JFrame{
	JLabel label;
	JButton plusButton, minusButton;
	public SimpleView() {
		this.label = new JLabel("Valeur :");
		Container pane = this.getContentPane();
		this.plusButton = new JButton("Plus");
		this.minusButton = new JButton("Moins");
		pane.setLayout(new FlowLayout());
		
		this.plusButton.setPreferredSize(new Dimension(100, 100));
		this.minusButton.setPreferredSize(new Dimension(100, 100));
		
		pane.add(this.label);
		pane.add(this.plusButton);
		pane.add(this.minusButton);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	public JButton getPlusButton() {
		return plusButton;
	}

	public JButton getMinusButton() {
		return minusButton;
	}

	public JLabel getLabel() {
		return label;
	}

}
