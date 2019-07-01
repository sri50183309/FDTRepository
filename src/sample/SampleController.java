package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class SampleController {
    public TextField serverIpAddress;
    public Label lblserverIpAddress;

    public TextField sourceFileLocation;
    public Label lblSourceFile;

    public Label lblDestinationFile;
    public TextField destinationDirectory;

    public Label fileTransferInitiated;
    public TextArea fileTransferCompleted;

    public Label lblJarFileLocation;
    public TextField jarFileLocation;
    public Label lblParameter;
    public TextField passThroughParameter;


    String FDT_JAR_LOCATION = "FDT Jar file location";
    String DESTINATION_FOLDER = "Destination folder";
    String SERVER_IP_ADDRESS = "Remote Server IP Address";
    String SOURCE_FILE = "Files to be transferred";

    @FXML
    protected void locateFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());
    }

    public void sayHelloWorld(ActionEvent actionEvent) {


        StringBuffer stringBuffer = new StringBuffer();

        lblJarFileLocation.setText(FDT_JAR_LOCATION);
        String jarFile = jarFileLocation.getText();


        lblserverIpAddress.setText(SERVER_IP_ADDRESS);
        String serverIpAddressText = serverIpAddress.getText();

        lblSourceFile.setText(SOURCE_FILE);
        String sourceFileLocationText = sourceFileLocation.getText();

        lblDestinationFile.setText(DESTINATION_FOLDER);
        String destinationDirectoryText = destinationDirectory.getText();

        String parameterP = passThroughParameter.getText();

        String commandString = "java -jar " + jarFile + " -c " + serverIpAddressText + " -P " + parameterP + " \"" + sourceFileLocationText + "\" -d " + destinationDirectoryText;

        //fileTransferInitiated.setText("File transfer Initiated !! Please wait........................" + commandString);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String s = null;

                try {
                    // code goes here.
                    // run the Unix "ps -ef" command
                    // using the Runtime exec method:
                    Process p = Runtime.getRuntime().exec(commandString);


                    BufferedReader stdInput = new BufferedReader(new
                            InputStreamReader(p.getInputStream()));

                    BufferedReader stdError = new BufferedReader(new
                            InputStreamReader(p.getErrorStream()));

                    // read the output from the command
                    stringBuffer.append(" Command String : \n");
                    stringBuffer.append(commandString);
                    stringBuffer.append(" \n \n");

                    stringBuffer.append("Here is the standard output of the command:\n");
                    while ((s = stdInput.readLine()) != null) {
                        stringBuffer.append(s);
                        stringBuffer.append("\n");
                    }

                    // read any errors from the attempted command
                    //stringBuffer.append("Here is the standard error of the command (if any):\n");
                    while ((s = stdError.readLine()) != null) {
                        stringBuffer.append(s);
                        stringBuffer.append("\n");
                    }

                    fileTransferCompleted.setText(stringBuffer.toString());
                } catch (Exception e) {
                    fileTransferCompleted.setText("exception happened - here's what I know: ");
                    e.printStackTrace();

                }

            }
        });


        //fileTransferInitiated.setText("File transfer done and status updated below");

    }

    public void sayUploadFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectFile = fileChooser.showOpenDialog(((Node) actionEvent.getTarget()).getScene().getWindow());

        if (selectFile != null) {
            String path = selectFile.getPath();

            sourceFileLocation.setText(path);
        }
    }
}
