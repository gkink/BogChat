package ge.bog.gkinkladze.bogchat.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Conversation implements Comparable{

    private String mUserId;
    private String mTime;
    private int mHaveRead;
    private String mMessage;
    private int mSend;
    private int mLastMessageId;

    public Conversation(String userId, String time, int haveRead, int send, String message){
        mHaveRead = haveRead;
        mUserId = userId;
        mTime = time;
        mMessage = message;
        mSend = send;
    }

    public Conversation(String userId, int haveRead){
        mHaveRead = haveRead;
        mUserId = userId;
    }

    public int getHaveRead() {
        return mHaveRead;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setHaveRead(int mHaveRead) {
        this.mHaveRead = mHaveRead;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public int setSend() {
        return mSend;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getTime() {
        return mTime;
    }

    public int getSend() {
        return mSend;
    }

    public int getLastMessageId() {
        return mLastMessageId;
    }

    public void setLastMessageId(int mLastMessageId) {
        this.mLastMessageId = mLastMessageId;
    }

    @Override
    public int compareTo(Object o) {
        Conversation conversation = (Conversation)o;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        try {
            Date date1 = dateFormat.parse(mTime);
            Date date2 = dateFormat.parse(conversation.getTime());
            return date2.compareTo(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
