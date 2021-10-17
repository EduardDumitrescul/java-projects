package com.example.hexagondijkstra;

/*
    Cube coordinates satisfy
        x + y + z = 0
 */

public class CubeCoordinates {
    private double x, y, z;

    public CubeCoordinates() {

    }

    public CubeCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
        z = 0 - x - y;
    }

    public CubeCoordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static CubeCoordinates roundCube(CubeCoordinates cube) {
        long rx = Math.round(cube.getX());
        long ry = Math.round(cube.getY());
        long rz = Math.round(cube.getZ());

        double xDiff = Math.abs(rx - cube.getX());
        double yDiff = Math.abs(ry - cube.getY());
        double zDiff = Math.abs(rz - cube.getZ());

        if(xDiff > yDiff && xDiff > zDiff)
            rx = 0 - ry - rz;
        else if(yDiff > zDiff)
            ry = 0 - rx - rz;
        else
            rz = 0 - rx - ry;
        return new CubeCoordinates(rx, ry, rz);
    }

    public CubeCoordinates add(CubeCoordinates cube) {
        return new CubeCoordinates(x + cube.getX(), y + cube.getY(), z + cube.getZ());
    }

    public CubeCoordinates subtract(CubeCoordinates cube) {
        return new CubeCoordinates(x - cube.getX(), y - cube.getY(), z - cube.getZ());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
