package mvcInteger.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SimpleView extends JFrame{
	JTextField label;
	JButton plusButton, minusButton, undoButton, redoButton;
	
	public SimpleView() {
		this.label = new JTextField("");
		this.label.setPreferredSize(new Dimension(100,20));
		Container pane = this.getContentPane();
		this.plusButton = new JButton();
		this.minusButton = new JButton();
		this.undoButton = new JButton();
		this.redoButton = new JButton();

		pane.setLayout(new FlowLayout());
		
		this.plusButton.setPreferredSize(new Dimension(100, 100));
		this.minusButton.setPreferredSize(new Dimension(100, 100));
		this.undoButton.setPreferredSize(new Dimension(100, 100));
		this.redoButton.setPreferredSize(new Dimension(100, 100));
		

		
		pane.add(this.label);
		pane.add(this.plusButton);
		pane.add(this.minusButton);
		pane.add(this.undoButton);
		pane.add(this.redoButton);
		
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

	public JTextField getLabel() {
		return label;
	}

	public JButton getUndoButton() {
		return undoButton;
	}

	public JButton getRedoButton() {
		return redoButton;
	}

}
