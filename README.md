<!-- Baidu Button BEGIN -->
<div id="article_content" class="article_content">

<div style="text-align:center"><strong><span style="font-size:20px">图片裁剪(基于RxPaparazzo)</span></strong></div>
<div style="text-align:center"><strong><span style="font-size:20px"><br>
</span></strong></div>
<div style="text-align:center"><strong><span style="font-size:20px"><br>
</span></strong></div>
<div style="text-align:left"><br>
<br>
<strong><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:Arial; line-height:26px">前言</span><span style="color:rgb(51,51,51); font-family:Arial; line-height:26px">：</span></span></strong><span style="font-size:16px">基于RxPaparazzo的图片裁剪，图片旋转、比例放大|缩小。</span><br>
</div>
<div style="text-align:left"><span style="font-size:14px"><br>
</span></div>
<div style="text-align:left"><span style="font-size:14px"><br>
</span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong>效果：</strong></span><br>
</span></div>
<div style="text-align:center"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><img src="http://img.blog.csdn.net/20170511105640724?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhoX2NzZG5fYXJk/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" width="320" height="520" alt=""><br>
</strong></span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><br>
</strong></span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><br>
</span></strong></span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial">开发环境：AndroidStudio2.2.1&#43;gradle-2.14.1</span><br>
</strong></span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><br>
</strong></span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><br>
</strong></span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong>引入依赖：</strong></span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><br>
</strong></span></span></div>
<div style="text-align:left"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong></strong></span><pre name="code" class="java">
    compile 'com.android.support:appcompat-v7:24.+'
    //RxPaparazzo 拍照&amp;相册
//    compile com.github.miguelbcr:RxPaparazzo:0.4.2-2.x;
    compile (com.github.miguelbcr:RxPaparazzo:0.5.2-2.x;) {
        exclude module: 'okhttp'
        exclude module: 'okio'
    }
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
    compile 'com.android.support:cardview-v7:24.+'
//    compile 'com.android.support:customtabs:24.+'
    compile 'com.android.support:design:24.+'
    compile 'com.jakewharton:butterknife:7.0.1'</pre><br>
<br>
</div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><br>
</strong></span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial"><strong>涉及知识：</strong></span></span><br>
</span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial; font-size:14px"><span style="font-family:Arial"><br>
</span></span></strong></span></span></div>
<div style="text-align:left"><span style="font-size:18px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial">1.Material Design（CardView&#43;CoordinatorLayout&#43;AppBarLayout&#43;</span></span></span><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial">NestedScrollView</span></span><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial">&#43;</span></span></span></div>
<div style="text-align:left"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial"><span style="font-size:18px">CollapsingToolbarLayout&#43;Toolbar&#43;FloatingActionButton）使用</span></span></span></div>
<div style="text-align:left"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial"><span style="font-size:18px"><br>
</span></span></span></div>
<div style="text-align:left"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial"><span style="font-size:18px">2.butterknife注解式开发</span></span></span></span></div>
<div style="text-align:left"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial"><span style="font-size:18px"><br>
</span></span></span></span></div>
<div style="text-align:left"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial"><span style="font-size:18px">3.基于RxJava&#43;RxAndroid的RxPaparazzo使用</span></span></span></span></div>
<div style="text-align:left"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial"><span style="font-size:18px"><br>
</span></span></span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial; font-size:14px"><span style="font-family:Arial"><br>
</span></span></strong></span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-weight:bold"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial">部分代码：</span><br>
</span></span></span></span></span></div>
<div style="text-align:left"><span style="font-size:14px"><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><span style="color:rgb(51,51,51); font-family:&quot;Microsoft YaHei&quot;,Arial; font-size:14px"><span style="font-family:Arial"><br>
</span></span></strong></span></span></div>
<div style="text-align:left"><span style="font-family:&quot;Microsoft YaHei&quot;,Arial"><strong><span style="font-family:&quot;Microsoft YaHei&quot;,Arial"><span style="font-family:Arial"></span></span></strong></span><pre name="code" class="java" style="color: rgb(51, 51, 51); font-size: 14px;">
 
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
                                    public void accept(Response<MainActivity, FileData>
                                                               response) throws Exception {
                                        if (response.resultCode() == Activity.RESULT_OK) {

                                            File filePath = response.data().getFile();
                                            Bitmap bitmap = BitmapFactory.
                                                    decodeFile(filePath.getPath());
                                            iv_appbar.setImageBitmap(bitmap);

                                        } else if (response.resultCode() == Activity.RESULT_CANCELED) {

                                            Toast.makeText(MainActivity.this, "取消相册访问",
                                                    Toast.LENGTH_SHORT).show();

                                        } else {

                                            Toast.makeText(MainActivity.this, "未知错误！",
                                                    Toast.LENGTH_SHORT).show();
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
                                    public void accept(Response<MainActivity, FileData>
                                                               response) throws Exception {

                                        if (response.resultCode() == Activity.RESULT_OK) {
                                            FileData filePath = response.data();
                                            Bitmap bitmap = BitmapFactory.
                                                    decodeFile(filePath.getFile().getPath());
                                            iv_appbar.setImageBitmap(bitmap);
                                        } else if (response.resultCode() == Activity.RESULT_CANCELED) {
                                            Toast.makeText(MainActivity.this, "取消拍照",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "未知错误！",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                      
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

</pre><br>
<span style="font-size:24px; color:#ff0000"><br>
</span></div>
   
</div>



<!-- Baidu Button END -->
