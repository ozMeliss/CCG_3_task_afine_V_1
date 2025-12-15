package com.cgvsu.objreader;

import com.cgvsu.render_engine.GraphicConveyor;
import com.cgvsu.render_engine.Transform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point2f;
import javax.vecmath.Vector3f;

public class AfineTest {

    private Transform transform;

    @BeforeEach
    public void setUp() {
        transform = new Transform();
    }

    @Test
    public void testDefaultConstructor() {
        // Проверяем значения по умолчанию
        Vector3f translation = transform.getTranslation();
        Vector3f rotation = transform.getRotation();
        Vector3f scale = transform.getScale();

        assertEquals(0.0f, translation.x, 0.001f);
        assertEquals(0.0f, translation.y, 0.001f);
        assertEquals(0.0f, translation.z, 0.001f);

        assertEquals(0.0f, rotation.x, 0.001f);
        assertEquals(0.0f, rotation.y, 0.001f);
        assertEquals(0.0f, rotation.z, 0.001f);

        assertEquals(1.0f, scale.x, 0.001f);
        assertEquals(1.0f, scale.y, 0.001f);
        assertEquals(1.0f, scale.z, 0.001f);
    }

    @Test
    public void testSetTranslation() {
        Vector3f newTranslation = new Vector3f(10.0f, 20.0f, 30.0f);
        transform.setTranslation(newTranslation);

        Vector3f actual = transform.getTranslation();
        assertEquals(10.0f, actual.x, 0.001f);
        assertEquals(20.0f, actual.y, 0.001f);
        assertEquals(30.0f, actual.z, 0.001f);
    }

    @Test
    public void testSetRotation() {
        Vector3f newRotation = new Vector3f(0.5f, 1.0f, 1.5f);
        transform.setRotation(newRotation);

        Vector3f actual = transform.getRotation();
        assertEquals(0.5f, actual.x, 0.001f);
        assertEquals(1.0f, actual.y, 0.001f);
        assertEquals(1.5f, actual.z, 0.001f);
    }

    @Test
    public void testSetScale() {
        Vector3f newScale = new Vector3f(2.0f, 3.0f, 4.0f);
        transform.setScale(newScale);

        Vector3f actual = transform.getScale();
        assertEquals(2.0f, actual.x, 0.001f);
        assertEquals(3.0f, actual.y, 0.001f);
        assertEquals(4.0f, actual.z, 0.001f);
    }

    @Test
    public void testTranslate() {
        // Исходное положение
        transform.setTranslation(new Vector3f(5.0f, 5.0f, 5.0f));

        // Применяем перемещение
        transform.translate(2.0f, -3.0f, 1.0f);

        Vector3f actual = transform.getTranslation();
        assertEquals(7.0f, actual.x, 0.001f);  // 5 + 2
        assertEquals(2.0f, actual.y, 0.001f);  // 5 + (-3)
        assertEquals(6.0f, actual.z, 0.001f);  // 5 + 1
    }

    @Test
    public void testTranslateMultipleTimes() {
        // Многократное перемещение
        transform.translate(1.0f, 0.0f, 0.0f);
        transform.translate(0.0f, 2.0f, 0.0f);
        transform.translate(0.0f, 0.0f, 3.0f);

        Vector3f actual = transform.getTranslation();
        assertEquals(1.0f, actual.x, 0.001f);
        assertEquals(2.0f, actual.y, 0.001f);
        assertEquals(3.0f, actual.z, 0.001f);
    }

    @Test
    public void testRotate() {
        // Исходный угол
        transform.setRotation(new Vector3f(0.1f, 0.2f, 0.3f));

        // Применяем вращение
        transform.rotate(0.5f, -0.1f, 0.2f);

        Vector3f actual = transform.getRotation();
        assertEquals(0.6f, actual.x, 0.001f);  // 0.1 + 0.5
        assertEquals(0.1f, actual.y, 0.001f);  // 0.2 + (-0.1)
        assertEquals(0.5f, actual.z, 0.001f);  // 0.3 + 0.2
    }

    @Test
    public void testScaleUniform() {
        // Исходный масштаб
        transform.setScale(new Vector3f(2.0f, 2.0f, 2.0f));

        // Равномерное масштабирование
        transform.scale(1.5f);

        Vector3f actual = transform.getScale();
        assertEquals(3.0f, actual.x, 0.001f);  // 2.0 * 1.5
        assertEquals(3.0f, actual.y, 0.001f);
        assertEquals(3.0f, actual.z, 0.001f);
    }

    @Test
    public void testScaleUniformFromDefault() {
        // Равномерное масштабирование от значений по умолчанию
        transform.scale(2.0f);

        Vector3f actual = transform.getScale();
        assertEquals(2.0f, actual.x, 0.001f);
        assertEquals(2.0f, actual.y, 0.001f);
        assertEquals(2.0f, actual.z, 0.001f);
    }

