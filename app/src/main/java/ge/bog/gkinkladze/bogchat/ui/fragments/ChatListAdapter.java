package ge.bog.gkinkladze.bogchat.ui.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ge.bog.gkinkladze.bogchat.R;
import ge.bog.gkinkladze.bogchat.model.Contact;
import ge.bog.gkinkladze.bogchat.model.Message;
import ge.bog.gkinkladze.bogchat.transport.AppDataProvider;

public class ChatListAdapter extends BaseAdapter {

    private Context context;
    private List<Message> messageList;

    public ChatListAdapter(Context context){
        this.context = context;
    }

    public void setMessageList(List<Message> messageList){
        this.messageList = messageList;
    }

    public void addMessage(Message message){
        messageList.add(message);
    }

    @Override
    public int getCount() {
        return messageList != null ? messageList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        Message curr = messageList.get(position);

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            viewHolder = new ViewHolder();
            if (curr.getSent() == 1){
                view = li.inflate(R.layout.sent_message_item, parent, false);
                viewHolder.textView = (TextView)view.findViewById(R.id.sent_message);
            }else {
                view = li.inflate(R.layout.received_message_item, parent, false);
                viewHolder.textView = (TextView)view.findViewById(R.id.received_message);
                viewHolder.imageView = (CircleImageView) view.findViewById(R.id.chat_contact_avatar);
            }

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (curr.getSent() == 0){
            Contact contact = ((AppDataProvider)context.getApplicationContext()).getCurrentContact();
            if (contact.getImage() != null){
                viewHolder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(contact.getImage(),
                        0, contact.getImage().length));
            }
        }

        viewHolder.textView.setText(curr.getMessage());
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    private static class ViewHolder {
        TextView textView;
        CircleImageView imageView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return messageList.get(position).getSent();
    }
}
