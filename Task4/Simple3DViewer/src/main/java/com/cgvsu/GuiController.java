package com.cgvsu;

import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.render_engine.Transform;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import javax.vecmath.Vector3f;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GuiController {

    final private float TRANSLATION_SPEED = 1.0F;
    final private float ROTATION_SPEED = 0.05F; // в радианах
    final private float SCALE_SPEED = 1.1F; // множитель

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;
    private Transform modelTransform = new Transform();

    private Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    // Флаги для клавиш
    private boolean keyUp = false;
    private boolean keyDown = false;
    private boolean keyLeft = false;
    private boolean keyRight = false;
    private boolean keyW = false;
    private boolean keyS = false;
    private boolean keyA = false;
    private boolean keyD = false;
    private boolean keyE = false;
    private boolean keyZ = false;
    private boolean keyX = false;
    private boolean keyC = false;
    private boolean keyF = false;
    private boolean keyV = false;
    private boolean keyG = false;
    private boolean keyB = false;
    private boolean keyO = false;
    private boolean keyP = false;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::handleKeyPressed);
        canvas.setOnKeyReleased(this::handleKeyReleased);

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(16), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            handleContinuousKeys();

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh,
                        (int) width, (int) height, modelTransform);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP:    keyUp = true; break;
            case DOWN:  keyDown = true; break;
            case LEFT:  keyLeft = true; break;
            case RIGHT: keyRight = true; break;
            case W:     keyW = true; break;
            case S:     keyS = true; break;
            case A:     keyA = true; break;
            case D:     keyD = true; break;
            case E:     keyE = true; break;
            case Z:     keyZ = true; break;
            case X:     keyX = true; break;
            case C:     keyC = true; break;
            case F:     keyF = true; break;
            case V:     keyV = true; break;
            case G:     keyG = true; break;
            case B:     keyB = true; break;
            case O:     keyO = true; break;
            case P:     keyP = true; break;
            case R:     modelTransform.reset(); break;
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case UP:    keyUp = false; break;
            case DOWN:  keyDown = false; break;
            case LEFT:  keyLeft = false; break;
            case RIGHT: keyRight = false; break;
            case W:     keyW = false; break;
            case S:     keyS = false; break;
            case A:     keyA = false; break;
            case D:     keyD = false; break;
            case E:     keyE = false; break;
            case Z:     keyZ = false; break;
            case X:     keyX = false; break;
            case C:     keyC = false; break;
            case F:     keyF = false; break;
            case V:     keyV = false; break;
            case G:     keyG = false; break;
            case B:     keyB = false; break;
            case O:     keyO = false; break;
            case P:     keyP = false; break;
        }
    }

    private void handleContinuousKeys() {
        if (keyUp)    modelTransform.translate(0, TRANSLATION_SPEED, 0);
        if (keyDown)  modelTransform.translate(0, -TRANSLATION_SPEED, 0);
        if (keyLeft)  modelTransform.translate(-TRANSLATION_SPEED, 0, 0);
        if (keyRight) modelTransform.translate(TRANSLATION_SPEED, 0, 0);

        // Вращение
        if (keyW) modelTransform.rotate(ROTATION_SPEED, 0, 0); // вокруг X
        if (keyS) modelTransform.rotate(-ROTATION_SPEED, 0, 0);
        if (keyA) modelTransform.rotate(0, ROTATION_SPEED, 0); // вокруг Y
        if (keyD) modelTransform.rotate(0, -ROTATION_SPEED, 0);
        if (keyO) modelTransform.rotate(0, 0, ROTATION_SPEED); // вокруг Z
        if (keyP) modelTransform.rotate(0, 0, -ROTATION_SPEED);

        // Масштабирование полностью
        if (keyE) modelTransform.scale(SCALE_SPEED);
        if (keyZ) modelTransform.scale(1.0f / SCALE_SPEED);
        // Масштабирование по оси X
        if (keyX) modelTransform.scaleX(SCALE_SPEED);
        if (keyC) modelTransform.scaleX(1.0f / SCALE_SPEED);
        // Масштабирование по оси Y
        if (keyF) modelTransform.scaleY(SCALE_SPEED);
        if (keyV) modelTransform.scaleY(1.0f / SCALE_SPEED);

        // Масштабирование по оси Z
        if (keyG) modelTransform.scaleZ(SCALE_SPEED);
        if (keyB) modelTransform.scaleZ(1.0f / SCALE_SPEED);
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            modelTransform.reset();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION_SPEED));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION_SPEED));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION_SPEED, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION_SPEED, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION_SPEED, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION_SPEED, 0));
    }
}