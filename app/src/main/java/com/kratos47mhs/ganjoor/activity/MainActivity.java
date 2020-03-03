package com.kratos47mhs.ganjoor.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import org.json.JSONArray;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.kratos47mhs.ganjoor.App;
import com.kratos47mhs.ganjoor.R;
import com.kratos47mhs.ganjoor.adapter.DrawerAdapter;
import com.kratos47mhs.ganjoor.db.Controller;
import com.kratos47mhs.ganjoor.dialog.ImportDialog;
import com.kratos47mhs.ganjoor.dialog.SaveDialog;
import com.kratos47mhs.ganjoor.fragment.MainFragment;
import com.kratos47mhs.ganjoor.fragment.template.RecyclerFragment;
import com.kratos47mhs.ganjoor.inner.Animator;
import com.kratos47mhs.ganjoor.inner.Formatter;
import com.kratos47mhs.ganjoor.model.Drawer;

public class MainActivity extends AppCompatActivity implements RecyclerFragment.Callbacks {
    public static final int PERMISSION_REQUEST = 3;

    private DrawerLayout drawerLayout;
    public View drawerHolder;
    private boolean exitStatus = false;

    private MainFragment fragment;
    private Toolbar toolbar;
    private View selectionEdit;
    private boolean permissionNotGranted = false;
    private boolean checkForPermission = true;

    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            exitStatus = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            //noinspection ConstantConditions
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (Exception ignored) {
        }

        setupDrawer();