    @Test
    public void testScaleX() {
        transform.scaleX(2.0f);

        Vector3f actual = transform.getScale();
        assertEquals(2.0f, actual.x, 0.001f);
        assertEquals(1.0f, actual.y, 0.001f);
        assertEquals(1.0f, actual.z, 0.001f);
    }

    @Test
    public void testScaleY() {
        transform.scaleY(3.0f);

        Vector3f actual = transform.getScale();
        assertEquals(1.0f, actual.x, 0.001f);
        assertEquals(3.0f, actual.y, 0.001f);
        assertEquals(1.0f, actual.z, 0.001f);
    }

    @Test
    public void testScaleZ() {
        transform.scaleZ(0.5f);

        Vector3f actual = transform.getScale();
        assertEquals(1.0f, actual.x, 0.001f);
        assertEquals(1.0f, actual.y, 0.001f);
        assertEquals(0.5f, actual.z, 0.001f);
    }

    @Test
    public void testMultipleScaleOperations() {
        transform.scale(2.0f);
        transform.scaleX(0.5f);
        transform.scaleY(3.0f);
        transform.scale(1.5f);

        Vector3f actual = transform.getScale();
        assertEquals(1.5f, actual.x, 0.001f);
        assertEquals(9.0f, actual.y, 0.001f);
        assertEquals(3.0f, actual.z, 0.001f);
    }

    @Test
    public void testReset() {

        transform.setTranslation(new Vector3f(10.0f, 20.0f, 30.0f));
        transform.setRotation(new Vector3f(1.0f, 2.0f, 3.0f));
        transform.setScale(new Vector3f(4.0f, 5.0f, 6.0f));
        transform.reset();
        Vector3f translation = transform.getTranslation();
        Vector3f rotation = transform.getRotation();
        Vector3f scale = transform.getScale();

        assertEquals(0.0f, translation.x, 0.001f);
        assertEquals(0.0f, translation.y, 0.001f);
        assertEquals(0.0f, translation.z, 0.001f);

        assertEquals(0.0f, rotation.x, 0.001f);
        assertEquals(0.0f, rotation.y, 0.001f);
        assertEquals(0.0f, rotation.z, 0.001f);

        assertEquals(1.0f, scale.x, 0.001f);
        assertEquals(1.0f, scale.y, 0.001f);
        assertEquals(1.0f, scale.z, 0.001f);
    }

    @Test
    public void testResetAfterOperations() {
        transform.translate(5.0f, 10.0f, 15.0f);
        transform.rotate(0.5f, 1.0f, 1.5f);
        transform.scale(2.0f);
        transform.scaleX(3.0f);
        transform.reset();
        Vector3f translation = transform.getTranslation();
        Vector3f rotation = transform.getRotation();
        Vector3f scale = transform.getScale();
        assertEquals(0.0f, translation.x, 0.001f);
        assertEquals(0.0f, translation.y, 0.001f);
        assertEquals(0.0f, translation.z, 0.001f);
        assertEquals(0.0f, rotation.x, 0.001f);
        assertEquals(0.0f, rotation.y, 0.001f);
        assertEquals(0.0f, rotation.z, 0.001f);
        assertEquals(1.0f, scale.x, 0.001f);
        assertEquals(1.0f, scale.y, 0.001f);
        assertEquals(1.0f, scale.z, 0.001f);
    }

    @Test
    public void testNegativeTranslation() {
        transform.translate(-5.0f, -10.0f, -15.0f);

        Vector3f actual = transform.getTranslation();
        assertEquals(-5.0f, actual.x, 0.001f);
        assertEquals(-10.0f, actual.y, 0.001f);
        assertEquals(-15.0f, actual.z, 0.001f);
    }

    @Test
    public void testNegativeRotation() {
        transform.rotate(-0.5f, -1.0f, -1.5f);

        Vector3f actual = transform.getRotation();
        assertEquals(-0.5f, actual.x, 0.001f);
        assertEquals(-1.0f, actual.y, 0.001f);
        assertEquals(-1.5f, actual.z, 0.001f);
    }

    @Test
    public void testFractionalScale() {
        transform.scale(0.5f);
        Vector3f actual = transform.getScale();
        assertEquals(0.5f, actual.x, 0.001f);
        assertEquals(0.5f, actual.y, 0.001f);
        assertEquals(0.5f, actual.z, 0.001f);
    }

