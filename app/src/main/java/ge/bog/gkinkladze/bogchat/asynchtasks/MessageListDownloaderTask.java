package ge.bog.gkinkladze.bogchat.asynchtasks;

import android.content.Context;
import android.os.AsyncTask;


import java.util.List;

import ge.bog.gkinkladze.bogchat.db.MyDBHelper;
import ge.bog.gkinkladze.bogchat.model.Message;

public class MessageListDownloaderTask extends AsyncTask<String, Void, List<Message>> {

    private Context mContext;
    private MessageListDownloadedListener messageListDownloadedListener;

    public MessageListDownloaderTask(Context context, MessageListDownloadedListener messageListDownloadedListener) {
        mContext = context;
        this.messageListDownloadedListener = messageListDownloadedListener;
    }

    @Override
    protected List<Message> doInBackground(String... params) {
        MyDBHelper db = MyDBHelper.getInstance(mContext);
        return db.selectMessages(params[0]);
    }

    @Override
    protected void onPostExecute(List<Message> messages) {
        super.onPostExecute(messages);
        messageListDownloadedListener.onMessageListDownloaded(messages);
    }

    public interface MessageListDownloadedListener {
        void onMessageListDownloaded(List<Message> list);
    }
}
