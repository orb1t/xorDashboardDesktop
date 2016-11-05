package orTestStandDesktop.ui.DashboardEditor.ControlPropertiesDialog;

import orTestStandDesktop.ui.DashboardEditor.Blocks.Proto.AbstractBlock;
import orTestStandDesktop.ui.DashboardEditor.ControlProperties.ControlProperties;

/**
 * Created by orb1t_ua on 02.10.16.
 */
public interface IAddBlockDialogResults {
//  void GetResultsConfig ( ControlProperties resultProperties );
  void GetResultsConfig( AbstractBlock resultComponent, ControlProperties resultComponentProperties, boolean result );
}