    @Test
    public void testScaleChain() {
        transform.scale(2.0f);
        transform.scale(0.5f);
        Vector3f actual = transform.getScale();
        assertEquals(1.0f, actual.x, 0.001f);  // 2 * 0.5 = 1
        assertEquals(1.0f, actual.y, 0.001f);
        assertEquals(1.0f, actual.z, 0.001f);
    }

    private static final float EPSILON = 0.0001f;

    @Test
    public void testCreateTranslationMatrix() {
        Vector3f translation = new Vector3f(10.0f, 20.0f, 30.0f);
        Matrix4f matrix = GraphicConveyor.createTranslationMatrix(translation);

        assertEquals(1.0f, matrix.m00, EPSILON);
        assertEquals(0.0f, matrix.m01, EPSILON);
        assertEquals(0.0f, matrix.m02, EPSILON);
        assertEquals(0.0f, matrix.m03, EPSILON);

        assertEquals(0.0f, matrix.m10, EPSILON);
        assertEquals(1.0f, matrix.m11, EPSILON);
        assertEquals(0.0f, matrix.m12, EPSILON);
        assertEquals(0.0f, matrix.m13, EPSILON);

        assertEquals(0.0f, matrix.m20, EPSILON);
        assertEquals(0.0f, matrix.m21, EPSILON);
        assertEquals(1.0f, matrix.m22, EPSILON);
        assertEquals(0.0f, matrix.m23, EPSILON);

        assertEquals(10.0f, matrix.m30, EPSILON);
        assertEquals(20.0f, matrix.m31, EPSILON);
        assertEquals(30.0f, matrix.m32, EPSILON);
        assertEquals(1.0f, matrix.m33, EPSILON);
    }

    @Test
    public void testCreateScaleMatrix() {
        Vector3f scale = new Vector3f(2.0f, 3.0f, 4.0f);
        Matrix4f matrix = GraphicConveyor.createScaleMatrix(scale);

        assertEquals(2.0f, matrix.m00, EPSILON);
        assertEquals(0.0f, matrix.m01, EPSILON);
        assertEquals(0.0f, matrix.m02, EPSILON);
        assertEquals(0.0f, matrix.m03, EPSILON);

        assertEquals(0.0f, matrix.m10, EPSILON);
        assertEquals(3.0f, matrix.m11, EPSILON);
        assertEquals(0.0f, matrix.m12, EPSILON);
        assertEquals(0.0f, matrix.m13, EPSILON);

        assertEquals(0.0f, matrix.m20, EPSILON);
        assertEquals(0.0f, matrix.m21, EPSILON);
        assertEquals(4.0f, matrix.m22, EPSILON);
        assertEquals(0.0f, matrix.m23, EPSILON);

        assertEquals(0.0f, matrix.m30, EPSILON);
        assertEquals(0.0f, matrix.m31, EPSILON);
        assertEquals(0.0f, matrix.m32, EPSILON);
        assertEquals(1.0f, matrix.m33, EPSILON);
    }

    @Test
    public void testCreateRotationXMatrix() {
        float angle = (float) Math.PI / 4;
        Matrix4f matrix = GraphicConveyor.createRotationXMatrix(angle);

        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        assertEquals(1.0f, matrix.m00, EPSILON);
        assertEquals(0.0f, matrix.m01, EPSILON);
        assertEquals(0.0f, matrix.m02, EPSILON);
        assertEquals(0.0f, matrix.m03, EPSILON);

        assertEquals(0.0f, matrix.m10, EPSILON);
        assertEquals(cos, matrix.m11, EPSILON);
        assertEquals(sin, matrix.m12, EPSILON);
        assertEquals(0.0f, matrix.m13, EPSILON);

        assertEquals(0.0f, matrix.m20, EPSILON);
        assertEquals(-sin, matrix.m21, EPSILON);
        assertEquals(cos, matrix.m22, EPSILON);
        assertEquals(0.0f, matrix.m23, EPSILON);

        assertEquals(0.0f, matrix.m30, EPSILON);
        assertEquals(0.0f, matrix.m31, EPSILON);
        assertEquals(0.0f, matrix.m32, EPSILON);
        assertEquals(1.0f, matrix.m33, EPSILON);
    }

    @Test
    public void testCreateRotationYMatrix() {
        float angle = (float) Math.PI / 3;
        Matrix4f matrix = GraphicConveyor.createRotationYMatrix(angle);

        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        assertEquals(cos, matrix.m00, EPSILON);
        assertEquals(0.0f, matrix.m01, EPSILON);
        assertEquals(-sin, matrix.m02, EPSILON);
        assertEquals(0.0f, matrix.m03, EPSILON);

        assertEquals(0.0f, matrix.m10, EPSILON);
        assertEquals(1.0f, matrix.m11, EPSILON);
        assertEquals(0.0f, matrix.m12, EPSILON);
        assertEquals(0.0f, matrix.m13, EPSILON);

        assertEquals(sin, matrix.m20, EPSILON);
        assertEquals(0.0f, matrix.m21, EPSILON);
        assertEquals(cos, matrix.m22, EPSILON);
        assertEquals(0.0f, matrix.m23, EPSILON);

        assertEquals(0.0f, matrix.m30, EPSILON);
        assertEquals(0.0f, matrix.m31, EPSILON);
        assertEquals(0.0f, matrix.m32, EPSILON);
        assertEquals(1.0f, matrix.m33, EPSILON);
    }

