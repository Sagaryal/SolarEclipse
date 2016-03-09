package render;

public class RawModel {
	
	private int VAOID;
	private int vertexCount;
	
	private Loader loader;
	
	public RawModel(int VAOID, int vertexCount) {
		this.VAOID = VAOID;
		this.vertexCount = vertexCount;
	}
	public int getVAOID() {
		return VAOID;
	}
	public int getVertexCount() {
		return vertexCount;
	}
	
	

}
