package render;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import models.RawModel;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Loader {
	
	private ArrayList<Integer> VAOS = new ArrayList<Integer>();
	private ArrayList<Integer> VBOS = new ArrayList<Integer>();
	private ArrayList<Integer> textures = new ArrayList<Integer>();

	public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		int VAOID = createVAO();
		bindIndicesBuffer(indices);
		storeDataAttributeList(0, 3, positions);
		storeDataAttributeList(1, 2, textureCoords);
		storeDataAttributeList(2, 3, normals);
		unBindVAO();
		
		return new RawModel(VAOID, indices.length);
		
	}
	public int loadTexture(String file) {
		System.out.println("asdsad");
		Texture texture = null;
		
		//String filename = file.split(".")[0];
		
		//Dont directly use . as delimiter add \\ before it
		String extension = file.split("\\.")[1]; 
		
		try {
			texture = TextureLoader.getTexture(extension.toUpperCase(), new FileInputStream("res/" + file));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("error texture file not found");
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("error texture");
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		return textureID;
	}
	public void clear() {
		
		for(int VAO: VAOS)
			GL30.glDeleteVertexArrays(VAO);
		
		for(int VBO: VBOS)
			GL15.glDeleteBuffers(VBO);
		
		for(int texture: textures)
			GL11.glDeleteTextures(texture);
			
	}
	
	private int createVAO() {
		int VAOID = GL30.glGenVertexArrays();
		VAOS.add(VAOID);
		GL30.glBindVertexArray(VAOID);
		
		return VAOID;
	}
	
	private void storeDataAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int VBOID = GL15.glGenBuffers();
		VBOS.add(VBOID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOID);
			FloatBuffer buffer = storeDataFloatBuffer(data);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	private void unBindVAO() {
		GL30.glBindVertexArray(0);
	}
	 
	private FloatBuffer storeDataFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
		
	}
	
	private void bindIndicesBuffer(int[] indices) {
		int VBOID = GL15.glGenBuffers();
		VBOS.add(VBOID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VBOID);
			IntBuffer buffer = storeDataIntBuffer(indices);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
	}
	
	private IntBuffer storeDataIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
		
	}

}