    @Test
    public void testCreateRotationZMatrix() {
        float angle = (float) Math.PI / 6;
        Matrix4f matrix = GraphicConveyor.createRotationZMatrix(angle);

        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        assertEquals(cos, matrix.m00, EPSILON);
        assertEquals(sin, matrix.m01, EPSILON);
        assertEquals(0.0f, matrix.m02, EPSILON);
        assertEquals(0.0f, matrix.m03, EPSILON);

        assertEquals(-sin, matrix.m10, EPSILON);
        assertEquals(cos, matrix.m11, EPSILON);
        assertEquals(0.0f, matrix.m12, EPSILON);
        assertEquals(0.0f, matrix.m13, EPSILON);

        assertEquals(0.0f, matrix.m20, EPSILON);
        assertEquals(0.0f, matrix.m21, EPSILON);
        assertEquals(1.0f, matrix.m22, EPSILON);
        assertEquals(0.0f, matrix.m23, EPSILON);

        assertEquals(0.0f, matrix.m30, EPSILON);
        assertEquals(0.0f, matrix.m31, EPSILON);
        assertEquals(0.0f, matrix.m32, EPSILON);
        assertEquals(1.0f, matrix.m33, EPSILON);
    }

    @Test
    public void testCreateModelMatrixTranslationOnly() {
        Vector3f translation = new Vector3f(10, 20, 30);
        Vector3f rotation = new Vector3f(0, 0, 0);
        Vector3f scale = new Vector3f(1, 1, 1);

        Matrix4f matrix = GraphicConveyor.createModelMatrix(translation, rotation, scale);

        assertEquals(1.0f, matrix.m00, EPSILON);
        assertEquals(1.0f, matrix.m11, EPSILON);
        assertEquals(1.0f, matrix.m22, EPSILON);
        assertEquals(10.0f, matrix.m30, EPSILON);
        assertEquals(20.0f, matrix.m31, EPSILON);
        assertEquals(30.0f, matrix.m32, EPSILON);
        assertEquals(1.0f, matrix.m33, EPSILON);
    }

    @Test
    public void testCreateModelMatrixScaleOnly() {
        Vector3f translation = new Vector3f(0, 0, 0);
        Vector3f rotation = new Vector3f(0, 0, 0);
        Vector3f scale = new Vector3f(2, 3, 4);

        Matrix4f matrix = GraphicConveyor.createModelMatrix(translation, rotation, scale);

        assertEquals(2.0f, matrix.m00, EPSILON);
        assertEquals(3.0f, matrix.m11, EPSILON);
        assertEquals(4.0f, matrix.m22, EPSILON);
        assertEquals(0.0f, matrix.m30, EPSILON);
        assertEquals(0.0f, matrix.m31, EPSILON);
        assertEquals(0.0f, matrix.m32, EPSILON);
        assertEquals(1.0f, matrix.m33, EPSILON);
    }

    @Test
    public void testMultiplyMatrix4ByVector3() {
        float[] matrixData = {
                2, 0, 0, 0,
                0, 3, 0, 0,
                0, 0, 4, 0,
                10, 20, 30, 1
        };
        Matrix4f matrix = new Matrix4f(matrixData);
        Vector3f vector = new Vector3f(1, 2, 3);
        Vector3f result = GraphicConveyor.multiplyMatrix4ByVector3(matrix, vector);
        assertEquals(12.0f, result.x, EPSILON);
        assertEquals(26.0f, result.y, EPSILON);
        assertEquals(42.0f, result.z, EPSILON);
    }

    @Test
    public void testMultiplyMatrix4ByVector3WithPerspective() {
        float[] matrixData = {
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 1,
                0, 0, 0, 0
        };
        Matrix4f matrix = new Matrix4f(matrixData);
        Vector3f vector = new Vector3f(1, 2, 3);

        Vector3f result = GraphicConveyor.multiplyMatrix4ByVector3(matrix, vector);

        assertEquals(1.0f / 3.0f, result.x, EPSILON);
        assertEquals(2.0f / 3.0f, result.y, EPSILON);
        assertEquals(1.0f, result.z, EPSILON);
    }
}