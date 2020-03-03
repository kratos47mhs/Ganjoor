package com.kratos47mhs.ganjoor.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.snackbar.Snackbar;
import com.kratos47mhs.ganjoor.R;
import com.kratos47mhs.ganjoor.adapter.GanjoorAdaptor;
import com.kratos47mhs.ganjoor.fragment.MainFragment;
import com.kratos47mhs.ganjoor.inner.Formatter;
import com.kratos47mhs.ganjoor.model.Drawer;

import java.util.Objects;


public class GanjorActivity extends AppCompatActivity {
    public View m;
    public Handler handler = new Handler();
    public Runnable runnable = () -> exitStatus = false;
    private boolean checkForPermission = true;

    public MainFragment fragment;

    public WebView webview;

    public ProgressDialog progressdialog;

    public DrawerLayout drawerLayout;

    public boolean exitStatus = false;
    private Toolbar toolbar;


    public void onClickDrawer(final int type) {
        drawerLayout.closeDrawers();
        try {
            handler.removeCallbacks(runnable);
        } catch (Exception ignored) {
        }
        new Thread() {
            public void run() {
                try {
                    sleep(500);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Intent intent;
                            switch (type) {
                                case 1:
                                    AlertDialog create = new AlertDialog.Builder(GanjorActivity.this).setTitle(R.string.license).setPositiveButton(R.string.ok, null).setIcon(R.mipmap.ic_launcher).setMessage(Html.fromHtml("<div align=\"center\" style=\"\">گنجور کلام ایپ توسعه یافته توسط <strong>حیدر آرت</strong><br><br>ما را دنبال کنید<br><li><ModelViewHolder href=\"http://www.kratos47mhs.tk\">www.kratos47mhs.tk</ModelViewHolder></li><li><ModelViewHolder href=\"http://www.kratos47mhs.com\">www.kratos47mhs.com</ModelViewHolder></li></div>")).create();
                                    create.show();
                                    ((TextView) Objects.requireNonNull(create.findViewById(R.id.content_holder))).setMovementMethod(LinkMovementMethod.getInstance());
                                    return;
                                case 2:
                                    intent = new Intent(getApplicationContext(), com.kratos47mhs.ganjoor.activity.MainActivity.class);
                                    break;
                                case 4:
                                    intent = new Intent(getApplicationContext(), com.kratos47mhs.ganjoor.activity.ContactActivity.class);
                                    break;
                                case 5:
                                    handler();
                                    return;
                                case 7:
                                    fragment();
                                    return;
                                case 8:
                                    webview = (WebView) findViewById(R.id.webView);
                                    webview.getSettings().setJavaScriptEnabled(true);
                                    webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
                                    webview.loadUrl("file:///android_asset/privacy.html");
                                    webview.setWebViewClient(new WebViewClient());
                                    webview.setWebChromeClient(new WebChromeClient() {
                                        public void onProgressChanged(WebView webView, int type) {
                                            super.onProgressChanged(webView, type);
                                            if (type > 0) {
                                                onNavigationItemSelected("صبر کنید۔۔!!");
                                            }
                                            if (type >= 100) {
                                                k();
                                            }
                                        }
                                    });
                                    return;
                                case 10:
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?toolbar=3AgoJdkf_Vg")));
                                    return;
                                default:
                                    return;
                            }
                            startActivity(intent);
                        }
                    });
                    interrupt();
                } catch (Exception ignored) {
                }
            }
        }.start();
    }

    private void setupDrawer() {
        ((TextView) findViewById(R.id.drawer_date)).setText(Formatter.formatDate());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        m = findViewById(R.id.drawer_holder);
        ListView listView = (ListView) findViewById(R.id.drawer_list);
        findViewById(R.id.nav_btn).setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
        findViewById(R.id.settings_btn).setOnClickListener(view -> onClickDrawer(Drawer.TYPE_SETTINGS));
        listView.setAdapter(new GanjoorAdaptor(getApplicationContext(), this::onClickDrawer));
    }


    public void handler() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=6022272342409292442")));
    }


    public void runnable() {
        startActivity(new Intent(getApplicationContext(), com.kratos47mhs.ganjoor.activity.MainActivity.class));
    }


    public void checkForPermission() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"kratos47mhs@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "گنجور برای اندروید - گزارش اشکال");
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }


    public void fragment() {
        String stringbuilder = "http://play.google.com/store/apps/details?id=" +
                getPackageName();
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringbuilder)));
    }


    public void webview() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", "دایرةالمعارف شعر زبان و ادبیات فارسی");
        intent.putExtra("android.intent.extra.SUBJECT", "آثار سخنسرایان پارسی‌ گو");
        startActivity(Intent.createChooser(intent, "اشتراک از طریق"));
    }


    public void progressdialog() {
        AlertDialog create = new AlertDialog.Builder(this).setTitle(R.string.license).setPositiveButton(R.string.ok, null).setIcon(R.mipmap.ic_launcher).setMessage(Html.fromHtml("<div align=\"center\" style=\"\">گنجور کلام ایپ توسعه یافته توسط <strong>حیدر آرت</strong><br><br>ما را دنبال کنید<br><li><ModelViewHolder href=\"http://www.kratos47mhs.tk\">www.kratos47mhs.tk</ModelViewHolder></li><li><ModelViewHolder href=\"http://www.haideron.com\">www.haideron.com</ModelViewHolder></li></div>")).create();
        create.show();
        ((TextView) Objects.requireNonNull(create.findViewById(R.id.content_holder))).setMovementMethod(LinkMovementMethod.getInstance());
    }


    public void drawerLayout() {
        AlertDialog create = new AlertDialog.Builder(this).setTitle(R.string.credit).setPositiveButton(R.string.ok, null).setIcon(R.mipmap.ic_launcher).setMessage(Html.fromHtml("<div align=\"center\" style=\"direction:rtl;\">نسبت دادن<strong>گنجور ویب سایت</strong><br><br><li><ModelViewHolder href=\"http://ganjoor.net\">www.ganjoor.net</ModelViewHolder></li></div>")).create();
        create.show();
        ((TextView) Objects.requireNonNull(create.findViewById(R.id.content_holder))).setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void onNavigationItemSelected(final String str) {
        runOnUiThread(() -> {
            if (progressdialog == null || !progressdialog.isShowing()) {
                progressdialog = ProgressDialog.show(GanjorActivity.this, "صفحه در حال بارگذاری است", str);
            }
        });
    }

    public void k() {
        runOnUiThread(() -> {
            try {
                if (progressdialog.isShowing()) {
                    progressdialog.dismiss();
                }
            } catch (Throwable ignored) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(drawerLayout)) {
            drawerLayout.closeDrawers();
            return;
        }

        if (fragment.selectionState) {
            fragment.toggleSelection(false);
            return;
        }

        if (exitStatus) {
            finish();
        } else {
            exitStatus = true;

            Snackbar.make(fragment.fab != null ? fragment.fab : toolbar, R.string.exit_message, Snackbar.LENGTH_LONG).show();

            handler.postDelayed(runnable, 3500);
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganjoor);

        fragment = new MainFragment();
//        fragment.onNavigationItemSelected(getString(R.string.int_ad));

//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                fragment.onNavigationItemSelected(onNavigationItemSelected);
//                fragment.onNavigationItemSelected((com.google.android.gms.ads.onNavigationItemSelected) new com.google.android.gms.ads.onNavigationItemSelected() {
//                    public void onNavigationItemSelected() {
//                        fragment.b();
//                    }
//                });
//            }
//        }, 60000);
        webview = (WebView) findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.loadUrl("file:///android_asset/main.html");
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (str == null || !str.startsWith("http://")) {
                    return false;
                }
                webView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int type) {
                super.onProgressChanged(webView, type);
                if (type > 0) {
                    onNavigationItemSelected("صبر کن۔۔!!");
                }
                if (type >= 100) {
                    k();
                }
            }
        });
        setupDrawer();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo((int) R.menu.menu_ganjoor);

        toolbar.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId != R.id.otherapps) {
                switch (itemId) {
                    case R.id.item_credit /*2131165293*/:
                        drawerLayout();
                        break;
                    case R.id.item_license /*2131165294*/:
                        progressdialog();
                        break;
                    case R.id.item_rate_app /*2131165295*/:
                        fragment();
                        break;
                    case R.id.item_submit_bug /*2131165296*/:
                        checkForPermission();
                        break;
                    default:
                        switch (itemId) {
                            case R.id.mSave /*2131165307*/:
                                runnable();
                                break;
                            case R.id.mShare /*2131165308*/:
                                webview();
                                break;
                        }
                }
            } else {
                handler();
            }
            return true;
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ganjoor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId != R.id.otherapps) {
            switch (itemId) {
                case R.id.item_credit /*2131165293*/:
                    drawerLayout();
                    break;
                case R.id.item_license /*2131165294*/:
                    progressdialog();
                    break;
                case R.id.item_rate_app /*2131165295*/:
                    fragment();
                    break;
                case R.id.item_submit_bug /*2131165296*/:
                    checkForPermission();
                    break;
                default:
                    switch (itemId) {
                        case R.id.mSave /*2131165307*/:
                            runnable();
                            break;
                        case R.id.mShare /*2131165308*/:
                            webview();
                            break;
                    }
            }
        } else {
            handler();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public boolean isCheckForPermission() {
        return checkForPermission;
    }

    public void setCheckForPermission(boolean checkForPermission) {
        this.checkForPermission = checkForPermission;
    }
}
