package render;

import models.Model;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import shaders.Shader;

public class RenderModel {
	
	private Matrix4f projectionMatrix;
	
	private final float F0V = 45;
	private final float NEAR_PLANE = 0.1f;
	private final float FAR_PLANE = 1000.0f;
	
	public RenderModel(Shader shader) {
		projectionMatrix();
		shader.useShader();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.removeShader();
	}

	public void init() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}
	
	public void render(Model model) {
		RawModel rawModel = model.getRawModel();
		
		GL30.glBindVertexArray(rawModel.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
			GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	public void render(RawModel rawModel) {
		
		GL30.glBindVertexArray(rawModel.getVAOID());
			GL20.glEnableVertexAttribArray(0);
			//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
			GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	private void projectionMatrix() {
		float aspectRatio = Display.getWidth() / Display.getHeight();
		float yScale = (float) ((1f / Math.tan(Math.toRadians(F0V / 2f))) * aspectRatio);
		float xScale = yScale / aspectRatio;
		float frustumLength = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = xScale;
		projectionMatrix.m11 = yScale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength);
		projectionMatrix.m33 = 0;
		
		
	}
}
