package operations;

import models.Vector3;

public class Vector3Operations {
    public static Vector3Operations instance;

    private Vector3Operations() {
    }

    public static Vector3Operations getInstance() {
        if (instance == null) {
            instance = new Vector3Operations();
        }
        return instance;
    }

    public double vectorNorm(Vector3 entry) {
        double x2 = Math.pow(entry.getX(), 2);
        double y2 = Math.pow(entry.getY(), 2);
        double z2 = Math.pow(entry.getZ(), 2);
        double norm = Math.sqrt(x2 + y2 + z2);
        return norm;
    }

    public Vector3 normalizeVector(Vector3 entry) {
        double norm = vectorNorm(entry);
        Vector3 normalizedVector = new Vector3(entry.getX() / norm, entry.getY() / norm, entry.getZ() / norm);
        return normalizedVector;
    }

    public Vector3 addition(Vector3 entry1, Vector3 entry2) {
        Vector3 result = new Vector3();
        result.setX(entry1.getX() + entry2.getX());
        result.setY(entry1.getY() + entry2.getY());
        result.setZ(entry1.getZ() + entry2.getZ());
        return result;
    }

    public Vector3 subtraction(Vector3 entry1, Vector3 entry2) {
        Vector3 negativeEntry2 = new Vector3();
        negativeEntry2.setX(entry2.getX() * (-1));
        negativeEntry2.setY(entry2.getY() * (-1));
        negativeEntry2.setZ(entry2.getZ() * (-1));

        return this.addition(entry1, negativeEntry2);
    }

    public Vector3 scalarMultiplication(double scalar, Vector3 vector) {
        return new Vector3(vector.getX() * scalar, vector.getY() * scalar, vector.getZ() * scalar);
    }

    public double dotProduct(Vector3 entry1, Vector3 entry2) {
        double xProduct = entry1.getX() * entry2.getX();
        double yProduct = entry1.getY() * entry2.getY();
        double zProduct = entry1.getZ() * entry2.getZ();

        return xProduct + yProduct + zProduct;
    }

    public Vector3 crossProduct(Vector3 entry1, Vector3 entry2) {
        double xProduct = (entry1.getY() * entry2.getZ()) - (entry1.getZ() * entry2.getY());
        double yProduct = (entry1.getZ() * entry2.getX()) - (entry1.getX() * entry2.getZ());
        double zProduct = (entry1.getX() * entry2.getY()) - (entry1.getY() * entry2.getX());

        return new Vector3(xProduct, yProduct, zProduct);
    }

    public Vector3 barycentricCoordinates(Vector3 P, Vector3 A, Vector3 B, Vector3 C) {

//        double alpha;
//        double beta;
//        double gama;
//
//        double a, b, c, d, e, f;
//
//        //| a  b|
//        //| c  d|
//
//        a = A.getX() - C.getX();
//        b = B.getX() - C.getX();
//        c = A.getY() - C.getY();
//        d = B.getY() - C.getY();
//
//        e = P.getX() - C.getX();
//        f = P.getY() - C.getY();
//
//        double det = (a * d - b * c);
//
//        a = d / det;
//        b = (-b) / det;
//        c = (-c) / det;
//        d = a / det;
//
//        alpha = a * e + b * f;
//        beta = c * e + d * f;
//        gama = 1 - alpha - beta;


        double abcArea = Math.abs(triangleArea(A,B,C));
        double u = Math.abs(triangleArea(P,B,C))/abcArea;
        double v = Math.abs(triangleArea(A,P,C))/abcArea;
        double w = Math.abs(triangleArea(A,B,P))/abcArea;
//
        return new Vector3(u, v, w);
    }

    public Vector3 barycentricReverse(Vector3 barycentricCords, Vector3 A, Vector3 B, Vector3 C) {
        double px = (barycentricCords.getX() * A.getX()) + (barycentricCords.getY() * B.getX()) + (barycentricCords.getZ() * C.getX());
        double py = (barycentricCords.getX() * A.getY()) + (barycentricCords.getY() * B.getY()) + (barycentricCords.getZ() * C.getY());

        return new Vector3(px, py, 0);
    }

    public Vector3 orthogonalize(Vector3 v, Vector3 n) {
        double aux1 = this.dotProduct(v, n);
        double aux2 = this.dotProduct(n, n);
        Vector3 aux3 = this.scalarMultiplication(aux1 / aux2, n);
        Vector3 result = this.subtraction(v, aux3);
        return result;
    }

    public Vector3 getU(Vector3 n, Vector3 v) {
//        Vector3 orthoV = this.orthogonalize(v,n);
        Vector3 u = this.crossProduct(n, v);
        return u;
    }

    private double triangleArea(Vector3 entry1, Vector3 entry2, Vector3 entry3) {
        double p1 = entry1.getX() * (entry2.getY() - entry3.getY());
        double p2 = entry2.getX() * (entry3.getY() - entry1.getY());
        double p3 = entry3.getX() * (entry1.getY() - entry2.getY());
        double area = (p1 + p2 + p3) / 2;

        return area;
    }
}
