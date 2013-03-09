package view;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ToolBar() {
		super("Toolbar", JToolBar.HORIZONTAL);
		setBounds(30,0,480,30);
		JButton draw = new JButton("Draw");
		add(draw);
	}

}
