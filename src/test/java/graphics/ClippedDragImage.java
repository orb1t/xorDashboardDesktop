package graphics;

/**
 * Created by orb1t_ua on 10/28/16.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ClippedDragImage extends DragImage {
	int oldX, oldY;

	public ClippedDragImage(Image i) {
		super(i);
	}

	public void mouseDragged(MouseEvent e) {
		imageX = e.getX();
		imageY = e.getY();
		Rectangle r = getAffectedArea(oldX, oldY, imageX, imageY, imageWidth, imageHeight);
		repaint(r); // repaint just the affected part of the component
		oldX = imageX;
		oldY = imageY;
	}

	private Rectangle getAffectedArea(int oldx, int oldy, int newx, int newy, int width, int height) {
		int x = Math.min(oldx, newx);
		int y = Math.min(oldy, newy);
		int w = (Math.max(oldx, newx) + width) - x;
		int h = (Math.max(oldy, newy) + height) - y;
		return new Rectangle(x, y, w, h);
	}

	public static void main(String[] args) {
		String imageFile = "A.jpg";
		Image image = Toolkit.getDefaultToolkit().getImage(
				ClippedDragImage.class.getResource(imageFile));
		image = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
		JFrame frame = new JFrame("ClippedDragImage");
		frame.getContentPane().add(new ClippedDragImage(image));
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class DragImage extends JComponent implements MouseMotionListener {
	static int imageWidth = 60, imageHeight = 60;

	int grid = 10;

	int imageX, imageY;

	Image image;

	public DragImage(Image i) {
		image = i;
		addMouseMotionListener(this);
	}

	public void mouseDragged(MouseEvent e) {
		imageX = e.getX();
		imageY = e.getY();
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.drawImage(image, imageX, imageY, this);
	}

}
