package mvc;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Complex_view extends View {

	private static final long serialVersionUID = 1L;

	JList<String> list;
	Container bigPanel;
	Container leftPanel;
	JTextField textField;
	DefaultListModel<String> listModel;;

	public Complex_view(MyMouseMotionListener mouseListener) {
		super();
		this.bigPanel = this.getContentPane();
		this.leftPanel = new JPanel();
		this.listModel = new DefaultListModel<String>();
		this.list = new JList<String>(this.listModel);
		this.textField = new JTextField();

		this.setTitle("Complex view");

		textField.setPreferredSize(new Dimension(250, 250));

		JPanel capturePanel = new JPanel();
		capturePanel.setPreferredSize(new Dimension(150, 500));
		capturePanel.addMouseMotionListener(mouseListener);

		JScrollPane listPanel = new JScrollPane();
		listPanel.setPreferredSize(new Dimension(250, 250));
		listPanel.getViewport().add(this.list);

		this.leftPanel.add(this.textField);
		this.leftPanel.add(listPanel);
		this.bigPanel.add(leftPanel);
		this.bigPanel.add(capturePanel);

		LayoutManager bigLayout = new GridLayout(1, 2);
		LayoutManager leftLayout = new GridLayout(2, 1);

		this.setLayout(bigLayout);
		this.leftPanel.setLayout(leftLayout);
		this.pack();
	}
}
