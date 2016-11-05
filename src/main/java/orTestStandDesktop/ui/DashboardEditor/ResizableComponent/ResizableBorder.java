package orTestStandDesktop.ui.DashboardEditor.ResizableComponent;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Properties;

/**
 * Created by orb1t_ua on 06.10.16.
 */

public class ResizableBorder extends TitledBorder implements Serializable {

  private int dist = 7;

  int locations[] = {
      SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.WEST,
      SwingConstants.EAST, SwingConstants.NORTH_WEST,
      SwingConstants.NORTH_EAST, SwingConstants.SOUTH_WEST,
      SwingConstants.SOUTH_EAST
  };

  int cursors[] = {
      Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR,
      Cursor.E_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
      Cursor.SW_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
  };

  public ResizableBorder(Properties properties, int dist) {
    super(BorderFactory.createLineBorder(new Color(-9983301)), (null != properties ? properties.get("Title").toString() : "Title"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Bitstream Vera Sans Mono", Font.BOLD, 12), new Color(-4478958));
    this.setTitleJustification(1);
    this.dist = dist;
  }

  @Override
  public Insets getBorderInsets(Component component) {
    return new Insets(dist * 2, dist, dist, dist);
  }

  @Override
  public boolean isBorderOpaque() {
    return false;
  }

  @Override
  public void paintBorder(Component component, Graphics g, int x, int y, int w, int h) {
    super.paintBorder(component, g, x, y, w, h);

    if (component.hasFocus()) {
      for (int i = 0; i < locations.length; i++) {
        Rectangle rect = getRectangle(x, y, w, h, locations[i]);
        g.setColor(Color.WHITE);
        g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
        g.setColor(Color.BLACK);
        g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
      }
    }

  }

  private Rectangle getRectangle(int x, int y, int w, int h, int location) {
    switch (location) {
      case SwingConstants.NORTH:
        return new Rectangle(x + w / 2 - dist / 2, y, dist, dist);

      case SwingConstants.SOUTH:
        return new Rectangle(x + w / 2 - dist / 2, y + h - dist, dist,
            dist);

      case SwingConstants.WEST:
        return new Rectangle(x, y + h / 2 - dist / 2, dist, dist);

      case SwingConstants.EAST:
        return new Rectangle(x + w - dist, y + h / 2 - dist / 2, dist,
            dist);

      case SwingConstants.NORTH_WEST:
        return new Rectangle(x, y, dist, dist);

      case SwingConstants.NORTH_EAST:
        return new Rectangle(x + w - dist, y, dist, dist);

      case SwingConstants.SOUTH_WEST:
        return new Rectangle(x, y + h - dist, dist, dist);

      case SwingConstants.SOUTH_EAST:
        return new Rectangle(x + w - dist, y + h - dist, dist, dist);

    }
    return null;
  }

  public int getCursor(MouseEvent me) {
    Component c = me.getComponent();
    int w = c.getWidth();
    int h = c.getHeight();

    for (int i = 0; i < locations.length; i++) {
      Rectangle rect = getRectangle(0, 0, w, h, locations[i]);
      if (rect.contains(me.getPoint())) {
        return cursors[i];
      }
    }
    return Cursor.MOVE_CURSOR;
  }

}
