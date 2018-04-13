package com.lfish.lotteryssc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import java.io.File;
import java.net.URLDecoder;
import java.text.NumberFormat;

public class ReadyUpdateActivity extends AppCompatActivity {
    public static   String url;
    RoundCornerProgressBar progressBar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readyupdate);
        progressBar = (RoundCornerProgressBar) findViewById(R.id.activity_pdfload_progressBar);
        textView = (TextView) findViewById(R.id.tv_progress);

        downLoad();

    }

    public void downLoad(){
        String fileName = url.substring(url.lastIndexOf("/")+1);
        Log.i("???",fileName);
        fileName= URLDecoder.decode(fileName);
        File file=new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),fileName);

        BroadcastReceiver installedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
//                    String packageName = intent.getData().getSchemeSpecificPart();
                    Intent uninstall_intent = new Intent();
                    uninstall_intent.setAction(Intent.ACTION_DELETE);
                    uninstall_intent.setData(Uri.parse("package:"+getPackageName()));
                    startActivity(uninstall_intent);
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme("package");
        this.registerReceiver(installedReceiver, filter);

        //---download 2
        Uri apkUri = FileProvider.getUriForFile(ReadyUpdateActivity.this, "com.lfish.lotteryssc.installapkdemo", file);
        FileDownloader.getImpl().create(url)
                .setPath(file.getAbsolutePath())
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
//                            closeProgressDialog2();
                        progressBar.setMax(totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        NumberFormat numberFormat = NumberFormat.getInstance();
                        // 设置精确到小数点后2位
                        numberFormat.setMaximumFractionDigits(0);
                        String result = numberFormat.format((float)soFarBytes/(float)totalBytes*100);
                        System.out.println("diliverNum和queryMailNum的百分比为:" + result + "%");
                        progressBar.setProgress(soFarBytes);

                        textView.setText(result+"%    "+soFarBytes+"/"+totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {

                        task.getTargetFilePath();
                        File apkFile = new File(task.getTargetFilePath());

                        if(Build.VERSION.SDK_INT >= 24){
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            // 由于没有在Activity环境下启动Activity,设置下面的标签
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                            Uri apkUri = FileProvider.getUriForFile(ReadyUpdateActivity.this, "com.lfish.lotteryssc.installapkdemo", apkFile);
                            //添加这一句表示对目标应用临时授权该Uri所代表的文件apkFile
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                            ReadyUpdateActivity.this.startActivity(intent);
                        }else{
                            Intent intent =new Intent(Intent.ACTION_VIEW);

                            intent.setDataAndType(Uri.fromFile(apkFile),

                                    "application/vnd.android.package-archive");

                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            ReadyUpdateActivity.this.startActivity(intent);
                        }

//                        finish();

                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }
}
