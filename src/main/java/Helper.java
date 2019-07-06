import models.CameraParams;
import models.ColorParams;
import models.Triangle;
import models.Vector3;
import operations.Vector3Operations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    private int vertNum;
    public List<Vector3> vectors = new ArrayList<>();
    public List<Triangle> triangles = new ArrayList<>();
    public CameraParams cameraParams;
    public ColorParams colorParams;

    public void loadCameraParams() throws IOException {
        this.cameraParams = new CameraParams();
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/camera_input/input.txt"));
        String line = reader.readLine();
        while (line != null) {
            cameraParams.addParam(line);
            line = reader.readLine();
        }
    }

    public void loadColorParams() throws IOException {
        this.colorParams = new ColorParams();
        BufferedReader reader = new BufferedReader(new FileReader("./src/main/color_input/input.txt"));
        String line = reader.readLine();
        while (line != null) {
            colorParams.addParam(line);
            line = reader.readLine();
        }
    }

    public void loadVertices() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./src/main/input/calice2.BYU"));
            String line = reader.readLine();
            String[] line1 = line.split(" ");
            vertNum = Integer.parseInt(line1[0]);
            int aux = 0;
            String[] splitLine;
            line = reader.readLine();
            while (line != null) {
                splitLine = line.split(" ");
                if (aux < vertNum) {
                    vectors.add(new Vector3(Double.parseDouble(splitLine[0]), Double.parseDouble(splitLine[1]), Double.parseDouble(splitLine[2])));
                } else {
                    triangles.add(new Triangle(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2])));
                }
                aux++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Vector3> normalizeTriangles() {
        Vector3 v1;
        Vector3 v2;
        Vector3 v3;
        List<Vector3> triangleNorms = new ArrayList<>();
        for (int i = 0; i < triangles.size(); i++) {
            v1 = vectors.get(triangles.get(i).getV1() - 1);
            v2 = vectors.get(triangles.get(i).getV2() - 1);
            v3 = vectors.get(triangles.get(i).getV3() - 1);

            Vector3 aux1 = Vector3Operations.getInstance().subtraction(v2, v1);
            Vector3 aux2 = Vector3Operations.getInstance().subtraction(v3, v1);
            Vector3 crossProduct = Vector3Operations.getInstance().crossProduct(aux1, aux2);
            triangleNorms.add(Vector3Operations.getInstance().normalizeVector(crossProduct));
        }

        return triangleNorms;
    }

    public List<Vector3> normalizeEdges(List<Vector3> normalizedTriangles) {
        List<Vector3> normalizedEdges = new ArrayList<>();
        Vector3 sum = new Vector3(0, 0, 0);
        for (int i = 0; i < vertNum; i++) {
            for (int t = 0; t < triangles.size(); t++) {
                if (triangles.get(t).containsEdge(i + 1)) {
                    sum = Vector3Operations.getInstance().addition(sum, normalizedTriangles.get(t));
                }
            }
            normalizedEdges.add(Vector3Operations.getInstance().normalizeVector(sum));
            sum.setX(0);
            sum.setY(0);
            sum.setZ(0);
        }

        return normalizedEdges;
    }


}
