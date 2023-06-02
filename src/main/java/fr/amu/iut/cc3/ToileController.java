package fr.amu.iut.cc3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ToileController implements Initializable {
    @FXML
    Pane pane;
    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public ToileController () {
        pane = new Pane();
    }

    @FXML
    void draw(ActionEvent event){
        // Get event informations
        TextField source = (TextField) event.getSource();
        int value = Integer.parseInt(source.getText());
        int axe = Integer.parseInt(String.valueOf(source.getId().charAt(4)));

        // Draw the circle at the right location
        Circle circle = new Circle();
        circle.setCenterX(getXRadarChart(value, axe));
        circle.setCenterY(getYRadarChart(value, axe));
        circle.setRadius(6);
        pane.getChildren().add(circle);
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
