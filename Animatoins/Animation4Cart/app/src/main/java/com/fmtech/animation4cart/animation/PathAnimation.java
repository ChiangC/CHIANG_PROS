package com.fmtech.animation4cart.animation;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class PathAnimation extends Animation {

    private PathMeasure mPathMeasure;
    private float[] pos = new float[2];

    public PathAnimation(Path path) {
        super();
        mPathMeasure = new PathMeasure(path, false);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        super.applyTransformation(interpolatedTime, transformation);
        mPathMeasure.getPosTan(mPathMeasure.getLength() * interpolatedTime, pos, null);
        transformation.getMatrix().setTranslate(pos[0], pos[1]);
    }
}