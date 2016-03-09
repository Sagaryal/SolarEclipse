package MathEngine;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Maths {

	public static Matrix4f createTransMatrix(Vector3f translate, Vector3f rotate, Vector3f scale) {
		
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		Matrix4f.translate(translate, matrix, matrix);
		
		Matrix4f.rotate((float) Math.toRadians(rotate.x), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotate.y), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotate.z), new Vector3f(0, 0, 1), matrix, matrix);
		
		Matrix4f.scale(new Vector3f(scale.x, scale.y, scale.z), matrix, matrix);
		
		return matrix;
	}
}
