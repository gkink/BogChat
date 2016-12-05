package ge.bog.gkinkladze.bogchat.model;

public class Message {
	private int mSent;
	private String mToId;
	private String mMessage;
	private String mTime;
	private int mHaveRead = 1;

	public Message(int sent, String toId, String message){
		mMessage = message;
		mSent = sent;
		mToId = toId;
	}

	public Message(String message, int sent){
		mMessage = message;
		mSent = sent;
	}

	public int getSent() {
		return mSent;
	}

	public String getMessage() {
		return mMessage;
	}

	public String getTime() {
		return mTime;
	}

	public String getToId() {
		return mToId;
	}

	public void setSent(int mSent) {
		this.mSent = mSent;
	}

	public void setMessage(String msg) {
		this.mMessage = msg;
	}

	public void setTime(String mTime) {
		this.mTime = mTime;
	}

	public void setToId(String mToId) {
		this.mToId = mToId;
	}

	public int getHaveRead() {
		return mHaveRead;
	}

	public void setHaveRead(int mHaveRead) {
		this.mHaveRead = mHaveRead;
	}
}
