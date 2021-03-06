package com.richardhughes.superfarm;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.richardhughes.jx.game.framework.GameBase;
import com.richardhughes.jx.util.FileHelper;
import com.richardhughes.jx.util.XMLHelper;

public class HUD {

	private ArrayList<HUDControl> _controls = new ArrayList<HUDControl>();

	private IHUDActionListener _listener = null;

	public boolean Load(String fileName, SuperFarmGame game) {

		FileHelper rh = new FileHelper();
		String fileData = rh.GetAssetData(fileName, game.CurrentApplicationContext);

		XMLHelper xhelp = new XMLHelper();
		xhelp.SetXMLData(fileData);

		NodeList controls = xhelp.GetValues("/hud/control/text()");

		for(int i = 0; i < controls.getLength(); i++) {

			Node n = controls.item(i);
			String controlFileName = n.getNodeValue();

			this.LoadControl(controlFileName, game);
		}

		return true;
	}

	public boolean LoadControl(String fileName, SuperFarmGame game) {

		HUDControl c = new HUDControl(this);

		c.Load(fileName, game);
		this._controls.add(c);
		
		return true;
	}

	public void Unload(SuperFarmGame game) {

		for(HUDControl c : this._controls) {

			c.Unload(game);
		}

		this._controls.clear();
	}
	
	public void Render(SuperFarmGame game) {

		for(HUDControl c : this._controls) {

			c.Render(game);
		}
	}
	
	public void Update(SuperFarmGame game) {

		for(HUDControl c : this._controls) {

			c.Update(game);
		}
	}

	public void OnTouchDown(int x, int y) {

		for(HUDControl c : this._controls) {

			c.OnTouchDown(x, y);
		}
	}

	public void OnTouchUp(int x, int y) {

		for(HUDControl c : this._controls) {

			c.OnTouchUp(x, y);
		}
	}

	public void SetActionListener(IHUDActionListener listener) {

		this._listener = listener;
	}
	
	public void OnImageAction(String action, String imageId) {

		if(this._listener == null)
			return;
		
		action = action.toLowerCase().trim();

		HUDActionListenerEventArgs e = new HUDActionListenerEventArgs();
		e.SetImageId(imageId);

		if(action.equals("dpad_center")) {

			this._listener.actionDPAD_CENTER(e);
		}
		else if(action.equals("dpad_center_up")) {

			this._listener.actionDPAD_CENTER_UP(e);
		}
		else if(action.equals("dpad_up")) {

			this._listener.actionDPAD_UP(e);
		}
		else if(action.equals("dpad_down")) {

			this._listener.actionDPAD_DOWN(e);
		}
		else if(action.equals("dpad_left")) {

			this._listener.actionDPAD_LEFT(e);
		}
		else if(action.equals("dpad_right")) {

			this._listener.actionDPAD_RIGHT(e);
		}
		else if(action.equals("button_menu")) {

			this._listener.actionBUTTON_MENU(e);
		}
		else if(action.equals("button_exit")) {

			this._listener.actionBUTTON_EXIT(e);
		}
		else if(action.equals("button_plant")) {

			this._listener.actionBUTTON_PLANT(e);
		}
		else if(action.equals("button_finish")) {

			this._listener.actionBUTTON_FINISH(e);
		}
		else if(action.equals("button_returnfrommenu")) {

			this._listener.actionBUTTON_RETURNFROMMENU(e);
		}
		else if(action.equals("button_returnfromplantmenu")) {

			this._listener.actionBUTTON_RETURNFROMPLANTMENU(e);
		}
		else if(action.equals("plant_selected")) {

			this._listener.actionPLANT_SELECTED(e);
		}
		else if(action.equals("button_save")) {

			this._listener.actionBUTTON_SAVE(e);
		}
	}

	public void ShowControl(String id) {

		id = id.trim().toLowerCase();

		for(HUDControl c : this._controls) {

			if(id.equals(c.GetId().trim().toLowerCase())) {

				c.SetIsVisible(true);
			}
		}
	}

	public void HideControl(String id) {

		id = id.trim().toLowerCase();

		for(HUDControl c : this._controls) {

			if(id.equals(c.GetId().trim().toLowerCase())) {

				c.SetIsVisible(false);
			}
		}
	}

	public void AddImageToControl(String id, Image i) {

		for(HUDControl c : this._controls) {

			if(id.equals(c.GetId().trim().toLowerCase())) {

				c.AddImage(i);
			}
		}
	}
}
