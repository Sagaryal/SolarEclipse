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

	private Vector3f closeViewPos = new Vector3f(0.87f, 0.0f,0.72f);
	private float closeViewYaw = 50;
	private float closeViewPitch = 0;
	
	private Vector3f persViewPos = new Vector3f(0.0f,4.2f,7f);
	private float persViewYaw = 0;
	private float persViewPitch = 30;
	
	public Camera(Vector3f position, float pitch, float yaw) {
		this.position = position;
		this.pitch = pitch;
		this.yaw = yaw;
	}
	
	public void moveCamera() {
		//System.out.println("camerea move");
		
		if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
			//System.out.println("W");
			this.position = persViewPos;
			this.yaw = persViewYaw;
			this.pitch = persViewPitch;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_C)) {
			//System.out.println("W");
			this.position = closeViewPos;
			this.yaw = closeViewYaw;
			this.pitch = closeViewPitch;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			//System.out.println("W");
			position.z -= speed * multiplier;
			System.out.println("Z: " + position.z);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			//System.out.println("D");
			position.x += speed * multiplier;
			System.out.println("X: " + position.x);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			//System.out.println("S");
			//System.out.println("S: " + position.z);
			position.z += speed * multiplier;
			System.out.println("Z: " + position.z);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			//System.out.println("A");
			position.x -= speed * multiplier;
			System.out.println("X: " + position.x);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			pitch -= 0.3f;
			if(pitch <= -89)
				pitch = -89;
			System.out.println("pitch: " + pitch);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {
			pitch += 0.3f;
			if(pitch >= 89)
				pitch = 89;
			//System.out.println("pitch: " + pitch);
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			yaw += 0.3f;
			System.out.println("yaw: " + yaw);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
			//System.out.println("A");
			yaw -= 0.3f;
			System.out.println("yaw: " + yaw);
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
