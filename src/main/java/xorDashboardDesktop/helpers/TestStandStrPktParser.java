package xorDashboardDesktop.helpers;

//import orImuClient.imu.accelCalibration;
import javax.swing.*;
import java.io.Serializable;

import static xorDashboardDesktop.ui.MainForm.tableModel;

//import orImuClient.ui.forms.formMain;
//import toxi.geom.Quaternion;

/**
 * Created by orb1t_ua on 07.09.16.
 */
public class TestStandStrPktParser implements Serializable {
  private static final int SERIAL_VALS_CNT = 9;
  static short[] value = new short[SERIAL_VALS_CNT];
  static float[] value_raw = new float[SERIAL_VALS_CNT];


  private static int pktsCnt = 0;
  private static long deltaT = 0;

  static float flowMetterFlowRate          = 0.0f;
  static float flowMetterFlowMilliLitres   = 0.0f;
  static float flowMetterTotalMilliLitres  = 0.0f;

  static float analogPotVal = 0.0f;
  static float servoPosVal = 0.0f;

  static int relSts0 = 0;
  static int relSts1 = 0;
  private static JTextArea _logg;

//  static pktTableModel model;


  public  static void parseStrPkt ( String strPkt, JTextArea logg ) {
    _logg = logg;
    String[] msg = strPkt.split(",");
    if ( ( msg != null ) && ( ( msg.length == SERIAL_VALS_CNT ))){
	    tableModel.prepareNewRow();
      for ( int i=0; i< msg.length; i++ ) {
        value[i] =  Float.valueOf(msg[i]).shortValue();
        value_raw [i] = Float.valueOf(msg[i]);
      }

      pktsCnt = value [ 0 ];
	    tableModel.newRowTmp.add ( pktsCnt );
	    tableModel.setMaxValueOf(0, pktsCnt);

      deltaT = value [ 1 ];
	    tableModel.newRowTmp.add ( deltaT );
//      model.setMaxValueOf(0, pktsCnt);

      flowMetterFlowRate = value_raw [ 2 ];
	    tableModel.newRowTmp.add ( flowMetterFlowRate );
      flowMetterFlowMilliLitres   = value_raw [ 3 ];
	    tableModel.newRowTmp.add ( flowMetterFlowMilliLitres );
      flowMetterTotalMilliLitres  = value_raw [ 4 ];
	    tableModel.newRowTmp.add ( flowMetterTotalMilliLitres );

      analogPotVal = value_raw [ 5 ];
	    tableModel.newRowTmp.add ( analogPotVal );
      servoPosVal = value_raw [ 6 ];
	    tableModel.newRowTmp.add ( servoPosVal );

      relSts0 = value [ 7 ];
	    tableModel.newRowTmp.add ( relSts0 );
      relSts1 = value [ 8 ];
	    tableModel.newRowTmp.add ( relSts1 );

      _logg.append("[" + pktsCnt + ":" + deltaT + "] rate: "+flowMetterFlowRate+" l/m, flow: "+flowMetterFlowMilliLitres+" ml/s, ttl: "+flowMetterTotalMilliLitres+" ml | "
                        +"pot: " + analogPotVal + ", srv: " + servoPosVal + " | "
                        +"rl0: "+relSts0+", rl1: "+relSts1 + "\n");

	    tableModel.addRow(tableModel.newRowTmp);
    }
  }


}

