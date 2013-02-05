
import model.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DoozerMain extends JComponent implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	/*
	 * Set main window
	 */
	private Crane crane = new Crane();
	
	public static void main(String[] args) {
		DoozerMain canvas = new DoozerMain();
		
		JFrame f = new JFrame("Doozer Main");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600, 480);
		f.setContentPane(canvas);
		f.setVisible(true);
		
	}
	
	public DoozerMain(){
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/*
	 * Draw things
	 */
	public void paintComponent(Graphics g) {
		crane.drawCrane(g);
	}

	/*
	 * Click, Drag and release
	 */
	@Override
	public void mousePressed(MouseEvent event) {
		Point clicked = event.getPoint();
		//Same way the normal window, x and y on top left
		System.out.print("Click"+clicked.getX()+" "+clicked.getY());
		//Pass the event to scene
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		Point clicked = event.getPoint();
		System.out.print("Click"+clicked.getX()+" "+clicked.getY());
	}
	
	@Override
	public void mouseReleased(MouseEvent event) {
		//Tell the system to stop doing what ever
	}
	
	/*
	 * Ignore the following event
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent event) {}
	@Override
	public void mouseMoved(MouseEvent event) {}
	@Override
	public void mouseExited(MouseEvent event) {}
	@Override
	public void mouseClicked(MouseEvent event) {}
}
