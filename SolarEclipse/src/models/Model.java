package models;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import MathEngine.Maths;
import render.RawModel;
import render.RenderModel;
import shaders.Shader;

public class Model {

	private ModelLoader modelLoader;
	private RawModel rawModel;
	
	private RenderModel render; 
	private Shader shader = null;
	
	public Model(String path, Shader shader) {
		modelLoader = new ModelLoader(path);
		this.shader = shader;
		rawModel = modelLoader.getModel();
		render = new RenderModel(shader);
	}
	
	public RawModel getRawModel() {
		return rawModel;
	}
	
	public ModelLoader getModelLoader() {
		return modelLoader;
	}
	
	public void init(Camera camera) {
		
		shader.useShader();
		shader.loadViewMatrix(camera);
	}
	
	public void transform(Matrix4f transformation) {
		//Matrix4f transformation = Maths.createTransMatrix(translate, rotate, scale);
		shader.loadTransformationMatrix(transformation);
	}
	
	public void render() {
		
		RawModel rawModel = this.getRawModel();
		
		GL30.glBindVertexArray(rawModel.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
			GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
}
