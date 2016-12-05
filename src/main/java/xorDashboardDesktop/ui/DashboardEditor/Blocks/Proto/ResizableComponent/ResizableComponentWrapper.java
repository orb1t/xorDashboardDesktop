package xorDashboardDesktop.ui.DashboardEditor.Blocks.Proto.ResizableComponent;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 06.10.16.
 */
public class ResizableComponentWrapper extends JComponent implements Serializable {

  protected transient JComponent panelContentBlock;
  private final  transient ResizableComponentWrapper me;
  private final transient ResizableComponentMouseInputAdapter resizeListener;

  protected boolean isSelected = false;
  protected int cursor;
  protected Point startPos = null;

//	protected Image image;
//	protected BufferedImage bufferedImage;


	/*@Override
	public void paintComponent ( Graphics g ) {
		super.paintComponent( g );

		 if ( bufferedImage == null ) {
			return;
		}

//		width = image.getWidth( this );
//		height = image.getHeight( this );
this.panelContentBlock.getGraphics().drawImage( ImageUtils.scaleImage( this.getWidth(), this.getHeight(), bufferedImage ), 0, 0, null );
//		g.drawImage( ImageUtils.scaleImage( this.getWidth(), this.getHeight(), bufferedImage ), 0, 0, null );
	}*/


  @Override
  public String toString() {
    return this.getName();
  }

  public ResizableComponentWrapper() {
    this(new JPanel(null), new ResizableBorder(null, 8));
  }


  public ResizableComponentWrapper(Component comp, ResizableBorder border) {
    setLayout(new BorderLayout());

    me = this;
    panelContentBlock = (JComponent) comp;
    resizeListener = new ResizableComponentMouseInputAdapter(this);

    if (null != comp) {
      add(comp, 0);
      comp.setVisible(true);
    }
    addMouseListener(resizeListener);
    addMouseMotionListener(resizeListener);
    setBorder(border);

    invalidate();
    revalidate();
  }


  protected void resize() {
    if (getParent() != null) {
      ((JComponent) getParent()).revalidate();
    }
  }

}
