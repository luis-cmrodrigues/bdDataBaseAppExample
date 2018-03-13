/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package class4dbapp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author luis_
 */
public class FXMLDocumentController implements Initializable {

    Connection con;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPlace;

    @FXML
    private Button btnView;
    @FXML
    private Button btnQuit;

    @FXML
    private void viewAllRecords(ActionEvent event) throws IOException, SQLException {
        con.close();
        Stage stage1 = (Stage) btnQuit.getScene().getWindow();
        stage1.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShowAllScene.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Adicionar Departamento");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void closeWindow(ActionEvent actionEvent) throws SQLException {
        con.close();
        System.exit(0);

    }

    @FXML
    private void saveData(ActionEvent actionEvent) throws SQLException, IOException {
        String nome = txtName.getText();
        String local = txtPlace.getText();
        int numDeps = getNumberOfDepartments() + 1;

        Statement stmt = null;
        String query = "INSERT INTO Departamento(DepNum, Nome, Local)" + "Values (" + numDeps
                + ", '" + nome + "', '" + local + "');";

        try {
            stmt = con.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            Logger.getLogger(ShowAllSceneController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stmt != null) {
                stmt.close();
                viewAllRecords(actionEvent);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost\\LUIS\\SQLEXPRESS:1433;databaseName=DBProject;user=sa;password=admin1234";
            con = DriverManager.getConnection(connectionUrl);

            System.out.println("Connected to database !");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
        }
    }

    private int getNumberOfDepartments() throws SQLException {
        Statement stmt = null;
        String query = "SELECT Count(*) FROM Departamento;";

        try {

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            //System.out.println("number:" + Integer.toString(rs.getInt(1)));

            return rs.getInt(1);
        } catch (SQLException e) {
            Logger.getLogger(ShowAllSceneController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return -1;
    }

    
}
