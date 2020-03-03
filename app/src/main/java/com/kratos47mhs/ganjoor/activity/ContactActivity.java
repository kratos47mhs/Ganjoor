package com.kratos47mhs.ganjoor.activity;

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
import android.widget.EditText;
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
import com.kratos47mhs.ganjoor.fragment.template.RecyclerFragment;
import com.kratos47mhs.ganjoor.inner.Formatter;

public class ContactActivity extends AppCompatActivity implements RecyclerFragment.Callbacks {
    public View drawerHolder;
    public Handler handler = new Handler();
    public MainFragment fragment;
    public DrawerLayout drawerLayout;
    public boolean exitStatus = false;
    public Runnable runnable = () -> exitStatus = false;
    private Toolbar toolbar;


    public void onClickDrawer() {
        this.drawerLayout.closeDrawers();
        try {
            this.handler.removeCallbacks(this.runnable);
        } catch (Exception ignored) {
        }
        new Thread() {
            public void run() {
                try {
                    sleep(500);
                    ContactActivity.this.runOnUiThread(() -> {

                        throw new UnsupportedOperationException("Method not decompiled: com.kratos47mhs.ganjoor.activity.ContactActivity.AnonymousClass8.AnonymousClass1.run():void");
                    });
                    interrupt();
                } catch (Exception ignored) {
                }
            }
        }.start();
    }

    private void setupDrawer() {
        // Set date in drawer
        ((TextView) findViewById(R.id.drawer_date)).setText(Formatter.formatDate());

        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerHolder = findViewById(R.id.drawer_holder);
        ListView listView = (ListView) findViewById(R.id.drawer_list);

        // Navigation menu button
        findViewById(R.id.nav_btn).setOnClickListener(view -> ContactActivity.this.drawerLayout.openDrawer(GravityCompat.START));

        // Settings button
        findViewById(R.id.settings_btn).setOnClickListener(view -> ContactActivity.this.onClickDrawer());

        // Set adapter of drawer
        listView.setAdapter(new GanjoorAdaptor(getApplicationContext(), type -> ContactActivity.this.onClickDrawer()));
    }


    public void l() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.kratos47mhs.ganjooradfree")));
    }


    public void drawerHolder() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=6022272342409292442")));
    }


    public void handler() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    public void runnable() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hayder4pak@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "گنجور برای اندروید - گزارش اشکال");
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }


    public void adview() {
        String stringbuilder = "http://play.google.com/store/apps/details?id=" +
                getPackageName();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(stringbuilder)));
    }


    public void fragment() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "دایره المعارف شعر و ادبیات فارسی");
        intent.putExtra(Intent.EXTRA_SUBJECT, "آثار سخنسرایان پارسی‌ گو");
        startActivity(Intent.createChooser(intent, "اشتراک از طریق"));
    }


    public void drawerLayout() {
        AlertDialog create = new AlertDialog.Builder(this).setTitle(R.string.license).setPositiveButton(R.string.ok, null).setIcon(R.mipmap.ic_launcher).setMessage(Html.fromHtml("<div align=\"center\" style=\"\">گنجور کلام ایپ توسعه یافته توسط <strong>حیدر آرت</strong><br><br>ما را دنبال کنید<br><li><ModelViewHolder href=\"http://www.kratos47mhs.tk\">www.kratos47mhs.tk</ModelViewHolder></li><li><ModelViewHolder href=\"http://www.haideron.com\">www.haideron.com</ModelViewHolder></li></div>")).create();
        create.show();
        ((TextView) create.findViewById(R.id.content_holder)).setMovementMethod(LinkMovementMethod.getInstance());
    }


    public void exitStatus() {
        AlertDialog create = new AlertDialog.Builder(this).setTitle(R.string.credit).setPositiveButton(R.string.ok, null).setIcon(R.mipmap.ic_launcher).setMessage(Html.fromHtml("<div align=\"center\" style=\"\">نسبت دادن<strong>گنجور ویب سایت</strong><br><br><li><ModelViewHolder href=\"http://ganjoor.net\">www.ganjoor.net</ModelViewHolder></li></div>")).create();
        create.show();
        ((TextView) create.findViewById(R.id.content_holder)).setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(drawerHolder)) {
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

    public void onClick(View view) {
        if (view.getId() == R.id.main_menu) {
            startActivity(new Intent(this, GanjorActivity.class));
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);


        findViewById(R.id.send).setOnClickListener(view -> {
            String txtto = ((EditText) ContactActivity.this.findViewById(R.id.txtTo)).getText().toString();
            String txtsubject = ((EditText) ContactActivity.this.findViewById(R.id.txtSubject)).getText().toString();
            String txtmessage = ((EditText) ContactActivity.this.findViewById(R.id.txtMessage)).getText().toString();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{txtto});
            intent.putExtra(Intent.EXTRA_SUBJECT, txtsubject);
            intent.putExtra(Intent.EXTRA_TEXT, txtmessage);
            intent.setType("message/rfc822");
            ContactActivity.this.startActivity(Intent.createChooser(intent, ContactActivity.this.getString(R.string.email_send)));
        });
        setupDrawer();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo);

        this.toolbar.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId != R.id.otherapps) {
                switch (itemId) {
                    case R.id.item_credit /*2131165293*/:
                        ContactActivity.this.exitStatus();
                        break;
                    case R.id.item_license /*2131165294*/:
                        ContactActivity.this.drawerLayout();
                        break;
                    case R.id.item_rate_app /*2131165295*/:
                        ContactActivity.this.adview();
                        break;
                    case R.id.item_submit_bug /*2131165296*/:
                        ContactActivity.this.runnable();
                        break;
                    default:
                        switch (itemId) {
                            case R.id.mNoAd /*2131165306*/:
                                ContactActivity.this.l();
                                break;
                            case R.id.mSave /*2131165307*/:
                                ContactActivity.this.handler();
                                break;
                            case R.id.mShare /*2131165308*/:
                                ContactActivity.this.fragment();
                                break;
                        }
                }
            } else {
                ContactActivity.this.drawerHolder();
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
                    exitStatus();
                    break;
                case R.id.item_license /*2131165294*/:
                    drawerLayout();
                    break;
                case R.id.item_rate_app /*2131165295*/:
                    adview();
                    break;
                case R.id.item_submit_bug /*2131165296*/:
                    runnable();
                    break;
                default:
                    switch (itemId) {
                        case R.id.mNoAd /*2131165306*/:
                            l();
                            break;
                        case R.id.mSave /*2131165307*/:
                            handler();
                            break;
                        case R.id.mShare /*2131165308*/:
                            fragment();
                            break;
                    }
            }
        } else {
            drawerHolder();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onChangeSelection(boolean state) {

    }

    @Override
    public void toggleOneSelection(boolean state) {

    }
}
