package models;

import items.Light;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import MathEngine.Maths;
import render.RenderModel;
import shaders.Shader;

public class Model {

	private ModelLoader modelLoader;
	private RawModel rawModel;
	
	private TexturedModel texturedModel;
	private RenderModel render; 
	private Shader shader = null;
	private Light light = null;
	private Vector3f moonPos;
	
	public Model(String path, Shader shader) {
		modelLoader = new ModelLoader(path);
		this.shader = shader;
		rawModel = modelLoader.getModel();
		render = new RenderModel(shader);
		//light = new Light(new Vector3f(-0.9f,0.0f,0.0f), new Vector3f(1,1,1));
		
		
	}
	
	public void loadLight(Light light) {
		this.light = light;
		shader.loadLight(light);
	}
	
	public void loadShadow(Vector3f vector) {
		this.moonPos = vector;
		shader.loadShadow(this.moonPos);
	}
	
	public TexturedModel loadTexture(String file) {
		texturedModel = new TexturedModel(modelLoader.getLoader().loadTexture(file));
		return texturedModel;
	}
	
	public RawModel getRawModel() {
		return rawModel;
	}
	
	public ModelLoader getModelLoader() {
		return modelLoader;
	}
	
	public void init(Camera camera) {
		
		shader.useShader();
		//shader.loadLight(light);
		shader.loadViewMatrix(camera);
	}
	
	public void transform(Matrix4f transformation) {
		//Matrix4f transformation = Maths.createTransMatrix(translate, rotate, scale);
		shader.loadTransformationMatrix(transformation);
	}
	
	public void render() {
		
		//RawModel rawModel = this.getRawModel();
		RawModel rawModel = this.getRawModel();
		GL30.glBindVertexArray(rawModel.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			//shader.loadShineVariables(texturedModel.getShineDamper(), texturedModel.getReflectivity());
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getID());
			//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
			GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
}
