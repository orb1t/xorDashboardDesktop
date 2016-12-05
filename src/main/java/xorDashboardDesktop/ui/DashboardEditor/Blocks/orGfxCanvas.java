package xorDashboardDesktop.ui.DashboardEditor.Blocks;

import xorDashboardDesktop.models.pktTableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.Timer;

import java.awt.image.BufferedImage;

enum Features  implements Serializable {
  ANTIALIASING,
  DRAW_DOTS
}

public class orGfxCanvas extends JPanel implements Serializable , TableModelListener {

  private int padding = 12;//35;
  private int labelPadding = 24;//25;

  private Color lineColor = new Color(44, 102, 230, 180);
  private Color pointColor = new Color(100, 100, 100, 180);
  private Color gridColor = new Color(200, 200, 200, 200);

  private static final Stroke GRAPH_STROKE = new BasicStroke(1f);
  private int pointWidth = 4;
  private int numberYDivisions = 10;
  private int numberXDivisions = 10;

  private pktTableModel model;// = new pktTableModel();
  // Multi Values Graph params
  public java.util.List<Integer> DRW_IDXs = new ArrayList<>();  // TableModel desired Columns list
  private Map<Integer, Boolean> updateFlag = new HashMap<Integer, Boolean>();
  private Map<Integer, ArrayList<Object>> graphPoints = new HashMap<>(  ); //HashMap<K, V>();
  // Scale Factors - own couple for each Data Column
  private Map<Integer, Float> xScale = new HashMap<Integer, Float>();
  private Map<Integer, Float> yScale = new HashMap<Integer, Float>();

  private java.util.List<Color> colors = new ArrayList<>();

  public static final int GRAPH_MODE_WINDOW = 0;
  public static final int GRAPH_MODE_APPEND = 1;

  int GRAPH_MODE = GRAPH_MODE_WINDOW;//GRAPH_MODE_APPEND;//
  int GRAPH_MODE_WINDOW_SIZE = 100;
  private int rowCount = 10;
  private int rowStr = 0;


  private int _x0, _y0, _x1, _y1;

  java.util.List<Color> cbColors = new ArrayList<>();
      //new Color[] {Color.RED, Color.GREEN, Color.blue, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK};

  transient BufferedImage meshImg = null;
  public boolean newData = false;

  private Font meshFont;
	private Dimension lastDims;
	private int columnsCount;


