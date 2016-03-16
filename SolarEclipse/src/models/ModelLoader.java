package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import render.Loader;

public class ModelLoader {

	private ArrayList<Vector3f> vertices;
	private ArrayList<Vector3f> normals;
	private ArrayList<Integer> indices;
	private ArrayList<Vector2f> textures;
	
	private Loader loader;
	private float[] verticesArray;
	private int[] indicesArray;
	private float[] normalsArray;
	private float[] texturesArray;
	
	public ModelLoader(String path) {
		loader = new Loader();
		
		vertices = new ArrayList<Vector3f>();
		normals = new ArrayList<Vector3f>();
		textures = new ArrayList<Vector2f>();
		indices = new ArrayList<Integer>();
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(new File(path)));
		} catch(FileNotFoundException f) {
			f.printStackTrace();
		}
		
		String line;
		
		try {
			while(true) {
				line = reader.readLine();
				if(line.startsWith("v ")) {
					float x = Float.valueOf(line.split(" ")[1]);
					float y = Float.valueOf(line.split(" ")[2]);
					float z = Float.valueOf(line.split(" ")[3]);
					Vector3f vertex = new Vector3f(x, y, z);
					
					vertices.add(vertex);
					//model.addVertex(vertex);
					//System.out.println("vertex : " + x + " " + y + " " + z);
				}
				if(line.startsWith("vt ")) {
					float x = Float.valueOf(line.split(" ")[1]);
					float y = Float.valueOf(line.split(" ")[2]);
					Vector2f texture = new Vector2f(x, y);
					textures.add(texture);
					

					//System.out.println("texture: " + x + " " + y);
					
				}
				if(line.startsWith("vn ")) {
					float x = Float.valueOf(line.split(" ")[1]);
					float y = Float.valueOf(line.split(" ")[2]);
					float z = Float.valueOf(line.split(" ")[3]);
					Vector3f normal = new Vector3f(x, y, z);
					
					normals.add(normal);
					
					//model.addNormal(normal);
					//System.out.println("normal : " + x + " " + y + " " + z);
					
				}
				if(line.startsWith("f ")) {
	
					float x1 = Float.valueOf(line.split(" ")[1].split("/")[0]);
					float y1 = Float.valueOf(line.split(" ")[2].split("/")[0]);
					float z1 = Float.valueOf(line.split(" ")[3].split("/")[0]);
					float x2 = Float.valueOf(line.split(" ")[1].split("/")[2]);
					float y2 = Float.valueOf(line.split(" ")[2].split("/")[2]);
					float z2 = Float.valueOf(line.split(" ")[3].split("/")[2]);
					
					//Vector3f vertex = new Vector3f(x1, y1, z1);
					//Vector3f normal = new Vector3f(x2, y2, z2);
					
					texturesArray = new float[vertices.size() * 2];
					normalsArray = new float[vertices.size() * 3];
					break;
					//model.addFace(new Face(vertex, normal));
				
					//System.out.println("faces 1 : " + x1 + " " + y1 + " " + z1);
					//System.out.println("faces 2 : " + x2 + " " + y2 + " " + z2);
					
				}
			}
			
			while(line != null) {
				if(!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				
				String[] currenLine = line.split(" ");
				String[] v1 = currenLine[1].split("/");
				String[] v2 = currenLine[2].split("/");
				String[] v3 = currenLine[3].split("/");
				
				processVIN(v1);
				processVIN(v2);
				processVIN(v3);
				
				line = reader.readLine();
			}
			reader.close(); 
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		verticesArray = new float[vertices.size() * 3];
		indicesArray = new int[indices.size()];
		
		int vertexPointer = 0;
		for(Vector3f vertex : vertices) {
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		
		for(int i = 0; i < indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		
	}
	
	private void processVIN(String[] vertex) {
		int vertexPos = Integer.valueOf(vertex[0]) - 1;
		//System.out.println(vertexPos);
		indices.add(vertexPos);
		Vector2f texture = textures.get(Integer.valueOf(vertex[1]) - 1);
		texturesArray[vertexPos * 2] = texture.x;
		texturesArray[vertexPos * 2 + 1] = 1 - texture.y;
		
		Vector3f normal = normals.get(Integer.valueOf(vertex[2]) - 1);
		normalsArray[vertexPos * 3] = normal.x;
		normalsArray[vertexPos * 3 + 1] = normal.y;
		normalsArray[vertexPos * 3 + 2] = normal.z;
	}

	public Loader getLoader() {
		return loader;
	}
	 
	public RawModel getModel() {
		
		return loader.loadToVAO(verticesArray, texturesArray, normalsArray, indicesArray);
	}
	
}
