package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaveDialog extends JDialog implements  ActionListener{

	private static final long serialVersionUID = 1L;
    private JTextField textField = new JTextField(1);
    String fileName = null;
    
	public SaveDialog(Frame owner) {
		super(owner,"Save as",true);
		setSize(250, 100);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		JLabel info = new JLabel("Please type a file name (XML)");
		add(info, BorderLayout.NORTH);
		
		add(textField, BorderLayout.CENTER);
		
        JButton ok = new JButton("Ok");
        ok.setActionCommand("OK");
        ok.addActionListener(this);
     
        JButton cancel = new JButton("Cancel");
        cancel.setActionCommand("CANCEL");
        cancel.addActionListener(this);
        
        JPanel panel = new JPanel();
        panel.add(ok);
        panel.add(cancel);        
        add(panel,BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "OK"){
			setVisible(false);
			System.out.print(textField.getText());
			fileName = textField.getText();
			textField.setText("");
		}else if(e.getActionCommand() == "CANCEL"){
			setVisible(false);
			textField.setText("");
		}
	}

	public String getFileName() {
		return fileName;
	}

}
