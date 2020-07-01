package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.CommonService;
import service.CommonServiceImpl;
import service.DatabaseService;
import service.DatabaseServiceImpl;
import service.PayService;
import service.PayServiceImpl;
import service.SingletonData;

public class PayController extends Controller implements Initializable {
	Parent root;
	boolean flag;
	CommonServiceImpl comSrv;
	DatabaseService dataSrv;
	PayServiceImpl paySrv;
	MenuController menuCon;
	
	
	@Override
	public void setRoot(Parent root) {
		// TODO Auto-generated method stub
		this.root = root;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		flag=true;
		comSrv = new CommonServiceImpl();
		dataSrv = new DatabaseServiceImpl();
	}
	
	
	public void onPay() {
		if(flag==false) {
			return;
		}
		CheckBox cb2 = (CheckBox)root.lookup("#ch2");
		cb2.setSelected(true);
		SingletonData sd = SingletonData.getInstance();
		Label total = (Label) root.lookup("#totalPrice");
		total.setText(sd.getTotalPrice());
		Label address = (Label)root.lookup("#address"); // address setText 필요
		address.setText(dataSrv.SelectAddress(sd.getCurrentUserId()));
		
		flag=false;
	}
	
	public void onPayProc(ActionEvent event) { //결제 버튼
		PayService paySrv = new PayServiceImpl();
		ChoiceBox<String> payselect = (ChoiceBox<String>)root.lookup("#payselect");
		if(payselect == null) {
			comSrv.ErrorMsg("결제 방식을 선택해 주세요");
			return;
		} else if (payselect.getValue() == null) {
			comSrv.ErrorMsg("결제 방식을 선택해 주세요");
			return;
		}else if (payselect.getValue() == "현금") {
			comSrv.ErrorMsg("현금으로 결제 하였습니다");
			comSrv.WindowClose(event);
			return;
		}else if (payselect.getValue() == "신용카드") {
			comSrv.ErrorMsg("신용카드로 결제 하였습니다");
			comSrv.WindowClose(event);
			return;
		}
		return;
		
	}
	
	public void onCancel(ActionEvent event) { //취소 버튼
		comSrv.WindowClose(event);
	}
	
	public void ch1On() { // 체크 박스 중첩 선택 방지
		CheckBox cb2 = (CheckBox)root.lookup("#ch2");
		cb2.setSelected(false);
	}
	
	public void ch2On() {
		CheckBox cb1 = (CheckBox)root.lookup("#ch1");
		cb1.setSelected(false);
	}
	
}