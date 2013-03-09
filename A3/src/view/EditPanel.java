package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class EditPanel extends JToolBar implements  ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DrawPanel link;
	
	public EditPanel() {
		super("Toolbar", JToolBar.HORIZONTAL);
		setSize(600,50);
		
		JButton draw = new JButton("Draw");
		draw.setActionCommand("DRAW");
		draw.addActionListener(this); 
		add(draw);
		
		JButton erase = new JButton("Erase");
		erase.setActionCommand("ERASE");
		erase.addActionListener(this); 
		add(erase);
		
		JButton select = new JButton("Select");
		select.setActionCommand("SELECT");
		select.addActionListener(this); 
		add(select);
	}
	
	public void addActionLink(DrawPanel drawPanel){
		link = drawPanel;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		String cmd = event.getActionCommand();
		link.setCommand(cmd);
	}

}
