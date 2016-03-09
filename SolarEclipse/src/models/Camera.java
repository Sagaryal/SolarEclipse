package models;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0,0,0);
	private float pitch;
	private float yaw;
	private float roll;
	private float speed = 0.06f;
	
	public void moveCamera() {
		System.out.println("camerea move");
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			System.out.println("W");
			position.z -= speed;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			System.out.println("D");
			position.x += speed;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			System.out.println("S");
			System.out.println("S: " + position.z);
			position.z += speed;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			System.out.println("A");
			position.x -= speed;
		}
	}
	
	public Matrix4f getViewMatrix() {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		
		Matrix4f.rotate((float) Math.toRadians(this.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(this.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix);
		
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
