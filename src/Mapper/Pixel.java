package Mapper;

public class Pixel {

	private static int PixelCounter = 0;
	private static int GroupCounter = 0;
	private int pixelNumber;
	private String nickName;
	private int grp;
	private int r;
	private int g;
	private int b;
	private int t;
	private int x;
	private int y;

	public Pixel(String nickName, int grp, int r, int g, int b, int t, int x, int y) {

		PixelCounter++;
		this.pixelNumber = PixelCounter;
		this.nickName = nickName;
		this.grp = grp;
		this.r = r;
		this.g = g;
		this.b = b;
		this.t = t;
		this.x = x;
		this.y = y;

	}

	public Pixel(String nickName, int grp, int r, int g, int b, int x, int y) {

		PixelCounter++;
		this.pixelNumber = PixelCounter;
		this.nickName = nickName;
		this.grp = grp;
		this.r = r;
		this.g = g;
		this.b = b;
		this.t = 0;
		this.x = x;
		this.y = y;

	}

	public Pixel(int grp, int r, int g, int b, int t, int x, int y) {

		PixelCounter++;
		this.pixelNumber = PixelCounter;
		this.nickName = "Pixel " + PixelCounter;
		this.grp = grp;
		this.r = r;
		this.g = g;
		this.b = b;
		this.t = t;
		this.x = x;
		this.y = y;

	}

	public Pixel(int r, int g, int b, int t, int x, int y) {

		PixelCounter++;
		this.pixelNumber = PixelCounter;
		this.nickName = "Pixel " + PixelCounter;
		this.grp = -1;
		this.r = r;
		this.g = g;
		this.b = b;
		this.t = t;
		this.x = x;
		this.y = y;

	}

	public Pixel(int r, int g, int b, int x, int y) {

		PixelCounter++;
		this.pixelNumber = PixelCounter;
		this.nickName = "Pixel " + PixelCounter;
		this.grp = -1;
		this.r = r;
		this.g = g;
		this.b = b;
		this.t = 0;
		this.x = x;
		this.y = y;

	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setGrp(int grp) {
		this.grp = grp;
	}

	public void setR(int r) {
		this.r = r;
	}

	public void setG(int g) {
		this.g = g;
	}

	public void setB(int b) {
		this.b = b;
	}

	public void setT(int t) {
		this.t = t;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public static int getPixelCounter() {
		return PixelCounter;
	}
	
	public static void setPixelCounter(int pixelCounter) {
		PixelCounter = pixelCounter;
	}
	
	public static int getGroupCounter() {
		return GroupCounter;
	}

	public int getPixelNumber() {
		return pixelNumber;
	}

	public void setPixelNumber(int pixelNumber) {
		this.pixelNumber = pixelNumber;
	}

	public String getNickName() {
		return nickName;
	}

	public int getGrp() {
		return grp;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public int getT() {
		return t;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}