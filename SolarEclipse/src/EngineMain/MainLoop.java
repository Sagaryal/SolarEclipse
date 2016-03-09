package EngineMain;

import static org.lwjgl.util.glu.GLU.gluPerspective;
import models.Camera;
import models.Model;
import models.ModelLoader;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import MathEngine.Maths;
import render.DisplayManager;
import render.Loader;
import render.RawModel;
import render.RenderModel;
import shaders.Shader;

public class MainLoop {

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		
		Shader shaderSun = new Shader("src/shaders/vertexShaderSun.txt", 
				"src/shaders/fragmentShaderSun.txt");
		Shader shaderEarth = new Shader("src/shaders/vertexShaderEarth.txt", 
				"src/shaders/fragmentShaderEarth.txt");
		Shader shaderMoon = new Shader("src/shaders/vertexShaderMoon.txt", 
				"src/shaders/fragmentShaderMoon.txt");
		
		float vertices[] = {
			     0.5f,  0.5f, 0.0f,  // Top Right
			     0.5f, -0.5f, 0.0f,  // Bottom Right
			    -0.5f, -0.5f, 0.0f,  // Bottom Left
			    -0.5f,  0.5f, 0.0f   // Top Left 
		};
		int indices[] = {  // Note that we start from 0!
			    0, 1, 3,   // First Triangle
			    1, 2, 3    // Second Triangle
		}; 
		
		//Loader loader = new Loader();
		//ModelLoader modelLoader = new ModelLoader("res/tsphere.obj");
		//ModelLoader next = new ModelLoader("res/cube.obj");
		
		//RawModel model = loader.loadToVAO(vertices, indices);

//		RawModel sphere = modelLoader.getModel();
	//	RawModel cube = next.getModel();
		
		Model sun = new Model("res/sun.obj", shaderSun);
		Model earth = new Model("res/earth.obj", shaderEarth);
		Model moon = new Model("res/moon.obj", shaderMoon);
		
		//RenderModel render = new RenderModel(shader1);
				
		float x = 0.0f;
		float y = 0.0f;
		float z = 0.0f;
		
		Camera camera = new Camera(new Vector3f(0,0,5), 0, 0);
		
		float angle = 0.0f;
		while(!Display.isCloseRequested()) {
			init();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				shaderSun.cleanShader();
				shaderEarth.cleanShader();
				shaderMoon.cleanShader();
				
				sun.getModelLoader().getLoader().clear();
				earth.getModelLoader().getLoader().clear();
				moon.getModelLoader().getLoader().clear();
				DisplayManager.closeDisplay();
				System.exit(0);
			}
			
			Matrix4f transformSun = new Matrix4f();
			transformSun.setIdentity();
			
			Matrix4f transformEarth = new Matrix4f();
			transformEarth.setIdentity();
			
			Matrix4f transformMoon = new Matrix4f();
			transformMoon.setIdentity();
			
			//Matrix4f transform = Maths.createTransMatrix(new Vector3f(-1,0,0),
				//	new Vector3f(0,0,0), new Vector3f(-0.3f, 0.5f, 1));
			
			//transform.translate(new Vector3f(x, y, z));
			//transform.scale(new Vector3f(-0.2f, -0.2f, -0.2f));
			//transform.rotate(angle, new Vector3f(1,0,0));
			//transform.rotate(angle, new Vector3f(0,1,0));
			transformSun.translate(new Vector3f(-0.9f, y, z));
			transformEarth.translate(new Vector3f(1.2f, y, z));
			transformMoon.translate(new Vector3f(0.87f, y, z));
			//transformCube.rotate(angle, new Vector3f(1,0,0));
			//transformCube.rotate(angle, new Vector3f(0,1,0));
			transformSun.scale(new Vector3f(-0.55f, 1, 1));
			transformEarth.scale(new Vector3f(-0.55f, 1, 1));
			transformMoon.scale(new Vector3f(-0.55f, 1, 1));
			
			transformSun.rotate(angle, new Vector3f(0,1,0));
			transformEarth.rotate(angle, new Vector3f(0,1,0));
			transformMoon.rotate(angle, new Vector3f(0,1,0));
			
			angle += 0.01f;
			camera.moveCamera();
			//render.init();
			
			
			//gluPerspective(30f, (float) (Display.getWidth() / Display.getHeight()), 0.3f, 100f);
			//System.out.println(Display.getWidth() + " " + Display.getHeight());
			
			//shader1.useShader();
			//shader1.loadViewMatrix(camera);
			
			//cube.transform(shader, transformCube);
			//cube.render();
			
			//shader.useShader();
			sun.init(camera);
			sun.transform(transformSun);
			sun.render();
			//shader.loadViewMatrix(camera);
			earth.init(camera);
			earth.transform(transformEarth);
			earth.render();
			
			moon.init(camera);
			moon.transform(transformMoon);
			moon.render();
			
			//shader.loadViewMatrix(camera);
			//render.render(sphere);
			DisplayManager.updateDisplay();
			
		}
		shaderSun.cleanShader();
		shaderEarth.cleanShader();
		shaderMoon.cleanShader();
		
		//loader.clear();
		sun.getModelLoader().getLoader().clear();	
		earth.getModelLoader().getLoader().clear();
		moon.getModelLoader().getLoader().clear();
		
		//modelLoader.getLoader().clear();	
		//next.getLoader().clear();
		DisplayManager.closeDisplay();

	}
	
	public static void init() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}

}
