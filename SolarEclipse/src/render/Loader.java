package render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Loader {
	
	private ArrayList<Integer> VAOS = new ArrayList<Integer>();
	private ArrayList<Integer> VBOS = new ArrayList<Integer>();

	public RawModel loadToVAO(float[] positions, int[] indices) {
		int VAOID = createVAO();
		bindIndicesBuffer(indices);
		storeDataAttributeList(0, positions);
		unBindVAO();
		
		return new RawModel(VAOID, indices.length);
		
	}
	
	public void clear() {
		
		for(int VAO: VAOS)
			GL30.glDeleteVertexArrays(VAO);
		
		for(int VBO: VBOS)
			GL15.glDeleteBuffers(VBO);
	
		
	}
	private int createVAO() {
		int VAOID = GL30.glGenVertexArrays();
		VAOS.add(VAOID);
		GL30.glBindVertexArray(VAOID);
		
		return VAOID;
	}
	
	private void storeDataAttributeList(int attributeNumber, float[] data) {
		int VBOID = GL15.glGenBuffers();
		VBOS.add(VBOID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOID);
			FloatBuffer buffer = storeDataFloatBuffer(data);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);
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
