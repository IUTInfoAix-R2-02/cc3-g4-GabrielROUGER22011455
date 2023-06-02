package fr.amu.iut.cc3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ToileController implements Initializable {
    @FXML
    Pane pane;
    @FXML
    Label errorLabel;
    ArrayList<Circle> circleList;
    Boolean drawn;
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
        circleList = new ArrayList<Circle>();
        drawn = false;
    }

    @FXML
    void addValue (ActionEvent event) {
        TextField source = (TextField) event.getSource();
        if (source.getText()!="") {
            int value = Integer.parseInt(source.getText());
            int axe = Integer.parseInt(String.valueOf(source.getId().charAt(4)));
            if (value > 20 || value < 0) inputError();
            else addCircle(value, axe);
            if (drawn) draw();
        }
    }
    void addCircle(int value, int axe){
        // Remove the old circle
        for (int i=0 ; i<pane.getChildren().size() ; ++i) {
            if (pane.getChildren().get(i).getId() != null
                    &&pane.getChildren().get(i).getId().equals("circle"+axe)) {
                circleList.remove(pane.getChildren().get(i));
                pane.getChildren().remove(i);
            }
        }
        // Add the new circle
        Circle circle = new Circle();
        circle.setCenterX(getXRadarChart(value, axe));
        circle.setCenterY(getYRadarChart(value, axe));
        circle.setRadius(6);
        circle.setId("circle"+axe);
        pane.getChildren().add(circle);
        circleList.add(circle);
    }
    void inputError () {
        errorLabel.setText("Erreur de saisie : \nLes valeurs doivent être entre 0 et 20");
        errorLabel.setTextFill(Color.RED);
    }
    @FXML
    void erase() {
         for (int i=0 ; i<pane.getChildren().size() ; ++i) {
             if (pane.getChildren().get(i).getId() != null) {
                 pane.getChildren().remove(i); // Only circles and lines have id for now
             }
         }
         errorLabel.setText("");
    }
    void eraseLines(){
        for (int i=0 ; i<pane.getChildren().size() ; ++i) {
            if (pane.getChildren().get(i).getId() != null
                    && pane.getChildren().get(i).getId().equals("Line")) {
                pane.getChildren().remove(i);
            }
        }
    }
    @FXML
    void draw () {
        if (circleList.size() == 6) {
            eraseLines();
            Circle start = null;
            Circle end = null;
            for (int i = 1 ; i <= 5 ; ++i ) {
                for (int j = 0 ; j < 6 ; ++j ) {
                    if (circleList.get(j).getId().equals("circle"+i)) start = circleList.get(j);
                    if (circleList.get(j).getId().equals("circle"+(i+1))) end = circleList.get(j);
                }
                drawLine(start, end);
            }
            for (int i = 0 ; i < 6 ; ++i ) {
                if (circleList.get(i).getId().equals("circle6")) start = circleList.get(i);
                if (circleList.get(i).getId().equals("circle1")) end = circleList.get(i);
            }
            drawLine(start, end);
            drawn = true;
        }
    }
    void drawLine(Circle start, Circle end) {
        Line line = new Line();
        line.setStartX(start.getCenterX());
        line.setStartY(start.getCenterY());
        line.setEndX(end.getCenterX());
        line.setEndY(end.getCenterY());
        line.setId("Line");
        pane.getChildren().add(line);
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
