package application;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class Controller implements Initializable {
	@FXML private TextField txfHeight;
	@FXML private TextField txfWeight;
	@FXML private TextField txfBMI;
	@FXML private MenuItem mniAbout;
	@FXML private MenuItem mniBMI;
	
	public void initialize(URL location, ResourceBundle resources) {}
	
	/**
	 * This method use the class Alert to show dialogs.
	 * @param type Decide the type of icon that will be shown
	 * @param title The title of the dialog
	 * @param head The head title of the dialog
	 * @param content The content of the dialog
	 */
	public void showMessage(AlertType type, String title, String head, String content) {
		Alert alert=new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(head);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	/**
	 * This method is to calculate BMI
	 * @param event
	 */
	public void showBMI(ActionEvent event) {
		final String[] status = {
				"偏瘦",
				"正常",
				"过重",
				"肥胖",
				"重度肥胖",
				"极重度肥胖"
		};
		final AlertType[] alertType = {
				AlertType.WARNING, 
				AlertType.INFORMATION, 
				AlertType.WARNING, 
				AlertType.WARNING, 
				AlertType.ERROR, 
				AlertType.ERROR
		};
		final String[] risk = {
				"低（但其它疾病危险性增加）", 
				"平均水平", 
				"增加", 
				"中度增加", 
				"严重增加", 
				"非常严重增加"
		};
		final String isNumberPattern = "^(\\-|\\+)?\\d+(\\.\\d+)?$";
		
		try {
			String _height=txfHeight.getText();
			String _weight=txfWeight.getText();
			if(_height.trim().isEmpty()||_weight.trim().isEmpty()) {
				txfBMI.setText("");
				showMessage(AlertType.WARNING, "提示", null, "身高或体重不能为空！");
				return ;
			}
			
			if(!_height.matches(isNumberPattern)||!_weight.matches(isNumberPattern)) {
				txfBMI.setText("");
				showMessage(AlertType.ERROR, "提示", null, "身高或体重不合法！");
				return ;
			}
			
			double height=Double.parseDouble(_height);
			double weight=Double.parseDouble(_weight);
			if(height<0||weight<0||height>300||weight>750) {
				txfBMI.setText("");
				showMessage(AlertType.ERROR, "提示", null, "身高或体重不合法！");
				return ;
			}
			
			double BMI=weight/(height*height)*10000;
			int statusID;
			if(BMI<18.5) {
				statusID=0;
			} else if(BMI>=18.5&&BMI<24) {
				statusID=1;
			} else if(BMI>=24&&BMI<27) {
				statusID=2;
			} else if(BMI>=27&&BMI<30) {
				statusID=3;
			} else if(BMI>=30&&BMI<40) {
				statusID=4;
			} else {
				statusID=5;
			}
			
			txfBMI.setText(new DecimalFormat("0.0").format(BMI)+" "+status[statusID]);
			showMessage(alertType[statusID], "结果", null, "结果："+new DecimalFormat("0.0").format(BMI)+"  "+status[statusID]+"\n相关疾病发病危险性："+risk[statusID]);
		} catch(NumberFormatException e) {
			txfBMI.setText("");
			showMessage(AlertType.ERROR, "提示", null, "输入异常！");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is to show something about BMI
	 * @param event
	 */
	public void AboutBMI(ActionEvent event) {
		showMessage(AlertType.INFORMATION, 
				"关于BMI", 
				"       身体质量指数（英文：Body Mass Index，简称BMI）是用体重公斤数除以身高米数平方得出的数字，\n"
				+ "是目前国际上常用的衡量人体胖瘦程度以及是否健康的一个标准。主要用于统计用途，当我们需要比较及\n"
				+ "分析一个人的体重对于不同高度的人所带来的健康影响时，BMI值是一个中立而可靠的指标。", 
				  "\tBMI标准：\n"
				+ "\t中国标准            BMI范围                相关疾病发病的危险性\n"
				+ "\t偏瘦                    <18.5                  低（但其它疾病危险性增加）\n"
				+ "\t正常                  18.5~24                平均水平\n"
				+ "\t过重                   24~27                  增加\n"
				+ "\t肥胖                   27~30                  中度增加 \n"
				+ "\t重度肥胖             30~40                 严重增加\n"
				+ "\t极重度肥胖           >40                    非常严重增加");
	}
	
	/**
	 * This method tells some information of this program.
	 * @param event
	 */
	public void AboutAuthor(ActionEvent event) {
		showMessage(AlertType.INFORMATION, "关于", "BMI计算器\nVersion 1.2.4\n", "Made by Human_Being_\nMay 12, 2018");
	}
}
