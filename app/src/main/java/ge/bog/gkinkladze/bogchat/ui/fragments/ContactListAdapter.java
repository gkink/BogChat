package ge.bog.gkinkladze.bogchat.ui.fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ge.bog.gkinkladze.bogchat.R;
import ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_image.ContactImageDownloaderTask;
import ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_image.FileContactImageDownloaderTask;
import ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_image.ImageDownloadedListener;
import ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_image.UrlContactImageDownloaderTask;
import ge.bog.gkinkladze.bogchat.model.Contact;

public class ContactListAdapter extends BaseAdapter{

    private Context context;
    private List<Contact> contacts;

    public void setContacts(List<Contact> contacts){
        this.contacts = contacts;
    }

    ContactListAdapter(Context context){
        this.context = context;
        contacts = null;
    }

    @Override
    public int getCount() {
        return  contacts != null ? contacts.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private ContactImageDownloaderTask buildChainOfResponsibility(ViewHolder viewHolder, Contact curr){

        ContactImageDownloaderTask fileTask = new FileContactImageDownloaderTask(curr, context);
        ContactImageDownloaderTask urlTask = new UrlContactImageDownloaderTask(curr, context);

        fileTask.setNextHandler(urlTask);
        urlTask.setNextHandler(null);

        fileTask.setImageDownloadedListener(viewHolder);
        urlTask.setImageDownloadedListener(viewHolder);
        return fileTask;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = li.inflate(R.layout.contact_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.contact_avatar);
            viewHolder.textView = (TextView)view.findViewById(R.id.contact_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            if (viewHolder.imageTask != null){
                Log.d("cla", "task is not null");
                viewHolder.imageTask.cancel(true);
            }
        }

        Contact curr = contacts.get(position);

        Log.d("cla", "getView");
        if (curr.getImage() == null){
            viewHolder.imageTask = buildChainOfResponsibility(viewHolder, curr);
            viewHolder.imageView.setImageResource(R.drawable.ic_person_outline_black_36dp);
            viewHolder.imageTask.execute();
        }else {
            viewHolder.onImageDownloaded(curr.getImage());
        }

        viewHolder.textView.setText(curr.getName());
        return view;
    }

    static class ViewHolder implements ImageDownloadedListener{
        TextView textView;
        ImageView imageView;
        ContactImageDownloaderTask imageTask;

        ViewHolder(){
            imageTask = null;
        }

        @Override
        public void onImageDownloaded(byte[] image){
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        }

        @Override
        public void onNextTaskExecuted(ContactImageDownloaderTask task) {
            imageTask = task;
        }
    }
}
