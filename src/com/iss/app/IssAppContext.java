
package com.iss.app;

import android.app.Application;

import com.iss.imageloader.cache.disc.naming.Md5FileNameGenerator;
import com.iss.imageloader.core.ImageLoader;
import com.iss.imageloader.core.ImageLoaderConfiguration;
import com.iss.imageloader.core.assist.QueueProcessingType;

public class IssAppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove
                                                                                 // for
                                                                                 // release
                                                                                 // app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
