package com.rinekri.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

public class DirectoryReturner {

    private Context mContext;

    public DirectoryReturner(Context c) {
        mContext = c;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public File returnFolderDirectory(String folder, String filename) {

        File directory = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            directory = innerDirectory(folder, filename);
        } else {
            if (Environment.isExternalStorageEmulated() == true) {
                directory = outerDirectory(folder, filename);
            } else {
                directory = innerDirectory(folder, filename);
            }
        }
        return directory;
    }

    private File innerDirectory(String folder, String filename) {
        File f = new File(mContext.getDir(folder,Context.MODE_PRIVATE), filename);
        f.setReadable(true, false);
        return f;
    }

    private File outerDirectory(String folder, String filename) {
        return new File(mContext.getExternalFilesDir(folder), filename);
    }
}
