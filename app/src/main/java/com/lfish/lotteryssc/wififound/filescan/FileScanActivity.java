package com.lfish.lotteryssc.wififound.filescan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.lfish.lotteryssc.R;
import com.lfish.lotteryssc.wififound.abroadcast.lanbroadcast.UdpBroadCastManager;
import com.lfish.lotteryssc.wififound.adapter.FileScanRvAdapter;
import com.lfish.lotteryssc.wififound.adapter.FileScanTitlesAdapter;
import com.lfish.lotteryssc.wififound.adapter.viewholder.FileScanRvViewHolder;
import com.lfish.lotteryssc.wififound.core.cmd.BroadCastFilePlay;
import com.lfish.lotteryssc.wififound.dao.FileWifiWebDes;
import com.lfish.lotteryssc.wififound.filescan.filescandes.FileScanImage;
import com.lfish.lotteryssc.wififound.filescan.filescandes.FileScanMusic;
import com.lfish.lotteryssc.wififound.filescan.filescandes.FileScanVideo;
import com.lfish.lotteryssc.wififound.wifishare.WiFiShareServerConfig;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shenmegui on 2017/6/6.
 */
public class FileScanActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static String Tag = "FileScanActivity";

    private FileScanRvAdapter fileScanRvAdapter;
    private FileWifiWebDes cacheFileWifiWebDes;
    private String ip;
    private FilePickerDialog filePickerDialog;
    private String chooseIp,chooseName;
    private RecyclerView fileDirDes,fileScanList;
    private FileScanTitlesAdapter fileScanTitlesAdapter;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton floatingActionImage,floatingActionVideo,floatingActionMusic,floactingActionApk,floactingActionName;
    private SearchFragment searchFragment;
    private  KProgressHUD loadingView;

    private List<FileWifiWebDes.FileWifiWeb> fileWifiWebList = new LinkedList<FileWifiWebDes.FileWifiWeb>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filescan);
        fileDirDes = (RecyclerView) findViewById(R.id.rv_filescan_dir_des);
        fileScanList = (RecyclerView) findViewById(R.id.rv_filescan_dir_list);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fam_scan_type);

        ip = getIntent().getStringExtra("ip");

//        getSupportActionBar().setTitle("访问:"+ip );
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_LIST);