        selectionEdit = findViewById(R.id.selection_edit);
        selectionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.onEditSelected();
            }
        });

        if (savedInstanceState == null) {
            fragment = new MainFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

        if (checkForPermission) {
            checkForPermission = false;
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle(R.string.permission)
                        .setMessage(R.string.storage_permission)
                        .setPositiveButton(R.string.request, (dialog, which) -> {
                            dialog.dismiss();
                            requestPermission();
                        })
                        .setNegativeButton(R.string.cancel, (dialog, which) -> {
                            dialog.dismiss();
                            displayPermissionError();
                        })
                        //.negativeColor(ContextCompat.getColor(this, R.color.secondary_text))

                        .show();
            }
        }
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

    private void setupDrawer() {
        // Set date in drawer
        ((TextView) findViewById(R.id.drawer_date)).setText(Formatter.formatDate());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerHolder = findViewById(R.id.drawer_holder);
        ListView drawerList = (ListView) findViewById(R.id.drawer_list);

        // Navigation menu button
        findViewById(R.id.nav_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Settings button
        findViewById(R.id.settings_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDrawer(Drawer.TYPE_SETTINGS);
            }
        });

        // Set adapter of drawer
        drawerList.setAdapter(new DrawerAdapter(
                getApplicationContext(),
                new DrawerAdapter.ClickListener() {
                    @Override
                    public void onClick(int type) {
                        onClickDrawer(type);
                    }
                }
        ));
    }

    private void onClickDrawer(final int type) {
        drawerLayout.closeDrawers();

        try {
            handler.removeCallbacks(runnable);
        } catch (Exception ignored) {
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    // wait for completion of drawer animation
                    sleep(500);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (type) {
                                case Drawer.TYPE_ABOUT:
                                    new MaterialAlertDialogBuilder(MainActivity.this)
                                            .setTitle(R.string.app_name)
                                            .setMessage(R.string.about_desc)
                                            .setPositiveButton(R.string.ok, (dialog, which) -> {
                                                dialog.dismiss();
                                            })

                                            .show();
                                    break;
                                case Drawer.TYPE_BACKUP:
                                    backupData();
                                    break;
                                case Drawer.TYPE_RESTORE:
                                    restoreData();
                                    break;
                                case Drawer.TYPE_SETTINGS:
                                    // TODO implement settings
                                    new MaterialAlertDialogBuilder(MainActivity.this)
                                            .setTitle(R.string.settings)
                                            .setMessage(R.string.not_implemented)
                                            .setPositiveButton(R.string.ok, (dialog, which) -> {
                                                dialog.dismiss();
                                            })
                                            .show();
                                    break;
                            }
                        }
                    });

                    interrupt();
                } catch (Exception ignored) {
                }
            }
        }.start();
    }

    @Override
    public void onChangeSelection(boolean state) {
        if (state) {
            Animator.create(getApplicationContext())
                    .on(toolbar)
                    .setEndVisibility(View.INVISIBLE)
                    .animate(R.anim.fade_out);
        } else {
            Animator.create(getApplicationContext())
                    .on(toolbar)
                    .setStartVisibility(View.VISIBLE)
                    .animate(R.anim.fade_in);
        }
    }

    @Override
    public void toggleOneSelection(boolean state) {
        selectionEdit.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    private void restoreData() {
        ImportDialog.newInstance(
                R.string.restore,
                new String[]{App.BACKUP_EXTENSION},
                new ImportDialog.ImportListener() {
                    @Override
                    public void onSelect(final String path) {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    readBackupFile(path);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            fragment.loadItems();

                                            Snackbar.make(fragment.fab != null ? fragment.fab : toolbar, R.string.data_restored, Snackbar.LENGTH_LONG).show();
                                        }
                                    });
                                } catch (final Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new MaterialAlertDialogBuilder(MainActivity.this)
                                                    .setTitle(R.string.restore_error)
                                                    .setPositiveButton(R.string.ok, (dialog, which) -> {
                                                        dialog.dismiss();
                                                    })
                                                    .setMessage(e.getMessage())
                                                    .show();
                                        }
                                    });
                                } finally {
                                    interrupt();
                                }
                            }
                        }.start();
                    }

                    @Override
                    public void onError(String msg) {
                        new MaterialAlertDialogBuilder(MainActivity.this)
                                .setTitle(R.string.restore_error)
                                .setPositiveButton(R.string.ok, (dialog, which) -> {
                                    dialog.dismiss();
                                })
                                .setMessage(msg)
                                .show();
                    }
                }
        ).show(getSupportFragmentManager(), "");
    }

    private void backupData() {
        SaveDialog.newInstance(
                R.string.backup,
                "memento",
                App.BACKUP_EXTENSION,
                new SaveDialog.SaveListener() {
                    @Override
                    public void onSelect(final String path) {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    saveBackupFile(path);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new MaterialAlertDialogBuilder(MainActivity.this)
                                                    .setTitle(R.string.backup)
                                                    .setPositiveButton(R.string.ok, (dialog, which) -> {
                                                        dialog.dismiss();
                                                    })
                                                    .setMessage(getString(R.string.backup_saved, path))
                                                    .show();
                                        }
                                    });
                                } catch (final Exception e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new MaterialAlertDialogBuilder(MainActivity.this)
                                                    .setTitle(R.string.backup_error)
                                                    .setPositiveButton(R.string.ok, (dialog, which) -> {
                                                        dialog.dismiss();
                                                    })
                                                    .setMessage(e.getMessage())
                                                    .show();
                                        }
                                    });
                                } finally {
                                    interrupt();
                                }
                            }
                        }.start();
                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onCancel() {

                    }
                }
        ).show(getSupportFragmentManager(), "");
    }

    private void readBackupFile(String path) throws Exception {
        DataInputStream dis = new DataInputStream(new FileInputStream(path));
        byte[] backup_data = new byte[dis.available()];
        dis.readFully(backup_data);
        JSONArray json = new JSONArray(new String(backup_data));
        dis.close();

        Controller.instance.readBackup(json);
    }

    private void saveBackupFile(String path) throws Exception {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write("[".getBytes("UTF-8"));
            Controller.instance.writeBackup(fos);
            fos.write("]".getBytes("UTF-8"));
            fos.flush();
        } finally {
            if (fos != null) fos.close();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
    }

    private void displayPermissionError() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.permission_error)
                .setMessage(R.string.permission_error_desc)
                .setNegativeButton(R.string.request, (dialog, which) -> {
                    dialog.dismiss();
                    requestPermission();
                })
                .setPositiveButton(R.string.continue_anyway, (dialog, which) -> {
                    dialog.dismiss();
                    permissionNotGranted = false;
                })
                //.negativeColor(ContextCompat.getColor(this, R.color.secondary_text))
                .show();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionNotGranted) {
            permissionNotGranted = false;
            displayPermissionError();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[0])) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), R.string.permission_granted, Toast.LENGTH_SHORT).show();
            } else {
                permissionNotGranted = true;
            }
        }
    }
}