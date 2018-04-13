package com.lfish.lotteryssc;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
//import com.liulishuo.filedownloader.BaseDownloadTask;
//import com.liulishuo.filedownloader.FileDownloadListener;
//import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.NumberFormat;

public class UpdateActivity extends AppCompatActivity {

    private WebView webView;
    public  static String WebUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        webView = (WebView) findViewById(R.id.web);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        initWebView();
        clearWebViewCache();

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new MyWebViewClient());

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        webView.loadUrl(WebUrl);



    }
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //设置 缓存模式
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        webView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME;
//      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        Log.i("...", "cacheDirPath="+cacheDirPath);
        //设置  Application Caches 缓存目录
        webView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webView.getSettings().setAppCacheEnabled(true);


    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache(){

        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME);
        Log.e("fdsf", "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath()+"/webviewCache");
        Log.e("123123", "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if(webviewCacheDir.exists()){
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if(appCacheDir.exists()){
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

        Log.i("fdsf", "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e("ewr", "delete file no exists " + file.getAbsolutePath());
        }
    }



    public class MyWebViewClient extends WebViewClient {
        // 如果页面中链接，如果希望点击链接继续在当前browser中响应，
        // 而不是新开Android的系统browser中响应该链接，必须覆盖 webview的WebViewClient对象。
        public boolean shouldOverviewUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            showProgressDialog2();
        }

        public void onPageFinished(WebView view, String url) {
            closeProgressDialog2();
        }

        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            closeProgressDialog2();
        }
    }



