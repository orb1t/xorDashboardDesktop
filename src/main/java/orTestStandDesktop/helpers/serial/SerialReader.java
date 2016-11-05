package orTestStandDesktop.helpers.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import orTestStandDesktop.helpers.TestStandStrPktParser;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.io.*;
import java.util.Enumeration;

/**
 * Created by orb1t_ua on 02.07.16.
 */
public class SerialReader implements Serializable, SerialPortEventListener {
    private SerialPort serialPort;

    private BufferedReader input;
    private OutputStream output;
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 115200;//57600;//115200;


  public boolean connected = false;

  private JTextArea _logg;


    private final int PAYLOAD_PKT_FORMAT = 0; // 0 - for TeapotPacket format, 1 - for orPKT format
    private int PAYLOAD_PKT_SIZE = 256;
    private int PAYLOAD_PKT_ACCEL_OFFSET = 20;
    private final int PAYLOAD_PKT_START_MARK = '$';
    private final int PAYLOAD_PKT_END_MARK0 = '\r';
    private final int PAYLOAD_PKT_END_MARK1 = '\n';
    public char[] payloadPacket;// = new char[PAYLOAD_PKT_SIZE];  // InvenSense Teapot packet
    int serialCount = 0;                 // current packet byte position
    int synced = 0;
    private int pktNum;


    /**
     * Handle an event on the serial port. Read the data and print it.
     */

    //public Quaternion getQuat(){return quat;}

    public void  serialWrite ( String str ){
      if ( this.connected == true && this.output != null )
        try {
          for (char ch : str.toCharArray()) {
            output.write(ch);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
              int len = 0;
              int data;
              while ( ( data = input.read()) > -1 )
              {
                if ( data == '\n' ) {
                  break;
                }
                payloadPacket[len++] = (char) data;
              }
              for ( int i = len; i < PAYLOAD_PKT_SIZE; i++ )
                payloadPacket [ i ] = ' ';
//              _logg.append(String.valueOf(payloadPacket) + "\n");
              TestStandStrPktParser.parseStrPkt ( String.valueOf(payloadPacket), this._logg);
            } catch (Exception e) {
//                System.err.println(e.toString());
              e.printStackTrace();
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

    public SerialReader (String PORT_NAME, int PORT_SPEED, JTextArea logg){
        payloadPacket = new char[PAYLOAD_PKT_SIZE];
      this._logg = logg;

      DefaultCaret caret = (DefaultCaret)this._logg.getCaret();
      caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();



        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            //for (String portName : PORT_NAME) {
                if (currPortId.getName().equals(PORT_NAME)) {
                    portId = currPortId;
                    break;
                }
            //}
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream(), "ISO-8859-1"));
            output = serialPort.getOutputStream();

            output.write('Q');

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);

            System.out.println("PORT_NAME = [" + PORT_NAME + "], PORT_SPEED = [" + PORT_SPEED + "] ---> SUCCESS !!!");
          connected = true;
        } catch (Exception e) {
            //System.err.println(e.toString());
          e.printStackTrace();
        }
    }


    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }
}
