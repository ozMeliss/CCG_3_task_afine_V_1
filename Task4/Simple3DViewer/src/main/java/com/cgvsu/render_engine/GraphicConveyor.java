package com.cgvsu.render_engine;
import javax.vecmath.*;

public class GraphicConveyor {

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultX = new Vector3f();
        Vector3f resultY = new Vector3f();
        Vector3f resultZ = new Vector3f();

        resultZ.sub(target, eye);
        resultX.cross(up, resultZ);
        resultY.cross(resultZ, resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        float[] matrix = new float[]{
                resultX.x, resultY.x, resultZ.x, 0,
                resultX.y, resultY.y, resultZ.y, 0,
                resultX.z, resultY.z, resultZ.z, 0,
                -resultX.dot(eye), -resultY.dot(eye), -resultZ.dot(eye), 1};
        Matrix4f m = new Matrix4f(matrix);
        m.transpose();
        return m;
    }

    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        Matrix4f result = new Matrix4f();
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.m00 = tangentMinusOnDegree / aspectRatio;
        result.m11 = tangentMinusOnDegree;
        result.m22 = (farPlane + nearPlane) / (farPlane - nearPlane);
        result.m23 = 1.0F;
        result.m32 = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);
        result.transpose();
        return result;
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4f matrix, final Vector3f vertex) {
        final float x = (matrix.m00 * vertex.x) + (matrix.m01 * vertex.y) + (matrix.m02 * vertex.z) + matrix.m03;
        final float y = (matrix.m10 * vertex.x) + (matrix.m11 * vertex.y) + (matrix.m12 * vertex.z) + matrix.m13;
        final float z = (matrix.m20 * vertex.x) + (matrix.m21 * vertex.y) + (matrix.m22 * vertex.z) + matrix.m23;
        final float w = (matrix.m30 * vertex.x) + (matrix.m31 * vertex.y) + (matrix.m32 * vertex.z) + matrix.m33;
        return new Vector3f(x / w, y / w, z / w);
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.x * width + width / 2.0F, -vertex.y * height + height / 2.0F);
    }

    // Матрица перемещения
    public static Matrix4f createTranslationMatrix(Vector3f translation) {
        float[] matrix = new float[]{
                1, 0, 0, translation.x,
                0, 1, 0, translation.y,
                0, 0, 1, translation.z,
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }

    // Матрица масштабирования
    public static Matrix4f createScaleMatrix(Vector3f scale) {
        float[] matrix = new float[]{
                scale.x, 0, 0, 0,
                0, scale.y, 0, 0,
                0, 0, scale.z, 0,
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }


    public static Matrix4f createRotationXMatrix(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float[] matrix = new float[]{
                1, 0, 0, 0,
                0, cos, sin, 0,
                0, -sin, cos, 0,
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }

    public static Matrix4f createRotationYMatrix(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float[] matrix = new float[]{
                cos, 0, -sin, 0,
                0, 1, 0, 0,
                sin, 0, cos, 0,
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }

    public static Matrix4f createRotationZMatrix(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float[] matrix = new float[]{
                cos, sin, 0, 0,
                -sin, cos, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }

    public static Matrix4f createModelMatrix(Vector3f translation, Vector3f rotation, Vector3f scale) {
        Matrix4f scaleMatrix = createScaleMatrix(scale);
        // Поворот (X → Y → Z)
        Matrix4f rotationMatrix = new Matrix4f();
        rotationMatrix.setIdentity();

        if (rotation.x != 0) {
            Matrix4f rotX = createRotationXMatrix(rotation.x);
            rotationMatrix.mul(rotX);
        }
        if (rotation.y != 0) {
            Matrix4f rotY = createRotationYMatrix(rotation.y);
            rotationMatrix.mul(rotY);
        }
        if (rotation.z != 0) {
            Matrix4f rotZ = createRotationZMatrix(rotation.z);
            rotationMatrix.mul(rotZ);
        }

        // Перемещение
        Matrix4f translationMatrix = createTranslationMatrix(translation);

        // Translation * Rotation * Scale
        Matrix4f result = new Matrix4f(translationMatrix);
        result.mul(rotationMatrix);
        result.mul(scaleMatrix);
        return result;
    }
}
//переписать для столбцов перенос
