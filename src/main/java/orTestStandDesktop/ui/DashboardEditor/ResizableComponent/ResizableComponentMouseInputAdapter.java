package orTestStandDesktop.ui.DashboardEditor.ResizableComponent;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * Created by orb1t_ua on 11.10.16.
 */
public class ResizableComponentMouseInputAdapter implements Serializable, MouseInputListener {

  protected final ResizableComponentWrapper component;


  public ResizableComponentMouseInputAdapter(ResizableComponentWrapper component) {
    this.component = component;
  }

  @Override
  public void mouseClicked(MouseEvent mouseEvent) {

  }

  @Override
  public void mousePressed(MouseEvent mouseEvent) {
    if ( this.component.getBorder() instanceof ResizableBorder ){
      this.component.requestFocus();

      ResizableBorder border = (ResizableBorder) this.component.getBorder();
      this.component.cursor = border.getCursor(mouseEvent);
      this.component.startPos = mouseEvent.getPoint();
      this.component.repaint();
    }
  }

  @Override
  public void mouseReleased(MouseEvent mouseEvent) {
    if (this.component.isSelected && this.component.startPos != null) {
      this.component.startPos = null;
      this.component.transferFocus();
      this.component.resize();
    }
  }

  @Override
  public void mouseEntered(MouseEvent mouseEvent) {

  }

  @Override
  public void mouseExited(MouseEvent mouseEvent) {
    this.component.setCursor(Cursor.getDefaultCursor());
    if (this.component.isSelected)
      if (this.component.startPos != null) {
        this.component.transferFocus();
        this.component.resize();
      }
  }

  @Override
  public void mouseDragged(MouseEvent mouseEvent) {
    if (this.component.startPos != null) {

      int x = this.component.getX();
      int y = this.component.getY();
      int w = this.component.getWidth();
      int h = this.component.getHeight();

      int dx = mouseEvent.getX() - this.component.startPos.x;
      int dy = mouseEvent.getY() - this.component.startPos.y;

      switch (this.component.cursor) {
        case Cursor.N_RESIZE_CURSOR:
          if (!(h - dy < 50)) {
            this.component.setBounds(x, y + dy, w, h - dy);
            this.component.resize();
          }
          break;

        case Cursor.S_RESIZE_CURSOR:
          if (!(h + dy < 50)) {
            this.component.setBounds(x, y, w, h + dy);
            this.component.startPos = mouseEvent.getPoint();
            this.component.resize();
          }
          break;

        case Cursor.W_RESIZE_CURSOR:
          if (!(w - dx < 50)) {
            this.component.setBounds(x + dx, y, w - dx, h);
            this.component.resize();
          }
          break;

        case Cursor.E_RESIZE_CURSOR:
          if (!(w + dx < 50)) {
            this.component.setBounds(x, y, w + dx, h);
            this.component.startPos = mouseEvent.getPoint();
            this.component.resize();
          }
          break;

        case Cursor.NW_RESIZE_CURSOR:
          if (!(w - dx < 50) && !(h - dy < 50)) {
            this.component.setBounds(x + dx, y + dy, w - dx, h - dy);
            this.component.resize();
          }
          break;

        case Cursor.NE_RESIZE_CURSOR:
          if (!(w + dx < 50) && !(h - dy < 50)) {
            this.component.setBounds(x, y + dy, w + dx, h - dy);
            this.component.startPos = new Point(mouseEvent.getX(), this.component.startPos.y);
            this.component.resize();
          }
          break;

        case Cursor.SW_RESIZE_CURSOR:
          if (!(w - dx < 50) && !(h + dy < 50)) {
            this.component.setBounds(x + dx, y, w - dx, h + dy);
            this.component.startPos = new Point(this.component.startPos.x, mouseEvent.getY());
            this.component.resize();
          }
          break;

        case Cursor.SE_RESIZE_CURSOR:
          if (!(w + dx < 50) && !(h + dy < 50)) {
            this.component.setBounds(x, y, w + dx, h + dy);
            this.component.startPos = mouseEvent.getPoint();
            this.component.resize();
          }
          break;

        case Cursor.MOVE_CURSOR:
          Rectangle bounds = this.component.getBounds();
          bounds.translate(dx, dy);
          this.component.setBounds(bounds);
          this.component.resize();
      }

      this.component.setCursor(Cursor.getPredefinedCursor(this.component.cursor));
    }
  }

  @Override
  public void mouseMoved(MouseEvent mouseEvent) {
    if (this.component.hasFocus() && this.component.getBorder() instanceof ResizableBorder ) {

      ResizableBorder border = (ResizableBorder) this.component.getBorder();
      this.component.setCursor(Cursor.getPredefinedCursor(border.getCursor(mouseEvent)));
    }
  }
}
