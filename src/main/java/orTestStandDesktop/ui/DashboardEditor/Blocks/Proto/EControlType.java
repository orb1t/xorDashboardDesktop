package orTestStandDesktop.ui.DashboardEditor.Blocks.Proto;

import java.io.Serializable;

/**
 * Created by orb1t_ua on 08.10.16.
 */
public enum EControlType implements Serializable{
  INDICATOR,  // listens for changes in configured fields of 'pktTableModel', and indicates accordingly
  CONTROLLER, // sends preconfigured commands to Controlled Device, based on Control's state/value
	UI,         // User interaction control
	TIME        // Schedulled for repeat execution (
}
