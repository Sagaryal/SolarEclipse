package shaders;

import items.Light;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import models.Camera;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Shader {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private int locationTransMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	private int locationLightPosition;
	private int locationLightColor;
	//private int locationShineDamper;
	//private int locationReflectivity;
	private int locationShadow;
	
	private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public Shader(String vertexShader, String fragmentShader) {
		vertexShaderID = loadShader(vertexShader, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		locationTransMatrix = getUniformLocation("transformation");
		locationProjectionMatrix = getUniformLocation("projection");
		locationViewMatrix = getUniformLocation("view");
		locationLightPosition = getUniformLocation("lightPosition");
		locationLightColor = getUniformLocation("lightColor");
		//locationShineDamper = getUniformLocation("shineDamper");
		//locationReflectivity = getUniformLocation("reflectivity");
		locationShadow = getUniformLocation("moonPos");
	}
	
	public void useShader() {
		GL20.glUseProgram(programID);
	}
	
	public void removeShader() {
		GL20.glUseProgram(0);
	}
	
	public void cleanShader() {
		removeShader();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	private void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);

	}
	
	public void loadLight(Light light) {
		loadVector(locationLightPosition, light.getPosition());
		loadVector(locationLightColor, light.getColor());
	}
	
	public void loadShadow(Vector3f vector) {
		loadVector(locationShadow, vector);
		
	}
	
	private void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	
	public void loadShineVariables(float damper, float reflectivity) {
		//loadFloat(locationShineDamper, damper);
		//loadFloat(locationReflectivity, reflectivity);
	}
	
	/*
	public void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	*/
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		loadMatrix(locationTransMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		loadMatrix(locationProjectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = camera.getViewMatrix();
		loadMatrix(locationViewMatrix, viewMatrix);
	}
	
	public int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	private int loadShader(String file, int type) {
		StringBuilder shader = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			
			while((line = reader.readLine()) != null) {
				shader.append(line).append("\n");
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.out.println("error loading shader");
			System.exit(1);
		}
		
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shader);
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.out.println("SHader couldnot be compliled");
			System.exit(1);
		}
		
		return shaderID;
	}
	
	private void bindAttributes() {
		GL20.glBindAttribLocation(programID, 0, "position");
		GL20.glBindAttribLocation(programID, 1, "textureCoords");
		GL20.glBindAttribLocation(programID, 2, "normal");
	}
	
}
