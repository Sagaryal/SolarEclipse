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
		
		Shader shader = new Shader("src/shaders/vertexShader.txt", "src/shaders/fragmentShader.txt");
		
		//shader1 not used
		Shader shader1 = new Shader("src/shaders/vertexShader1.txt", "src/shaders/fragmentShader1.txt");
		
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
		
		Model sphere = new Model("res/tsphere.obj", shader);
		Model cube = new Model("res/cube.obj", shader1);
		
		//RenderModel render = new RenderModel(shader1);
				
		float x = 0.0f;
		float y = 0.0f;
		float z = 0.0f;
		
		Camera camera = new Camera();
		
		float angle = 0.0f;
		while(!Display.isCloseRequested()) {
			init();
			
			Matrix4f transform = new Matrix4f();
			transform.setIdentity();
			
			Matrix4f transformCube = new Matrix4f();
			transformCube.setIdentity();
			
			//Matrix4f transform = Maths.createTransMatrix(new Vector3f(-1,0,0),
				//	new Vector3f(0,0,0), new Vector3f(-0.3f, 0.5f, 1));
			
			//transform.translate(new Vector3f(x, y, z));
			//transform.scale(new Vector3f(-0.2f, -0.2f, -0.2f));
			transform.rotate(angle, new Vector3f(1,0,0));
			transform.rotate(angle, new Vector3f(0,1,0));

			transformCube.translate(new Vector3f(-2.5f, y, z));
			transformCube.rotate(angle, new Vector3f(1,0,0));
			transformCube.rotate(angle, new Vector3f(0,1,0));
			
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
			sphere.init(camera);
			sphere.transform(transform);
			sphere.render();
			//shader.loadViewMatrix(camera);
			cube.init(camera);
			cube.transform(transformCube);
			cube.render();
			
			//shader.loadViewMatrix(camera);
			//render.render(sphere);
			DisplayManager.updateDisplay();
			
		}
		shader.cleanShader();
		//loader.clear();
		sphere.getModelLoader().getLoader().clear();	
		//cube.getModelLoader().getLoader().clear();
		
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
