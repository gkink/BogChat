package ge.bog.gkinkladze.bogchat.asynchtasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import ge.bog.gkinkladze.bogchat.db.MyDBHelper;
import ge.bog.gkinkladze.bogchat.model.Conversation;

public class ConversationListDownloaderTask extends AsyncTask<Void, Void, List<Conversation>> {

    public interface ConversationListDownloadedListener{
        void onConversationListDownloaded(List<Conversation> list);
    }

    private Context mContext;

    public ConversationListDownloaderTask(Context context){
        mContext = context;
    }

    @Override
    protected List<Conversation> doInBackground(Void... params) {
        MyDBHelper db = MyDBHelper.getInstance(mContext);
        return db.selectConversations();
    }

    @Override
    protected void onPostExecute(List<Conversation> conversations) {
        super.onPostExecute(conversations);
        ((ConversationListDownloadedListener)mContext).onConversationListDownloaded(conversations);
    }
}
