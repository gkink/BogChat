package ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_image;

import android.content.Context;

import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import ge.bog.gkinkladze.bogchat.model.Contact;

public class UrlContactImageDownloaderTask extends ContactImageDownloaderTask {

    public UrlContactImageDownloaderTask(Contact contact, Context context) {
        super(contact, context);
    }

    @Override
    protected byte[] doInBackground(Void... voids) {
        InputStream is = null;

        try {
            URL url = new URL(mContact.getAvatarImageURL());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();

            is = conn.getInputStream();
            byte[] image = IOUtils.toByteArray(is);
            saveAvatarImg(image, mContact.getId());

            return image;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void saveAvatarImg(byte[] imgData, String contactId){
        FileOutputStream file;
        try {
            file = mContext.openFileOutput(contactId, Context.MODE_PRIVATE);
            file.write(imgData);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
