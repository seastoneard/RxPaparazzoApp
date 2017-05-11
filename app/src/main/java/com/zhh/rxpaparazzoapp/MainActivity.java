package com.zhh.rxpaparazzoapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData;
import com.miguelbcr.ui.rx_paparazzo2.entities.Response;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.iv_appbar)
    ImageView iv_appbar;

    @Bind(R.id.main_toolbar)
    Toolbar toolbar;

 /*   @BindView(R.id.btn_float)
    FloatingActionButton btn_float;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBar();
    }


    private void initToolBar() {
        this.setSupportActionBar(toolbar);
        toolbar.setTitle("我的");
    }

    @OnClick({R.id.main_toolbar, R.id.btn_float})
    public void onClick(View view) {
        final UCrop.Options options = new UCrop.Options();
        int color = ContextCompat.getColor(view.getContext(), R.color.colorPrimary);
        options.setToolbarColor(color);
        options.setStatusBarColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark));
        options.setActiveWidgetColor(color);
        switch (view.getId()) {
            case R.id.main_toolbar:
                Toast.makeText(MainActivity.this, "Toolbar点击", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_float: {
                showDialog(view, options);
                break;
            }
        }
    }

    private void showDialog(View view, final UCrop.Options options) {
        final Context context = view.getContext();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("设置背景图片：").setMessage("如何获取图片？")
                .setPositiveButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        RxPaparazzo.takeImage(MainActivity.this)
                        RxPaparazzo.single(MainActivity.this)
                                .crop(options)
                                .usingGallery()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Response<MainActivity, FileData>>() {
                                    @Override
                                    public void accept(Response<MainActivity, FileData> mainActivityFileDataResponse) throws Exception {
                                        if (mainActivityFileDataResponse.resultCode() == Activity.RESULT_OK) {
                                            File filePath = mainActivityFileDataResponse.data().getFile();
                                            Bitmap bitmap = BitmapFactory.decodeFile(filePath.getPath());
                                            iv_appbar.setImageBitmap(bitmap);
                                        } else if (mainActivityFileDataResponse.resultCode() == Activity.RESULT_CANCELED) {
                                            Toast.makeText(MainActivity.this, "取消相册访问", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "未知错误！", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("拍照", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        RxPaparazzo.takeImage(MainActivity.this)
                        RxPaparazzo.single(MainActivity.this)
                                .crop(options)
                                .usingCamera()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Response<MainActivity, FileData>>() {
                                    @Override
                                    public void accept(Response<MainActivity, FileData> response) throws Exception {
                                        if (response.resultCode() == Activity.RESULT_OK) {
                                            FileData filePath = response.data();
                                            Bitmap bitmap = BitmapFactory.decodeFile(filePath.getFile().getPath());
                                            iv_appbar.setImageBitmap(bitmap);
                                        } else if (response.resultCode() == Activity.RESULT_CANCELED) {
                                            Toast.makeText(MainActivity.this, "取消拍照", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "未知错误！", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        /**
                         * new Consumer<Response<MainActivity, String>>() {
                        @Override public void accept(@NonNull Response<MainActivity, String> response) throws Exception {
                        if (response.resultCode() == Activity.RESULT_OK) {
                        String filePath = response.data();
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        iv_appbar.setImageBitmap(bitmap);
                        } else if (response.resultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(MainActivity.this, "取消拍照", Toast.LENGTH_SHORT).show();
                        } else {
                        Toast.makeText(MainActivity.this, "未知错误！", Toast.LENGTH_SHORT).show();
                        }
                        }
                        }
                         *
                         */
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).

                setTextColor(ContextCompat.getColor(context, R.color.colorPrimary)
                );
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).

                setTextColor(ContextCompat.getColor(context, R.color.colorPrimary)

                );
        dialog.getButton(DialogInterface.BUTTON_NEUTRAL).

                setTextColor(ContextCompat.getColor(context, R.color.colorAccent)

                );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);//解除绑定
    }
}