//        gridView.setOnItemClickListener(this);

        initDirPicker();

        initDirDesTile();

        initDirList();

        initActionMenuSearch();

    }

    private void initActionMenuSearch() {

        floatingActionImage = (FloatingActionButton) findViewById(R.id.menu_item_img);
        floatingActionVideo = (FloatingActionButton) findViewById(R.id.menu_item_video);
        floatingActionMusic = (FloatingActionButton) findViewById(R.id.menu_item_music);
        floactingActionApk = (FloatingActionButton) findViewById(R.id.menu_item_apk);
        floactingActionName = (FloatingActionButton) findViewById(R.id.menu_item_name);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                loadingView = KProgressHUD.create(FileScanActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("检索:"+fileWifiWebList.get(fileWifiWebList.size()-1).getName())
                        .setDetailsLabel("检索文件耗时较长，请耐心等待...")
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f);
                switch (v.getId()){
                    case R.id.menu_item_img:
                        loadingView.show();
                        if(cacheFileWifiWebDes==null){
                            getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE+"="+Environment.getExternalStorageDirectory().getAbsolutePath()+WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_IMAGE);
                        }else{
                            getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE+"="+cacheFileWifiWebDes.getPath()+WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_IMAGE);
                        }

                        break;
                    case R.id.menu_item_video:
                        loadingView.show();
                        if(cacheFileWifiWebDes==null){
                            getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE+"="+Environment.getExternalStorageDirectory().getAbsolutePath()+WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_VIDEO);
                        }else{
                            getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE+"="+cacheFileWifiWebDes.getPath()+WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_VIDEO);
                        }
                        break;
                    case R.id.menu_item_music:
                        loadingView.show();
                        if(cacheFileWifiWebDes==null){
                            getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE+"="+Environment.getExternalStorageDirectory().getAbsolutePath()+WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_MUSIC);
                        }else{
                            getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE+"="+cacheFileWifiWebDes.getPath()+WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_MUSIC);
                        }
                        break;
                    case R.id.menu_item_apk:
                        loadingView.show();
                        if(cacheFileWifiWebDes==null){
                            getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE+"="+Environment.getExternalStorageDirectory().getAbsolutePath()+WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_APK);
                        }else{
                            getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE+"="+cacheFileWifiWebDes.getPath()+WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_APK);
                        }
                        break;
                    case R.id.menu_item_name:
                        searchFragment.show(getSupportFragmentManager(),SearchFragment.TAG);
                        break;
                }
            }
        };

        //--------名称搜索
        searchFragment = SearchFragment.newInstance();
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                //这里处理逻辑
                loadingView.show();
                  if(cacheFileWifiWebDes==null){
                    getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_FILENAME+"="+Environment.getExternalStorageDirectory().getAbsolutePath()+WiFiShareServerConfig.HOST_PARAM_FILE_SEARCH_SPEC+keyword);
                  }else{
                    getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_FILENAME+"="+cacheFileWifiWebDes.getPath()+WiFiShareServerConfig.HOST_PARAM_FILE_SEARCH_SPEC+keyword);
                  }
            }
        });

        floatingActionImage.setOnClickListener(onClickListener);
        floatingActionVideo.setOnClickListener(onClickListener);
        floatingActionMusic.setOnClickListener(onClickListener);
        floactingActionApk.setOnClickListener(onClickListener);
        floactingActionName.setOnClickListener(onClickListener);

    }

    private void initDirList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        fileScanList.setLayoutManager(linearLayoutManager);

    }

    private void initDirDesTile() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fileDirDes.setLayoutManager(linearLayoutManager);
        FileWifiWebDes.FileWifiWeb fileWifiWeb = new FileWifiWebDes().new FileWifiWeb();
        fileWifiWeb.setName("SD 卡根目录");
        fileWifiWebList.add(fileWifiWeb);
        fileScanTitlesAdapter = new FileScanTitlesAdapter(fileWifiWebList, FileScanActivity.this);
        fileDirDes.setAdapter(fileScanTitlesAdapter);
        fileScanTitlesAdapter.setOnItemClickListener(new FileScanTitlesAdapter.OnItemClickListener() {
            @Override
            public void onClick(int posation) {
                changeTitleWithPostaion(posation);
            }
        });
    }

    /**
     * 更改 title 变化 posation
     * @param posation
     */
    private void changeTitleWithPostaion(int posation) {
        List<FileWifiWebDes.FileWifiWeb> fileWifiWebListTemp = new LinkedList<FileWifiWebDes.FileWifiWeb>();

        String cachePath = "";
        for(int i = 0;i<=posation;i++){
            FileWifiWebDes.FileWifiWeb fileWifiWebCache = fileWifiWebList.get(i);
            if(i!=0){
                String name = fileWifiWebCache.getName();
                cachePath = cachePath +"/"+ name;
            }
            fileWifiWebListTemp.add(fileWifiWebCache);
        }
        fileWifiWebList = fileWifiWebListTemp;
        fileScanTitlesAdapter.setFileWifiWebList(fileWifiWebList);
        fileScanTitlesAdapter.notifyDataSetChanged();

        if(posation==0){
            getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_LIST);
        }else{
            Log.i(Tag,cachePath);
            getFils(ip+"?"+ WiFiShareServerConfig.HOST_PARAM_FILE_FILEPATH+"="+Environment.getExternalStorageDirectory()+""+cachePath);
        }
    }

    /**
     * 初始化 文件下载目录选择器
     */
    private void initDirPicker() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.DIR_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;
        filePickerDialog = new FilePickerDialog(FileScanActivity.this,properties);
        filePickerDialog.setTitle("选择文件保存地址");
        filePickerDialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
                Log.i(Tag,"file alreay choose");
                downLoadFile(chooseIp,files[0]);
            }
        });
    }

    /**
     * 初始化 文件下载
     */
    public void downLoadFile(String address,String path){
        Log.i(Tag,"下载文件："+address);
        FileDownloader.getImpl().create(address)
                .setPath(path+"/"+chooseName)
                .setListener(new FileDownloadListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void completed(final BaseDownloadTask task) {

                        fileScanList.post(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(fileScanList, "下载完毕 "+task.getTargetFilePath(), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });

                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(final BaseDownloadTask task, Throwable e) {
                        fileScanList.post(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(fileScanList, "下载错误 "+task.getFilename(), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();
    }

    /**
     * 获取文件 with address
     * @param ip
     */
    private void getFils(String ip) {
        Log.i(Tag,ip);
        HttpCallback callBack = new HttpCallback(){
            @Override
            public void onSuccess(String t) {
                if(loadingView!=null){
                    loadingView.dismiss();
                }
                cacheFileWifiWebDes = new Gson().fromJson(t,FileWifiWebDes.class);
                fileScanRvAdapter = new FileScanRvAdapter(cacheFileWifiWebDes, FileScanActivity.this) {
                    @Override
                    public String getChooseIp() {
                        String fileAddress = FileScanActivity.this.ip + "?" + WiFiShareServerConfig.HOST_PARAM_FILE_FILEPATH + "=";
                        return fileAddress;
                    }

                    @Override
                    public void onDesImageClick(ImageView imageView,int posation) {
                        Log.i(""," click......");
//                        desImageCache = imageView;
//                        photoView.setVisibility(View.VISIBLE);
//                        photoView.setImageDrawable(imageView.getDrawable());
//                        photoView.bringToFront();
//                        Glide.with(FileScanActivity.this).load(filePath).into(photoView);
//                        photoView.animaFrom(PhotoView.getImageViewInfo(imageView));

                    }

                    @Override
                    public void onDownloadClick(int posation) {
                        final FileWifiWebDes.FileWifiWeb fileWifiWeb = cacheFileWifiWebDes.getWifiWebList().get(posation);

                        new MaterialDialog.Builder(FileScanActivity.this)
                                .title(fileWifiWeb.getName())
                                .items(new String[]{"下载","删除","广播给所有设备"})
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        switch (which){
                                            case 0:
                                                chooseItemLogic(fileWifiWeb);
                                                break;
                                            case 1:
                                                delectFile(fileWifiWeb);

                                                break;
                                            case 2:
//                                                delectFile(fileWifiWeb);
                                                BroadCastFilePlay broadCastFilePlay = new BroadCastFilePlay();
                                                broadCastFilePlay.setFileWifiWeb(fileWifiWeb);
                                                broadCastFilePlay.setIpAddress(getChooseIp());
                                                UdpBroadCastManager.getInstance().sendBroadCast(broadCastFilePlay);
                                                break;
                                        }
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onItemClick(int posation, FileScanRvViewHolder fileScanRvViewHolder) {
                        int itemViewType = fileScanRvAdapter.getItemViewType(posation);
                        switch (itemViewType){
                            case FileScanRvAdapter.DIR:
                                FileWifiWebDes.FileWifiWeb fileWifiWeb = cacheFileWifiWebDes.getWifiWebList().get(posation);
                                chooseItemLogic(fileWifiWeb);
                                break;
                            case FileScanRvAdapter.FILE_IMAGE:
                                String name = fileScanRvAdapter.getFileWifiWebDes().getWifiWebList().get(posation).getPath();
                                String filepath = getChooseIp() + name;
                                FileScanImage.startFileScanImage(FileScanActivity.this,fileScanRvViewHolder.getImagedes(),filepath);
                                break;
                            case FileScanRvAdapter.FILE_VIDEO:
                                String nameVideo = fileScanRvAdapter.getFileWifiWebDes().getWifiWebList().get(posation).getPath();
                                String filepathVideo = getChooseIp() + nameVideo;
                                FileScanVideo.startVideoScanImage(FileScanActivity.this,filepathVideo);
                                break;
                            case FileScanRvAdapter.FILE_MUSIC:
                                String nameMusic = fileScanRvAdapter.getFileWifiWebDes().getWifiWebList().get(posation).getPath();
                                String filepathMusic = getChooseIp() + nameMusic;
                                FileScanMusic.startFileScanMusic(FileScanActivity.this,filepathMusic);
                                break;
                        }
                    }
                };

                fileScanList.setAdapter(fileScanRvAdapter);
            }
            @Override
            public void onFailure(int errorNo, String strMsg) {

            }
        };

        new RxVolley.Builder()
                .url(ip)
                .httpMethod(RxVolley.Method.GET) //default GET or POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
                .cacheTime(1) //default: get 5min, post 0min
                .contentType(RxVolley.ContentType.FORM)//default FORM or JSON
                .shouldCache(true) //default: get true, post false
                .callback(callBack)
                .encoding("UTF-8") //default
                .timeout(5000000)
                .doTask();
    }

    public static void OpenFileScan(String ip, Context context){
        Intent intent = new Intent(context,FileScanActivity.class);
        intent.putExtra("ip",ip);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileWifiWebDes.FileWifiWeb fileWifiWeb = cacheFileWifiWebDes.getWifiWebList().get(position);
        chooseItemLogic(fileWifiWeb);
    }

    /**
     * 当条目被选中时的逻辑
     * @param fileWifiWeb
     */
    private void chooseItemLogic(FileWifiWebDes.FileWifiWeb fileWifiWeb) {
        if(fileWifiWeb.isFile()){
            String fileAddress = ip + "?" + WiFiShareServerConfig.HOST_PARAM_FILE_FILEPATH + "=" + fileWifiWeb.getPath();
            chooseIp = fileAddress;
            chooseName = fileWifiWeb.getName();
            if(filePickerDialog!=null){
                filePickerDialog.show();
            }
        }else {
            changeTitle(fileWifiWeb);
            Log.i(Tag, ip + "?" + WiFiShareServerConfig.HOST_PARAM_FILE_FILEPATH + "=" + fileWifiWeb.getPath());
            getFils(ip + "?" + WiFiShareServerConfig.HOST_PARAM_FILE_FILEPATH + "=" + fileWifiWeb.getPath());
        }
    }

    public void delectFile(FileWifiWebDes.FileWifiWeb fileWifiWeb){
        getFils(ip + "?" + WiFiShareServerConfig.HOST_PARAM_FILE_DELECT + "=" + fileWifiWeb.getPath());
    }


    public void changeTitle(FileWifiWebDes.FileWifiWeb fileWifiWeb){
        fileWifiWebList.add(fileWifiWeb);
        fileScanTitlesAdapter.notifyDataSetChanged();
        fileDirDes.scrollToPosition(fileScanTitlesAdapter.getItemCount()-1);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.i("...........","exit"+fileWifiWebList.size());
            if(fileWifiWebList.size()>1){
                changeTitleWithPostaion(fileWifiWebList.size()-2);
                return true;
            }else{
                finish();
                return false;
            }
        }
        return false;
    }

}
