/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mx.edu.itses.electricidad.aflda.seccion;




import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Seccion {
    


    private final List<Residencia> allResidences = new ArrayList<>();
    private final List<Residencia> studyGroup = new ArrayList<>();

    private final Circle allResidencesCenter = new Circle(0, 0, 8, Color.BLUE);
    private final Text allResidencesCenterLabel = new Text("Centro de las residencias: (0.0, 0.0)");

    private final Circle studyGroupCenter = new Circle(0, 0, 8, Color.GREEN);
    private final Text studyGroupCenterLabel = new Text("Centro del grupo de estudio: (0.0, 0.0)");

    @Override
    public void start(Stage primaryStage) {
        // Inicializar residencias
        // Nota: Las residencias se crean con un radio de 10. Los clics se gestionan en base a estos círculos.
        Residencia res1 = new Residencia("Residencia A", 300, 150, 150);
        Residencia res2 = new Residencia("Residencia B", 500, 500, 250);
        Residencia res3 = new Residencia("Residencia C", 200, 300, 450);

        allResidences.addAll(Arrays.asList(res1, res2, res3));

        // Configurar el grupo de estudio (mínimo 3 personas, 1 de otra residencia)
        studyGroup.addAll(Arrays.asList(res1, res2, res3)); // Ejemplo: una persona de cada residencia

        // Configurar el panel principal
        Pane mapPane = new Pane();
        mapPane.setPrefSize(800, 600);
        mapPane.setStyle("-fx-background-color: lightgrey;");

        // Agregar residencias y sus etiquetas al panel
        for (Residencia res : allResidences) {
            Circle resCircle = res.getCircle();
            resCircle.setFill(Color.RED);
            resCircle.setStroke(Color.BLACK);

            Text label = new Text(res.getCenterX() + 15, res.getCenterY(),
                    String.format("%s (Pop: %d)", res.getName(), res.getPopulation()));

            res.populationProperty().addListener((obs, oldVal, newVal) -> {
                label.setText(String.format("%s (Pop: %d)", res.getName(), newVal.intValue()));
                updateCentralPoints();
            });

            // Hacer que las residencias sean arrastrables
            resCircle.setOnMouseDragged(e -> {
                resCircle.setCenterX(e.getX());
                resCircle.setCenterY(e.getY());
                label.setX(e.getX() + 15);
                label.setY(e.getY());
                updateCentralPoints();
            });

            mapPane.getChildren().addAll(resCircle, label);
        }

        // Agregar puntos centrales y sus etiquetas
        allResidencesCenter.setFill(Color.BLUE);
        studyGroupCenter.setFill(Color.GREEN);

        mapPane.getChildren().addAll(allResidencesCenter, studyGroupCenter, allResidencesCenterLabel, studyGroupCenterLabel);

        // Actualizar puntos iniciales
        updateCentralPoints();

        // Configurar UI para ajustar la población
        VBox controls = new VBox(10);
        controls.setPadding(new Insets(10));
        
        for(Residencia res : allResidences) {
            Label resLabel = new Label("Población de " + res.getName() + ": " + res.getPopulation());
            Slider slider = new Slider(50, 1000, res.getPopulation());
            
            res.populationProperty().bind(slider.valueProperty());
            slider.valueProperty().addListener((obs, oldVal, newVal) -> resLabel.setText("Población de " + res.getName() + ": " + newVal.intValue()));

            controls.getChildren().addAll(resLabel, slider);
        }

        VBox root = new VBox(10, mapPane, controls);
        
        Scene scene = new Scene(root);

        primaryStage.setTitle("Campus Map");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateCentralPoints() {
        // Calcular y actualizar el centro de todas las residencias
        double allResX = CentralPointCalculator.calculateCenterX(allResidences);
        double allResY = CentralPointCalculator.calculateCenterY(allResidences);
        allResidencesCenter.setCenterX(allResX);
        allResidencesCenter.setCenterY(allResY);
        allResidencesCenterLabel.setX(allResX + 15);
        allResidencesCenterLabel.setY(allResY - 10);
        allResidencesCenterLabel.setText(String.format("Centro de todas las residencias: (%.2f, %.2f)", allResX, allResY));

        // Calcular y actualizar el centro del grupo de estudio
        double studyGroupX = CentralPointCalculator.calculateCenterX(studyGroup);
        double studyGroupY = CentralPointCalculator.calculateCenterY(studyGroup);
        studyGroupCenter.setCenterX(studyGroupX);
        studyGroupCenter.setCenterY(studyGroupY);
        studyGroupCenterLabel.setX(studyGroupX + 15);
        studyGroupCenterLabel.setY(studyGroupY - 10);
        studyGroupCenterLabel.setText(String.format("Centro del grupo de estudio: (%.2f, %.2f)", studyGroupX, studyGroupY));
    }

    public static void main(String[] args) {
        launch(args);
    }
    }
}
