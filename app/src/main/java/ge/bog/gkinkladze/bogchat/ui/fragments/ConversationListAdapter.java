package ge.bog.gkinkladze.bogchat.ui.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;

import ge.bog.gkinkladze.bogchat.R;
import ge.bog.gkinkladze.bogchat.model.Conversation;
import ge.bog.gkinkladze.bogchat.transport.AppDataProvider;

public class ConversationListAdapter extends BaseAdapter {

    private AppDataProvider appDataProvider;
    private Context context;

    ConversationListAdapter(AppDataProvider appDataProvider, Context context){
        this.appDataProvider = appDataProvider;
        this.context = context;
    }

    @Override
    public int getCount() {
        return appDataProvider.getConversationList() != null ?
                appDataProvider.getConversationList().size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return appDataProvider.getConversationList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = li.inflate(R.layout.conversation_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.conversation_contact_avatar);
            viewHolder.messageText = (TextView)view.findViewById(R.id.conversation_contact_text);
            viewHolder.contactName = (TextView)view.findViewById(R.id.conversation_contact_name);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Conversation curr = appDataProvider.getConversationList().get(position);
        try {
            context.openFileInput(curr.getUserId());
            String fileName = new File(context.getFilesDir(), curr.getUserId()).getAbsolutePath();
            viewHolder.imageView.setImageBitmap(BitmapFactory.decodeFile(fileName));

        } catch (FileNotFoundException e) {
            viewHolder.imageView.setImageResource(R.drawable.ic_person_outline_white_36dp);
        }

        viewHolder.messageText.setText(buildText(curr));
        viewHolder.contactName.setText(appDataProvider.getContactById(curr.getUserId()).getName());

        if (curr.getHaveRead() == 0){
            viewHolder.messageText.setTypeface(null, Typeface.BOLD);
        }
        return view;
    }

    private String buildText(Conversation conversation){
        String result = "";
        if (conversation.getSend() == 1)
            result += context.getString(R.string.your_message_prefix);
        return result + conversation.getMessage();
    }

    private static class ViewHolder {
        TextView contactName;
        TextView messageText;
        ImageView imageView;
    }
}
