package com.cgvsu.render_engine;

import javax.vecmath.Vector3f;

public class Transform {
    private Vector3f translation = new Vector3f(0, 0, 0);
    private Vector3f rotation = new Vector3f(0, 0, 0); // углы в радианах
    private Vector3f scale = new Vector3f(1, 1, 1);

    public Transform() {}

    public Vector3f getTranslation() { return translation; }
    public Vector3f getRotation() { return rotation; }
    public Vector3f getScale() { return scale; }

    public void setTranslation(Vector3f translation) { this.translation = translation; }
    public void setRotation(Vector3f rotation) { this.rotation = rotation; }
    public void setScale(Vector3f scale) { this.scale = scale; }

    public void translate(float dx, float dy, float dz) {
        translation.x += dx;
        translation.y += dy;
        translation.z += dz;
    }

    public void rotate(float dx, float dy, float dz) {
        rotation.x += dx;
        rotation.y += dy;
        rotation.z += dz;
    }

    // Масштабирование по всем осям для увеличения/уменьшения
    public void scale(float factor) {
        scale.x *= factor;
        scale.y *= factor;
        scale.z *= factor;
    }

    public void scaleX(float factor) {
        scale.x *= factor;
    }

    public void scaleY(float factor) {
        scale.y *= factor;
    }

    public void scaleZ(float factor) {
        scale.z *= factor;
    }

    public void reset() {
        translation = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
        scale = new Vector3f(1, 1, 1);
    }
}