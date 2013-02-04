
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class DoozerMain extends JComponent {

	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	/*
	 * Set main window
	 */
	public static void main(String[] args) {
		
		DoozerMain canvas = new DoozerMain();
		JFrame f = new JFrame("Doozer Main");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 400);
		f.setContentPane(canvas);
		f.setVisible(true);
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(20, 240);
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(0, 0, 0, -200); // vertical axis
		g2.drawLine(0, 0, 200, 0);  // horizontal axis
		g2.setStroke(new BasicStroke(5));   // line
		g2.setColor(Color.RED); 
		g2.drawLine(40, 0, 120, 0); 
		g2.drawOval(40-4, -4, 8, 8);
		g2.drawOval(120-4, -4, 8, 8); 
		 // Copy last 4 lines.  Change color to GREEN.
		// What transformations to include to have it rotate
		// 45 degrees about the left-most endpoint?
		g2.setColor(Color.GREEN); 
		g2.translate(40,0);
		g2.rotate(-Math.PI/4.0);
		g2.translate(-40,0);
		g2.drawLine(40, 0, 120, 0); 
		g2.drawOval(40-4, -4, 8, 8);
		g2.drawOval(120-4, -4, 8, 8); 
}
}
