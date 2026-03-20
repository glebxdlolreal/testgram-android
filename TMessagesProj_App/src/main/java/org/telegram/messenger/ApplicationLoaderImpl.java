package org.telegram.messenger;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import org.telegram.ui.Components.UpdateAppAlertDialog;
import org.telegram.ui.Components.UpdateLayout;
import org.telegram.ui.IUpdateLayout;

import java.io.File;

public class ApplicationLoaderImpl extends ApplicationLoader {
    @Override
    protected String onGetApplicationId() {
        return org.telegram.messenger.regular.BuildConfig.APPLICATION_ID;
    }

    @Override
    public boolean isCustomUpdate() {
        return !TextUtils.isEmpty(org.telegram.messenger.BuildConfig.BETA_URL);
    }

    @Override
    public BetaUpdate getUpdate() {
        if (!isCustomUpdate()) return null;
        return BetaUpdaterController.getInstance().getUpdate();
    }

    @Override
    public void checkUpdate(boolean force, Runnable whenDone) {
        if (!isCustomUpdate()) return;
        BetaUpdaterController.getInstance().checkForUpdate(force, whenDone);
    }

    @Override
    protected void startAppCenterInternal(Activity context) {
        checkForUpdatesInternal();
    }

    @Override
    protected void checkForUpdatesInternal() {
        if (!isCustomUpdate()) return;
        BetaUpdaterController.getInstance().checkForUpdate(false, null);
    }

    @Override
    public void downloadUpdate() {
        if (!isCustomUpdate()) return;
        BetaUpdaterController.getInstance().downloadUpdate();
    }

    @Override
    public void cancelDownloadingUpdate() {
        if (!isCustomUpdate()) return;
        BetaUpdaterController.getInstance().cancelDownloadingUpdate();
    }

    @Override
    public boolean isDownloadingUpdate() {
        if (!isCustomUpdate()) return false;
        return BetaUpdaterController.getInstance().isDownloading();
    }

    @Override
    public float getDownloadingUpdateProgress() {
        if (!isCustomUpdate()) return 0;
        return BetaUpdaterController.getInstance().getDownloadingProgress();
    }

    @Override
    public File getDownloadedUpdateFile() {
        if (!isCustomUpdate()) return null;
        return BetaUpdaterController.getInstance().getDownloadedFile();
    }

    @Override
    public IUpdateLayout takeUpdateLayout(Activity activity, ViewGroup sideMenuContainer) {
        if (!isCustomUpdate()) return null;
        return new UpdateLayout(activity, sideMenuContainer);
    }

    @Override
    public boolean showCustomUpdateAppPopup(Context context, BetaUpdate update, int account) {
        try {
            (new UpdateAppAlertDialog(context, update, account)).show();
        } catch (Exception e) {
            FileLog.e(e);
        }
        return true;
    }
}