//    private ThinDownloadManager downloadManager;
    Uri destinationUri;
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, final String mimetype,
                                    long contentLength) {
            String fileName=url.substring(url.lastIndexOf("/")+1);
            fileName= URLDecoder.decode(fileName);
            showProgressDialog();
            mDialog.setProgressNumberFormat(""+0+"/"+contentLength);
            File file=new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),fileName);

            //---download 2
            Uri apkUri = FileProvider.getUriForFile(UpdateActivity.this, "com.lfish.lotteryssc.installapkdemo", file);
            FileDownloader.getImpl().create(url)
                    .setPath(file.getAbsolutePath())
                    .setListener(new FileDownloadListener() {
                        @Override
                        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                        }

                        @Override
                        protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
//                            closeProgressDialog2();

                        }

                        @Override
                        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            NumberFormat numberFormat = NumberFormat.getInstance();
                            // 设置精确到小数点后2位
                            numberFormat.setMaximumFractionDigits(0);
                            String result = numberFormat.format((float)soFarBytes/(float)totalBytes*100);
                            System.out.println("diliverNum和queryMailNum的百分比为:" + result + "%");
                            mDialog.setProgress(Integer.parseInt(result));

                            mDialog.setProgressNumberFormat(""+soFarBytes+"/"+totalBytes);
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
                            closeProgressDialog();
                            File apkFile = new File(task.getTargetFilePath());

                            if(Build.VERSION.SDK_INT >= 24){
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                // 由于没有在Activity环境下启动Activity,设置下面的标签
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                                Uri apkUri = FileProvider.getUriForFile(UpdateActivity.this, "com.lfish.lotteryssc.installapkdemo", apkFile);
                                //添加这一句表示对目标应用临时授权该Uri所代表的文件apkFile
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                                UpdateActivity.this.startActivity(intent);
                            }else{
                                Intent intent =new Intent(Intent.ACTION_VIEW);

                                intent.setDataAndType(Uri.fromFile(apkFile),

                                        "application/vnd.android.package-archive");

                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                UpdateActivity.this.startActivity(intent);
                            }
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



    //内部类
    private class DownloaderTask extends AsyncTask<String, Void, String> {

        public DownloaderTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url=params[0];
//          Log.i("tag", "url="+url);
            String fileName=url.substring(url.lastIndexOf("/")+1);
            fileName= URLDecoder.decode(fileName);
            Log.i("tag", "fileName="+fileName);

            File directory= Environment.getExternalStorageDirectory();
            File file=new File(directory,fileName);
            if(file.exists()){
                Log.i("tag", "The file has already exists.");
                return fileName;
            }
            try {

                URL urlcon = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) urlcon.openConnection();

                if(HttpURLConnection.HTTP_OK==urlConnection.getResponseCode()){
                    InputStream input =   urlConnection.getInputStream();

                    writeToSDCard(fileName,input);

                    input.close();
//                  entity.consumeContent();
                    return fileName;
                }else{
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            closeProgressDialog();
            if(result==null){
                Toast t=Toast.makeText(UpdateActivity.this, "连接错误！请稍后再试！", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                return;
            }

            Toast t=Toast.makeText(UpdateActivity.this, "已保存到SD卡。", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
            File directory=Environment.getExternalStorageDirectory();
            File file=new File(directory,result);
            Log.i("tag", "Path="+file.getAbsolutePath());

            Intent intent = getFileIntent(file);

            startActivity(intent);

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }


    }


    private ProgressDialog mDialog;
    private void showProgressDialog(){
        if(mDialog==null){
            mDialog = new ProgressDialog(UpdateActivity.this);
            mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置风格为圆形进度条
            mDialog.setMessage("开始下载，请稍后...");
            mDialog.setIndeterminate(false);//设置进度条是否为不明确
            mDialog.setCancelable(true);//设置进度条是否可以按退回键取消
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    mDialog=null;
                    FileDownloader.getImpl().pauseAll();
                }
            });
            mDialog.show();

        }
    }
    private void closeProgressDialog(){
        if(mDialog!=null){
            mDialog.dismiss();
            FileDownloader.getImpl().pauseAll();
            mDialog=null;
        }
    }


    private ProgressDialog mDialog2;
    private void showProgressDialog2(){
        if(mDialog2==null){
            mDialog2 = new ProgressDialog(UpdateActivity.this);
            mDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
            mDialog2.setMessage("正在加载 ，请等待...");
            mDialog2.setIndeterminate(false);//设置进度条是否为不明确
            mDialog2.setCancelable(true);//设置进度条是否可以按退回键取消
            mDialog2.setCanceledOnTouchOutside(false);
            mDialog2.setProgress(10);
            mDialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    mDialog2=null;
                }
            });
            mDialog2.show();

        }
    }
    private void closeProgressDialog2(){
        if(mDialog2!=null){
            mDialog2.dismiss();
            mDialog2=null;
        }
    }

    public Intent getFileIntent(File file){
//       Uri uri = Uri.parse("http://m.ql18.com.cn/hpf10/1.pdf");
        Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        Log.i("tag", "type="+type);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, type);
        return intent;
    }

    public void writeToSDCard(String fileName,InputStream input){

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File directory=Environment.getExternalStorageDirectory();
            File file=new File(directory,fileName);
//          if(file.exists()){
//              Log.i("tag", "The file has already exists.");
//              return;
//          }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[2048];
                int j = 0;
                while ((j = input.read(b)) != -1) {
                    fos.write(b, 0, j);
                }
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            Log.i("tag", "NO SDCard.");
        }
    }

    private String getMIMEType(File f){
        String type="";
        String fName=f.getName();
      /* 取得扩展名 */
        String end=fName.substring(fName.lastIndexOf(".")+1,fName.length()).toLowerCase();

      /* 依扩展名的类型决定MimeType */
        if(end.equals("pdf")){
            type = "application/pdf";//
        }
        else if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||
                end.equals("xmf")||end.equals("ogg")||end.equals("wav")){
            type = "audio/*";
        }
        else if(end.equals("3gp")||end.equals("mp4")){
            type = "video/*";
        }
        else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||
                end.equals("jpeg")||end.equals("bmp")){
            type = "image/*";
        }
        else if(end.equals("apk")){
        /* android.permission.INSTALL_PACKAGES */
            type = "application/vnd.android.package-archive";
        }
//      else if(end.equals("pptx")||end.equals("ppt")){
//        type = "application/vnd.ms-powerpoint";
//      }else if(end.equals("docx")||end.equals("doc")){
//        type = "application/vnd.ms-word";
//      }else if(end.equals("xlsx")||end.equals("xls")){
//        type = "application/vnd.ms-excel";
//      }
        else{
//        /*如果无法直接打开，就跳出软件列表给用户选择 */
            type="*/*";
        }
        return type;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileDownloader.getImpl().clearAllTaskData();
        webView.clearCache(true);
    }
}