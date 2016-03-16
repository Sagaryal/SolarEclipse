package models;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position;
	private float pitch;
	private float yaw;
	private float roll;
	private float speed = 0.06f;
	private float multiplier = 0.2f;

	
	public Camera(Vector3f position, float pitch, float yaw) {
		this.position = position;
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	public void moveCamera() {
		//System.out.println("camerea move");
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			//System.out.println("W");
			position.z -= speed * multiplier;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			//System.out.println("D");
			position.x += speed * multiplier;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			//System.out.println("S");
			//System.out.println("S: " + position.z);
			position.z += speed * multiplier;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			//System.out.println("A");
			position.x -= speed * multiplier;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			pitch -= 0.3f;
			System.out.println("pitch: " + pitch);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			pitch += 0.3f;
			System.out.println("pitch: " + pitch);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			//System.out.println("A");
			yaw += 0.3f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			//System.out.println("A");
			yaw -= 0.3f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
			//System.out.println("S");
			position.y += speed * multiplier;
			System.out.println("y: " + position.y);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
			//System.out.println("S");
			position.y -= speed * multiplier;
			System.out.println("y: " + position.y);
		}
	}
	
	public Matrix4f getViewMatrix() {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		
		Matrix4f.rotate((float) Math.toRadians(this.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(this.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix);
		//System.out.println("viewmatrix: " + this.getPitch() + " " + this.getYaw());
		Vector3f cameraPos = this.getPosition();
		Vector3f negCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negCameraPos, viewMatrix, viewMatrix);
		
		return viewMatrix;
		
	}

	public Vector3f getPosition() {
		return position;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}

	
}