	public void drawMesh(Graphics g2) {
    g2.setColor(Color.GRAY);
    g2.fillRect(_x0, _y0,  _x1, _y1);
		System.out.println( "fillRect = [" + _x0 + ", " + _y0 + " : " + _x1 + ", " + _y1 + "]" );

    g2.setColor(Color.DARK_GRAY);
    // create hatch marks and grid lines for y axis.
    for (int i = 0; i < numberYDivisions + 1; i++) {
      int x0 = _x0;
      int x1 = pointWidth + _x0;
      int y0 = getHeight() - ((i * _y1) / numberYDivisions + labelPadding);
      int y1 = y0;
      if (this.model.getRowCount() > 0) {
        g2.setColor(gridColor);

        g2.drawLine(_x0+ 1 + pointWidth, y0, getWidth() - padding, y1);

        int tt, ty;
        tt = 8;
        ty = 0;
        for (int idx : DRW_IDXs) {
          String yLabel = ((int) ((getMinScore(idx) + (getMaxScore(idx) - getMinScore(idx)) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
          this.drawString(yLabel, x0, y0 + ty, cbColors.get(idx), g2 ); //g2);
          ty+=tt;
        }
      }
      g2.drawLine(x0, y0, x1, y1);
	    System.out.println( "line = [" + x0 + ", " + y0 + " : " + x1 + ", " + y1 + "]" );
    }

    // and for x axis
		for (int i = 0; i < numberXDivisions + 1; i++) {
        int x0 = ( i + 0) * _x1 / (rowCount -1) + padding + labelPadding;
        int x1 = x0;
        int y0 = _y1 + padding/2;
        int y1 = y0 - pointWidth;
          g2.setColor(gridColor);
          g2.drawLine(x0, _y1 + padding/2 - 1 - pointWidth, x1, padding);

          g2.setColor(Color.BLACK);
          String xLabel = ( i + this.model.getRowCount()-rowCount ) + "";
          FontMetrics metrics = g2.getFontMetrics();
          int labelWidth = metrics.stringWidth(xLabel);
          g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 1);
        g2.drawLine(x0, y0, x1, y1);
    }

    // create x and y axes
    g2.drawLine(_x0, _y1+ padding/2, _x0, padding/2);
    g2.drawLine(_x0, _y1+ padding/2, getWidth() - padding/2, _y1+ padding/2);
    g2.drawLine(_x0, getHeight()/2 - padding/2+1, getWidth() - padding/2, getHeight()/2-padding/2+1);

  }




  public void addColumntToGraph(int idx){
    if ( ! this.DRW_IDXs.contains(idx) ) {
      synchronized (DRW_IDXs) {
        this.DRW_IDXs.add(idx);

        graphPoints.put(idx, new ArrayList<>());
        updateFlag.put(idx, true);
        xScale.put(idx, ((float) getWidth() - (2 * padding) - labelPadding) / (rowCount - 1));
        yScale.put(idx, (float) (((float) getHeight() - 2 * padding - labelPadding) / (getMaxScore(idx) - getMinScore(idx))));

        if ( getWidth() > 0 && getHeight() > 0)
          meshImg = createMeshImg();
//        this.paint ( (Graphics2D) this.getGraphics() );
	      repaint();
      }
    } else
      System.out.println("!!! idx = [" + idx + "] - already added to current Graph!");
  }

public void removeColumntToGraph(int idx){
    if ( this.DRW_IDXs.contains(idx) ) {
      synchronized (DRW_IDXs) {
        this.DRW_IDXs.remove(DRW_IDXs.indexOf(idx));
        graphPoints.remove(idx);
        updateFlag.remove(idx);
        xScale.remove(idx);
        yScale.remove(idx);

        meshImg = createMeshImg();
//        this.paint ( (Graphics2D) this.getGraphics() );
	      repaint();
      }
    } else
      System.out.println("!!! idx = [" + idx + "] - column #"+idx+" - not added to current Graph!");
  }


  @Override
  public void tableChanged(TableModelEvent e) {
    if ( null == meshImg )
      meshImg = createMeshImg();
    newData = true;
	  repaint();
  }


  private void genRandomColors(){
// assumes hue [0, 360), saturation [0, 100), lightness [0, 100)
    Random rnd = new Random();
    for( int i = 0; i < 360; i += 360 / ( this.model.getHeaders().size()  ) ) {
      Color c = new Color( Color.HSBtoRGB(i, Math.round(new Float(90 + rnd.nextFloat() * 10)), Math.round(new Float(50 + rnd.nextFloat() * 10))) );
      cbColors.add(c);
    }
  }

  public orGfxCanvas(pktTableModel _model, int MODE, int width, int heigth) {
    this.model = _model;
    this.model.addTableModelListener(this);

//    setSize(width,heigth);

    genRandomColors();

    this.GRAPH_MODE = MODE;

    xScale.clear();
    yScale.clear();
    graphPoints.clear();



//    this.wid widthProperty().addListener(observable -> redraw());
//    canvas.heightProperty().addListener(observable -> redraw());
//    this.addComponentListener((ComponentListener) this);

	  lastDims = this.getSize();
	  updateGraphParams();

	  startGraphs();

	  revalidate();
	  repaint();
  }

	public void startGraphs() {
		TimerTask task = new TimerTask() {
			public void run () {
//				for ( int i = 0; i < graphs.size(); i++ )
				if ( DRW_IDXs.size() > 0 && isVisible() && getWidth() > 0 && getHeight() > 0  ) {
					newData = true;
					paint( (Graphics2D) getGraphics() );
				}
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 0, 1000/30);
	}


  private BufferedImage createMeshImg(){
    BufferedImage orBufferedImage = null;
    if ( getWidth() > 0 && getHeight() > 0) {
      GraphicsConfiguration graphicsConfiguration
          = GraphicsEnvironment
          .getLocalGraphicsEnvironment()
          .getDefaultScreenDevice()
          .getDefaultConfiguration();

      //Create a Compatible orBufferedImage
      orBufferedImage
          = (BufferedImage) graphicsConfiguration.createCompatibleImage(
          this.getWidth(),
          this.getHeight());
      //Copy from original Image to new Compatible orBufferedImage
      Graphics g = orBufferedImage.getGraphics();

      drawMesh(g);
      g.dispose();
    }
    return orBufferedImage;
  }

	@Override
  public void paint(Graphics g1) {

		Dimension tDims = this.getSize();
		if ( ! lastDims.equals( tDims )) {
			System.out.println( "DIMS_DIFF = [" + lastDims + " vs " + tDims + "]" );
			lastDims = tDims;
			updateGraphParams();
			newData = true;
		}

    if ( DRW_IDXs.size() > 0 && isVisible() && isShowing() && newData ) try {


	    if ( null == meshFont )
		    meshFont = new Font( this.getGraphics().getFont().getName(), this.getGraphics().getFont().getStyle(), 10 );

//	    BufferStrategy bs = getBufferStrategy();
//	    if ( bs == null ) {
//		    try {
//			    createBufferStrategy( 2 );
//		    } catch ( Exception e ) {
//			    e.printStackTrace();
//		    }
//		    return;
//	    }
	    Graphics grp = this.getGraphics();// bs.getDrawGraphics();

	    //Get current GraphicsConfiguration
	    GraphicsConfiguration graphicsConfiguration
			    = GraphicsEnvironment
					      .getLocalGraphicsEnvironment()
					      .getDefaultScreenDevice()
					      .getDefaultConfiguration();

	    //Create a Compatible orBufferedImage
	    BufferedImage orBufferedImage
			    = (BufferedImage) graphicsConfiguration.createCompatibleImage(
			    this.getWidth(),
			    this.getHeight() );
	    //Copy from original Image to new Compatible orBufferedImage
	    Graphics g = orBufferedImage.getGraphics();
//      if (meshImg != null)
	    if ( null == meshImg )
		    meshImg = createMeshImg();
	    g.drawImage( meshImg, 0, 0, null );

	    this.drawGraph( g );

	    g.dispose();
	    grp.drawImage( orBufferedImage, 0, 0, null );
//	    grp.
//	    g1.dr
//	    g1./ draw Image( grp, 0, 0, null );
//	    grp.
	    g1.drawImage( orBufferedImage, 0, 0, null );
	    grp.dispose();
//	    bs.show();

	    newData = false;
    } catch ( Exception e ) {
	    e.printStackTrace();
    }

  }

	private void updateGraphParams () {
		  _x0 = ( padding ) + labelPadding;
		  _y0 = padding / 2;
		  _x1 = getWidth() - ( padding / 2 ) - padding - labelPadding;
		  _y1 = getHeight() - padding / 2 - labelPadding;

		  for ( int i : this.DRW_IDXs ) {
			  xScale.replace( i, ( (float) getWidth() - ( 2 * padding ) - labelPadding ) / ( rowCount - 1 ) );
			  yScale.replace( i, (float) ( ( (float) getHeight() - 2 * padding - labelPadding ) / ( getMaxScore( i ) - getMinScore( i ) ) ) );
		  }

		meshImg = null;// createMeshImg();
	}


	private void drawGraph(Graphics g2){
    Stroke oldStroke = ((Graphics2D)g2).getStroke();

    int to = this.model.getRowCount();//(  ( this.model.getRowCount() >= (this.getWidth() - padding/2-padding - labelPadding) ? this.model.getRowCount() - (this.getWidth() - padding/2-padding - labelPadding) : this.getWidth() - this.model.getRowCount() ));// - 1);
    int from = 0;//this.model.getRowCount();
    if ( this.model.getRowCount() >= (this.getWidth() - padding/2-padding - labelPadding) )
      from = this.model.getRowCount() - (this.getWidth() - padding/2-padding - labelPadding);
//    System.out.println("from --> to [" + from + ", " + to + "]");
    synchronized (DRW_IDXs){
    for (int i = from; i < to-1; i++) {
//      if ((( i - from ) % ((int) (((to-from -1) / 20.0)) + 1)) == 0) {
//        int x0 = ( i - from) * _x1 / (to-from -1) + padding + labelPadding;
//        int x1 = x0;
//        int y0 = _y1 + padding/2;
//        int y1 = y0 - pointWidth;
//          g2.setColor(gridColor);
//          g2.drawLine(x0, _y1 + padding/2 - 1 - pointWidth, x1, padding);
//
//        g2.setColor(Color.BLACK);
//        String xLabel = i + "";// (i + this.model.getRowCount() - rowCount) + "";
//        FontMetrics metrics = g2.getFontMetrics();
//        int labelWidth = metrics.stringWidth(xLabel);
//        g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 1);
//      }
      for (int idx : DRW_IDXs) {
        g2.setColor(this.cbColors.get( DRW_IDXs.indexOf(  idx )));//lineColor);
        ((Graphics2D) g2).setStroke(GRAPH_STROKE);
        int x1 = i - from + padding + labelPadding;// (int) (  (i+0)  * xScale.get(idx) + padding + labelPadding);
        int y1 = /*getHeight() / 2 +*/ (int) ((getMaxScore(idx) - Double.valueOf(this.model.getValueAt((i), idx).toString())) * yScale.get(idx) + padding);
        int x2 = i - from + 1 + padding + labelPadding;//(int) (  (i+1)  * xScale.get(idx) + padding + labelPadding);
        int y2 = /*getHeight() / 2 +*/ (int) ((getMaxScore(idx) - Double.valueOf(this.model.getValueAt((i + 1), idx).toString())) * yScale.get(idx) + padding);
        g2.drawLine(x1, y1, x2, y2);
      }
    }
    }
  }


  public void drawAll(Graphics g2){
    if ( this.DRW_IDXs.size() > 0 && this.isVisible() ) {
      this.drawGraph(g2);
    }
  }


  private double getMinScore(int idx) {
    return (double) model.getMinValueOf(idx);
  }

  private double getMaxScore(int idx) {
    return (double) model.getMaxValueOf(idx);
  }



//	@Override
//  public void componentResized(ComponentEvent e) {
//    System.out.println("reshape= [" + e.getComponent().getWidth() + ", " + e.getComponent().getHeight() + "] [ "+this.getWidth() + ", " + this.getHeight() + " ]");
//
////	  if ( getWidth() <= Toolkit.getDefaultToolkit().getScreenSize().getWidth() &&
////	       getHeight() <= Toolkit.getDefaultToolkit().getScreenSize().getWidth() ) {
//
//		  _x0 = ( padding ) + labelPadding;
//		  _y0 = padding / 2;
//		  _x1 = getWidth() - ( padding / 2 ) - padding - labelPadding;
//		  _y1 = getHeight() - padding / 2 - labelPadding;
//
//		  meshImg = createMeshImg();
//
//		  for ( int i : this.DRW_IDXs ) {
//			  xScale.replace( i, ( (float) getWidth() - ( 2 * padding ) - labelPadding ) / ( rowCount - 1 ) );
//			  yScale.replace( i, (float) ( ( (float) getHeight() - 2 * padding - labelPadding ) / ( getMaxScore( i ) - getMinScore( i ) ) ) );
//		  }
////		  this.paint( (Graphics2D) this.getGraphics() );
//		  repaint();
////	  }
//  }
//
//  @Override
//  public void componentMoved(ComponentEvent componentEvent) {
//
//  }
//
//  @Override
//  public void componentShown(ComponentEvent componentEvent) {
//
//  }
//
//  @Override
//  public void componentHidden(ComponentEvent componentEvent) {
//
//  }


  private void  drawString ( String str, int x0, int y0, Color color, Graphics g2){
    g2.setColor(color);//Color.BLACK);
    String yLabel = str;//((int) ((getMinScore(DRW_IDXs.get(0)) + (getMaxScore(DRW_IDXs.get(0)) - getMinScore(DRW_IDXs.get(0))) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
    g2.setFont(meshFont);
    FontMetrics metrics = g2.getFontMetrics();
    int labelWidth = metrics.stringWidth(yLabel);
    g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 1);
  }

	public int getColumnsCount () {
		return columnsCount;
	}
}
