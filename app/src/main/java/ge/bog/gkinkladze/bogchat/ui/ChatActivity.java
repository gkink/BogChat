package ge.bog.gkinkladze.bogchat.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import ge.bog.gkinkladze.bogchat.R;
import ge.bog.gkinkladze.bogchat.asynchtasks.MessageListDownloaderTask;
import ge.bog.gkinkladze.bogchat.db.MyDBHelper;
import ge.bog.gkinkladze.bogchat.model.Contact;
import ge.bog.gkinkladze.bogchat.model.Conversation;
import ge.bog.gkinkladze.bogchat.model.Message;
import ge.bog.gkinkladze.bogchat.transport.AppDataProvider;
import ge.bog.gkinkladze.bogchat.ui.fragments.ChatListAdapter;

public class ChatActivity extends AppCompatActivity implements
        MessageListDownloaderTask.MessageListDownloadedListener {

	private AppDataProvider appDataProvider;
	private Contact mContact;
	private EditText mEnterText;
    private ChatListAdapter chatListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_layout);

		this.appDataProvider = (AppDataProvider)getApplicationContext();
		mContact = appDataProvider.getCurrentContact();
		setTitle(mContact.getName());

		ListView mChat = (ListView)findViewById(R.id.chat_list);
		TextView empty = (TextView)findViewById(R.id.empty_list);
		empty.setText(getText(R.string.no_message));
		mChat.setEmptyView(empty);

        chatListAdapter = new ChatListAdapter(this);
		mChat.setAdapter(chatListAdapter);

		mEnterText = (EditText)findViewById(R.id.enter_text);

		MessageListDownloaderTask task = new MessageListDownloaderTask(this, this);
		task.execute(mContact.getId());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onMessageListDownloaded(List<Message> list) {
		System.out.println("messages downloaded");
        chatListAdapter.setMessageList(list);
        chatListAdapter.notifyDataSetChanged();
	}

    private void orderMessage(final Contact contact){
        Handler handler = new Handler();
        Random random = new Random();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = new Message(0, contact.getId(), "The Last Question?");
                ChatActivity.this.onNewMessage(message);
            }
        }, random.nextInt(10000));
    }

	public void sendMessageClicked(View view) {
        if (mEnterText.getText().toString().isEmpty()) return;
        Message message = new Message(1, mContact.getId(), mEnterText.getText().toString());
        mEnterText.setText("");
        onNewMessage(message);
        orderMessage(mContact);
	}

    public void onNewMessage(Message message){
        chatListAdapter.addMessage(message);
        updateConversations(message);
        chatListAdapter.notifyDataSetChanged();
        MyDBHelper.getInstance(this).insertMessage(message);
    }

    private void updateConversations(Message message){
        List<Conversation> list = appDataProvider.getConversationList();
        for (int i = 0; i < list.size(); i++){
            if (message.getToId().equals(list.get(i).getUserId())){
                Conversation c = list.get(i);
                c.setMessage(message.getMessage());
                list.remove(c);
                list.add(0, c);
                return;
            }
        }
        list.add(0, new Conversation(message.getToId(), "", 1, 1, message.getMessage()));
    }
}
