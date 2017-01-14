/* ----------------------------------------------------------
 * 文件名称：FXMLDocumentController.java
 * 
 * 作者：秦建辉
 * 
 * 微信：splashcn
 * 
 * 博客：http://www.firstsolver.com/wordpress/
 * 
 * 开发环境：
 *      NetBeans 8.1
 *      JDK 8u92
 *      
 * 版本历史：
 *      V1.1    2016年07月17日
 *              因SDK改进更新代码
 *
 *      V1.0    2014年09月09日
 *              执行设备命令
------------------------------------------------------------ */
package Splash;

import Com.FirstSolver.Splash.FaceId;
import Com.FirstSolver.Splash.FaceIdAnswer;
import Com.FirstSolver.Splash.FaceId_ErrorCode;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author 秦建辉
 */
public class FXMLDocumentController implements Initializable {

	private final String DeviceCharset = "GBK";

	@FXML
	private TextField textFieldDeviceIP;

	@FXML
	private TextField textFieldDevicePort;

	@FXML
	private TextField textFieldSecretKey;

	@FXML
	private TextField textFieldDeviceCommand;

	@FXML
	private TextArea textAreaAnswer;

	@FXML
	private void handleButtonExecuteDeviceCommand(ActionEvent event)
			throws Exception {
		// 获取当前应用窗体
		// Stage stage =
		// (Stage)((Button)event.getSource()).getScene().getWindow();

		// 清空命令结果文本显示框
		textAreaAnswer.clear();
		try (FaceId tcpClient = new FaceId(textFieldDeviceIP.getText(),
				Integer.parseInt(textFieldDevicePort.getText()))) {
			// 设置通信密码
			tcpClient.setSecretKey(textFieldSecretKey.getText()); // 注意：密码和设备通信密码一致

			FaceIdAnswer output = new FaceIdAnswer();
			FaceId_ErrorCode ErrorCode = tcpClient.Execute(
					textFieldDeviceCommand.getText(), output, DeviceCharset);
			if (ErrorCode.equals(FaceId_ErrorCode.Success)) {
				textAreaAnswer.appendText(output.answer);
			} else {
				Alert alert = new Alert(AlertType.ERROR, "执行设备命令失败！",
						ButtonType.OK);
				alert.setTitle("TcpClientDemo");
				alert.setHeaderText("错误");
				alert.showAndWait();
			}
		} catch (RuntimeException | IOException e) {
			Alert alert = new Alert(AlertType.ERROR, "连接设备失败！", ButtonType.OK);
			alert.setTitle("TcpClientDemo");
			alert.setHeaderText("错误");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleButtonClearAction(ActionEvent event) {
		textAreaAnswer.clear();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}
}
