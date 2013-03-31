package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class EditPanel extends JToolBar implements  ActionListener{

	private static final long serialVersionUID = 1L;

	DrawPanel link;
	SaveDialog dialogLink;
	
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
		
		JButton save = new JButton("Save");
		save.setActionCommand("SAVE");
		save.addActionListener(this);
		add(save);
	}
	
	public void addActionLink(DrawPanel drawPanel){
		link = drawPanel;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		if(cmd == "SAVE"){
			dialogLink.setVisible(true);
			String fileName = dialogLink.getFileName();
			if(fileName != null){
				link.saveAs(dialogLink.getFileName());
			}
		}else{
			link.setCommand(cmd);
		}
	}

	public void addDialog(SaveDialog saveDialog) {
		dialogLink = saveDialog;
	}

}
