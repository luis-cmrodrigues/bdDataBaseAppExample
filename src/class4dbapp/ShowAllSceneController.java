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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author luis_
 */
public class ShowAllSceneController implements Initializable {

    Connection con;

    ArrayList<Integer> numDep = new ArrayList<>();
    ArrayList<String> nomeDep = new ArrayList<>();
    ArrayList<String> localDep = new ArrayList<>();

    ObservableList<Departamento> deps;

    @FXML
    private TableView<Departamento> depTable = new TableView<>();

    @FXML
    private TableColumn numCol = new TableColumn<Departamento, String>("num");
    @FXML
    private TableColumn nomeCol = new TableColumn<Departamento, Integer>("nome");
    @FXML
    private TableColumn localCol = new TableColumn<Departamento, String>("local");
    @FXML
    private Button btnBack;
    @FXML
    private Button btnAddNew;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://localhost\\LUIS\\SQLEXPRESS:1433;databaseName=DBProject;user=sa;password=admin1234";
            this.con = DriverManager.getConnection(connectionUrl);

            listDepartments();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: " + cE.toString());
        }

    }

    @FXML
    public void changeToAddNew(ActionEvent actionEvent) throws IOException, SQLException {
        con.close();
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Todos os Departamentos");
        stage.setScene(new Scene(root1));
        stage.show();

    }

    private void listDepartments() throws SQLException {
        Statement stmt = null;
        String query = "SELECT * FROM Departamento;";

        try {

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ObservableList<Object> observableArrayList = FXCollections.observableArrayList();

            ArrayList<Departamento> tempList = new ArrayList<>();

            while (rs.next()) {
                Departamento d = new Departamento(rs.getInt("DepNum"), rs.getString(2), rs.getString(3));
                tempList.add(d);
            }
            deps = FXCollections.observableArrayList(tempList);

            //print para debug, para verificar se os objetos foram bem adicionados
//            for (Departamento departamento : deps) {
//                System.out.println(departamento.toString());
//            }
        } catch (SQLException e) {
            Logger.getLogger(ShowAllSceneController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (stmt != null) {
                stmt.close();
                updateTable();
            }
        }
    }

    private void updateTable() {
        depTable.setEditable(true);
        depTable.getColumns().clear();

        numCol.setCellValueFactory(new PropertyValueFactory<>("Num"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        localCol.setCellValueFactory(new PropertyValueFactory<>("Local"));

        depTable.setItems(deps);
        depTable.getColumns().addAll(numCol, nomeCol, localCol);

//        TableColumn numeroDep = new TableColumn("dasd");
//        TableColumn nomeDep = new TableColumn("dasdddddddddddddd");
//        TableColumn localDep = new TableColumn("fff");
//        
//        depTable.getColumns().addAll(numeroDep, nomeDep, localDep);
    }

    @FXML
    private void closeWindow(ActionEvent actionEvent) throws SQLException {
        con.close();
        System.exit(0);

    }
}
