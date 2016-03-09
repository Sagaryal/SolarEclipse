package models;

import org.lwjgl.util.vector.Vector3f;

public class Face {

	private Vector3f vertex;
	private Vector3f normal;
	
	public Face(Vector3f vertex, Vector3f normal) {
		this.setVertex(vertex);
		this.setNormal(normal);
	}

	public Vector3f getVertex() {
		return vertex;
	}

	public void setVertex(Vector3f vertex) {
		this.vertex = vertex;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}
	
}
