package fr.amu.iut.cc3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ToileController implements Initializable {
    @FXML
    Pane pane;
    @FXML
    Label errorLabel;
    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public ToileController () {
        pane = new Pane();
        errorLabel = new Label();
    }

    @FXML
    void addValue (ActionEvent event) {
        // Get event informations
        TextField source = (TextField) event.getSource();
        int value = Integer.parseInt(source.getText());
        int axe = Integer.parseInt(String.valueOf(source.getId().charAt(4)));

        if (value > 20 || value < 0) inputError();
        else draw(value, axe);
    }
    void draw(int value, int axe){
        // Remove the old circle
        for (int i=0 ; i<pane.getChildren().size() ; ++i) {
            if (pane.getChildren().get(i).getId() != null
                    &&pane.getChildren().get(i).getId().equals("circle"+axe)) pane.getChildren().remove(i);
        }
        // Add the new circle
        Circle circle = new Circle();
        circle.setCenterX(getXRadarChart(value, axe));
        circle.setCenterY(getYRadarChart(value, axe));
        circle.setRadius(6);
        circle.setId("circle"+axe);
        pane.getChildren().add(circle);
    }
    void inputError () {
        errorLabel.setText("Erreur de saisie : \nLes valeurs doivent être entre 0 et 20");
        errorLabel.setTextFill(Color.web("#FF0000"));
    }
    @FXML
    void empty() {
         for (int i=0 ; i<pane.getChildren().size() ; ++i) {
             System.out.println(pane.getChildren());
         }
    }

    int getXRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur + Math.cos(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

    int getYRadarChart(double value, int axe ){
        return (int) (rayonCercleExterieur - Math.sin(Math.toRadians(angleDepart - (axe-1)  * angleEnDegre)) * rayonCercleExterieur
                *  (value / noteMaximale));
    }

}
