package com.sampson.android.nodejsmanual.ui.manual;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.sampson.android.nodejsmanual.R;
import com.sampson.android.nodejsmanual.core.persistence.AppPreference;
import com.sampson.android.nodejsmanual.ui.BaseFragment;
import com.sampson.android.nodejsmanual.utils.Tip;
import com.sampson.android.nodejsmanual.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by chengyang on 2017/8/4.
 */

public class NodeJsManualFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.fl_ding)
    FrameLayout flDing;
    @Bind(R.id.fl_home)
    FrameLayout flHome;
    @Bind(R.id.fl_back)
    FrameLayout flBack;
    @Bind(R.id.fl_forward)
    FrameLayout flForward;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;

    private String url;
    private ProgressDialog dialog;
    private String fristPath = "";

    private static NodeJsManualFragment instance;

    public static NodeJsManualFragment getInstance() {
        if (instance == null) {
            instance = new NodeJsManualFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        url = "http://nodejs.cn/api/";

        flDing.setOnClickListener(this);
        flHome.setOnClickListener(this);
        flBack.setOnClickListener(this);
        flForward.setOnClickListener(this);

        dialog = ProgressDialog.show(getContext(), null, "加载中...");
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    dialog.dismiss();
                }

                return false;
            }
        });

        webView.canGoBack();
        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        //加入js调用
        webView.addJavascriptInterface(new LoadListener(), "HTMLOUT");

        webSettings.setBlockNetworkImage(false);

        webSettings.setSupportZoom(true);

        webSettings.setDatabaseEnabled(true);
        String dir = getContext().getApplicationContext().getDir("database", MODE_PRIVATE).getPath();
        webSettings.setGeolocationEnabled(true);
        webSettings.setDatabasePath(dir);
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);

        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();

                view.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementById('description').content);");

                webView.loadUrl("javascript:playPause()");
                if (null != fristPath && fristPath.equals("")) {
                    fristPath = webView.getUrl();
                }
                AppPreference.setLastReadPage(url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.show();
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota,
                                                long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
                super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota,
                        quotaUpdater);
                quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

        if (!TextUtils.isEmpty(AppPreference.getLastReadPage())) {
            String url = AppPreference.getLastReadPage();
            webView.loadUrl(url);
        } else {
            webView.loadUrl(url);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_back:
                webView.goBack();
                break;
            case R.id.fl_ding:
                String newRrl = webView.getUrl();
                String title = webView.getTitle();

                ToastUtils.show(title + "==>" + newRrl);
                break;
            case R.id.fl_forward:
                webView.goForward();
                break;
            case R.id.fl_home:
                url = "http://nodejs.cn/api/";
                webView.loadUrl(url);
                break;
        }
    }

    class LoadListener {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            if (!TextUtils.isEmpty(html)) {
                Tip.i("===============>>>>>>>>>>>>>>>>>>>>description = " + html);
            } else {
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
